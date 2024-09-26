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

export const getAll = async (setUsers) => {
  try {
    const token = localStorage.getItem("userToken");
    console.log("Token:", token);
    if (!token) {
      throw new Error("Không tìm thấy token xác thực");
    }
    const response = await axios.get(`${BASE_URL}/api/admin/users`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (response.data && response.data.result) {
      // Xử lý ngày tháng cho mỗi người dùng trước khi cập nhật state
      const processedUsers = response.data.result.map((user) => ({
        ...user,
        createdAt: extractDate(user.createdAt),
        updatedAt: extractDate(user.updatedAt),
      }));
      setUsers(processedUsers);
    } else {
      throw new Error("Dữ liệu không hợp lệ từ server");
    }
  } catch (error) {
    console.error("Lỗi khi lấy danh sách người dùng:", error);
    if (error.response) {
      console.error("Mã trạng thái:", error.response.status);
      console.error("Dữ liệu lỗi:", error.response.data);
    }
    // Thêm xử lý lỗi cụ thể cho lỗi 400
    if (error.response && error.response.status === 400) {
      console.error("Lỗi 400 Bad Request: Yêu cầu không hợp lệ");
      // Có thể thêm xử lý đặc biệt cho lỗi 400 ở đây
    }
    // Gọi hàm xử lý lỗi (nếu được cung cấp)
    // if (typeof setError === 'function') {
    //   setError("Không thể tải danh sách người dùng. Vui lòng thử lại sau.");
    // }
  }
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
