import { Link } from "react-router-dom";
import classNames from "classnames/bind";
import styles from "./SearchBookItem.module.scss";

import Image from "~/components/Image";
import { HeartIcon } from "~/components/Icons";
import Rating from "~/pages/components/Rating";

const cx = classNames.bind(styles);

function SearchBookItem({ result }) {
    return (
        <div className={cx("search-book-item")}>
            <Link className={cx("book-title")} to={`/books/${result.isbn13}`}>
                <Image
                    src={result.image}
                    // src=""
                    alt="BookImage"
                    width={200}
                    height={270}
                    className={cx("image")}
                />
                <p className={cx("title")}>{result.title}</p>
                <p className={cx("categories")}>BIOGRAPHY</p>
                <div className={cx("rating")}>
                    <Rating />
                </div>
            </Link>
            <button className={cx("favorite-btn")}>
                <HeartIcon />
            </button>
        </div>
    );
}

export default SearchBookItem;
