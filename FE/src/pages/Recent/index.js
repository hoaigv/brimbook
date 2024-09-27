import classNames from "classnames/bind";
import styles from "./Recent.module.scss";

const cx = classNames.bind(styles);

function Recent() {
  return (
    <div>
      <title>Recent Page | BrimBook</title>
      <div className={cx("wrapper")}></div>
    </div>
  );
}

export default Recent;
