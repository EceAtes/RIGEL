import React from "react";
import styles from "./mystyle.module.css";

const LogoutButton = () => {
  return (
    <div className={styles.logOutButton}>
      <p className={styles.logOut}> LOG OUT</p>
    </div>
  );
};

export default LogoutButton;