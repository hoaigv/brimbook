import { NavLink } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./Logo.module.scss";

import { LogoIcon } from "~/components/Icons";

const cx = classNames.bind(styles);

function Logo() {
    return (
        <div>
            <NavLink to={"/"} className={cx("logo")}>
                <LogoIcon className={cx("logo-icon")} />
                <div className={cx("logo-text")}>
                    <span className={cx("logo-text-1")}>Brimbook</span>
                    <span className={cx("logo-text-2")}>
                        Book Store Website
                    </span>
                </div>
            </NavLink>
        </div>
    );
}

export default Logo;
