import classNames from "classnames/bind";
import style from "./Sidebar.module.scss";

import { config } from "~/config";
import Button from "~/components/Button";
import Logo from "~/layouts/components/Logo";

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
                        <MenuItem
                            title="Home"
                            to={config.routes.home}
                            icon={<HomeIcon />}
                        />
                        <MenuItem
                            title="Search"
                            to={config.routes.search}
                            icon={<SearchIcon />}
                        />
                    </Menu>
                    {/* <Menu>
            <div className={cx("menu-title")}>Library</div>
            <MenuItem
              title="Recent"
              to={config.routes.recent}
              icon={<RecentIcon />}
            />
            <MenuItem
              title="Top Rated"
              to={config.routes.topRated}
              icon={<StarIcon />}
            />
            <MenuItem
              title="Favorite"
              to={config.routes.favorite}
              icon={<HeartIcon />}
            />
          </Menu> */}
                </div>
                {isLoggedIn && (
                    <Menu>
                        <div className={cx("menu-title")}>General</div>
                        <MenuItem
                            title="Setting"
                            to={config.routes.setting}
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
