import React, { useState, useEffect } from "react";
import { Table, Button, Modal, Form, Input, Space, DatePicker, Upload, message } from "antd";
import { EditOutlined, DeleteOutlined, PlusOutlined } from "@ant-design/icons";
import * as USERAPI from "~/apis/user";
import Radio from "antd/es/radio/radio";

const UserManagement = () => {
  const [users, setUsers] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingUser, setEditingUser] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [form] = Form.useForm();
  const [isDeleteModalVisible, setIsDeleteModalVisible] = useState(false);
  const [userToDelete, setUserToDelete] = useState(null);
  const [notification, setNotification] = useState();

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

  useEffect(() => {
    USERAPI.getAll(setUsers);
  }, [notification]);

  const showModal = (user = null) => {
    setEditingUser(user);
    setIsModalVisible(true);
    if (user) {
      form.setFieldsValue(user);
    } else {
      form.resetFields();
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
      // Giả lập API call để xóa người dùng
      setTimeout(() => {
        setUsers(users.filter((user) => user.id !== userToDelete.id));
        setIsDeleteModalVisible(false);
        setUserToDelete(null);
      }, 1000);
    }
  };

  const columns = [
    {
      title: "Tên",
      dataIndex: "username",
      key: "username",
      render: (text, record) => record?.username || "N/A",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      render: (text, record) => record?.email || "N/A",
    },
    {
      title: "Ngày tạo",
      dataIndex: "createdAt",
      key: "createdAt",
      sorter: (a, b) => new Date(b.createdAt) - new Date(a.createdAt),
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

  const filteredUsers = users.filter(
    (user) =>
      user?.username?.toLowerCase().includes(searchText.toLowerCase()) ||
      user?.email?.toLowerCase().includes(searchText.toLowerCase()),
  );
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
      <Table columns={columns} dataSource={filteredUsers} rowKey="id" />
      <Modal
        title={editingUser ? "Sửa người dùng" : "Thêm người dùng mới"}
        open={isModalVisible}
        onOk={handleOk}
        onCancel={() => setIsModalVisible(false)}
      >
        <i>{notification}</i>
        <Form form={form} layout="vertical" onClick={handleEnterValue}>
          <Form.Item
            name="username"
            label="User Name"
            rules={[
              { required: true, message: "Vui lòng nhập tên" },
              {
                validator: (_, value) => {
                  if (!value) {
                    return Promise.resolve();
                  }
                  const userNameRegex = /^[A-Za-z\d@$!%*#?&]{6,}$/;

                  if (!userNameRegex.test(value)) {
                    return Promise.reject(
                      new Error(
                        "Tên đăng nhập phải hơn 6 ký tự , không khoảng trắng (vd :'exam112')",
                      ),
                    );
                  }

                  return Promise.resolve();
                },
              },
            ]}
          >
            <Input />
          </Form.Item>
          {!editingUser && (
            <Form.Item
              name="password"
              label="Password"
              rules={[
                { required: true, message: "Vui lòng nhập mật khẩu " },
                {
                  validator: (_, value) => {
                    if (!value) {
                      return Promise.resolve();
                    }
                    const passwordRegex =
                      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{6,}$/;

                    if (!passwordRegex.test(value)) {
                      return Promise.reject(
                        new Error("Mật khẩu phải có chữ cái, số, ký tự đặc biệt (vd:example@a)"),
                      );
                    }

                    return Promise.resolve();
                  },
                },
              ]}
            >
              <Input />
            </Form.Item>
          )}
          <Form.Item
            name="email"
            label="Email"
            rules={[
              { required: true, message: "Vui lòng nhập email" },
              { type: "email", message: "Email không hợp lệ" },
              {
                validator: (_, value) => {
                  if (value && /^\d/.test(value)) {
                    return Promise.reject(new Error("Email không được bắt đầu bằng số"));
                  }
                  return Promise.resolve();
                },
              },
            ]}
          >
            <Input />
          </Form.Item>
          {editingUser && (
            <Form.Item name="gender" label="Gender">
              <Radio.Group>
                <Radio value="MALE">Male</Radio>
                <Radio value="FEMALE">Female</Radio>
              </Radio.Group>
            </Form.Item>
          )}
          {editingUser && (
            <Form.Item
              name="firstName"
              label="First Name"
              rules={[
                {
                  validator: (_, value) => {
                    if (!value) {
                      return Promise.resolve();
                    }
                    const firstNameRegex = /^[A-Za-z]+$/;
                    if (!firstNameRegex.test(value)) {
                      return Promise.reject(
                        new Error("Họ chỉ bao gồm chữ cái và không chứa khoảng trắng !"),
                      );
                    }

                    return Promise.resolve();
                  },
                },
              ]}
            >
              <Input />
            </Form.Item>
          )}
          {editingUser && (
            <Form.Item
              name="lastName"
              label="Last Name"
              rules={[
                {
                  validator: (_, value) => {
                    if (!value) {
                      return Promise.resolve();
                    }
                    const lastNameRegex = /^[A-Za-z]+$/;
                    if (!lastNameRegex.test(value)) {
                      return Promise.reject(
                        new Error("Tên chỉ bảo gồm chữ và không chứa khoảng trắng !"),
                      );
                    }

                    return Promise.resolve();
                  },
                },
              ]}
            >
              <Input />
            </Form.Item>
          )}
          {editingUser && (
            <Form.Item
              name="phone"
              label="Phone Number"
              rules={[
                {
                  validator: (_, value) => {
                    if (!value) {
                      return Promise.resolve();
                    }
                    const phoneRegex = /^\d{10,15}$/;
                    if (!phoneRegex.test(value)) {
                      return Promise.reject(
                        new Error("Tên chỉ bảo gồm chữ và không chứa khoảng trắng !"),
                      );
                    }

                    return Promise.resolve();
                  },
                },
              ]}
            >
              <Input />
            </Form.Item>
          )}
          {editingUser && (
            <Form.Item name="birthDate" label="Birth Date">
              <DatePicker />
            </Form.Item>
          )}
          {editingUser && (
            <Form.Item name="role" label="Role">
              <Radio.Group>
                <Radio value="ADMIN">Admin</Radio>
                <Radio value="USER">user</Radio>
              </Radio.Group>
            </Form.Item>
          )}
          {editingUser && (
            <Form.Item
              name="image"
              label="Image"
              valuePropName="fileList"
              getValueFromEvent={(e) => e && e.fileList}
            >
              <Upload
                listType="picture-card" // Hiển thị ảnh dạng thẻ  // Danh sách file hiện tại
                onChange={handleChange} // Xử lý sự kiện khi file được upload
                beforeUpload={beforeUpload} // // Không tải lên tự động, chỉ lưu local
                multiple={false} // Chỉ cho phép upload 1 file
              >
                {fileList.length === 1 ? null : (
                  <div>
                    <PlusOutlined />
                    <div>Upload</div>
                  </div>
                )}
              </Upload>
            </Form.Item>
          )}
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
