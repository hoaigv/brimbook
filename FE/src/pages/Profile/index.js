// import { useState, useEffect, useRef } from "react";
import classNames from "classnames/bind";
import styles from "./Profile.module.scss";

// import Image from "~/components/Image";
// import { ArrowDown } from "~/components/Icons";
import Button from "~/components/Button";

const cx = classNames.bind(styles);

// var genderOption = ["Male", "Female"];

function Profile() {
  // const [toggle, setToggle] = useState(false);

  // const [name, setName] = useState("Bakku Hoang");
  // const [username, setUsername] = useState("bach2908");
  // const [gender, setGender] = useState("");
  // const [phone, setPhone] = useState("+84 123 456 789");

  // const resultRef = useRef(null);

  // const handleClick = (item) => {
  //   setGender(item);
  //   setToggle(false);
  // };

  // useEffect(() => {
  //   const handleClickOutside = (event) => {
  //     if (resultRef.current && !resultRef.current.contains(event.target)) {
  //       setToggle(false);
  //     }
  //   };

  //   document.addEventListener("mousedown", handleClickOutside);

  //   return () => {
  //     document.removeEventListener("mousedown", handleClickOutside);
  //   };
  // }, []);

  return (
    <div className={cx("wrapper")}>
      <title>Profile Page | BrimBook</title>
      <div className={cx("inner")}>
        <div className={cx("menu")}>
          <ul className={cx("menu-list")}>
            <li>Profile</li>
            <li>Change Password</li>
          </ul>
        </div>
      </div>
    </div>
  );
}

export default Profile;
