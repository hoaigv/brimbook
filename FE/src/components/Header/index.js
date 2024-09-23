import { NavLink } from "react-router-dom";

import classNames from "classnames/bind";
import styles from "./Header.module.scss";

import { LogoImage, LogoIcon } from "~/components/Icons";
import Search from "~/components/Search";
import Actions from "~/components/Actions";

const cx = classNames.bind(styles);

export default function Header() {
  return (
    <header className={cx("container")}>
      <NavLink to={"/"} className={cx("logo")}>
        <LogoImage width="170px" height="50px" className={cx("logo-image")} />
        <LogoIcon className={cx("logo-icon")} />
      </NavLink>
      <Search />
      <Actions />
    </header>
  );
}
