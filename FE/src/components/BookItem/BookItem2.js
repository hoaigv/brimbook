import { Link } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./BookItem.module.scss";

// import { StoreIcon } from "~/components/Icons";
// import Button from "~/components/Button";
import Image from "~/components/Image";

const cx = classNames.bind(styles);

function BookItem1({ result }) {
  return (
    <Link to={`/books/${result.id}`} className={cx("book-item-2")}>
      <Image src={result.image_url} alt="" className={cx("img-book")} />
      <div className={cx("inner")}>
        <p className={cx("title")}>{result.title}</p>
        <p className={cx("categories")}>{result.category.name}</p>
        <p className={cx("author")}>Author: {result.user.username}</p>
      </div>
    </Link>
  );
}

export default BookItem1;
