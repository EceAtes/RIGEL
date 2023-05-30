
import React, { iframe } from 'react';
import Header from "./Headers.js";
import Button from '@mui/material/Button';
import "./EvaluationPage.css";
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import { useState, useEffect } from 'react'
import { Navigate, useHistory, useNavigate } from 'react-router-dom';
import logo from './bilkent.png';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {sendFeedbackData, fetchInternshipReportKey} from "./apiConnect";

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

function RowRadioButtonsGroup(revision, setRevision,textareaDisabled) {
    
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
                <FormControlLabel disabled = {textareaDisabled} value="Yes" control={<Radio />} label="Yes" />
                <FormControlLabel disabled = {textareaDisabled} value="No" control={<Radio />} label="No" />
            </RadioGroup>
        </FormControl>
    );
}

function FeedbackReportPage() {
    const [studentFailed, setStudentFailed] = useState(false);
    const [option1, setOption1] = useState('not-selected');
    const [option2, setOption2] = useState('not-selected');
    const [textareaDisabled, setTextareaDisabled] = useState(true);

    const handleOption1Change = (e) => {
        setOption1(e.target.value);
        updateTextareaDisabledState(e.target.value, option2);
    };

    const handleOption2Change = (e) => {
        setOption2(e.target.value);
        updateTextareaDisabledState(option1, e.target.value);
    };

    const updateTextareaDisabledState = (option1Value, option2Value) => {
        const disableTextarea = !(option1Value === 'yes' && option2Value === 'yes');
        setTextareaDisabled(disableTextarea);
    };

    const linkImg = require("./bilkent.png");
    const [firstTime, setFirstTime] = useState(true);
    const [data, setData]= useState({});
    const [key, setKey]= useState();
    const [feedback, setFeedback] = useState();
    const [sent, setSent] = useState({});
    const [open, setOpen]= useState(false);
    const [revision, setRevision] = useState('No');
    const [criteria, setCriteria]= useState(false);
    const submit= {question:"Do you want to submit your feedback?", cancel: "Cancel", submit:"Submit"};
    const goto ={question:"Do you want to go criteria mode?", cancel: "Cancel", submit:"Go To"};
    const navigate = useNavigate();    
    useEffect(() => {
        const fetchData = async () => {
    
          const courseInfo = localStorage.getItem('arrayOfStructs');
 
          const index = localStorage.getItem('index');
          const index_number = parseInt(index, 10);
          
          const course = JSON.parse(courseInfo)[index_number];

          setData(course);
          localStorage.setItem('criteria-report', course.criteriaReportID);
          console.log(course);        
          const internship_report = course.lastInternshipReportID;
          console.log("INTERNSHIP REPORT ID:" + internship_report);
       

          
          try {
            const report_data = await fetchInternshipReportKey(internship_report);
            localStorage.setItem("internship-report", report_data.reportKey);
            console.log("Report key: " + report_data.reportKey);
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
        //setOpen(false);
        setCriteria(true);
        setOpen(false);
        navigate("/instructorMainPage");
        console.log("submitted- "+ body);
        axios.patch(`http://localhost:8080/internship_report/give_feedback/${data.course_id}`, 
        {
            feedback: body
        })
    };
    
    const handleOpen = (event) => {
        event.preventDefault();
        const courseIdVariable = parseInt(data.course_id, 10);
        console.log(`course id ENTER ${courseIdVariable}`);
        
        if (option1 === "no" || option2 === "no") {
            console.log(`student failed status: ${studentFailed}`);
            setStudentFailed(true);
          
            const isOption1True = option1 === "yes";
            const isOption2True = option2 === "yes";
          
            const object = {
              recommendation: "not_satisfactory",
              courseId: courseIdVariable,
              isRelated: isOption1True,
              isSupervisorEngineer: isOption2True,
              revisionRequired: false
            };
            
            console.log(`here comes the object, hello object, welcome : ${object}`)
            const url = "http://localhost:8080/grade-form";
          try{
            fetch(url, {
              method: 'POST',
              body: JSON.stringify(object),
              headers: {
                'Content-Type': 'application/json'
              }
            })
              .then(response => {
                if (response.ok) {
                  console.log("OK");
                  return response.json(); // Parse the JSON if the response is successful (status code 200)
                } else {
                  console.log("BAD");
                  throw new Error('Request failed with status code ' + response.status);
                }
              })
              .then(parsedData => {
                console.log(parsedData);
                navigate("/instructorMainPage");
              });
          } catch{
            navigate("/instructorMainPage");
          }
        }
          else {
            if (revision === "Yes") {
              const object = {
                recommendation: "satisfactory",
                courseId: courseIdVariable,
                revisionRequired: true,
                isRelated: true,
                isSupervisorEngineer: true
              } 





              try{
              const url = "http://localhost:8080/grade-form";
              console.log(`here comes the object, hello object, welcome : ${JSON.stringify(object)}`)
              fetch(url, {
                method: 'POST',
                body: JSON.stringify(object),
                headers: {
                  'Content-Type': 'application/json'
                }
              })
                .then(response => {
                  if (response.ok) {
                    console.log("OK");
                    return response.json(); // Parse the JSON if the response is successful (status code 200)
                  } else {
                    console.log("BAD");
                    throw new Error('Request failed with status code ' + response.status);
                  }
                })
                .then(parsedData => {
                  console.log(parsedData);
                  setRevision("Yes");
                  localStorage.setItem("status", "r");
                  navigate("./instructorMainPage");
                });
                }catch (error) {
                    console.error(error);
                    navigate("./instructorMainPage");
                }
            } else {
              setFirstTime(false);
              setOpen(true);
            }
        }  
        
};
    
    const handleOpenCriteria = (event) => {
        setCriteria(true);
    };
    
    const  handleCloseCriteria = (event) => {
        setCriteria(false);
    };
    
    const handleNavigate = () => {

        navigate("/criteriaMode");
    }
    
    return (key)? (
        <div className="EvaluationPage">
            <div className="instructorMainPage-header">
                <div className="instructorMainPage-image-div">
                <img src={logo} alt="Bilkent University logo" className="instructorMainPage-image" />
                <h2 className="instructorMainPage-header_title">INTERNSHIP MANAGEMENT SYSTEM</h2>
                </div>
                <img className="instructorMainPage-announcement_icon" />
                <img className="instructorMainPage-nofitication_icon" />
                <img className="instructorMainPage-logout_icon" />
            </div>
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
                    {firstTime &&  (
                        <>
                        <div>
                            <label>
                            Is the work done related to computer engineering? [Y/N]:
                            <input
                                type="radio"
                                name="option1"
                                value="yes"
                                checked={option1 === 'yes'}
                                onChange={handleOption1Change}
                            />{' '}
                            Yes
                            <input
                                type="radio"
                                name="option1"
                                value="no"
                                checked={option1 === 'no'}
                                onChange={handleOption1Change}
                            />{' '}
                            No
                            </label>
                        </div>
                        <div style = {{marginTop: "15px", marginBottom: "15px"}}>
                            <label>
                            Is the supervisor a computer engineer or has a similar engineering background? [Y/N] :
                            <input
                                type="radio"
                                name="option2"
                                value="yes"
                                checked={option2 === 'yes'}
                                onChange={handleOption2Change}
                            />{' '}
                            Yes
                            <input
                                type="radio"
                                name="option2"
                                value="no"
                                checked={option2 === 'no'}
                                onChange={handleOption2Change}
                            />{' '}
                            No
                            </label>
                        </div>
                        </>
                        )}
                        <React.Fragment >
                            {RowRadioButtonsGroup(revision, setRevision, textareaDisabled)}
                        </React.Fragment>
                        <textarea disabled={textareaDisabled} className="PostTextField"
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
                        <Button className="sendFeedback" variant="contained" onClick={handleOpen} sx={{ width: "16vw", height: "8vh", borderRadius: "3vh", marginTop: "5vh", left: "5vw" }}>SUBMIT</Button>
                    </div>
                </div>
            </div>
            <React.Fragment>
                {Popup(submit, open, handleOpen, handleClose, handleGiveFeedback)}
            </React.Fragment>
            {!studentFailed ? (
            <React.Fragment>
                {Popup(goto, criteria, handleOpenCriteria, handleCloseCriteria, handleNavigate)}
            </React.Fragment>
            ):null}
        </div >
    ): null;
}

export default FeedbackReportPage;