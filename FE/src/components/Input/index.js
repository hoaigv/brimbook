import classNames from "classnames/bind";
import styles from "./Input.module.scss";

const cx = classNames.bind(styles);

function Input({ type, handleChage, defaultValue, startIcon, endIcon, mb }) {
  return (
    <div className={cx("wrapper")} style={{ marginBottom: mb }}>
      {startIcon && <div className={cx("icon")}>{startIcon}</div>}
      <input
        type={type}
        className={cx("input")}
        placeholder={defaultValue}
        onChange={handleChage}
      />
      {endIcon && <div className={cx("icon")}>{endIcon}</div>}
    </div>
  );
}

export default Input;
