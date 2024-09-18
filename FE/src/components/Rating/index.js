import classNames from "classnames/bind";
import styles from "./Rating.module.scss";
import { RatingIcon } from "~/components/Icons";

const cx = classNames.bind(styles);

function Rating({ results }) {
    const stars = [];
    for (let i = 1; i <= 5; i++) {
        stars.push(
            <RatingIcon
                key={i}
                fill={i <= results ? "var(--orange)" : "var(--g1)"}
            />
        );
    }
    return <div className={cx("rating")}>{stars}</div>;
}

export default Rating;
