import { Link } from "react-router-dom";
import classNames from "classnames/bind";
import styles from "./Button.module.scss";

const cx = classNames.bind(styles);

function Button({
    to,
    href,
    onClick,
    children,

    width,
    height,
    padding,
    color,

    type1 = false,
    type2 = false,
    LogOut = false,
    outline = false,
    noline = false,
    loadpage = false,
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
        type1,
        type2,
        LogOut,
        outline,
        noline,
        loadpage,
    });

    return (
        <Comp
            className={cx(classes)}
            {...props}
            style={{
                width: width,
                height: height,
                padding: padding,
                color: color,
            }}
        >
            <span>{children}</span>
        </Comp>
    );
}

export default Button;
