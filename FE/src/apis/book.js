import { axios } from "./http";

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
