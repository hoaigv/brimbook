import { useState, useEffect, useRef } from "react";
import classNames from "classnames/bind";
import styles from "./Profile.module.scss";

import { PenIcon } from "~/components/Icons";
import Image from "~/components/Image";
import Input from "~/components/Input";
import Button from "~/components/Button";

import * as User from "~/apis/user";
import images from "~/assets/Image";
// import images from "~/assets/Image";

const cx = classNames.bind(styles);

function Profile() {
  const inputRef = useRef(null);
  const resultRef = useRef();

  const [userMe, setUserMe] = useState({
    result: {
      firstName: "",
      lastName: "",
      username: "",
      email: "",
      gender: "",
      phone: "",
      birthday: "",
      image_url: "",
      password: "",
    },
  });
  const [formData, setFormData] = useState({});
  const [image, setImage] = useState(null);

  const handleClick = () => {
    inputRef.current.click();
  };

  const handleImageChage = (e) => {
    setImage(e.target.files[0]);
  };

  const handleChangeFirstName = (e) => {
    setFormData({ ...formData, firstName: e.target.value });
  };

  const handleChangeLastName = (e) => {
    setFormData({ ...formData, lastName: e.target.value });
  };

  const handleChangePhone = (e) => {
    setFormData({ ...formData, phone: e.target.value });
  };
  const handleChangeBirthday = (e) => {
    setFormData({ ...formData, birthDate: e.target.value });
  };

  const handleChangeEmail = (e) => {
    setFormData({ ...formData, email: e.target.value });
  };

  const handleChangeGender = (e) => {
    setFormData({ ...formData, gender: e.target.value });
  };

  useEffect(() => {
    User.getUser(setUserMe);
  }, []);

  const data = JSON.stringify(formData);

  const handleSaveChanges = () => {
    const formData = new FormData();
    formData.append("image", image);
    formData.append("data", data);
    User.update(formData);
  };

  return (
    <div className={cx("wrapper")}>
      <title>Profile Page | BrimBook</title>
      <div className={cx("inner")}>
        <div className={cx("content-wrapper")}>
          <div className={cx("content")}>
            <div className={cx("user-profile")}>
              <div className={cx("image-input")}>
                <Image
                  src={image ? URL.createObjectURL(image) : userMe.result.image_url}
                  fallback={images.placeholderPerson}
                  alt="PostStory"
                  className={cx("avatar")}
                />
                <button className={cx("edit-image")} onClick={handleClick}>
                  <PenIcon />
                  <input
                    type={"file"}
                    className={cx("input")}
                    onChange={handleImageChage}
                    ref={inputRef}
                  />
                </button>
              </div>
              <div className={cx("profile-info")}>
                <h1 className={cx("name")}>
                  {userMe.result.firstName} {userMe.result.lastName}
                </h1>
                <h2 className={cx("username")}>@{userMe.result.username}</h2>
              </div>
            </div>
            <div className={cx("form")}>
              <div className={cx("form-group")}>
                <label className={cx("label")}>First Name</label>
                <Input
                  type="text"
                  defaultValue={userMe.result.firstName}
                  handleChage={handleChangeFirstName}
                />
              </div>
              <div className={cx("form-group")}>
                <label className={cx("label")}>Last Name</label>
                <Input
                  type="text"
                  defaultValue={userMe.result.lastName}
                  handleChage={handleChangeLastName}
                />
              </div>

              <div className={cx("form-group")}>
                <label className={cx("label")}>Phone</label>
                <Input
                  type="text"
                  defaultValue={userMe.result.phone}
                  handleChage={handleChangePhone}
                />
              </div>
              <div className={cx("form-group")} ref={resultRef}>
                <label className={cx("label")}>Gender</label>
                <select className={cx("select-list")}>
                  <option className={cx("item")} value={"Male"} onClick={handleChangeGender}>
                    Male
                  </option>
                  <option className={cx("item")} value={"Female"} onClick={handleChangeGender}>
                    Female
                  </option>
                </select>
              </div>
              <div className={cx("form-group")}>
                <label className={cx("label")}>Birthday</label>
                <Input
                  type="date"
                  defaultValue={userMe.result.birthday}
                  handleChage={handleChangeBirthday}
                />
              </div>
              <div className={cx("form-group", "email")}>
                <label className={cx("label")}>Email</label>
                <Input
                  type="text"
                  defaultValue={userMe.result.email}
                  handleChage={handleChangeEmail}
                />
              </div>
            </div>
            <Button type1 sx={{ marginTop: "50px" }} onClick={handleSaveChanges}>
              Save Changes
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Profile;
