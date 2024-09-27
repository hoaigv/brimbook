import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./BookDetail.module.scss";

import { MessageIcon, LikeIcon } from "~/components/Icons";
import Image from "~/components/Image";
import Comment from "~/components/Comment";
import Button from "~/components/Button";
import CommentInput from "~/components/CommentInput";
import * as BookAPI from "~/apis/book";
import * as Comments from "~/apis/comment";

const cx = classNames.bind(styles);

function BookDetail() {
  const param = useParams();
  const [toggleState, setToggleState] = useState(1);
  const [book, setBook] = useState({
    result: {
      category: {
        id: "",
        name: "",
      },
      description: "",
      id: null,
      image_url: "",
      publishedDate: "",
      title: "",
      total_likes: null,
      total_reads: null,
      user: {
        firstName: "",
        lastName: "",
        image_url: "",
      },
    },
  });
  const [comments, setComments] = useState({
    result: [],
  });

  useEffect(() => {
    BookAPI.getOne(param.id, setBook);
  }, [param]);

  useEffect(() => {
    Comments.getAll(param.id, setComments);
  }, [param]);

  console.log(comments);

  return (
    <>
      <title>{book.result.title} | BrimBook</title>
      <div className={cx("wrapper")}>
        <div className={cx("inner")}>
          <image className={cx("book-image")}>
            <Image
              src={book.result.image_url}
              alt="BookImage"
              width={390}
              height={560}
              className={cx("image")}
            />
          </image>
          <div className={cx("book")}>
            <p className={cx("book-title")}>{book.result.title}</p>
            <div className={cx("book-info")}>
              <div className={cx("review-like")}>
                <div className={cx("review")}>
                  <MessageIcon className={cx("icon")} width="27px" height="27px" />
                  <p className={cx("text")}>{book.result.reads || 0} Reviews</p>
                </div>
                <div className={cx("review")}>
                  <LikeIcon className={cx("icon")} width="27px" height="27px" />
                  <p className={cx("text")}>{book.result.total_likes || 0} Likes</p>
                </div>
              </div>
              <Button type2 sx={{ width: "120px", height: "35px" }}>
                {book.result.category.name}
              </Button>
            </div>
            <div className={cx("book-author")}>
              <div className={cx("bookdetail")}>
                <div className={cx("book-detail")}>
                  <Image
                    src={book.result.user.image_url}
                    alt="author-img"
                    width={50}
                    height={50}
                    className={cx("author-img")}
                  />
                  <div className={cx("detail")}>
                    <span className={cx("text1")}>Writen by</span>
                    <br />
                    <span className={cx("text2")}>
                      {book.result.user.firstName} {book.result.user.lastName}
                    </span>
                  </div>
                </div>
                <div className={cx("detail")}>
                  <span className={cx("text1")}>Create day</span>
                  <br />
                  <span className={cx("text2")}>{book.result.publishedDate}</span>
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
                <div className={cx("content", toggleState === 1 && "active-content")}>
                  <div className={cx("description")}>{book.result.description}</div>
                </div>
                <div className={cx("content", toggleState === 2 && "active-content")}>
                  <div className={cx("comments")}>
                    <h5 className={cx("text")}>Showing {comments.result.length} reviews</h5>
                    {comments.result.map((comments) => (
                      <Comment key={comments.id} comments={comments} />
                    ))}
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
