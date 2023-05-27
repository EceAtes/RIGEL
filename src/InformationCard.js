import Switch from '@mui/material/Switch';
import * as React from 'react';
import BasicDateCalendar from "./SimpleCalendar.js";

export default function InfoCard(props) {
    const label = { inputProps: { 'aria-label': 'Switch demo' } };
    return (
        <div className="infoCard" style={{
            backgroundColor: "#6c9bff", width: "30vw", height: "70vh",
            borderRadius: "2vw", position: "relative", left: "5vw", top: "5vh",
            display: "flex", flexDirection: "column",
            justifyContent: "center", textAlign: "center"
        }}>
            <div className="writing"
                style={{ fontSize: "3vh", color: "white", fontWeight: 600, flex:1, paddingTop:"5vh",maxHeight:"30%" }}
            >
                {props.name} <br/>
                {props.studentid}  <br/>
                Courses: {props.courses} <br />
                {props.email} <br/>
                Notify me with email:
                <Switch {...label} defaultChecked color="warning" size="medium" />
            </div>
            <div className="divider" style={{backgroundColor:"white", height:"2px"}}></div>
            <div className="Calendar" style={{flex:1, display:"flex", height:"60%"}}>
                <BasicDateCalendar/>
            </div>
        </div>
    );
}