import axios from "axios";
import { BASE_URL } from "~/utils/constants";

// export const loginUser = async (user, navigate) => {
//   await axios
//     .post(`${BASE_URL}/auth/login`, user)
//     .then((res) => {
//       localStorage.setItem("userToken", res.data.token);
//       localStorage.setItem("userRole", res.data.role);
//       if (res.data.role === "ADMIN") {
//         navigate("/user-management");
//       } else {
//         navigate("/");
//       }
//     })
//     .catch((err) => console.log(err));
// };
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

export const getAll = async (setUsers) => {
  try {
    const token = localStorage.getItem("userToken");
    if (!token) {
      throw new Error("Không tìm thấy token xác thực");
    }
    const response = await axios.get(`${BASE_URL}/api/admin/users`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    setUsers(response.data.result);
  } catch (error) {
    if (error.response) {
      if (error.response.data.code === 9999) {
        console.error("Lỗi không xác định từ server:", error.response.data.message);
        // Xử lý lỗi cụ thể ở đây, ví dụ:
        // setError("Đã xảy ra lỗi không xác định. Vui lòng thử lại sau.");
      } else {
        console.error("Lỗi server:", error.response.data);
      }
      console.error("Mã trạng thái:", error.response.status);
    } else if (error.request) {
      console.error("Không nhận được phản hồi từ server:", error.request);
    } else {
      console.error("Lỗi:", error.message);
    }
    // Có thể thêm xử lý lỗi chung ở đây, ví dụ:
    // setUsers([]);
    // setError("Không thể tải danh sách người dùng. Vui lòng thử lại sau.");
  }
};

export const registerUser = async (user, navigate) => {
  await axios
    .post(`${BASE_URL}/api/users/sign-up`, user)
    .then((res) => res.data)
    .then(() => {
      navigate("/");
    })
    .catch((err) => console.log(err));
};

export const registerUserByAdmin = async (user, setNotification) => {
  await axios
    .post(`${BASE_URL}/api/admin/users/sign-up`, user)
    .then((res) => {
      let data = res?.data?.message || res?.response?.data?.message;
      setNotification(data);
    })
    .catch((err) => {
      let messageError = err.response?.data?.message || "errror not cat";
      setNotification(messageError);
    });
};

export const logoutUser = (navigate) => {
  localStorage.removeItem("userToken");
  navigate("/login");
};
