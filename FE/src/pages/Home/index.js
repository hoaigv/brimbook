import { useState, useEffect } from "react";

import classNames from "classnames/bind";
import styles from "./Home.module.scss";

import {
  ThunderIcon,
  ShieldIcon,
  LikeIcon,
  StarIcon,
  GroupIcon,
  BookIcon,
  ShopIcon,
  PenIcon,
} from "~/components/Icons";
import BookItem1, { BookItem2 } from "~/components/BookItem";
import SliderHome from "~/components/SliderHome";
import * as BOOKAPI from "~/apis/book";

const cx = classNames.bind(styles);

function Home() {
  const [searchResult, setSearchResult] = useState([]);
  const [topBooks, setTopBooks] = useState([]);
  const [topReadBooks, setTopReadBooks] = useState([]);

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const books = await BOOKAPI.getAllBooks();
        // Sắp xếp sách theo total_likes giảm dần
        const sortedByLikes = books.sort((a, b) => b.total_likes - a.total_likes);
        // Lấy top 6 cuốn sách được like nhiều nhất
        const topLikedBooks = sortedByLikes.slice(0, 6);
        setTopBooks(topLikedBooks);

        // Sắp xếp sách theo total_reads giảm dần
        const sortedByReads = [...books].sort((a, b) => b.total_reads - a.total_reads);
        // Lấy top 6 cuốn sách được đọc nhiều nhất
        const topReadBooks = sortedByReads.slice(0, 3);
        setTopReadBooks(topReadBooks);

        setSearchResult(books || []); // Giữ nguyên searchResult
      } catch (error) {
        console.error("Không thể lấy danh sách sách:", error);
        setTopBooks([]);
        setTopReadBooks([]);
        setSearchResult([]);
      }
    };

    fetchBooks();
  }, []);

  const commitItems = [
    {
      icon: <ThunderIcon width="40px" height="40px" className={cx("icons")} />,
      title: "100% tỷ lệ chấp nhận!",
      description: "",
    },
    {
      icon: <ShieldIcon width="40px" height="40px" className={cx("icons")} />,
      title: "Không có phí trễ hạn hoặc phí ẩn.",
      description: "",
    },
    {
      icon: <LikeIcon width="40px" height="40px" className={cx("icons")} />,
      title: "Thanh toán sau ở bất kỳ đâu",
      description: "",
    },
    {
      icon: <StarIcon width="40px" height="40px" className={cx("icons")} />,
      title: "Không bao giờ rơi vào nợ nần",
      description: "",
    },
  ];
  return (
    <div className={cx("wrapper")}>
      <title>Home Page | BrimBook</title>
      <div className={cx("inner")}>
        <SliderHome />
        <div className={cx("commit-section")}>
          <div className={cx("commit-items")}>
            {commitItems.map((item, index) => (
              <div key={index} className={cx("commit-item")}>
                <div className={cx("icon-wrapper")}>{item.icon}</div>
                <div className={cx("content")}>
                  <p>{item.title}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
        <div className={cx("top-list")}>
          <h3 className={cx("book-title")}>Sách Được Yêu Thích Nhất</h3>
          <div className={cx("books-list")}>
            {topBooks.length > 0 ? (
              topBooks.map((book) => <BookItem1 key={book.id} result={book} />)
            ) : (
              <p>Không có sách nào để hiển thị.</p>
            )}
          </div>
        </div>
        <div className={cx("introduce-items")}>
          <div className={cx("introduce-item")}>
            <GroupIcon width="95px" height="95px" />
            <h3>125,663</h3>
            <p>Happy Customers</p>
          </div>
          <div className={cx("introduce-item")}>
            <BookIcon width="95px" height="95px" />
            <h3>50,672+</h3>
            <p>Book Collections</p>
          </div>
          <div className={cx("introduce-item")}>
            <ShopIcon width="95px" height="95px" />
            <h3>1,562</h3>
            <p>Our Stores</p>
          </div>
          <div className={cx("introduce-item")}>
            <PenIcon width="95px" height="95px" />
            <h3>457</h3>
            <p>Famous Writers</p>
          </div>
        </div>
        <div className={cx("special-offers")}>
          <h3>Sách được đọc nhiều nhất</h3>
          <h4>Khám phá những cuốn sách được độc giả yêu thích nhất của chúng tôi</h4>
          <div className={cx("books-list")}>
            {topReadBooks.map((book) => (
              <BookItem2 key={book.isbn13} result={book} />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
