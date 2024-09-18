import classNames from "classnames/bind";
import style from "./Sidebar.module.scss";

import Button from "~/components/Button";
import Logo from "~/components/Logo";

import {
    HomeIcon,
    SearchIcon,
    // RecentIcon,
    // StarIcon,
    // HeartIcon,
    SettingIcon,
    LogoutIcon,
} from "~/components/Icons";
import Menu, { MenuItem } from "../Menu";

const cx = classNames.bind(style);

function Sidebar({ isLoggedIn, onLogout }) {
    return (
        <aside className={cx("wrapper")}>
            <Logo />
            <div className={cx("menu-wrapper")}>
                <div className={cx("menu-inner")}>
                    <Menu>
                        <div className={cx("menu-title")}>Menu</div>
                        <MenuItem title="Home" to={"/"} icon={<HomeIcon />} />
                        <MenuItem
                            title="Search"
                            to={"/search"}
                            icon={<SearchIcon />}
                        />
                    </Menu>
                    {/* <Menu>
            <div className={cx("menu-title")}>Library</div>
            <MenuItem
              title="Recent"
              to={"/recent"}
              icon={<RecentIcon />}
            />
            <MenuItem
              title="Top Rated"
              to={"/top-rated"}
              icon={<StarIcon />}
            />
            <MenuItem
              title="Favorite"
              to={"/favorite"}
              icon={<HeartIcon />}
            />
          </Menu> */}
                </div>
                {isLoggedIn && (
                    <Menu>
                        <div className={cx("menu-title")}>General</div>
                        <MenuItem
                            title="Setting"
                            to={"/setting"}
                            icon={<SettingIcon />}
                        />
                        <Button LogOut onClick={onLogout}>
                            <LogoutIcon width="18px" height="18px" />
                            <span>Log Out</span>
                        </Button>
                    </Menu>
                )}
            </div>
        </aside>
    );
}

export default Sidebar;
