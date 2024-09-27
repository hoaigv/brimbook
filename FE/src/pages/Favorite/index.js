import classNames from "classnames/bind";
import styles from "./Favorite.module.scss";

import SearchBookItem from "~/components/SearchBookItem";

const cx = classNames.bind(styles);

function Favorite() {
  return (
    <div>
      <title>Favorite Page | BrimBook</title>
      <div className={cx("wrapper")}>
        <SearchBookItem />
        <SearchBookItem />
        <SearchBookItem />
        <SearchBookItem />
        <SearchBookItem />
        <SearchBookItem />
        <SearchBookItem />
      </div>
    </div>
  );
}

export default Favorite;
