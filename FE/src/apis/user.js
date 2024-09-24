import axios from "axios";
import { BASE_URL } from "~/utils/constants";

export const loginUser = async (user, navigate) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/login`, user);
    if (response.data && response.data.result.token) {
      localStorage.setItem("userToken", response.data.result.token);
      navigate("/");
    }
  } catch (err) {
    console.log(err);
  }
};

export const registerUser = async (user, navigate) => {
  await axios
    .post(`${BASE_URL}/api/users/sign-up`, user)
    .then((res) => res.data)
    .then(() => {
      navigate("/login");
    })
    .catch((err) => console.log(err));
};

export const logoutUser = (navigate) => {
  localStorage.removeItem("userToken");
  navigate("/login");
};
