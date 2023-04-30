import React from "react";
import AnnouncementsImage from "./AnouncementsImage.js";
import ReportsImg from "./ReportsImg.js";
import styles from "./mystyle.module.css";

const AnnounceReportCard = () => {
    return (
      <div className={styles.announceReportCard}>
          <p className={styles.anouncementLabel}>
            ANNOUNCEMENT PAGE
        </p>
        <AnnouncementsImage
          className={styles.announcementsImage}
        />
        <p className={styles.reportPageLabel}>
            REPORT PAGE
        </p>
        <ReportsImg className={styles.reportsImg} />
      </div>
    );
  };
  
  export default AnnounceReportCard;