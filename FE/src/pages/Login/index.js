import { useState } from "react";
import { useNavigate } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./Login.module.scss";

import { EyeIcon, EyeOffIcon, LogoImage } from "~/components/Icons";
import Button from "~/components/Button";
import Checkbox from "~/components/Checkbox";
import * as User from "~/apis/user";
import Input from "~/components/Input";

const cx = classNames.bind(styles);

function Login() {
  const navigate = useNavigate();

  const [err, setErr] = useState(false);
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });
  const [visible, setVisible] = useState("");

  const handleChage = (e) => {
    setFormData((curr) => ({ ...curr, username: e.target.value }));
  };

  const handleInputPassword = (e) => {
    setFormData((curr) => ({ ...curr, password: e.target.value }));
  };

  const handleClick = () => {
    if (!formData.username || !formData.password) {
      setErr(true);
    }
    User.loginUser(formData, navigate);
  };

  return (
    <>
      <title>Login Page | BrimBook</title>

      <div className={cx("wrapper")}>
        <div className={cx("form")}>
          <LogoImage width="250px" height="60px" className={cx("logo")} />
          <Input type={"text"} handleChage={handleChage} defaultValue={"Enter username"} mb={20} />
          <div className={cx("password-wrapper")}>
            <div className={cx("password-inner")}>
              <input
                className={cx("password-input", err && "err")}
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
          <div className={cx("checkbox-wrapper")}>
            <Checkbox type1>Remember me</Checkbox>
            <Button
              noline
              // to={}
              sx={{ width: "123px", color: "var(--primary-purple)" }}
            >
              Forgot password?
            </Button>
          </div>
          <div className={cx("register-btn")}>
            <h5>Do you have any account? |</h5>
            <Button noline to={"/register"} sx={{ width: "60px", color: "var(--primary-purple)" }}>
              Sign up
            </Button>
          </div>
          <Button type1 onClick={handleClick}>
            Login
          </Button>
        </div>
      </div>
    </>
  );
}

export default Login;
