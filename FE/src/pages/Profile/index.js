import { useState, useEffect, useRef } from "react";
import classNames from "classnames/bind";
import styles from "./Profile.module.scss";

import { ArrowDown, PenIcon } from "~/components/Icons";
import Image from "~/components/Image";
import Input from "~/components/Input";
import Button from "~/components/Button";

import { genderOption } from "~/_mock/genderOption";
import * as User from "~/apis/user";
import images from "~/assets/Image";
// import images from "~/assets/Image";

const cx = classNames.bind(styles);

function Profile() {
  const inputRef = useRef(null);
  const resultRef = useRef();
  const [toggle, setToggle] = useState(false);

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
  const [formData, setFormData] = useState({
    user: {
      firstName: "",
      lastName: "",
      username: "",
      email: "",
      gender: "",
      phone: "",
      birthday: "",
    },
    image: null,
  });

  const handleClick = () => {
    inputRef.current.click();
  };

  const handleImageChage = (e) => {
    setFormData({ ...formData, image: e.target.files[0] });
  };

  const handleChangeFirstName = (e) => {
    setFormData({ ...formData, user: { ...formData.user, firstName: e.target.value } });
  };

  const handleChangeLastName = (e) => {
    setFormData({ ...formData, user: { ...formData.user, lastName: e.target.value } });
  };

  const handleChangeUserName = (e) => {
    setFormData({ ...formData, user: { ...formData.user, username: e.target.value } });
  };

  const handleChangePhone = (e) => {
    setFormData({ ...formData, user: { ...formData.user, phone: e.target.value } });
  };
  const handleChangeBirthday = (e) => {
    setFormData({ ...formData, user: { ...formData.user, birthday: e.target.value } });
  };

  const handleChangeEmail = (e) => {
    setFormData({ ...formData, user: { ...formData.user, email: e.target.value } });
  };

  const handleChangeGender = (item) => {
    setFormData({ ...formData, user: { ...formData.user, gender: item } });
    setToggle(!toggle);
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
    User.getUser(setUserMe);
  }, []);

  const handleSaveChanges = () => {
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
                  src={
                    formData.image ? URL.createObjectURL(formData.image) : userMe.result.image_url
                  }
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
                <label className={cx("label")}>Username</label>
                <Input
                  type="text"
                  defaultValue={userMe.result.username}
                  handleChage={handleChangeUserName}
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
                <div className={cx("select")} onClick={() => setToggle(!toggle)}>
                  <span className={cx("value")}>
                    {formData.user.gender ? formData.user.gender : userMe.result.gender}
                  </span>
                  <ArrowDown />
                </div>
                <ul className={cx("select-list", { active: toggle })}>
                  {genderOption.map((item) => {
                    return (
                      <li
                        key={item}
                        className={cx("item")}
                        onClick={() => handleChangeGender(item)}
                      >
                        {item}
                      </li>
                    );
                  })}
                </ul>
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
