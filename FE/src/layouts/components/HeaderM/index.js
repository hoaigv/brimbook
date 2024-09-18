import classNames from "classnames/bind";
import styles from "./HeaderM.module.scss";

import Search from "~/layouts/components/Search";
import Actions from "~/layouts/components/Actions";

const cx = classNames.bind(styles);

function HeaderM({ isLoggedIn }) {
    return (
        <header className={cx("wrapper")}>
            <div className={cx("inner")}>
                <Search />
                <Actions isLoggedIn={isLoggedIn} />
            </div>
        </header>
    );
}

export default HeaderM;
