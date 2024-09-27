import { Link } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./SearchBookItem.module.scss";

import Image from "~/components/Image";

const cx = classNames.bind(styles);

function SearchBookItem({ result }) {
  return (
    <div className={cx("search-book-item")}>
      <Link to={`/books/${result.id}`} className={cx("book-title")}>
        <Image
          //   src={result.image}
          src=""
          alt="BookImage"
          width={200}
          height={270}
          className={cx("image")}
        />
        {/* <p className={cx("title")}>{result.title }</p> */}
        <p className={cx("categories")}>BIOGRAPHY</p>
      </Link>
    </div>
  );
}

export default SearchBookItem;
