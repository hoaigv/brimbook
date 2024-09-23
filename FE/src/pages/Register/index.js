import { useState } from "react";
import { useNavigate } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./Register.module.scss";

import { EyeIcon, EyeOffIcon, LogoImage } from "~/components/Icons";
import Button from "~/components/Button";
import Input from "~/components/Input";
import * as User from "~/apis/user";

const cx = classNames.bind(styles);

function Register() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState([]);
  const [visible, setVisible] = useState(false);

  // const handleInputName = (e) => {
  //   setFormData((curr) => ({ ...curr, username: e.target.value }));
  // };

  // const handleInputEmail = (e) => {
  //   setFormData((curr) => ({ ...curr, email: e.target.value }));
  // };

  const handleInputPassword = (e) => {
    setFormData((curr) => ({ ...curr, password: e.target.value }));
  };

  const handleClick = () => {
    User.registerUser(formData, navigate);
  };

  return (
    <>
      <title>Register Page | BrimBook</title>

      <div className={cx("wrapper")}>
        <div className={cx("form")}>
          <LogoImage width="250px" height="60px" className={cx("logo")} />
          <Input type={"text"} defaultValue={"Enter Name"} mb={20} />
          <Input type={"text"} defaultValue={"Enter email"} mb={20} />
          <div className={cx("password-wrapper")}>
            <div className={cx("password-inner")}>
              <input
                className={cx("password-input")}
                type={visible ? "text" : "password"}
                placeholder="Enter password"
                onChange={handleInputPassword}
              />
              <div
                className={cx("pass-icon-wrapper")}
                onClick={() => {
                  setVisible(!visible);
                }}
              >
                {visible ? (
                  <EyeIcon className={cx("pass-icon")} width="24px" height="24px" />
                ) : (
                  <EyeOffIcon className={cx("pass-icon")} width="24px" height="24px" />
                )}
              </div>
            </div>
          </div>
          <div className={cx("login-btn")}>
            <h5>Already have an account? |</h5>
            <Button noline to={"/login"} width="60px" color={"var(--primary-purple)"}>
              Sign in
            </Button>
          </div>
          <Button type1 onClick={handleClick}>
            Sign up
          </Button>
        </div>
      </div>
    </>
  );
}

export default Register;
