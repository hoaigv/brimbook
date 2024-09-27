import { useRef, useState, useEffect } from "react";
import classNames from "classnames/bind";
import styles from "./PostStory.module.scss";

import { ArrowDown } from "~/components/Icons";
import Image from "~/components/Image";
import Input from "~/components/Input";
import Button from "~/components/Button";
import { Editor } from "@tinymce/tinymce-react";
import * as Category from "~/apis/category";
import * as Book from "~/apis/book";

const cx = classNames.bind(styles);

function PostStory() {
  const resultRef = useRef(null);
  const inputRef = useRef(null);
  const editorRef = useRef(null);

  const handleClick = () => {
    inputRef.current.click();
  };

  const [toggle, setToggle] = useState(false);
  const [category, setCategory] = useState([]);
  const [image, setImage] = useState();

  const [formData, setFormData] = useState({
    title: "",
    categoriesID: "",
    description: "",
  });
  const [categ, setCateg] = useState();

  const handleImageChage = (e) => {
    setImage(e.target.files[0]);
  };

  const handleTitleChange = (e) => {
    setFormData({ ...formData, title: e.target.value });
  };

  const handleCategoryChange = (id, name) => {
    setFormData({ ...formData, categoriesID: id });
    setCateg(name);
    setToggle(!toggle);
  };

  const handleEditorChange = (content, editor) => {
    setFormData({ ...formData, description: content });
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (resultRef.current && !resultRef.current.contains(event.target)) {
        setToggle(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  useEffect(() => {
    Category.getAll(setCategory);
  }, []);

  const data = JSON.stringify(formData);

  const handleSubmit = () => {
    const formDatas = new FormData();
    formDatas.append("image", image);
    formDatas.append("data", data);

    Book.update(formDatas).then(() => {
      setFormData({
        title: "",
        categoriesID: "",
        description: "",
      });
    });
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
          <Input
            value={formData.title}
            type="text"
            defaultValue={"Title"}
            handleChage={handleTitleChange}
          />
          <div className={cx("group")} ref={resultRef}>
            <div className={cx("select")} onClick={() => setToggle(!toggle)}>
              <span className={cx("value")}>{categ ? categ : "Category"}</span>
              <ArrowDown />
            </div>
            <ul className={cx("select-list", { active: toggle })}>
              {category.map((item) => {
                return (
                  <li
                    key={item.id}
                    className={cx("item")}
                    onClick={() => handleCategoryChange(item.id, item.name)}
                  >
                    {item.name}
                  </li>
                );
              })}
            </ul>
          </div>
          <Editor
            value={formData.description}
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
          <Button type1 sx={{ maxWidth: "200px" }} onClick={handleSubmit}>
            Post
          </Button>
        </div>
      </div>
    </div>
  );
}

export default PostStory;
