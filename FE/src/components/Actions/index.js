import { useState, useEffect, useRef } from "react";
import { NavLink } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./Actions.module.scss";

import { BellIcon, GroupIcon, ArrowDown } from "~/components/Icons";
import images from "~/assets/Image";
import Image from "~/components/Image";
import Button from "~/components/Button";

import { login } from "~/_mock/login";

const cx = classNames.bind(styles);

function Actions() {
  const resultRef = useRef(null);

  const [toggle, setToggle] = useState(false);

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
      {login ? (
        <>
          <div className={cx("actions")}>
            <NavLink
              className={(nav) => cx("action-btn", { active: nav.isActive })}
              to={"/notifications"}
            >
              <BellIcon width="18px" height="18px" />
            </NavLink>
          </div>
          <div className={cx("dropdown")} ref={resultRef}>
            <div>
              <div className={cx("select")} onClick={() => setToggle(!toggle)}>
                <span className={cx("user")}>{"Bakku Hoàng"}</span>
                <div className={cx("arrow")}>
                  <ArrowDown />
                </div>
              </div>
              {toggle && (
                <ul className={cx("list")}>
                  <li>
                    <Button normal to={"/profile"} onClick={() => setToggle(!toggle)}>
                      Profile
                    </Button>
                  </li>
                  <li>
                    <Button normal to={"/create-story"}>
                      Đăng truyện
                    </Button>
                  </li>
                  <li>
                    <Button normal to={"/setting"} onClick={() => setToggle(!toggle)}>
                      Setting
                    </Button>
                  </li>
                  <li>
                    <Button normal onClick={null}>
                      Log out
                    </Button>
                  </li>
                </ul>
              )}
            </div>
          </div>
          <div className={cx("user-avatar")}>
            <Image className={cx("user-avatar")} src={images.placeholderPerson} alt="avatar" />
          </div>
        </>
      ) : (
        <div className={cx("actions")}>
          <Button type2 to={"/login"} width={100}>
            Log In
          </Button>
          <Button type1 width={150}>
            <GroupIcon />
            Sign Up
          </Button>
        </div>
      )}
    </div>
  );
}

export default Actions;
