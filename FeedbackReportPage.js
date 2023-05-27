
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
import {sendFeedbackData} from "./api_connection/apiConnect";

function Popup(open, handleOpen, handleClose, handleGiveFeedback) {
    if (!open) return;

    return (
        <Dialog
            open={open}
            onClose={handleOpen}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle id="alert-dialog-title">
                {"Do you want to submit your feedback?"}
            </DialogTitle>
            <DialogActions>
                <Button onClick={handleClose}>Cancel</Button>
                <Button onClick={handleGiveFeedback} autoFocus>
                    Submit
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
    const url = "https://drive.google.com/file/d/1OHFYGVO6U3jCSuuSl_8ZYe2TQjGDt3Qi/preview";
    const [feedback, setFeedback] = useState();
    const [sent, setSent] = useState({});
    const [open, setOpen]= useState(false);
    const [revision, setRevision] = useState('No');
    
    /*useEffect(() => {
        console.log(sent);
      }, [sent]);*/
      
    const handleClose = (event) => {
        setOpen(false);
    };
    
    const handleGiveFeedback = (event) => {
        setSent({feedback:feedback, feedback_giver_id:4, internship_report_id: 1});
        const feedbackData= {feedback:feedback, feedback_giver_id:4, internship_report_id: 1};
        setOpen(false);
        console.log("submitted");
        sendFeedbackData(feedbackData);
    };
    
    const handleOpen = (event) => {
        setOpen(true);
    };
    
    return (
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
                    <iframe src="https://drive.google.com/file/d/1OHFYGVO6U3jCSuuSl_8ZYe2TQjGDt3Qi/preview" width="100%" height="100%" allow="autoplay"></iframe>
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
                {Popup(open, handleOpen, handleClose, handleGiveFeedback )}
            </React.Fragment>
        </div >
    );
}

export default FeedbackReportPage;