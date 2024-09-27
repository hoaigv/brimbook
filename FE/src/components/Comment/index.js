import classNames from "classnames/bind";
import styles from "./Comment.module.scss";

import Image from "~/components/Image";
import TimeConverter from "../SetTime";

const cx = classNames.bind(styles);

function Comment({ comments, width }) {
  return (
    <div className={cx("comment")} style={{ width: width }}>
      <div className={cx("comment-inner")}>
        <div className={cx("user-info")}>
          <div className={cx("user-id")}>
            <Image
              src={comments.user.image_url}
              alt="user-img"
              width={50}
              height={50}
              className={cx("user-img")}
            />
            <p className={cx("name-user")}>
              {comments.user.firstName} {comments.user.lastName} <br />
              <TimeConverter
                sx={{ color: " var(--g0)", fontSize: "1.6rem", fontWeight: 700 }}
                timestamp={comments.createdAt}
              ></TimeConverter>
            </p>
          </div>
          <p className={cx("comment-text")}>{comments.commentText}</p>
        </div>
      </div>
    </div>
  );
}

export default Comment;
