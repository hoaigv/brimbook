import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./BookDetail.module.scss";

import { MessageIcon, LikeIcon } from "~/components/Icons";
import Image from "~/components/Image";
import CustomerReviews from "~/components/CustomerReviews";
import Comment from "~/components/Comment";
import Button from "~/components/Button";
import * as BookAPI from "~/apis/book";
import { category2 } from "~/_mock/category";
import CommentInput from "~/components/CommentInput";

const cx = classNames.bind(styles);

function BookDetail() {
  const [toggleState, setToggleState] = useState(1);
  const [book, setBook] = useState([]);
  const param = useParams();

  useEffect(() => {
    BookAPI.getOne(param.isbn13, setBook);
  }, [param]);

  return (
    <>
      <title>{book.title} | BrimBook</title>
      <div className={cx("wrapper")}>
        <div className={cx("inner")}>
          <image className={cx("book-image")}>
            <Image
              // src={book.image}
              src="https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/hinh-nen-dien-thoai-35.jpg"
              alt="BookImage"
              width={390}
              height={560}
              className={cx("image")}
            />
          </image>
          <div className={cx("book")}>
            <p className={cx("book-title")} title={book.title}>
              {book.title}
            </p>
            <div className={cx("book-info")}>
              <div className={cx("review-like")}>
                <div className={cx("review")}>
                  <MessageIcon className={cx("icon")} width="27px" height="27px" />
                  <p className={cx("text")}>235 Reviews</p>
                </div>
                <div className={cx("review")}>
                  <LikeIcon className={cx("icon")} width="27px" height="27px" />
                  <p className={cx("text")}>123 Likes</p>
                </div>
              </div>
              <Button type2 sx={{ width: "120px", height: "35px" }}></Button>
            </div>
            <div className={cx("book-author")}>
              <div className={cx("bookdetail")}>
                <div className={cx("book-detail")}>
                  <Image
                    src={""}
                    alt="author-img"
                    width={50}
                    height={50}
                    className={cx("author-img")}
                  />
                  <div className={cx("detail")}>
                    <span className={cx("text1")}>Writen by</span>
                    <br />
                    <span className={cx("text2")}>{book.authors}</span>
                  </div>
                </div>
                <div className={cx("detail")}>
                  <span className={cx("text1")}>Create day</span>
                  <br />
                  <span className={cx("text2")}>{book.year}</span>
                </div>
              </div>
              <div className={cx("like-btn")}>
                <Button outline sx={{ width: 52, height: 52 }} startIcon={<LikeIcon />} />
              </div>
            </div>
            <div className={cx("book-review")}>
              <div className={cx("bloc-tabs")}>
                <div
                  className={cx("tabs", toggleState === 1 && "active-tabs")}
                  onClick={() => setToggleState(1)}
                >
                  Description
                </div>
                <div
                  className={cx("tabs", toggleState === 2 && "active-tabs")}
                  onClick={() => setToggleState(2)}
                >
                  Customer Reviews
                </div>
              </div>
              <div className={cx("content-tabs")}>
                <div className={cx("content", toggleState === 1 && "active-content")}></div>
                <div className={cx("content", toggleState === 2 && "active-content")}>
                  <CustomerReviews result={book.rating} />
                  <div className={cx("comments")}>
                    <h5 className={cx("text")}>Showing 4 of 20 reviews</h5>
                    <Comment />
                    <Comment />
                    <Comment />
                  </div>
                  <div className={cx("comment-input")}>
                    <CommentInput />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default BookDetail;
