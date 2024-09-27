import { Link } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./BookItem.module.scss";

import Image from "~/components/Image";

const cx = classNames.bind(styles);
function BookItem1({ result }) {
  console.log(result.image_url);
  return (
    <Link to={`/books/${result.id}`} className={cx("book-item-1")}>
      {/* <div className={cx("img")}>
        <Image
          src={result.image}
          //   src="https://thanhnien.mediacdn.vn/Uploaded/minhnguyet/2022_05_08/bia-sach2-9886.jpg"
          alt=""
          width="230px"
          height="320px"
          className={cx("img-book")}
        />
      </div>
      <p className={cx("title")} title={result.title}>
        {result.title}
      </p>
      <p className={cx("author")}>{result.user.username}</p>
      <p className={cx("categories")}>{result.category.name}</p> */}

      <section class="cards-wrapper">
        <div class="card-grid-space">
          <Link
            to={`/books/${result.id}`}
            className={cx("card")}
            style={{ backgroundImage: `url(${result.image_url})` }}
          >
            <div className="card-content">
              <h1>{result.title}</h1>
              <p>{result.description}</p>
              <div class="date">{result.publishedDate}</div>
              <div class="tags">
                <div class="tag">{result.category.name}</div>
              </div>
            </div>
          </Link>
        </div>
      </section>
    </Link>
  );
}

export default BookItem1;
