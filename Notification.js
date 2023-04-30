import React from "react";
import styles from "./mystyle.module.css";

const Notification = () => {
    return (
      <div className={styles.notification}>
        <img
          alt=""
          className={styles.notificationsIcon}
          src={require("./images/Notifications Icon.png")}
        />
        <p className={styles.notifications}>Notifications</p>
      </div>
    );
  };
  
  export default Notification;