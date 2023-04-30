import React from "react";
import Notification from "./Notification.js";
import Profile from "./Profile.js";
import Language from "./Language.js";
import styles from "./mystyle.module.css";

const MenuContainer = () => {
    return (
      <div className={styles.MenuContainer}>
          <Notification/>
          <Profile/>
          <Language/>
      </div>
    )
}
export default MenuContainer;