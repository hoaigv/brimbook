import { axios } from "./http";

export const getAll = async (param, setComment) => {
  await axios
    .get(`api/comments/${param}`)
    .then((res) => {
      setComment(res.data);
    })
    .catch((err) => console.log(err));
};
