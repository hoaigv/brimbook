import { useState, useEffect } from "react";
import classNames from "classnames/bind";
import styles from "./Favorite.module.scss";

import SearchBookItem from "~/components/SearchBookItem";
import * as UserAPI from "~/apis/user";

const cx = classNames.bind(styles);

function Favorite() {
  const [listBookLike, setListBookLike] = useState([]);

  useEffect(() => {
    UserAPI.listBookLike(0, 10, setListBookLike);
  }, []);

  console.log(listBookLike);

  return (
    <div>
      <title>Favorite Page | BrimBook</title>
      <div className={cx("wrapper")}>
        {listBookLike.map((item) => (
          <SearchBookItem result={item} />
        ))}
      </div>
    </div>
  );
}

export default Favorite;
