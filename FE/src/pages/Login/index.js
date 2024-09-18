import { useState } from "react";
import { Link } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./Login.module.scss";

import Image from "~/components/Image";
import images from "~/assets/Image";
import {
    EyeIcon,
    EyeOffIcon,
    LineIcon,
    FacebookIcon,
    GoogleIcon,
} from "~/components/Icons";
import Checkbox from "~/components/Checkbox";
import Button from "~/components/Button";

const cx = classNames.bind(styles);

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [visible, setVisible] = useState(false);

    return (
        <div className={cx("container")}>
            <title>Login Page | BrimBook</title>
            <div className={cx("login-form")}>
                <Image src={images.universe} alt="picture" />
                <form className={cx("form")}>
                    <Image src={images.logo} className={cx("logo")} />
                    <input
                        className={cx("email-input")}
                        type="text"
                        placeholder="Enter email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <div className={cx("password-wrapper")}>
                        <input
                            className={cx("password-input")}
                            type={visible ? "text" : "password"}
                            placeholder="Enter password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                        <div
                            className={cx("pass-icon-wrapper")}
                            onClick={() => {
                                setVisible(!visible);
                            }}
                        >
                            {visible ? (
                                <EyeIcon
                                    className={cx("pass-icon")}
                                    width="24px"
                                    height="24px"
                                />
                            ) : (
                                <EyeOffIcon
                                    className={cx("pass-icon")}
                                    width="24px"
                                    height="24px"
                                />
                            )}
                        </div>
                    </div>
                    <div className={cx("checkbox-wrapper")}>
                        <Checkbox type1>Remember me</Checkbox>
                        <Link
                            // to={}
                            className={cx("forgot-password-btn")}
                        >
                            Forgot password?
                        </Link>
                    </div>
                    <h5>
                        Do you have any account? |
                        <Link
                            // to={}
                            className={cx("register-btn")}
                        >
                            Sign in
                        </Link>
                    </h5>
                    <Button type1 width="400px">
                        Login
                    </Button>
                    <div className={cx("line")}>
                        <LineIcon />
                        <p>or</p>
                        <LineIcon />
                    </div>
                    <div className={cx("login-with-wrapper")}>
                        <Button outline width={"165px"} padding={"10px 0"}>
                            <FacebookIcon />
                            <p>
                                Login with <br />
                                <span>Facebook</span>
                            </p>
                        </Button>
                        <Button outline width={"165px"} padding={"10px 0"}>
                            <GoogleIcon />
                            <p>
                                Login with <br />
                                <span>Google</span>
                            </p>
                        </Button>
                    </div>
                </form>
                <div className={cx("circle-wrapper")}>
                    <div className={cx("circle", "size-1")} />
                    <div className={cx("circle", "size-2")} />
                    <div className={cx("circle", "size-3")} />
                </div>
            </div>
        </div>
    );
}

export default Login;
