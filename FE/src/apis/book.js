import axios from "axios";
import { BASE_URL } from "~/utils/constants";

export const getAll = async (query, currentButton, setSearchResult) => {
  await axios
    .get(`${BASE_URL}/search/${query}/${currentButton}`)
    .then((res) => {
      setSearchResult(res.data.books);
    })
    .catch((err) => console.log(err));
};

export const getOne = async (param, setBook) => {
  await axios
    .get(`https://api.itbook.store/1.0/books/${param}`)
    .then((res) => {
      setBook(res.data);
    })
    .catch((err) => console.log(err));
};
