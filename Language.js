import React from "react";
import styles from "./mystyle.module.css";

const Language = () => {
  return (
    <div className={styles.languageContainer}>
      <img
        alt=""
        className={styles.languageIcon}
        src={require("./images/language icon.png")}
      />
      <p className={styles.language}>Language</p>
    </div>
  );
};

export default Language;