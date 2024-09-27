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
  if (!config.url.includes("auth/sign-up") && !config.url.includes("auth/login")) {
    config.headers = { ...config.headers, Authorization: `Bearer ${token}` };
  }

  return config;
});
