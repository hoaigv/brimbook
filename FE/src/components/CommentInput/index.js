import { useEffect, useRef, useState } from "react";

import classNames from "classnames/bind";
import styles from "./CommentInput.module.scss";

import images from "~/assets/Image";
import Button from "~/components/Button";
import Image from "~/components/Image";

import * as Comment from "~/apis/comment";
import * as User from "~/apis/user";

const cx = classNames.bind(styles);

function CommentInput({ param, isSetSendComment }) {
  const textareaRef = useRef(null);
  const [userMe, setUserMe] = useState({
    result: {
      username: "",
      image_url: "",
    },
  });
  const [value, setValue] = useState();

  useEffect(() => {
    textareaRef.current.style.height = "auto";
    if (value !== "" && value !== undefined) {
      textareaRef.current.style.height = textareaRef.current.scrollHeight + "px";
    }
  }, [value]);

  const handleChange = (e) => {
    setValue(e.target.value);
  };

  const handleSubmit = () => {
    Comment.comment(param, value).then(() => {
      isSetSendComment((pre) => !pre);
    });
  };

  useEffect(() => {
    User.getUser(setUserMe);
  }, []);

  console.log(userMe);

  return (
    <div className={cx("comment-input")}>
      <div className={cx("comment-user-info")}>
        <div className={cx("user")}>
          <Image
            src={userMe.result.image_url}
            alt="user-img"
            width={50}
            height={50}
            className={cx("user-img")}
          />
          <p className={cx("name-user")}>{userMe.result.username}</p>
        </div>
        <textarea
          className={cx("comment-text")}
          placeholder="Comment ..."
          value={value}
          onChange={handleChange}
          ref={textareaRef}
        ></textarea>
        <Button type1 width={"100%"} onClick={handleSubmit}>
          Submit
        </Button>
      </div>
    </div>
  );
}

export default CommentInput;
