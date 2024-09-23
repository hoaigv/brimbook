import axios from "axios";
import { BASE_URL } from "~/utils/constants";

export const loginUser = async (user, navigate) => {
  await axios
    .post(`${BASE_URL}/auth/login`, user)
    .then((res) => res.data)
    .then(() => {
      navigate("/");
    })
    .catch((err) => console.log(err));
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
