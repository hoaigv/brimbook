import { useState } from "react";

import classNames from "classnames/bind";
import styles from "./FilterOption.module.scss";

import { ArrowDown, ArrowUp } from "~/components/Icons";

const cx = classNames.bind(styles);

function FilterOption({ content, children }) {
    const [visible, setVisible] = useState(false);

    return (
        <div className={cx("wrapper")}>
            <div className={cx("accordion")}>
                <div className={cx("header", visible && "show")}>
                    <p>{content}</p>
                    <div
                        className={cx("show-hide")}
                        onClick={() => {
                            setVisible(!visible);
                        }}
                    >
                        {visible ? <ArrowDown /> : <ArrowUp />}
                    </div>
                </div>
                <div className={cx(visible ? "content-active" : "content")}>
                    {children}
                </div>
            </div>
        </div>
    );
}

export default FilterOption;
