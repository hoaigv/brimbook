import React from "react";
import styles from "./styles.module.scss";
import classNames from "classnames/bind";
import { LogoIcon } from "~/components/Icons";

const cx = classNames.bind(styles);

function Loading() {
  return (
    <div className={cx("wrapper-pl")}>
      <div className={cx("pl")}>
        <div className={cx("pl__dot")}></div>
        <div className={cx("pl__dot")}></div>
        <div className={cx("pl__dot")}></div>
        <div className={cx("pl__dot")}></div>
        <div className={cx("pl__dot")}></div>
        <div className={cx("pl__dot")}></div>
        <div className={cx("pl__dot")}></div>
        <div className={cx("pl__dot")}></div>
        <div className={cx("pl__dot")}></div>
        <div className={cx("pl__dot")}></div>
        <div className={cx("pl__dot")}></div>
        <div className={cx("pl__dot")}></div>
        <div className={cx("pl__text")}>Loading…</div>
      </div>
    </div>
  );
}

export default Loading;
