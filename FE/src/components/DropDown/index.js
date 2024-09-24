import { useState, useEffect, useRef } from "react";
import classNames from "classnames/bind";
import styles from "./DropDown.module.scss";

import { ArrowDown } from "~/components/Icons";

const cx = classNames.bind(styles);

function DropDown({ children, name, sx }) {
  const resultRef = useRef(null);

  const [toggle, setToggle] = useState(false);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (resultRef.current && !resultRef.current.contains(event.target)) {
        setToggle(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <div className={cx("dropdown")} ref={resultRef}>
      <div className={cx("select")} style={sx} onClick={() => setToggle(!toggle)}>
        <div className={cx("user")}>{name}</div>
        <div className={cx("arrow")}>
          <ArrowDown />
        </div>
      </div>
      {toggle && <ul className={cx("list")}>{children}</ul>}
    </div>
  );
}

export default DropDown;
