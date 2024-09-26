import axios from "axios";
import { BASE_URL } from "~/utils/constants";

export const loginUser = async (user, navigate) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/login`, user);
    if (response.data && response.data.result.token) {
      localStorage.setItem("userToken", response.data.result.token);
      localStorage.setItem("userRole", response.data.result.role);
      if (response.data.result.role === "ADMIN") {
        navigate("/user-management");
      } else {
        navigate("/");
      }
    }
  } catch (err) {
    console.log(err);
  }
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
      console.log("Phản hồi API:", response.data);
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

const isTokenExpired = (token) => {
  try {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return payload.exp < Date.now() / 1000;
  } catch (error) {
    return true;
  }
};

const checkToken = () => {
  const token = localStorage.getItem("userToken");
  if (!token || isTokenExpired(token)) {
    throw new Error("Token không hợp lệ hoặc đã hết hạn");
  }
  return token;
};

export const registerUser = async (user, navigate) => {
  try {
    const token = checkToken();

    const response = await axios.post(`${BASE_URL}/api/users/sign-up`, user, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log("Đăng ký thành công:", response.data);
    navigate("/");
  } catch (error) {
    if (error.response && error.response.data.code === 1006) {
      console.error("Lỗi xác thực người dùng:", error.response.data.message);
      localStorage.removeItem("userToken");
      navigate("/login");
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

export const logoutUser = (navigate) => {
  localStorage.removeItem("userToken");
  navigate("/login");
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
