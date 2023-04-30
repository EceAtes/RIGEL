import React from "react";
import styles from "./mystyle.module.css";

const Headers = () => {
  return (
    <div className={styles.headers}>
      <img
        alt=""
        className={styles.bilkentLogo}
        src={require("./images/bilkent logo.png")}
      />
      <p
        className={styles.bilkentInternshipManagementSystem}
      >
        Internship Management System
      </p>
      <div className={styles.oklar}>
        <img
          alt=""
          className={styles.solOk}
          src={require("./images/sol ok.png")}
        />
        <img
          alt=""
          className={styles.sagOk}
          src={require("./images/sağ ok.png")}
        />
      </div>
    </div>
  );
};

export default Headers;