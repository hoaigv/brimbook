import { useRef, useState, useEffect } from "react";
import classNames from "classnames/bind";
import styles from "./PostStory.module.scss";
import { Editor } from "@tinymce/tinymce-react";

import Image from "~/components/Image";
import Input from "~/components/Input";
import Button from "~/components/Button";

const cx = classNames.bind(styles);

function PostStory() {
  const inputRef = useRef(null);
  const [image, setImage] = useState();
  const [value, setValue] = useState("");
  const editorRef = useRef(null);

  // Xóa useEffect này vì nó không cần thiết khi sử dụng TinyMCE
  // useEffect(() => {
  //   editorRef.current.style.height = "auto";
  //   if (value !== "") {
  //     editorRef.current.style.height = editorRef.current.scrollHeight + "px";
  //   }
  // }, [value]);

  const handleClick = () => {
    inputRef.current.click();
  };

  const handleImageChage = (e) => {
    setImage(e.target.files[0]);
  };

  const handleEditorChange = (content, editor) => {
    setValue(content);
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
          <Editor
            apiKey="avak5tehp8k9meap6nlbw1ngvn4lup3fxpjfglmzq6ayaoyd"
            onInit={(evt, editor) => (editorRef.current = editor)}
            initialValue=""
            init={{
              height: 300,
              menubar: false,
              plugins: [
                "advlist autolink lists link image charmap print preview anchor",
                "searchreplace visualblocks code fullscreen",
                "insertdatetime media table paste code help wordcount",
              ],
              toolbar:
                "undo redo | formatselect | " +
                "bold italic backcolor | alignleft aligncenter " +
                "alignright alignjustify | bullist numlist outdent indent | " +
                "removeformat | help",
              content_style: "body { font-family:Helvetica,Arial,sans-serif; font-size:14px }",
            }}
            onEditorChange={handleEditorChange}
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
