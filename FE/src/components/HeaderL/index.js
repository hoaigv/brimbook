import Search from "~/components/Search";
import Actions from "~/components/Actions";
import Logo from "../Logo";

import classNames from "classnames/bind";
import styles from "./HeaderL.module.scss";

const cx = classNames.bind(styles);

function HeaderL() {
    return (
        <header className={cx("wrapper")}>
            <div className={cx("inner")}>
                <div className={cx("logo")}>
                    <Logo />
                </div>
                <Search />
                <Actions />
            </div>
        </header>
    );
}

export default HeaderL;
