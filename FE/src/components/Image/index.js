import { useState, forwardRef } from "react";

import classNames from "classnames/bind";
import styles from "./Image.module.scss";

import images from "~/assets/Image";

const Image = (
  { src, alt, className, fallback: customFallback = images.noImage, ...props },
  ref,
) => {
  const [fallback, setFallback] = useState("");

  const handleError = () => {
    setFallback(customFallback);
  };

  return (
    <img
      className={classNames(styles.wrapper, className)}
      ref={ref}
      src={src || fallback}
      alt={alt}
      {...props}
      onError={handleError}
    />
  );
};

export default forwardRef(Image);
