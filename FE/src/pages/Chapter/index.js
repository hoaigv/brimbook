import { useEffect, useRef, useState } from "react";
import classNames from "classnames/bind";
import styles from "./Chapter.module.scss";

import { HeartIcon } from "~/components/Icons";
import images from "~/assets/Image";
import Button from "~/components/Button";
import Image from "~/components/Image";
import Comment from "~/components/Comment";
import ChapterNav from "./ChapterNav";

import { chapter } from "~/_mock/chapter";

const cx = classNames.bind(styles);

function Chapter() {
  const textareaRef = useRef(null);
  const [value, setValue] = useState("");
  const handleChange = (e) => {
    setValue(e.target.value);
  };

  useEffect(() => {
    textareaRef.current.style.height = "auto";
    textareaRef.current.style.height = textareaRef.current.scrollHeight + "px";
  }, [value]);

  return (
    <div className={cx("wrapper")}>
      <div className={cx("inner")}>
        <div className={cx("chapter-detail")}>
          <div className={cx("chapter-info")}>
            <div className={cx("content")}>
              <p className={cx("title")}>All Good News</p>
              <p className={cx("chapter-number")}>- Chapter 12</p>
            </div>
            <Button outline padding={"15px"}>
              <HeartIcon />
            </Button>
          </div>
          <ChapterNav />
        </div>
        <div className={cx("chapter-wrapper")}>
          <div className={cx("content")}>
            {chapter.map((item) => (
              <p key={item}>{item}</p>
            ))}
          </div>
        </div>
        <div className={cx("comment-wrapper")}>
          <ChapterNav />
          <div className={cx("comment")}>
            <Comment width={"1019px"} />
            <div className={cx("comment-input")}>
              <div className={cx("comment-user-info")}>
                <div className={cx("user-id")}>
                  <Image
                    src={images.placeholderPerson}
                    alt="user-img"
                    width={50}
                    height={50}
                    className={cx("user-img")}
                  />
                  <p className={cx("name-user")}>Bakku Hoang</p>
                </div>
                <textarea
                  rows={2}
                  className={cx("comment-text")}
                  placeholder="Comment ..."
                  value={value}
                  onChange={handleChange}
                  ref={textareaRef}
                ></textarea>
                <Button type1 width={"100%"}>
                  Submit
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Chapter;
