import "./criteriaPage.css";
import Header from "./Headers.js";
import QuestionGrid from "./QuestionGrid.js";
import Button from '@mui/material/Button';
import FileOpenIcon from '@mui/icons-material/FileOpen';
import React, { useState, useEffect } from 'react';
import { fetchCriteriReportData, fetchInternshipReportKey, sendCriteriReportData, submitCriteriReport } from './apiConnect.js';

import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

import axios from "axios";
import { Alert } from "@mui/material";
import { useNavigate } from "react-router-dom";

const evalScore = [
    ['', ''],
    ['', ''],
    ['', ''],
    ['', ''],
    ['', ''],
    ['', ''],
    ['', ''],
    ['', '']
];

const questions = ["", "", "", "", "", "", "", ""];
/*
function Popup(props, open, handleOpen, handleClose, handleSave, setCompany) {
    if (!open) return;

    return (
        <Dialog
            open={open}
            onClose={handleOpen}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle id="alert-dialog-title">
                {RadioButtonsGroup(setCompany)}
            </DialogTitle>
            <DialogActions>
                <Button onClick={handleClose}>{props.cancel}</Button>
                <Button onClick={handleSubmit} autoFocus>
                    {props.submit}
                </Button>
            </DialogActions>
        </Dialog>

    );
}

function RadioButtonsGroup(setCompany) {
    
    const handleChange = (event) => {
        setCompany(event.target.value);
    };
    
  return (
    <FormControl>
      <FormLabel sx={{ color: "black" }} id="demo-radio-buttons-group-label"></FormLabel>
      <RadioGroup
        aria-labelledby="demo-radio-buttons-group-label"
        defaultValue={null}
        onChange={handleChange}
        name="radio-buttons-group"
      >
        <FormControlLabel value="I strongly recommend this place for future students" control={<Radio />} label="I strongly recommend this place for future students" />
        <FormControlLabel value="I am satisfied with this place" control={<Radio />} label="I am satisfied with this place" />
        <FormControlLabel value="I recommend this place not to be allowed for future students" control={<Radio />} label="I recommend this place not to be allowed for future students" />
      </RadioGroup>
    </FormControl>
  );
  
}*/
function CriteriaReportPage() {
    const linkImg = require("./bilkent.png");
    
    const [saveButton, setSaveButton] = useState(false);
    const [submitButton, setSubmitButton] = useState(false);
    const [nextButton, setNext] = useState(false);
    const [fetched, setFetched]= useState(false);
    const [key, setKey]= useState();
    const [course, setCourse]= useState();
    const [company, setCompany]= useState();
    const navigate = useNavigate();
    
    useEffect(() => {
        /*const courseInfo = JSON.parse(localStorage.getItem('arrayOfStructs'));
        const index = parseInt(localStorage.getItem('index'), 10);
        const course = courseInfo[index];
        setCourse(course);
        const criteri_report = course.criteriaReportID;
        const internship_report = course.lastInternshipReportID;*/
        
        const fetchData = async () => {
            const lastInternshipReport = localStorage.getItem("internship-report");
            setKey(lastInternshipReport);
            console.log(lastInternshipReport);
            const course = localStorage.getItem("criteria-report");
            console.log(`criteria report id:  ${course}`);
            try {
                const data = await fetchCriteriReportData(course);
                console.log(data.questions)
                data.questions.map((item, index) => {
                    questions[index]= item.question;
                    evalScore[index][0]=item.answer;
                    evalScore[index][1]=item.score;
                  });
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
    
    
    function makeBody( myAnswer, myScore){
        return {answer: myAnswer, score: myScore};
    }
    
    const handleSave = (e) => {
        e.preventDefault();
        setSaveButton(true);
        evalScore.map((question, index) => {
            const body= makeBody(question[0], question[1]);
            sendCriteriReportData(body, 1, index);
          });
    }
    
    const handleSubmit = (e) => {
        e.preventDefault();
        const body= {courseId: 1};
        handleSave(e);
        submitCriteriReport(body);
        navigate("/instructorMainPage");
    }
    
    return(fetched) ? (
        <div className="CriteriaReportPage" style={{display:"fixed"}}>
            <Header
                link={linkImg}
                headerText="Evaluate Report"
            />
            <div className="answerDriveReport" style={{display:"flex",displayDirection:"column" }}>
                <div className="criteria" style={{width:"55vw"}}>
                <QuestionGrid
                sendData={handleData}
                questions={questions}
                fillin={evalScore}
                />
                </div>
                <iframe src={`https://drive.google.com/file/d/${key}/preview`} style={{width:"45vw", height:"100vh"}}/>
            </div>
            
            <div className="buttonHolder"
                style={{ display: "flex", flexDirection: "row", jusfyContent: "space-between", width: "30vw", paddingBottom: "6vh", position: "relative", left: 150, top: 80, bottom: 100 }}>
                <div style={{ marginRight: 'auto' }}><Button className="Save" variant="outlined"
                    sx={{
                        "width": "10vw",
                        "height": "8vh",
                        "font-size": "20px",
                        "font-weight": "bold",
                        "border": "3px solid rgb(25, 118, 210, 0.5)",
                        "border-radius": "15px"
                    }}
                    onClick={handleSave}>Save</Button></div>
                <div style={{ marginRight: 'auto' }} onClick={handleSubmit}><Button className="Submit" variant="contained"
                    sx={{
                        "width": "10vw",
                        "height": "8vh",
                        "font-size": "20px",
                        "font-weight": "bold",
                        "border-radius": "15px"
                    }}
                >Submit</Button></div>
            </div>
        </div>
    ) : null;
}

export default CriteriaReportPage;