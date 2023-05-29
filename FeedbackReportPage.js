
import React, { iframe } from 'react';
import Header from "./common_mainpage_comp/Headers.js";
import Button from '@mui/material/Button';
import "./eval_page_comp/EvaluationPage.css";
import 'react-pdf/dist/esm/Page/AnnotationLayer.css';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import { useState, useEffect } from 'react'
import { useHistory, useNavigate } from 'react-router-dom';

import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {sendFeedbackData, fetchInternshipReportKey} from "./api_connection/apiConnect";

import axios from 'axios';

function Popup(props, open, handleOpen, handleClose, handleGiveFeedback) {
    if (!open) return;

    return (
        <Dialog
            open={open}
            onClose={handleOpen}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle id="alert-dialog-title">
                {props.question}
            </DialogTitle>
            <DialogActions>
                <Button onClick={handleClose}>{props.cancel}</Button>
                <Button onClick={handleGiveFeedback} autoFocus>
                    {props.submit}
                </Button>
            </DialogActions>
        </Dialog>

    );
}

function RowRadioButtonsGroup(revision, setRevision) {
    
    const handleChange = (event) => {
        setRevision(event.target.value);
    };
    
    return (
        <FormControl>
            <FormLabel id="demo-row-radio-buttons-group-label" sx={{ color: "black" }}>Do you request revision?</FormLabel>
            <RadioGroup
                row
                aria-labelledby="demo-row-radio-buttons-group-label"
                name="row-radio-buttons-group"
                value={revision}
                onChange={handleChange}
            >
                <FormControlLabel value="Yes" control={<Radio />} label="Yes" />
                <FormControlLabel value="No" control={<Radio />} label="No" />
            </RadioGroup>
        </FormControl>
    );
}

function FeedbackReportPage() {
    const linkImg = require("./images/reports-img.png");
    const [data, setData]= useState({});
    const [key, setKey]= useState();
    const [feedback, setFeedback] = useState();
    const [sent, setSent] = useState({});
    const [open, setOpen]= useState(false);
    const [revision, setRevision] = useState('No');
    const [criteria, setCriteria]= useState(false);
    const submit= {question:"Do you want to submit your feedback?", cancel: "Cancel", submit:"Submit"};
    const goto ={question:"Do you want to go criteria mode?", cancel: "Cancel", submit:"Go To"};
    
    useEffect(() => {
        const fetchData = async () => {
          /*const courseInfo = localStorage.getItem('arrayOfStructs');
          const index = localStorage.getItem('index');
          const course = courseInfo[index];
          setData(course);
      
          const internship_report = course.lastInternshipReportID;*/
          try {
            const report_data = await fetchInternshipReportKey(6);
            setKey(report_data.reportKey);
          } catch (error) {
            console.log('Error fetching internship report:', error);
          }
        };
      
        fetchData();
      }, []);
      
    const handleClose = (event) => {
        setOpen(false);
    };
    //        axios.patch(`http://localhost:8080/internship_report/give_feedback/${data.course_id}`, 

    const handleGiveFeedback = (event, company) => {
        setSent({feedback:feedback});
        const body = `"${feedback}"`;       
        setOpen(false);
        setCriteria(true);
        console.log("submitted- "+ body);
        axios.patch(`http://localhost:8080/internship_report/give_feedback/2`, 
        {
            feedback: body,
        },
        {
            headers:
            {
                'Content-Type': 'multipart/form-data' // Important: Set the content type for form data
            }
        })
    };
    
    const handleOpen = (event) => {
        setOpen(true);
    };
    
    const handleOpenCriteria = (event) => {
        setCriteria(true);
    };
    
    const  handleCloseCriteria = (event) => {
        setCriteria(false);
    };
    
    const handleNavigate = (even) => {
        
    }
    
    return (key)? (
        <div className="EvaluationPage">
            <Header
                link={linkImg}
                headerText="Evaluate Report"
            />
            <div className="EvaluationPanel"
                style={{ "width": "100%", "height": "100vh" }}
            >

                <div classnName="ReportHolder"
                    style={{
                        "display": "inline-block",
                        "verticalAlign": "top", "width": "70%", "height": "100%"
                    }}
                >
                    <iframe src={`https://drive.google.com/file/d/${key}/preview`} width="100%" height="100%" allow="autoplay"></iframe>
                </div>

                <div classnName="FeedbackHolder"
                    style={{
                        "display": "inline-block",
                        "verticalAlign": "top", "width": "30%", "height": "100%"
                    }}>

                    <div className="feedbackItems"
                        style={{ width: "80%", height: "100%", display: "flex", flexDirection: "column", jusfyContent: "space-between", margin: "auto", paddingTop: "10vh" }}>
                        <React.Fragment>
                            {RowRadioButtonsGroup(revision, setRevision)}
                        </React.Fragment>
                        <textarea className="PostTextField"
                            type="text"
                            placeHolder="Write your feedback here..."
                            value={feedback}
                            onChange={(e) => {
                                setFeedback(e.target.value);
                            }}
                            style={{
                                width: "25vw",
                                height: "30vh",
                                textAlign: "leftcenter",
                                padding: "3px 3px",
                                marginBottom: "30px",
                                border: "1px solid rgba(25, 118, 210, 0.5)",
                                fontSize: "1.2vw",
                                fontFamily: "Arial",
                                color: "black",
                                margin: 0
                            }}
                        />
                        <Button className="sendFeedback" variant="contained" onClick={handleOpen} sx={{ width: "16vw", height: "8vh", borderRadius: "3vh", marginTop: "5vh", left: "5vw" }}>Give Feedback</Button>
                    </div>
                </div>
            </div>
            <React.Fragment>
                {Popup(submit, open, handleOpen, handleClose, handleGiveFeedback)}
            </React.Fragment>
            
            <React.Fragment>
                {Popup(goto, criteria, handleOpenCriteria, handleCloseCriteria, handleNavigate )}
            </React.Fragment>
        </div >
    ): null;
}

export default FeedbackReportPage;