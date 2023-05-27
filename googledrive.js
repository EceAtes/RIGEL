import React from 'react';
import './instructorMainPage.css'
import logo from './bilkent.png'
const GoogleDrive = () => {

    return(
        <div style = {{display: "flex", flexDirection: "column"}}>
            
            <div className="instructorMainPage-header">
            <div className="instructorMainPage-image-div">
                <img src={logo} alt="Bilkent University logo" className="instructorMainPage-image" />
                <h2 className="instructorMainPage-header_title">INTERNSHIP MANAGEMENT SYSTEM</h2>
            </div>
            <img className="instructorMainPage-announcement_icon" />
            <img className="instructorMainPage-nofitication_icon" />
            <img className="instructorMainPage-logout_icon" />
            </div>
            <h3 style = {{marginBottom: "0px", fontFamily: "Ariel"}} className="instructorMainPage-header_welcome_message">Reports Page</h3>
            <iframe src="https://drive.google.com/embeddedfolderview?id=1CtWsQkZJJUHH5lEn9cgap7AukpVonJSc" style={{ width:"100vw", height:"100vh", backgroundColor: 'white'}}/>
        </div>
      );
};
export default GoogleDrive; 