import classNames from "classnames/bind";
import styles from "./HeaderOnly.module.scss";
import HeaderL from "../components/HeaderL";

const cx = classNames.bind(styles);

function HeaderOnly({ children }) {
    return (
        <div className={cx("wrapper")}>
            <div className={cx("inner")}>
                <HeaderL />
                <div className={cx("content")}>{children}</div>
            </div>
        </div>
    );
}

export default HeaderOnly;
