import { useState, useEffect } from "react";

import classNames from "classnames/bind";
import styles from "./Recent.module.scss";

import SearchBookItem from "~/components/SearchBookItem";
import * as UserAPI from "~/apis/user";

const cx = classNames.bind(styles);

function Recent() {
  const [listBook, setListBook] = useState([]);

  useEffect(() => {
    UserAPI.listBookRead(0, 10, setListBook);
  }, []);

  return (
    <div>
      <title>Recent Page | BrimBook</title>
      <div className={cx("wrapper")}>
        {listBook.map((item) => (
          <SearchBookItem result={item} />
        ))}
      </div>
    </div>
  );
}

export default Recent;
