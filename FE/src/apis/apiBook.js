import axios from "axios";
import { BASE_URL } from "~/utils/constants";

export const getAll = async (query, currentButton, setSearchResult) => {
  await axios
    .get(`${BASE_URL}/search/${query}/${currentButton}`)
    .then((res) => {
      setSearchResult(res.data.books);
    })
    .catch((err) => alert(err));
};

export const getOne = async (id, setBook) => {
  await axios
    .get(`${BASE_URL}/books/${id}`)
    .then((res) => {
      setBook(res.data);
    })
    .catch((err) => alert(err));
};
