import { Link } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./BookItem.module.scss";

// import { StoreIcon } from "~/components/Icons";
// import Button from "~/components/Button";
import Image from "~/components/Image";

const cx = classNames.bind(styles);

function BookItem1({ result }) {
  return (
    <Link to={`/books/${result.isbn13}`} className={cx("book-item-2")}>
      <Image
        src={result.image}
        // src="https://image.nhandan.vn/w800/Uploaded/2024/genaghlrgybna/2023_08_16/bs-giai1a-982.jpg.webp"
        alt=""
        className={cx("img-book")}
      />
      <div className={cx("inner")}>
        <p className={cx("title")}>{result.title}</p>
        <p className={cx("categories")}>{result.category.name}</p>
        <p className={cx("author")}>Author: {result.user.username}</p>
      </div>
    </Link>
  );
}

export default BookItem1;
