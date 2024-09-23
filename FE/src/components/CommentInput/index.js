import { useEffect, useRef, useState } from "react";

import classNames from "classnames/bind";
import styles from "./CommentInput.module.scss";

import images from "~/assets/Image";
import Button from "~/components/Button";
import Image from "~/components/Image";

const cx = classNames.bind(styles);

function CommentInput() {
  const textareaRef = useRef(null);
  const [value, setValue] = useState("");

  useEffect(() => {
    textareaRef.current.style.height = "auto";
    if (value !== "") {
      textareaRef.current.style.height = textareaRef.current.scrollHeight + "px";
    }
  }, [value]);

  const handleChange = (e) => {
    setValue(e.target.value);
  };

  return (
    <div className={cx("comment-input")}>
      <div className={cx("comment-user-info")}>
        <div className={cx("user")}>
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
  );
}

export default CommentInput;
