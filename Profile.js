import React from "react";
import styles from "./mystyle.module.css";

const Profile = () => {
  return (
    <div className={styles.profileContainer}>
      <img
        alt=""
        className={styles.profileIcon}
        src={require("./images/Profile Icon.png")}
      />
      <p className={styles.profile}>Profile</p>
    </div>
  );
};

export default Profile;