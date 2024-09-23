import { useState, useEffect, useRef } from "react";
import classNames from "classnames/bind";
import styles from "./Profile.module.scss";

import Image from "~/components/Image";
import { ArrowDown } from "~/components/Icons";
import Button from "~/components/Button";

const cx = classNames.bind(styles);

var genderOption = ["Male", "Female"];

function Profile() {
  const [toggle, setToggle] = useState(false);

  const [name, setName] = useState("Bakku Hoang");
  const [username, setUsername] = useState("bach2908");
  const [gender, setGender] = useState("");
  const [phone, setPhone] = useState("+84 123 456 789");

  const resultRef = useRef(null);

  const handleClick = (item) => {
    setGender(item);
    setToggle(false);
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
        <Image className={cx("color-box")} />
        <Image
          className={cx("avatar")}
          src={"https://taoanhdep.com/wp-content/uploads/2023/12/hinhnen-khoa-hoc.jpg"}
        />
        <div className={cx("profile-info")}>
          <h1 className={cx("name")}>{name}</h1>
          <h2 className={cx("username")}>@{username}</h2>
        </div>
        <form className={cx("form")}>
          <div className={cx("form-group")}>
            <label className={cx("label")}>Full Name</label>
            <input
              className={cx("input")}
              type="text"
              placeholder={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>
          <div className={cx("form-group")}>
            <label className={cx("label")}>Username</label>
            <input
              className={cx("input")}
              type="text"
              placeholder={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
          <div className={cx("form-group")} ref={resultRef}>
            <label className={cx("label")}>Gender</label>
            <div className={cx("select")} onClick={() => setToggle(!toggle)}>
              <span>{gender}</span>
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
            <label className={cx("label")}>Phone</label>
            <input
              type="text"
              placeholder={phone}
              className={cx("input")}
              onChange={(e) => setPhone(e.target.value)}
            />
          </div>
          <div className={cx("btn")}>
            <Button type1 width={"500px"}>
              Save
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Profile;
