import { useRef, useState, useEffect } from "react";
import classNames from "classnames/bind";
import styles from "./PostStory.module.scss";

import Image from "~/components/Image";
import Input from "~/components/Input";
import Button from "~/components/Button";

const cx = classNames.bind(styles);

function PostStory() {
  const inputRef = useRef(null);
  const [image, setImage] = useState();
  const [value, setValue] = useState("");
  const textareaRef = useRef(null);

  useEffect(() => {
    textareaRef.current.style.height = "auto";
    if (value !== "") {
      textareaRef.current.style.height = textareaRef.current.scrollHeight + "px";
    }
  }, [value]);

  const handleClick = () => {
    inputRef.current.click();
  };

  const handleImageChage = (e) => {
    setImage(e.target.files[0]);
  };

  const handleChange = (e) => {
    setValue(e.target.value);
  };

  return (
    <div className={cx("wrapper")}>
      <title>Post Story Page | BrimBook</title>
      <div className={cx("inner")}>
        <div onClick={handleClick} className={cx("image-input")}>
          <Image
            src={image ? URL.createObjectURL(image) : ""}
            alt="PostStory"
            className={cx("image")}
          />
          <input type={"file"} className={cx("input")} onChange={handleImageChage} ref={inputRef} />
        </div>
        <div className={cx("content")}>
          <Input type="text" defaultValue={"Title"} />
          <textarea
            type="text"
            placeholder="Description"
            className={cx("description")}
            onChange={handleChange}
            ref={textareaRef}
          />
          <Button type1 sx={{ maxWidth: "200px" }}>
            Submit
          </Button>
        </div>
      </div>
    </div>
  );
}

export default PostStory;
