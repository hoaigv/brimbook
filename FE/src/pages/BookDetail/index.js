import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./BookDetail.module.scss";

import Image from "~/components/Image";
import Button from "~/components/Button";
import { HeartIcon, MessageIcon, LikeIcon } from "~/components/Icons";
import ListChapter from "./ListChapter";
import CustomerReviews from "../../components/CustomerReviews";
import Rating from "~/components/Rating";
import Comment from "~/components/Comment";

const cx = classNames.bind(styles);

function BookDetail() {
    const [toggleState, setToggleState] = useState(1);
    const [book, setBook] = useState([]);
    const param = useParams();

    useEffect(() => {
        const getBookDetail = async () => {
            const result = await fetch(
                `https://api.itbook.store/1.0/books/${param.isbn13}`
            );
            const data = await result.json();
            setBook(data);
        };
        getBookDetail();
    }, [param]);

    return (
        <div className={cx("wrapper")}>
            <title>{book.title} | BrimBook</title>
            <div className={cx("inner")}>
                <div className={cx("book-detail")}>
                    <div className={cx("image")}>
                        <Image
                            // src={book.image}
                            src="https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/hinh-nen-dien-thoai-35.jpg"
                            alt="BookImage"
                            width={390}
                            height={560}
                            className={cx("image")}
                        />
                    </div>
                    <div className={cx("book-info")}>
                        <p className={cx("title")} title={book.title}>
                            {book.title}
                        </p>
                        <div className={cx("rating-review-like")}>
                            <div className={cx("rating")}>
                                <Rating results={book.rating} />
                                <p className={cx("rating-text")}>
                                    {book.rating}
                                </p>
                            </div>
                            <div className={cx("review")}>
                                <MessageIcon
                                    className={cx("icon")}
                                    width="27px"
                                    height="27px"
                                />
                                <p className={cx("review-text")}>235 Reviews</p>
                            </div>
                            <div className={cx("like")}>
                                <LikeIcon
                                    className={cx("icon")}
                                    width="27px"
                                    height="27px"
                                />
                                <p className={cx("like-text")}>123 Likes</p>
                            </div>
                        </div>
                        <p className={cx("description")}>{book.desc}</p>
                        <div className={cx("information")}>
                            <Image
                                src={""}
                                alt="author-img"
                                width={50}
                                height={50}
                                className={cx("author-img")}
                            />
                            <div className={cx("info-list")}>
                                <div className={cx("info")}>
                                    <h5>Writen by</h5>
                                    <p>{book.authors}</p>
                                </div>
                                <div className={cx("info")}>
                                    <h5>Pubisher</h5>
                                    <p>{book.publisher}</p>
                                </div>
                                <div className={cx("info")}>
                                    <h5>Year</h5>
                                    <p>{book.year}</p>
                                </div>
                            </div>
                        </div>
                        <div className={cx("button")}>
                            <div className={cx("first-last-chapter")}>
                                <Button type1 to={"/books/:isbn13/:chapter"}>
                                    First Chapter
                                </Button>
                                <Button type2 to={"/books/:isbn13/:chapter"}>
                                    Last Chapter
                                </Button>
                            </div>
                            <button className={cx("favorite-btn")}>
                                <HeartIcon />
                            </button>
                        </div>
                    </div>
                </div>
                <div className={cx("book-review")}>
                    <div className={cx("chapter-review")}>
                        <div className={cx("bloc-tabs")}>
                            <div
                                className={cx(
                                    "tabs",
                                    toggleState === 1 && "active-tabs"
                                )}
                                onClick={() => setToggleState(1)}
                            >
                                Chapter
                            </div>
                            <div
                                className={cx(
                                    "tabs",
                                    toggleState === 2 && "active-tabs"
                                )}
                                onClick={() => setToggleState(2)}
                            >
                                Customer Reviews
                            </div>
                        </div>
                        <div className={cx("content-tabs")}>
                            <div
                                className={cx(
                                    "content",
                                    toggleState === 1 && "active-content"
                                )}
                            >
                                <ListChapter />
                            </div>
                            <div
                                className={cx(
                                    "content",
                                    toggleState === 2 && "active-content"
                                )}
                            >
                                <CustomerReviews result={book.rating} />
                                <div className={cx("comments")}>
                                    <h5>Showing 4 of 20 reviews</h5>
                                    <Comment />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className={cx("related-book")}></div>
                </div>
            </div>
        </div>
    );
}

export default BookDetail;
