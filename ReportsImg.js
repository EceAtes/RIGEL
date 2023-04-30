import React from "react";
import styles from "./mystyle.module.css";

const ReportsImg = () => {
  return (
    <div className={styles.reportsImgTwo}>
      <img
        alt=""
        className={styles.reportsImg}
        src={require("./images/reports-img.png")}
      />
    </div>
  );
};

export default ReportsImg;