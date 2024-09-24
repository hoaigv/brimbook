import { NavLink } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./Actions.module.scss";

import { BellIcon, GroupIcon } from "~/components/Icons";
import images from "~/assets/Image";
import Image from "~/components/Image";
import Button from "~/components/Button";
import DropDown from "../DropDown";
import { menuItem } from "~/_mock/menu";

import { login } from "~/_mock/login";

const cx = classNames.bind(styles);

function Actions() {
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
          <div className={cx("dropdown")}>
            <div>
              <DropDown name={"Bakku Hoàng"}>
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
                >
                  Log Out
                </Button>
              </DropDown>
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
