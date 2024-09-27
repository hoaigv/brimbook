import { axios } from "./http";

export const loginUser = async (formData) => {
  try {
    const response = await axios.post(`/auth/login`, formData);
    if (response.data && response.data.result.token) {
      window.location.replace("/");
      localStorage.setItem("userToken", response.data.result.token);
    }
  } catch (err) {
    console.log(err);
  }
};

export const getAll = async (setUsers) => {
  await axios
    .get(`api/admin/users`)
    .then((res) => setUsers(res.data.result))
    .catch((err) => console.log(err));
};

export const registerUser = async (formData, navigate) => {
  await axios
    .post(`api/users/sign-up`, formData)
    .then((res) => res.data)
    .then(() => {
      navigate("/login");
    })
    .catch((err) => console.log(err));
};

export const registerUserByAdmin = async (user, setNotification) => {
  await axios
    .post(`api/admin/users/sign-up`, user)
    .then((res) => {
      let data = res?.data?.message || res?.response?.data?.message;
      setNotification(data);
    })
    .catch((err) => {
      let messageError = err.response?.data?.message || "errror not cat";
      setNotification(messageError);
    });
};

export const logoutUser = () => {
  localStorage.removeItem("userToken");
  window.location.replace("/login");
};

export const getUser = async (setUser) => {
  await axios
    .get(`api/users/me`)
    .then((res) => {
      setUser(res.data);
    })
    .catch((err) => console.log(err));
};

export const update = async (formData) => {
  await axios
    .put(`api/users/update`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .catch((err) => console.log(err));
};
