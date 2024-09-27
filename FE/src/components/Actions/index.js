import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import classNames from "classnames/bind";
import styles from "./Actions.module.scss";

import images from "~/assets/Image";
import Image from "~/components/Image";
import Button from "~/components/Button";
import DropDown from "../DropDown";
import * as User from "~/apis/user";
import { menuItem } from "~/_mock/menu";

const cx = classNames.bind(styles);

const user = localStorage.getItem("userToken");

function Actions() {
  const navigate = useNavigate();
  const [userMe, setUserMe] = useState({
    result: {
      username: "",
      image_url: "",
    },
  });

  useEffect(() => {
    User.getUser(setUserMe);
  }, []);

  const handleLogout = () => {
    User.logoutUser(navigate);
  };

  return (
    <div className={cx("wrapper")}>
      {user ? (
        <>
          <div className={cx("dropdown")}>
            <div>
              <DropDown name={userMe.result.username} sx={{ maxWidth: "130px" }}>
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
                  onClick={handleLogout}
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
        </>
      ) : (
        <>
          <Button type2 to="/login" sx={{ width: "150px" }}>
            Login
          </Button>
          <Button type1 to="/register" sx={{ width: "180px" }}>
            Register
          </Button>
        </>
      )}
    </div>
  );
}

export default Actions;
