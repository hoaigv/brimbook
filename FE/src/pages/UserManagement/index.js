import React, { useState, useEffect } from "react";
import { Table, Button, Modal, Form, Input, Space, DatePicker, Upload, message } from "antd";
import { EditOutlined, DeleteOutlined, PlusOutlined } from "@ant-design/icons";
import * as USERAPI from "~/apis/user";
import Radio from "antd/es/radio/radio";
import moment from "moment";

const UserManagement = () => {
  const [users, setUsers] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingUser, setEditingUser] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [form] = Form.useForm();
  const [isDeleteModalVisible, setIsDeleteModalVisible] = useState(false);
  const [userToDelete, setUserToDelete] = useState(null);
  const [notification, setNotification] = useState();
  const [pagination, setPagination] = useState({
    current: 1,
    pageSize: 10,
    total: 0,
    totalPages: 1,
  });

  const [fileList, setFileList] = useState([]);

  // Xử lý sự kiện khi người dùng upload ảnh
  const handleChange = ({ fileList: newFileList }) => {
    // Chỉ giữ lại file cuối cùng nếu có nhiều hơn 1 file
    if (newFileList.length > 1) {
      message.error("You can only upload one image!");
      return;
    }
    setFileList(newFileList);
  };

  const fetchUsers = (page, pageSize) => {
    console.log(`Đang fetch users với page=${page} và pageSize=${pageSize}`);
    USERAPI.getAll(
      (data) => {
        console.log("Dữ liệu nhận được từ API:", data);
        if (data && data.result) {
          setUsers(data.result);
          setPagination({
            current: data.page,
            pageSize: data.pageSize,
            total: data.totalResults,
            totalPages: data.totalPages,
          });
        } else {
          console.error("Dữ liệu không đúng định dạng:", data);
        }
      },
      page - 1,
      pageSize,
    );
  };

  useEffect(() => {
    fetchUsers(pagination.current, pagination.pageSize);
  }, [pagination.current, pagination.pageSize]);

  const showModal = (user = null) => {
    setEditingUser(user);
    setIsModalVisible(true);
    if (user) {
      // Đặt giá trị cho các trường form dựa trên thông tin người dùng
      form.setFieldsValue({
        username: user.username,
        email: user.email,
        gender: user.gender || undefined,
        firstName: user.firstName || undefined,
        lastName: user.lastName || undefined,
        phone: user.phone || undefined,
        birthDate: user.birthDate ? moment(user.birthDate) : undefined,
        role: user.role,
      });
      // Đặt fileList cho trường image nếu có
      if (user.image_url) {
        setFileList([
          {
            uid: "-1",
            name: "image.png",
            status: "done",
            url: user.image_url,
          },
        ]);
      } else {
        setFileList([]);
      }
    } else {
      form.resetFields();
      setFileList([]);
    }
  };

  const handleOk = () => {
    form
      .validateFields()
      .then((values) => {
        if (editingUser) {
          // Xử lý chỉnh sửa người dùng
          setUsers(
            users.map((user) => (user.id === editingUser.id ? { ...user, ...values } : user)),
          );
          setIsModalVisible(false);
        } else {
          // Xử lý thêm người dùng mới
          USERAPI.registerUserByAdmin(values, (notificationData) => {
            setNotification(notificationData.message);
            if (notificationData.success) {
              // Thêm người dùng mới vào danh sách
              if (notificationData.user) {
                setUsers((prevUsers) => [...prevUsers, notificationData.user]);
              }
              // Đóng modal
              setIsModalVisible(false);
              // Reset form
              form.resetFields();
              // Hiển thị thông báo thành công
              message.success("Tạo người dùng thành công");
            } else {
              // Hiển thị thông báo lỗi
              message.error(notificationData.message || "Có lỗi xảy ra khi tạo người dùng");
            }
          });
        }
      })
      .catch((error) => {
        console.error("Validation failed:", error);
      });
  };

  const showDeleteModal = (user) => {
    setUserToDelete(user);
    setIsDeleteModalVisible(true);
  };

  const handleDelete = () => {
    if (userToDelete) {
      USERAPI.deleteUser(userToDelete.id, (response) => {
        if (response.success) {
          setUsers(users.filter((user) => user.id !== userToDelete.id));
          setIsDeleteModalVisible(false);
          setUserToDelete(null);
          message.success("Người dùng đã được xóa thành công");
        } else {
          message.error("Có lỗi xảy ra khi xóa người dùng");
        }
      });
    }
  };

  const columns = [
    {
      title: "Tên",
      dataIndex: "username",
      key: "username",
      render: (text, record) => (
        <div
          style={{
            maxWidth: 150,
            overflow: "hidden",
            textOverflow: "ellipsis",
            whiteSpace: "nowrap",
          }}
        >
          {record?.username || "N/A"}
        </div>
      ),
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      render: (text, record) => (
        <div
          style={{
            maxWidth: 200,
            overflow: "hidden",
            textOverflow: "ellipsis",
            whiteSpace: "nowrap",
          }}
        >
          {record?.email || "N/A"}
        </div>
      ),
    },
    {
      title: "Ngày tạo",
      dataIndex: "createdAt",
      key: "createdAt",
      sorter: (a, b) => new Date(b.createdAt) - new Date(a.createdAt),
      render: (text) => USERAPI.extractDate(text),
    },
    {
      title: "Hành động",
      key: "action",
      render: (_, record) => (
        <Space size="middle">
          <Button icon={<EditOutlined />} onClick={() => showModal(record)}>
            Sửa
          </Button>
          <Button icon={<DeleteOutlined />} danger onClick={() => showDeleteModal(record)}>
            Xóa
          </Button>
        </Space>
      ),
    },
  ];

  const filteredUsers = Array.isArray(users)
    ? users.filter(
        (user) =>
          user?.username?.toLowerCase().includes(searchText.toLowerCase()) ||
          user?.email?.toLowerCase().includes(searchText.toLowerCase()),
      )
    : [];

  const handleEnterValue = () => {
    if (notification) {
      setNotification();
    }
  };
  const beforeUpload = (file) => {
    const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
    if (!isJpgOrPng) {
      message.error("You can only upload JPG/PNG file!");
    }
    return isJpgOrPng || Upload.LIST_IGNORE; // Nếu không đúng định dạng, ngăn việc upload file
  };
  return (
    <div>
      <Space style={{ marginBottom: 16, width: "100%", justifyContent: "flex-end" }}>
        <Input
          placeholder="Tìm kiếm theo tên hoặc email"
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
        />
        <Button type="primary" icon={<PlusOutlined />} onClick={() => showModal()}>
          Thêm người dùng
        </Button>
      </Space>
      <Table
        columns={columns}
        dataSource={filteredUsers}
        pagination={{
          ...pagination,
          showSizeChanger: true,
          showQuickJumper: true,
          showTotal: (total, range) =>
            `${range[0]}-${range[1]} của ${total} người dùng (Tổng ${pagination.totalPages} trang)`,
          onChange: (page, pageSize) => {
            fetchUsers(page, pageSize);
          },
        }}
      />
      <Modal
        title={editingUser ? "Sửa người dùng" : "Thêm người dùng mới"}
        open={isModalVisible}
        onOk={handleOk}
        onCancel={() => {
          setIsModalVisible(false);
          setEditingUser(null);
          form.resetFields();
          setFileList([]);
        }}
      >
        <i>{notification}</i>
        <Form form={form} layout="vertical" onClick={handleEnterValue}>
          <Form.Item name="username" label="User Name">
            <Input disabled={!!editingUser} />
          </Form.Item>
          <Form.Item
            name="email"
            label="Email"
            rules={
              [
                /* ... */
              ]
            }
          >
            <Input />
          </Form.Item>
          <Form.Item name="gender" label="Gender">
            <Radio.Group>
              <Radio value="MALE">Nam</Radio>
              <Radio value="FEMALE">Nữ</Radio>
            </Radio.Group>
          </Form.Item>
          <Form.Item name="firstName" label="First Name">
            <Input />
          </Form.Item>
          <Form.Item name="lastName" label="Last Name">
            <Input />
          </Form.Item>
          <Form.Item name="phone" label="Phone Number">
            <Input />
          </Form.Item>
          <Form.Item name="birthDate" label="Birth Date">
            <DatePicker />
          </Form.Item>
          <Form.Item name="role" label="Role">
            <Radio.Group>
              <Radio value="ADMIN">Admin</Radio>
              <Radio value="USER">User</Radio>
            </Radio.Group>
          </Form.Item>
          {/* Trường image */}
          <Form.Item
            name="image"
            label="Image"
            valuePropName="fileList"
            getValueFromEvent={(e) => e && e.fileList}
          >
            <Upload
              listType="picture-card"
              fileList={fileList}
              onChange={handleChange}
              beforeUpload={beforeUpload}
              multiple={false}
            >
              {fileList.length === 1 ? null : (
                <div>
                  <PlusOutlined />
                  <div>Tải lên</div>
                </div>
              )}
            </Upload>
          </Form.Item>
        </Form>
      </Modal>
      <Modal
        title="Xác nhận xóa người dùng"
        open={isDeleteModalVisible}
        onOk={handleDelete}
        onCancel={() => setIsDeleteModalVisible(false)}
        okText="Xóa"
        cancelText="Hủy"
      >
        <p>Bạn có chắc chắn muốn xóa người dùng này?</p>
      </Modal>
    </div>
  );
};

export default UserManagement;
