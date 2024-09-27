import Axios from "axios";
import { BASE_URL } from "~/utils/constants";

export const axios = Axios.create({
  baseURL: BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

axios.interceptors.request.use((config) => {
  const token = localStorage.getItem("userToken");
  if (
    !config.url.includes("api/users/sign-up") &&
    !config.url.includes("auth/login") &&
    !config.url.includes("api/books/getAll")
  ) {
    config.headers = { ...config.headers, Authorization: `Bearer ${token}` };
  }

  return config;
});
