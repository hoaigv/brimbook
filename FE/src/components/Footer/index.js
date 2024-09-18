import classNames from "classnames/bind";
import styles from "./Footer.module.scss";

import {
    ArrowRight,
    LogoIcon,
    MapPinIcon,
    MailIcon,
    PhoneIcon,
} from "~/components/Icons";
import Button from "~/components/Button";

const cx = classNames.bind(styles);

const name = [
    "Hoang Trong Bach",
    "Hoang Minh Hoai",
    "Nguyen Thi Hoang Vy",
    "Ta Duy Xuan",
];

const category = [
    "Action",
    "Animation",
    "Avanture",
    "Biography",
    "Comedy",
    "Crime",
    "Documentary",
    "Fantasy",
    "History",
    "Horror",
    "Mystery",
    "Romance",
];

function Footer() {
    return (
        <footer className={cx("wrapper")}>
            <div className={cx("inner")}>
                <div className={cx("content")}>
                    <div className={cx("logo")}>
                        <LogoIcon className={cx("logo-icon")} />
                        <div className={cx("logo-text")}>
                            <span className={cx("logo-text-1")}>Brimbook</span>
                            <span className={cx("logo-text-2")}>
                                Book Store Website
                            </span>
                        </div>
                    </div>
                    <span className={cx("text")}>Make by</span>
                    <div className={cx("name-wrapper")}>
                        {name.map((item) => (
                            <span key={item} className={cx("name")}>
                                {item}
                            </span>
                        ))}
                    </div>
                    <span className={cx("copyright")}>
                        BrimBook Store Website - 2024 All Rights Reserved
                    </span>
                </div>
                <div className={cx("link")}>
                    <span className={cx("text")}>Category</span>
                    <div className={cx("list")}>
                        {category.map((item) => (
                            <Button
                                key={item}
                                noline
                                className={cx("name-wrapper")}
                                color={"var(--g0)"}
                            >
                                {item}
                            </Button>
                        ))}
                        <Button noline color={"var(--primary-purple)"}>
                            View All
                            <ArrowRight />
                        </Button>
                    </div>
                </div>
                <div className={cx("link")}>
                    <span className={cx("text")}>Quick Links</span>
                    <div className={cx("list")}>
                        <Button noline color={"var(--g0)"}>
                            About us
                        </Button>
                        <Button noline color={"var(--g0)"}>
                            Contact us
                        </Button>
                        <Button noline color={"var(--g0)"}>
                            Products
                        </Button>
                        <Button noline color={"var(--g0)"}>
                            Login
                        </Button>
                        <Button noline color={"var(--g0)"}>
                            Sign Up
                        </Button>
                        <Button noline color={"var(--g0)"}>
                            FAQ
                        </Button>
                        <Button noline color={"var(--g0)"}>
                            Shipment
                        </Button>
                    </div>
                </div>
                <div className={cx("link")}>
                    <span className={cx("text")}>Our Store</span>
                    <iframe
                        src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3833.9796497737916!2d108.22705397586284!3d16.066545784612543!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3142198021ee4d69%3A0x46476db0aa96889f!2sNAPA%20Global%20-%20Software%20Outsourcing%20%26%20Offshore%20Development!5e0!3m2!1sen!2s!4v1726541559334!5m2!1sen!2s"
                        width="320"
                        height="200"
                        allowfullscreen=""
                        loading="lazy"
                        referrerpolicy="no-referrer-when-downgrade"
                        title="map"
                        className={cx("map")}
                    ></iframe>

                    <div className={cx("container")}>
                        <MapPinIcon className={cx("icon")} />
                        <span className={cx("text")}>
                            381 Trần Hưng Đạo, Sơn Trà, Đà Nẵng, Vietnam
                        </span>
                    </div>
                    <div className={cx("container")}>
                        <PhoneIcon className={cx("icon")} />
                        <span className={cx("text")}>+84 123 456 789</span>
                    </div>
                    <div className={cx("container")}>
                        <MailIcon className={cx("icon")} />
                        <span className={cx("text")}>
                            htbach.20it11@vku.udn.vn
                        </span>
                    </div>
                </div>
            </div>
        </footer>
    );
}

export default Footer;
