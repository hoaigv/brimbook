import { useState } from "react";
import classNames from "classnames/bind";
import styles from "./DefaultLayout.scss";
import HeaderM from "../../components/HeaderM";
import Sidebar from "../../components/Sidebar";
import Footer from "../../components/Footer";

const cx = classNames.bind(styles);

function DefaultLayout({ children }) {
    const [isLoggedIn, setIsLoggedIn] = useState(true);

    const handleLogout = () => {
        setIsLoggedIn(false);
    };

    return (
        <div className={cx("wrapper")}>
            <div className={cx("inner")}>
                <Sidebar isLoggedIn={isLoggedIn} onLogout={handleLogout} />
                <HeaderM isLoggedIn={isLoggedIn} />
                <div className={cx("content")}>{children}</div>
                <Footer />
            </div>
        </div>
    );
}

export default DefaultLayout;
