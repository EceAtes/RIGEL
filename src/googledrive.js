import React, { useEffect, useState } from 'react';
import './instructorMainPage.css'
import logo from './bilkent.png'
import axios from 'axios';

const GoogleDrive = () => {
    const [key, setKey]= useState();

    useEffect(() =>{
        const fetchData = async () => {
        try {
            const folder= localStorage.getItem("folder-id");
            console.log(folder);
            //const response = await axios.get(`http://localhost:8080/users/1`);
            setKey(folder)
          } catch (error) {
            throw new Error('Failed to fetch user data');
        }
    }
        fetchData();
    },[]);

    
    return(key) ? (
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
            <h3 className="instructorMainPage-header_welcome_message" style = {{marginTop: "0px",marginBottom: "0px", width: "96%"}}>Reports Page</h3>
            <iframe src={`https://drive.google.com/embeddedfolderview?id=${key}`} style={{ width:"100vw", height:"100vh", backgroundColor: 'white'}}/>
        </div>
      ): null;
};
export default GoogleDrive; 