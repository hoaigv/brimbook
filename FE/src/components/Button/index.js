import { Link } from "react-router-dom";
import classNames from "classnames/bind";
import styles from "./Button.module.scss";

const cx = classNames.bind(styles);

function Button({
  to,
  href,
  onClick,
  children,
  sx,

  normal = false,
  type1 = false,
  type2 = false,
  logOut = false,
  outline = false,
  noline = false,
  loadpage = false,

  startIcon,
  endIcon,
}) {
  let Comp = "button";
  const props = {
    onClick,
  };

  if (to) {
    props.to = to;
    Comp = Link;
  } else if (href) {
    props.href = href;
    Comp = "a";
  }

  const classes = cx("wrapper", {
    normal,
    type1,
    type2,
    logOut,
    outline,
    noline,
    loadpage,
  });

  return (
    <Comp className={classes} {...props} style={sx}>
      {startIcon && <div className={cx("icon")}>{startIcon}</div>}
      {children}
      {endIcon && <div className={cx("icon")}>{endIcon}</div>}
    </Comp>
  );
}

export default Button;
