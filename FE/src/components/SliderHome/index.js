import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import styles from "./styles.module.scss";
import "./styles.module.scss";
import "swiper/css";
import { EffectCoverflow } from "swiper/modules";
import classNames from "classnames/bind";
import slide_image_1 from "../../assets/Image/img_1.jpg";
import slide_image_2 from "../../assets/Image/img_1.jpg";
import slide_image_3 from "../../assets/Image/img_1.jpg";
import slide_image_4 from "../../assets/Image/img_1.jpg";
import slide_image_5 from "../../assets/Image/img_1.jpg";
import slide_image_6 from "../../assets/Image/img_1.jpg";
import slide_image_7 from "../../assets/Image/img_1.jpg";
const cx = classNames.bind(styles);
function SliderHome() {
  return (
    <div className="container-slider">
      <h1 className="heading">Newly Released Books</h1>
      <Swiper
        effect={"coverflow"}
        grabCursor={true}
        centeredSlides={true}
        slidesPerView={"auto"}
        initialSlide={7}
        coverflowEffect={{
          rotate: 0,
          stretch: 0,
          depth: 100,
          modifier: 2.5,
        }}
        modules={[EffectCoverflow]}
        className="swiper_container"
        breakpoints={{
          640: {
            slidesPerView: 2,
          },
          768: {
            slidesPerView: 3,
          },
          1024: {
            slidesPerView: 4,
          },
        }}
      >
        <SwiperSlide>
          <img src={slide_image_1} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_2} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_3} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_4} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_5} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_6} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_7} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_1} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_2} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_3} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_4} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_5} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_6} alt="slide_image" />
        </SwiperSlide>
        <SwiperSlide>
          <img src={slide_image_7} alt="slide_image" />
        </SwiperSlide>
      </Swiper>
    </div>
  );
}

export default SliderHome;
