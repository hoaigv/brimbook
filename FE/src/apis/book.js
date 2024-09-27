import { axios } from "./http";
import { BASE_URL } from "~/utils/constants";

export const getAll = async (query, currentButton, setSearchResult) => {
  await axios
    .get(`https://api.itbook.store/1.0/search/${query}/${currentButton}`)
    .then((res) => {
      setSearchResult(res.data.books);
    })
    .catch((err) => console.log(err));
};

export const getOne = async (param, setBook) => {
  await axios
    .get(`api/books/${param}`)
    .then((res) => {
      setBook(res.data);
    })
    .catch((err) => console.log(err));
};

export const update = async (formData) => {
  await axios
    .post(`api/books`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .catch((err) => console.log(err));
};

export const getAllBooks = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/api/books/getAll`, {
      withCredentials: false, // Đảm bảo không gửi cookie xác thực
    });
    console.log(response.data.result);
    return response.data.result;
  } catch (error) {
    console.error("Lỗi khi lấy tất cả sách:", error.response?.data || error.message);
    throw error;
  }
};
