import { Button, ButtonBase, Input } from '@mui/material';
import * as React from 'react';
import HourglassEmptyIcon from '@mui/icons-material/HourglassEmpty';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import FileUploadIcon from '@mui/icons-material/FileUpload';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';

import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

import { useState } from 'react'
import { useHistory, useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

import axios from 'axios';

function determineStatus(param) {
    if (
        param === "waitingInstructorAppointment" ||
        param === "waitingInstructorEvaluation" ||
        param === "waitingFinalConfirmation"
    ) {
        return "Waiting";
    } else if (param === "gradeSatisfactory") {
        return "Completed";
    } else if (param === "gradeUnsatisfactory") {
        return "Failed";
    } else if (param === "withdrawn") {
        return "Withdrawn";
    } else {
        return "Upload";
    }
}

function determineBackgroundColor(param) {
    console.log(param);

    if (param === "Waiting") {
        return "#fffac4"; // Yellow
    } else if (param === "Completed") {
        return "#b4d8c5"; // Green
    } else if (param === "Upload") {
        return "#c2dfff"; // Blue
    } else if (param === "Failed") {
        return "#f34444"; // Red
    } else {
        return "#a6a6a6"; // Gray
    }
}

let uploadedFile;

const handleFileOpen = () => {
    if (uploadedFile) {
        const fileUrl = URL.createObjectURL(uploadedFile);
        window.open(fileUrl, '_blank');
    }
};

function handleSubmit(params) {
    // upload file
    const formData = new FormData();
    formData.append(
        "file",
        params.file,
        params.file.name
    )
    console.log(params.file);
    axios.post('http://localhost:8080/upload',
        {
            folderId: params.folderId,
            file: params.file,
            courseId: params.courseId,
            description: params.description
        },
        {
            headers:
            {
                'Content-Type': 'multipart/form-data' // Important: Set the content type for form data
            }
        })
        .then((res) => {
            console.log("uploaded -" + params.file.name);
        })
        .catch((err) => {
            // inform the user
            console.error(err)
            //removeFile(uploadedFile.name)
        });
}

const UploadHandler = (event, param) => {

    const file = event.target.files[0];
    if (!file) return;
    uploadedFile = file;
    uploadedFile.isUploading = true;

    param.setFile(uploadedFile)
    console.log(param.file);
}

function Icons(props, status) {
    const [file, setFile] = useState();
    const [open, setOpen] = useState(false);
    const [text, setText] =useState();
    
    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    if (status === "Waiting") {
        return (<div className="Icons" style={{ display: "flex", width: "25%", height: "100%", justifyContent: "center", alignItems: "center", flex: 1 }}>
            <div style={{ border: '10px solid #1976d2', borderRadius: '500px' }}><HourglassEmptyIcon style={{ color: "#1976d2", width: "10vw", height: "15vh" }} /></div>
        </div>);

    }
    else {
        if (status === "Completed" || status == "Withdrawn" || status === "Failed") {
            if (status === "Completed") {
                return (<div className="Icons" style={{ display: "flex", width: "25%", height: "100%", justifyContent: "center", alignItems: "center", flex: 1 }}>
                    <CheckCircleOutlineIcon style={{ color: "#1976d2", width: "15vw", height: "20vh" }} />
                </div>);
            }
            else
                return (<div className="Icons" style={{ display: "flex", width: "25%", height: "100%", justifyContent: "center", alignItems: "center", flex: 1 }}>
                    <ErrorOutlineIcon style={{ color: "black", width: "15vw", height: "20vh" }} />
                </div>);
        }
        else {
            return (
                <div className="Icons" style={{ display: "flex", flexDirection: "column", width: "25%", height: "100%", justifyContent: "center", alignItems: "center", flex: 1 }}>
                    <input type="file"
                        onChange={(event) => {
                            UploadHandler(event, { status: props.status, files: file, setFile: setFile });
                            handleOpen();
                        }}
                        style={{ cursor: "pointer", opacity: 0, width: "10vw", height: "15vh", zIndex: "2", position: "absolute" }} />
                    <button style={{ border: '10px solid #1976d2', borderRadius: '45px' }}>
                        <FileUploadIcon style={{ zIndex: 1, color: "#1976d2", width: "8vw", height: "12vh" }} />
                    </button>
                    {uploadedFile && (
                        <div>
                            <div onClick={handleFileOpen} style={{ cursor: 'pointer', marginTop: "10px", borderRadius: "3px", fontSize: "1vw", padding: "4px", border: "3px solid #1976d2" }}>
                                {uploadedFile.name}
                            </div>
                        </div>
                    )}
                    <Dialog
                        open={open}
                        onClose={handleOpen}
                        aria-labelledby="alert-dialog-title"
                        aria-describedby="alert-dialog-description"
                    >
                        <DialogTitle id="alert-dialog-title">
                            {"Please write the description of your changes"}
                        </DialogTitle>
                        <DialogContent>
                            <textarea style={{ width: "30vw", height: "10vh" }} 
                            type="text"
                            placeHolder="Write your evaluation here..."
                            value={text}
                            onChange={(e) => {
                                setText(e.target.value);
                            }}
                            />
                        </DialogContent>
                        <DialogActions>
                            <Button onClick={handleClose}>Cancel</Button>
                            <Button onClick={(event) => { handleSubmit({ file: file, description: text, folderId: props.folderId, courseId: props.courseId }); handleClose() }} autoFocus>
                                Submit
                            </Button>
                        </DialogActions>
                    </Dialog>
                </div>);
        }
    }
}

function Buttons(status) {

    if (status === "Waiting") {
        return (<div style={{ display: "flex", flexDirection: "column", flex: 1 }}>
            <Button variant="contained" sx={{
                borderRadius: "2vw", width: "12vw", height: "4.5vh",
                fontSize: "0.9vw", margin: "auto", left: "4.5vw"
            }}> See Reports</Button>
        </div>);
    }
    else {
        if (status === "Completed" || status === "Failed" || status === "Withdrawn") {
            return (<div style={{ display: "flex", flexDirection: "column", marginBottom: "1.5vh", marginTop: "8vh", flex: 1 }}>
                <Button variant="contained" sx={{
                    borderRadius: "2vw", width: "12vw", height: "4.5vh",
                    fontSize: "0.9vw", marginBottom: "0.5em", left: "4.5vw"
                }}>See Reports</Button>
                <br /><br />
                <Button variant="contained" sx={{
                    borderRadius: "2vw", width: "12vw", height: "4.5vh",
                    fontSize: "0.9vw", left: "4.5vw"
                }}>See Feedback</Button>
            </div>
            );
        }
        else {
            return (<div style={{ display: "flex", flexDirection: "column", marginBottom: "0.2vh", marginTop: "0.5vh", flex: 1 }}>
                <Link to="/ReportsPage">
                    <Button variant="contained"
                        sx={{
                            borderRadius: "2vw", width: "12vw", height: "4.5vh",
                            fontSize: "0.9vw", marginBottom: "0.5vh", left: "4.5vw", top: "5vh"
                        }}>See Reports</Button>
                </Link>
                <br />
                <Button variant="contained" sx={{
                    borderRadius: "2vw", width: "12vw", height: "4.5vh",
                    fontSize: "0.9vw", marginBottom: "0.5vh", left: "4.5vw", top: "5vh"
                }}>See Feedback</Button>
                <br />
                <Button variant="contained" sx={{
                    backgroundColor: "#be3c3c",
                    borderRadius: "2vw", width: "13vw", height: "4.5vh",
                    fontSize: "0.9vw", marginBottom: "0.5vh", left: "4vw", top: "5vh"
                }}>Ask for Extension</Button>
            </div>);
        }
    }

}

export function StudentCard(props) {
    //first render uploadFile should be set to the file from localhost:8080 with useEffect
    const status = determineStatus(props.status);

    return (
        <div className="card" style={{
            backgroundColor: determineBackgroundColor(status), width: "55vw", height: "32vh",
            borderRadius: "65px", display: "flex", displayDirection: "column", marginBottom: "6vh"
        }}>
            <div className="info">
                <div style={{
                    color: "#be3c3c", fontSize: "1.5vw", fontWeight: "700", width: "7vw", height: "4.5vh", margin: 0,
                    position: "relative", top: "4vh", left: "3vw", flexBasis: "auto"
                }}>{props.courseName}</div>
                <div style={{
                    color: "#313030", fontSize: "1.2vw", fontWeight: "500", width: "14vw", height: "4.5vh", margin: 0,
                    position: "relative", top: "7vh", left: "3vw", flexBasis: "auto"
                }}>
                    Instructor: {props.instructor} <br /> <br />
                    Status: {status} <br /> <br />
                    Deadline: {props.deadline}
                </div>
            </div>

            <React.Fragment>
                {Buttons(status)}
            </React.Fragment>

            <React.Fragment>
                {Icons(props, status)}
            </React.Fragment>

        </div>

    );
}

export default StudentCard;
