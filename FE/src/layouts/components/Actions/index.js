import { config } from "~/config";
import { NavLink } from "react-router-dom";
import classNames from "classnames/bind";
import styles from "./Actions.module.scss";

import Image from "~/components/Image";
import Button from "~/components/Button";
import images from "~/assets/Image";
import { BellIcon, ArrowDown, GroupIcon } from "~/components/Icons";

const cx = classNames.bind(styles);

function Actions({ isLoggedIn }) {
    return (
        <div className={cx("actions")}>
            {isLoggedIn ? (
                <>
                    <div className={cx("actions-inner")}>
                        {/* <NavLink
                            className={(nav) =>
                                cx("action-btn", { active: nav.isActive })
                            }
                            to={config.routes.store}
                        >
                            <StoreIcon width="18px" height="18px" />
                        </NavLink> */}
                        <NavLink
                            className={(nav) =>
                                cx("action-btn", { active: nav.isActive })
                            }
                            to={config.routes.notification}
                        >
                            <BellIcon width="18px" height="18px" />
                        </NavLink>
                    </div>
                    <div className={cx("user-login")}>
                        <NavLink
                            className={(nav) =>
                                cx("user-btn", { active: nav.isActive })
                            }
                            to={config.routes.profile}
                        >
                            Bakku Hoàng
                            <ArrowDown />
                        </NavLink>
                        <Image
                            className={cx("user-avatar")}
                            src={
                                "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2024/03/hinh-nen-desktop.jpg" ||
                                images.placeholderPerson
                            }
                            alt="avatar"
                        />
                    </div>
                </>
            ) : (
                <div className={cx("actions-inner")}>
                    <Button type2 to={config.routes.login}>
                        Log In
                    </Button>
                    <Button type1>
                        <GroupIcon />
                        Sign Up
                    </Button>
                </div>
            )}
        </div>
    );
}

export default Actions;
