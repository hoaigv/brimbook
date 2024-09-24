import { Link } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./BookItem.module.scss";

// import { PointIcon } from "~/component/Icons";
import Image from "~/components/Image";

const cx = classNames.bind(styles);

function BookItem1({ result }) {
    return (
        <Link to={`/books/${result.isbn13}`} className={cx("book-item-1")}>
            <div className={cx("img")}>
                <Image
                    // src={result.image}
                    src="https://thanhnien.mediacdn.vn/Uploaded/minhnguyet/2022_05_08/bia-sach2-9886.jpg"
                    alt=""
                    width="230px"
                    height="320px"
                    className={cx("img-book")}
                />
            </div>
            <p className={cx("title")} title={result.title}>
                {result.title}
            </p>
            <p className={cx("categories")}>ADVANTURE, SURVIVAL</p>
            {/* <div className={cx("node")}>
                <div className={cx("point-star")}>
                    <PointIcon />
                    <p>4.5</p>
                </div>
                <div className={cx("price")}>
                    <p className={cx("new-price")}>45.999</p>
                    <p className={cx("old-price")}>60.999</p>
                </div>
            </div> */}
        </Link>
    );
}

export default BookItem1;
