import React from "react";
import "./header.css";
import LogoutIcon from '@mui/icons-material/Logout';
import NotificationsNoneIcon from '@mui/icons-material/NotificationsNone';
import CampaignIcon from '@mui/icons-material/Campaign';

export function Header () {
  const imgLogo= require("./bilkent.png")
  return (
    <div className={"headers"}>
      <img
        alt=""
        className={"bilkentLogo"}
        src={imgLogo}
      />
      <p className={"bilkentInternshipManagementSystem"}>
        Internship Management System
      </p>
      <div className="iconHolder" style={{position:"relative", top:"0.5vh", right:"3vw"}}>
        <CampaignIcon style={{color:"white", height:"6vh", width:"4vw"}}/>
        <NotificationsNoneIcon style={{color:"white", height:"6vh", width:"4vw"}}/>
        <LogoutIcon style={{color:"white", height:"6vh", width:"4vw"}}/>
      </div>
    </div>
  );
}

export function NameHeader (props) {
  return ( 
    <div className="nameHeader" style={{width:"100vw" ,height:"5vh",backgroundColor: "#8cc6ff", display:"flex", height:"7vh", alignItems: "flex-center",   justifyContent: "flex-center"}}>
      <p style={{color: "white", display:"flex", fontSize:"4vh",margin:0, fontWeight:"500", position:"relative", left:"5vw", top:"1.5vh"}}>Welcome {props.name}</p>
    </div>
  );
}

export default Header;