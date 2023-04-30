import React from "react";
import styles from "./mystyle.module.css";
import MenuContainer from "./MenuContainer.js";

const ProgressBar = () => {
  return (
    <div className={styles.progressBar}>
      <div className={styles.relativeWrapperOne}>
        <div className={styles.allRect} />
        <div className={styles.completeRect} />
        <p className={styles.completionLabel}>%40 Complete</p>
      </div>
      <MenuContainer/>
    </div>
  );
};

export default ProgressBar;