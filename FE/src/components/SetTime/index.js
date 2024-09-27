import React, { useState, useEffect } from "react";

function TimeConverter({ timestamp, sx }) {
  const [formattedTime, setFormattedTime] = useState("");

  useEffect(() => {
    const calculateTimeDifference = (timestamp) => {
      const now = new Date().getTime();
      const timestampDate = new Date(timestamp).getTime();
      const difference = now - timestampDate; // Chuyển timestamp về mili giây

      const seconds = Math.floor(difference / 1000);
      const minutes = Math.floor(seconds / 60);
      const hours = Math.floor(minutes / 60);
      const days = Math.floor(hours / 24);

      if (seconds < 60) {
        return `${seconds} giây trước`;
      } else if (minutes < 60) {
        return `${minutes} phút trước`;
      } else if (hours < 24) {
        return `${hours} giờ trước`;
      } else if (days < 30) {
        return `${days} ngày trước`;
      } else {
        // ... xử lý các trường hợp khác như tháng, năm
        return new Date(timestampDate).toLocaleDateString();
      }
    };

    setFormattedTime(calculateTimeDifference(timestamp));
  }, [timestamp]);

  return <div style={sx}>{formattedTime}</div>;
}

export default TimeConverter;
