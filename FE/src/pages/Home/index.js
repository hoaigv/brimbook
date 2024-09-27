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

const cx = classNames.bind(styles);

function Home() {
  const [searchResult, setSearchResult] = useState([]);

  useEffect(() => {
    fetch("https://api.itbook.store/1.0/new")
      .catch((err) => console.log(err))
      .then((res) => res.json())
      .then((res) => setSearchResult(res.books));
  }, []);
  const commitItems = [
    {
      icon: <ThunderIcon width="40px" height="40px" className={cx("icons")} />,
      title: "100% tỷ lệ chấp nhận!",
      description: ""
    },
    {
      icon: <ShieldIcon width="40px" height="40px" className={cx("icons")} />,
      title: "Không có phí trễ hạn hoặc phí ẩn.",
      description: ""
    },
    {
      icon: <LikeIcon width="40px" height="40px" className={cx("icons")} />,
      title: "Thanh toán sau ở bất kỳ đâu với lãi suất 0% với Pay in 30 hoặc Pay in 3.",
      description: ""
    },
    {
      icon: <StarIcon width="40px" height="40px" className={cx("icons")} />,
      title: "Không bao giờ rơi vào nợ nần với tính năng mới của chúng tôi gọi là auto PayPlan.",
      description: ""
    }
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
                <div className={cx("icon-wrapper")}>
                  {item.icon}
                </div>
                <div className={cx("content")}>
                  <p>{item.title}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
        <div className={cx("top-list")}>
          <h3 className={cx("book-title")}>Books on Top</h3>
          <div className={cx("books-list")}>
            {searchResult.slice(0, 6).map((books) => (
              <BookItem1 key={books.isbn13} result={books} />
            ))}
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
          <h3>Special Selection</h3>
          <h4>
            Lorem ipsum dolor sit amet, consectetus adipiscing elit, sed do eiusmod tempro
            <br />
            incididunt ut labore et dolore magna aliqua
          </h4>
          <div className={cx("books-list")}>
            {searchResult.slice(4, 7).map((books) => (
              <BookItem2 key={books.isbn13} result={books} />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
