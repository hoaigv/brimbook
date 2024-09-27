import { BASE_URL } from "~/utils/constants";
import { axios } from "./http";

export const loginUser = async (user) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/login`, user);
    if (response.data && response.data.result.token) {
      localStorage.setItem("userToken", response.data.result.token);
      localStorage.setItem("userRole", response.data.result.role);
      if (response.data.result.role === "ADMIN") {
        window.location.href = "/user-management";
      } else {
        window.location.href = "/";
      }
    }
  } catch (err) {}
};

export const extractDate = (dateTimeString) => {
  return dateTimeString.split("T")[0];
};

export const getAll = (callback, page = 0, size = 10) => {
  const token = localStorage.getItem("userToken");
  if (!token) {
    console.error("Không tìm thấy token xác thực");
    return;
  }

  axios
    .get(`${BASE_URL}/api/admin/users`, {
      params: {
        page: page,
        size: size,
      },
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    .then((response) => {
      callback(response.data);
    })
    .catch((error) => {
      console.error("Lỗi khi gọi API:", error);
      if (error.response && error.response.status === 401) {
        console.error("Token không hợp lệ hoặc đã hết hạn");
        // Có thể thêm xử lý đăng xuất hoặc chuyển hướng đến trang đăng nhập ở đây
      }
    });
};

export const registerUser = async (user, navigate) => {
  try {
    const response = await axios.post(`${BASE_URL}/api/users/sign-up`, user);

    navigate("/login");
  } catch (error) {
    if (error.response) {
      console.error("Lỗi đăng ký:", error.response.data.message);
      // Hiển thị thông báo lỗi cho người dùng
    } else {
      console.error("Lỗi:", error.message);
    }
  }
};

export const registerUserByAdmin = async (user, setNotification) => {
  console.log("user", user);
  try {
    const token = localStorage.getItem("userToken");
    if (!token) {
      throw new Error("Không tìm thấy token xác thực");
    }
    const response = await axios.post(`${BASE_URL}/api/admin/users/sign-up`, user, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    let data = response.data.message || "Thêm người dùng thành công";
    setNotification({ success: true, message: data });
    return { success: true, user: response.data.result };
  } catch (error) {
    let messageError = error.response?.data?.message || "Lỗi không xác định khi thêm người dùng";
    setNotification({ success: false, message: messageError });
    return { success: false, message: messageError };
  }
};

export const logoutUser = async (navigate) => {
  try {
    // Xóa token
    localStorage.removeItem("userToken");

    // Xóa các thông tin người dùng khác nếu cần
    localStorage.removeItem("userRole");

    // Đợi một chút trước khi chuyển hướng
    await new Promise((resolve) => setTimeout(resolve, 100));

    // Chuyển hướng đến trang đăng nhập
    navigate("/login");

    console.log("Đăng xuất thành công");
  } catch (error) {
    console.error("Lỗi khi đăng xuất:", error);
    // Xử lý lỗi ở đây, ví dụ: hiển thị thông báo cho người dùng
  }
};

export const updateUserByAdmin = async (userId, userData, image) => {
  try {
    const token = localStorage.getItem("userToken");
    if (!token) {
      throw new Error("Không tìm thấy token xác thực");
    }

    const formData = new FormData();
    formData.append("data", JSON.stringify(userData));
    if (image) {
      formData.append("image", image);
    }

    const response = await axios.put(`${BASE_URL}/api/admin/users/${userId}`, formData, {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "multipart/form-data",
      },
    });

    let message = response.data.message || "Cập nhật người dùng thành công";
    return { success: true, message: message, user: response.data.result };
  } catch (error) {
    console.error("Lỗi khi cập nhật người dùng:", error);
    let errorMessage =
      error.response?.data?.message || "Lỗi không xác định khi cập nhật người dùng";
    return { success: false, message: errorMessage };
  }
};

export const deleteUser = async (userId, setNotification) => {
  try {
    const token = localStorage.getItem("userToken");
    if (!token) {
      throw new Error("Không tìm thấy token xác thực");
    }
    const response = await axios.delete(`${BASE_URL}/api/admin/users/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    let data = response.data.message || "Xóa người dùng thành công";
    setNotification({ success: true, message: data });
    return { success: true, message: data };
  } catch (error) {
    let messageError = error.response?.data?.message || "Lỗi không xác định khi xóa người dùng";
    setNotification({ success: false, message: messageError });
    return { success: false, message: messageError };
  }
};

export const getUser = async (setUser) => {
  try {
    const token = localStorage.getItem("userToken");
    if (!token) {
      throw new Error("Không tìm thấy token xác thực");
    }

    const response = await axios.get(`${BASE_URL}/api/users/me`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    setUser(response.data);
  } catch (error) {
    console.error("Lỗi khi lấy thông tin người dùng:", error.message);
    // Xử lý lỗi ở đây, ví dụ: thông báo cho người dùng hoặc chuyển hướng đến trang đăng nhập
  }
};

export const update = async (formData) => {
  await axios
    .put(`api/users/update`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .catch((err) => console.log(err));
  await axios.put(`api/users/update`, formData).catch((err) => console.log(err));
};

export const getUserById = async (userId, setUser, setNotification) => {
  try {
    const token = localStorage.getItem("userToken");
    if (!token) {
      throw new Error("Không tìm thấy token xác thực");
    }

    const response = await axios.get(`${BASE_URL}/api/admin/users/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    setUser(response.data.result);
    return { success: true, user: response.data.result };
  } catch (error) {
    let messageError =
      error.response?.data?.message || "Lỗi không xác định khi lấy thông tin người dùng";
    setNotification({ success: false, message: messageError });
    return { success: false, message: messageError };
  }
};

export const getLike = async (param) => {
  try {
    const result = await axios.get(`api/users/like/${param}`);

    return result.data.result;
  } catch (err) {
    console.log(err);
  }
};

export const like = async (param) => {
  try {
    await axios.post(`api/users/like/${param}`);
  } catch (err) {
    console.log(err);
  }
};

export const unlike = async (param) => {
  try {
    await axios.delete(`api/users/like/${param}`);
  } catch (err) {
    console.log(err);
  }
};

export const listBookLike = async (page, size, setBook) => {
  try {
    await axios.get(`api/users/list-book-like?page=${page}&size=${size}`).then((res) => {
      setBook(res.data.result);
    });
  } catch (err) {
    console.log(err);
  }
};

export const listBookRead = async (page, size, setBook) => {
  try {
    await axios.get(`api/users/list-book-read?page=${page}&size=${size}`).then((res) => {
      setBook(res.data.result);
    });
  } catch (err) {
    console.log(err);
  }
};

export const readBook = async (param, setBook) => {
  try {
    await axios.get(`api/users/read/${param}`).then((res) => {
      setBook(res.data.result);
    });
  } catch (err) {
    console.log(err);
  }
};
