import { axios } from "./http";

export const getAll = async (setCategory) => {
  await axios
    .get(`api/categories`)
    .then((res) => {
      setCategory(res.data.result);
    })
    .catch((err) => console.log(err));
};
