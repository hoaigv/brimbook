import classNames from "classnames/bind";
import styles from "./Comment.module.scss";

import images from "~/assets/Image";
import Image from "~/components/Image";
import Rating from "~/components/Rating";

const cx = classNames.bind(styles);

function Comment({ width }) {
    return (
        <div className={cx("comment")} style={{ width: width }}>
            <div className={cx("comment-inner")}>
                <div className={cx("user-info")}>
                    <div className={cx("user-id")}>
                        <Image
                            src={images.placeholderPerson}
                            alt="user-img"
                            width={50}
                            height={50}
                            className={cx("user-img")}
                        />
                        <p className={cx("name-user")}>
                            Bakku Hoang <br />
                            <span>Jan 4th, 2020</span>
                        </p>
                    </div>
                    <p className={cx("comment-text")}>
                        Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor
                        Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor
                        Lorem ipsum dolor Lorem ipsum dolor
                    </p>
                </div>
                <div className={cx("rating")}>
                    <p>4.0</p>
                    <Rating results={4} />
                </div>
            </div>
        </div>
    );
}

export default Comment;
