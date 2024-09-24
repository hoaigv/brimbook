import React, { useState, useEffect } from "react";
import { Table, Button, Modal, Form, Input, Space, Popconfirm } from "antd";
import { EditOutlined, DeleteOutlined, PlusOutlined } from "@ant-design/icons";

const UserManagement = () => {
  const [users, setUsers] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingUser, setEditingUser] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [form] = Form.useForm();
  const [isDeleteModalVisible, setIsDeleteModalVisible] = useState(false);
  const [userToDelete, setUserToDelete] = useState(null);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = () => {
    // Giả lập API call
    setTimeout(() => {
      setUsers([
        { id: 1, name: "Nguyễn Văn A", email: "nguyenvana@example.com", createdAt: "2023-05-01" },
        { id: 2, name: "Trần Thị B", email: "tranthib@example.com", createdAt: "2023-05-02" },
        { id: 3, name: "Lê Văn C", email: "levanc@example.com", createdAt: "2023-05-03" },
      ]);
    }, 1000);
  };

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
    form.validateFields().then((values) => {
      if (editingUser) {
        // Giả lập API call để cập nhật người dùng
        setTimeout(() => {
          setUsers(
            users.map((user) => (user.id === editingUser.id ? { ...user, ...values } : user)),
          );
          setIsModalVisible(false);
        }, 1000);
      } else {
        // Giả lập API call để thêm người dùng mới
        setTimeout(() => {
          const newUser = {
            ...values,
            id: Date.now(),
            createdAt: new Date().toISOString().split("T")[0],
          };
          setUsers([...users, newUser]);
          setIsModalVisible(false);
        }, 1000);
      }
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
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
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
      user.name.toLowerCase().includes(searchText.toLowerCase()) ||
      user.email.toLowerCase().includes(searchText.toLowerCase()),
  );

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
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={() => setIsModalVisible(false)}
      >
        <Form form={form} layout="vertical">
          <Form.Item
            name="name"
            label="Tên"
            rules={[{ required: true, message: "Vui lòng nhập tên" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="email"
            label="Email"
            rules={[
              { required: true, message: "Vui lòng nhập email" },
              { type: "email", message: "Email không hợp lệ" },
            ]}
          >
            <Input />
          </Form.Item>
        </Form>
      </Modal>
      <Modal
        title="Xác nhận xóa người dùng"
        visible={isDeleteModalVisible}
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
