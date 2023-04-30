import React from "react";
import styles from "./mystyle.module.css";

const AnnouncementsImage = () => {
  return (
    <div className={styles.announcementsImageTwo}>
      <img
        alt=""
        className={styles.announcementsImage}
        src={require("./images/announcements-image.png")}
      />
    </div>
  );
};

export default AnnouncementsImage;