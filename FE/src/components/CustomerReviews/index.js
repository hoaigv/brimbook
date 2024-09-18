import classNames from "classnames/bind";
import styles from "./CustomerReviews.module.scss";
import Rating from "~/components/Rating";

const cx = classNames.bind(styles);

function CustomerReviews({ result }) {
    return (
        <div className={cx("container")}>
            <div className={cx("rating-information")}>
                <div className={cx("text")}>
                    <h1>Rating Information</h1>
                    <span>
                        Provides a quick overview of what customers have said
                        about this book.
                    </span>
                </div>
                <div className={cx("rating")}>
                    <p>
                        {result}
                        <span>out of 5</span>
                    </p>
                    <Rating results={result} />
                </div>
            </div>
        </div>
    );
}

export default CustomerReviews;
