import classNames from "classnames/bind";
import styles from "./SlideAds.module.scss";

import "react-slideshow-image/dist/styles.css";
import { Slide } from "react-slideshow-image";

const cx = classNames.bind(styles);

const slideImages = [
    {
        id: 1,
        url: "https://png.pngtree.com/thumb_back/fw800/background/20240301/pngtree-desktop-wallpaper-hd-image_15634025.jpg",
        caption: "First Slide",
    },
    {
        id: 2,
        url: "https://cdn-media.sforum.vn/storage/app/media/wp-content/uploads/2023/07/hinh-nen-ai-98.jpg",
        caption: "Second Slide",
    },
    {
        id: 3,
        url: "https://24hstore.vn/upload_images/images/2023/hinh-nen-may-tinh/1-1-hinh-nen-may-tinh-chill-win-10-1.jpg",
        caption: "Third Slide",
    },
];

function SlideAds() {
    return (
        <div className={cx("wrapper")}>
            <Slide>
                {slideImages.map((image) => (
                    <div
                        key={image.id}
                        className={cx("slide")}
                        style={{ backgroundImage: `url(${image.url})` }}
                    />
                ))}
            </Slide>
        </div>
    );
}

export default SlideAds;
