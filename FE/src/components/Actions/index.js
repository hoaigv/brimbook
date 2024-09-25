import { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./Actions.module.scss";

import { BellIcon } from "~/components/Icons";
import images from "~/assets/Image";
import Image from "~/components/Image";
import Button from "~/components/Button";
import DropDown from "../DropDown";
import * as User from "~/apis/user";
import { menuItem } from "~/_mock/menu";

const cx = classNames.bind(styles);

function Actions() {
  const [userMe, setUserMe] = useState({
    result: {
      username: "",
      image_url: "",
    },
  });

  useEffect(() => {
    User.getUser(setUserMe);
  }, []);

  const handleClick = () => {
    User.logoutUser();
  };

  return (
    <div className={cx("wrapper")}>
      <div className={cx("actions")}>
        <NavLink
          className={(nav) => cx("action-btn", { active: nav.isActive })}
          to={"/notifications"}
        >
          <BellIcon width="18px" height="18px" />
        </NavLink>
      </div>
      <div className={cx("dropdown")}>
        <div>
          <DropDown name={userMe.result.username}>
            {menuItem.map((item, index) => (
              <li key={index}>
                <Button normal sx={{ padding: "5px 25px" }} to={item.link}>
                  {item.name}
                </Button>
              </li>
            ))}
            <Button
              logOut
              sx={{
                padding: "5px 25px",
                justifyContent: "flex-start",
                color: "var(--red)",
                fontSize: "1.6rem",
                fontWidth: 600,
              }}
              onClick={handleClick}
            >
              Log Out
            </Button>
          </DropDown>
        </div>
      </div>
      <div className={cx("user-avatar")}>
        <Image
          className={cx("user-avatar")}
          src={userMe.result.image_url}
          fallback={images.placeholderPerson}
          alt="avatar"
        />
      </div>
    </div>
  );
}

export default Actions;
