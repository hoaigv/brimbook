import { useState, useEffect, useRef } from "react";
import classNames from "classnames/bind";
import styles from "./Profile.module.scss";

import { ArrowDown } from "~/components/Icons";
import Image from "~/components/Image";
import Input from "~/components/Input";
import Button from "~/components/Button";

import { genderOption } from "~/_mock/genderOption";

const cx = classNames.bind(styles);

function Profile() {
  const resultRef = useRef();
  const [toggleState, setToggleState] = useState(1);
  const [toggle, setToggle] = useState(false);

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    username: "",
    email: "",
    gender: "",
    phone: "",
    birthday: "",
    avatar: "",
    password: "",
  });

  const handleChangeFirstName = (e) => {
    setFormData({ ...formData, firstName: e.target.value });
  };

  const handleChangeLastName = (e) => {
    setFormData({ ...formData, lastName: e.target.value });
  };

  const handleChangeUserName = (e) => {
    setFormData({ ...formData, username: e.target.value });
  };

  const handleChangePhone = (e) => {
    setFormData({ ...formData, phone: e.target.value });
  };
  const handleChangeBirthday = (e) => {
    setFormData({ ...formData, birthday: e.target.value });
  };

  const handleChangeEmail = (e) => {
    setFormData({ ...formData, email: e.target.value });
  };

  const handleClick = (item) => {
    setFormData({ ...formData, gender: item });
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

  return (
    <div className={cx("wrapper")}>
      <title>Profile Page | BrimBook</title>
      <div className={cx("inner")}>
        <div className={cx("menu")}>
          <ul className={cx("menu-list")}>
            <li
              className={cx("list", toggleState === 1 && "active-list")}
              onClick={() => setToggleState(1)}
            >
              Profile
            </li>
            <li
              className={cx("list", toggleState === 2 && "active-list")}
              onClick={() => setToggleState(2)}
            >
              Change Password
            </li>
          </ul>
        </div>
        <div className={cx("content-wrapper")}>
          <div className={cx("content", toggleState === 1 && "active-content")}>
            <div className={cx("user-profile")}>
              <Image
                className={cx("avatar")}
                src={"https://taoanhdep.com/wp-content/uploads/2023/12/hinhnen-khoa-hoc.jpg"}
              />
              <div className={cx("profile-info")}>
                <h1 className={cx("name")}>
                  {formData.firstName} {formData.lastName}
                </h1>
                <h2 className={cx("username")}>@{formData.username}</h2>
              </div>
            </div>
            <form className={cx("form")}>
              <div className={cx("form-group")}>
                <label className={cx("label")}>First Name</label>
                <Input
                  type="text"
                  defaultValue={formData.name}
                  handleChage={handleChangeFirstName}
                />
              </div>
              <div className={cx("form-group")}>
                <label className={cx("label")}>Last Name</label>
                <Input
                  type="text"
                  defaultValue={formData.name}
                  handleChage={handleChangeLastName}
                />
              </div>
              <div className={cx("form-group")}>
                <label className={cx("label")}>Username</label>
                <Input
                  type="text"
                  defaultValue={formData.username}
                  handleChage={handleChangeUserName}
                />
              </div>
              <div className={cx("form-group")}>
                <label className={cx("label")}>Phone</label>
                <Input type="text" defaultValue={formData.phone} handleChage={handleChangePhone} />
              </div>
              <div className={cx("form-group")} ref={resultRef}>
                <label className={cx("label")}>Gender</label>
                <div className={cx("select")} onClick={() => setToggle(!toggle)}>
                  <span className={cx("value")}>{formData.gender}</span>
                  <ArrowDown />
                </div>
                <ul className={cx("select-list", { active: toggle })}>
                  {genderOption.map((item) => {
                    return (
                      <li key={item} className={cx("item")} onClick={() => handleClick(item)}>
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
                  defaultValue={formData.birthday}
                  handleChage={handleChangeBirthday}
                />
              </div>
              <div className={cx("form-group", "email")}>
                <label className={cx("label")}>Email</label>
                <Input type="text" defaultValue={formData.email} handleChage={handleChangeEmail} />
              </div>
            </form>
            <Button type1 sx={{ marginTop: "50px" }}>
              Save Changes
            </Button>
          </div>
          <div className={cx("content", toggleState === 2 && "active-content")}>
            <div className={cx("change-password")}>
              <div className={cx("form-group")}>
                <label className={cx("label")}>New password</label>
                <Input type="text" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Profile;
