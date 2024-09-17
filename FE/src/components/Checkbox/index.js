import classNames from "classnames/bind";
import styles from "./Checkbox.module.scss";
import { CheckIcon } from "~/components/Icons";

const cx = classNames.bind(styles);

function Checkbox({ children, type1 = false, type2 = false }) {
    const classes = cx("wrapper", {
        type1,
        type2,
    });

    return (
        <div className={cx("checkbox")}>
            <input type="checkbox" />
            <label htmlFor="rememberMe" className={classes}>
                <CheckIcon className={cx("check")} />
                {children}
            </label>
        </div>
    );
}

export default Checkbox;
