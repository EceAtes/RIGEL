import "./criteria_page_comp/criteriaPage.css";
import Header from "./common_mainpage_comp/Header.js";
import QuestionGrid from "./criteria_page_comp/QuestionGrid.js";
import Button from '@mui/material/Button';
import Popup from "./popup_pages/SavePopup";
import React, { useState, useEffect } from 'react';
import { fetchUserData } from './api_connection/fetchData.js';
import axios from "axios";

const evalScore = [
    ['', ''],
    ['', ''],
    ['', ''],
    ['', ''],
    ['', ''],
    ['', ''],
    ['', '']
];

const questions = ["", "", "", "", "", "", ""];

function CriteriaReportPage() {
    const linkImg = require("./images/reports-img.png");
    
    const [saveButton, setSaveButton] = useState(false);
    const [submitButton, setSubmitButton] = useState(false);
    const [nextButton, setNext] = useState(false);
    const [fetched, setFetched]= useState(false);
    
    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await fetchUserData(6);
                questions[0] = data.name;
                evalScore[0][0] = data.email;
                evalScore[0][1] = data.id;
                setFetched(true);
                console.log(evalScore);
            } catch (error) {
            }
        };
    
        fetchData();
    }, []);

    const handleData = (rowNo, colNo, newData) => {
        evalScore[rowNo][colNo] = newData;
        console.log(evalScore)
    }
    
    /*const createPost =(evalScore, postData) =>{
        evalScore.map((row, index) => (
            
        ));
    }*/
    
    const handleSave = (e) => {
        e.preventDefault();
        setSaveButton(true)
        axios.post("http://localhost:8080/users", 
        {   name: questions[0],
            email:evalScore[0][0],
            password :evalScore[0][1],
            notifToMail:true,
            role:2,
            department_id:1
        }).then(res => console.log("posting data", res))
        .catch(err => console.log(err))
    }
    
    const handleSubmit = () => {
        /*Router to another path*/
        
    }
    return(fetched) ? (
        <div className="CriteriaReportPage" style={{display:"fixed"}}>
            <Header
                link={linkImg}
                headerText="Evaluate Report"
            />
            <QuestionGrid
                title="Evaluation of Work"
                sendData={handleData}
                questions={questions}
                fillin={evalScore}
            />

            <div className="buttonHolder"
                style={{ display: "flex", flexDirection: "row", jusfyContent: "space-between", width: 700, margin: 0, position: "relative", left: 150, top: 100, bottom: 100 }}>
                <div style={{ marginRight: 'auto' }}><Button className="Save" variant="outlined"
                    sx={{
                        "width": "150px",
                        "height": "80px",
                        "font-size": "20px",
                        "font-weight": "bold",
                        "border": "3px solid rgb(25, 118, 210, 0.5)",
                        "border-radius": "15px"
                    }}
                    onClick={handleSave}>Save</Button></div>
                <div style={{ marginRight: 'auto' }}><Button className="Submit" variant="contained"
                    sx={{
                        "width": "180px",
                        "height": "80px",
                        "font-size": "20px",
                        "font-weight": "bold",
                        "border-radius": "15px"
                    }}
                >Submit</Button></div>
                <div style={{ marginRight: 'auto' }}><Button className="Next" variant="outlined"
                    sx={{
                        "width": "150px",
                        "height": "80px",
                        "font-size": "20px",
                        "font-weight": "bold",
                        "border": "3px solid rgb(25, 118, 210, 0.5)",
                        "border-radius": "15px"
                    }}
                >Next</Button></div>
            </div>
            <Popup message="Evaluation of Work is saved. Do you want to go back to Evaluate Report Page?" trigger={saveButton} setTrigger={setSaveButton} />
            <Popup message="Evaluation of Work is submitted. Are you sure?" trigger={submitButton} setTrigger={setSubmitButton} />
        </div>
    ) : null;
}

export default CriteriaReportPage;