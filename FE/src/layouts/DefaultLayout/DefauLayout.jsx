import classNames from "classnames/bind";
import styles from "./DefaultLayout.scss";

import Header from "~/components/Header";
import Sidebar from "~/components/Sidebar";
import Footer from "~/components/Footer";

const cx = classNames.bind(styles);

function DefaultLayout({ children }) {
  return (
    <div className={cx("wrapper")}>
      <div className={cx("inner")}>
        <Sidebar />
        <Header width={"1390px"} display={"none"} />
        <div className={cx("content")}>{children}</div>
        <Footer />
      </div>
    </div>
  );
}

export default DefaultLayout;
