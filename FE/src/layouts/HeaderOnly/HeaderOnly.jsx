import classNames from "classnames/bind";
import styles from "./HeaderOnly.module.scss";

import Header from "~/components/Header";
// import Footer from "~/components/Footer";

const cx = classNames.bind(styles);

function HeaderOnly({ children }) {
  return (
    <div className={cx("container")}>
      <Header />
      <div className={cx("content")}>{children}</div>
      {/* <Footer /> */}
    </div>
  );
}

export default HeaderOnly;
