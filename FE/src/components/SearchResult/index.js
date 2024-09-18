import { NavLink } from "react-router-dom";
import classNames from "classnames/bind";
import styles from "./SearchResult.module.scss";

import Image from "~/components/Image";

const cx = classNames.bind(styles);

function SearchBookItem({ result }) {
    return (
        <NavLink className={cx("wrapper")} to={`/books/${result.isbn13}`}>
            <Image
                className={cx("book-img")}
                src={result.image}
                alt="Book"
                width={40}
                height={70}
            />
            <div className={cx("info")}>
                <p className={cx("title")}>{result.title}</p>
                <p className={cx("author")}>{result.subtitle}</p>
            </div>
        </NavLink>
    );
}

export default SearchBookItem;
