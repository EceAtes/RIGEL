import { Button, ButtonBase, Input } from '@mui/material';
import * as React from 'react';
import HourglassEmptyIcon from '@mui/icons-material/HourglassEmpty';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import FileUploadIcon from '@mui/icons-material/FileUpload';

import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';

import { useState } from 'react'
import { useHistory, useNavigate } from 'react-router-dom';

let uploadedFile;

const handleFileOpen = () => {
    if (uploadedFile) {
        const fileUrl = URL.createObjectURL(uploadedFile);
        window.open(fileUrl, '_blank');
    }
};

const uploadHandler = (event, props) => {
    const file = event.target.files[0];
    if (!file) return;
    uploadedFile = file;
    uploadedFile.isUploading = true;

    // upload file
    const formData = new FormData();
    formData.append(
        props.status,
        uploadedFile,
        uploadedFile.name
    )
    console.log(formData);
    props.setFile(uploadedFile)
    console.log(uploadedFile);
    console.log(props.file);
    /*
    axios.post('', formData)
        .then((res) => {
            uploadedFile.isUploading = false;
            props.setFile([...props.files, uploadedFile])
        })
        .catch((err) => {
            // inform the user
            console.error(err)
            removeFile(uploadedFile.name)
        });
        */
}

function Icons(props, handleOpen) {
    const [file, setFile] = useState();

    
    if (props.status === "Waiting") {
        return (<div className="Icons" style={{ display: "flex", width: "25%", height: "100%", justifyContent: "center", alignItems: "center", flex: 1 }}>
            <div style={{ border: '10px solid #1976d2', borderRadius: '500px' }}><HourglassEmptyIcon style={{ color: "#1976d2", width: "10vw", height: "15vh" }} /></div>
        </div>);

    }
    else {
        if (props.status === "Completed") {
            return (<div className="Icons" style={{ display: "flex", width: "25%", height: "100%", justifyContent: "center", alignItems: "center", flex: 1 }}>
                <CheckCircleOutlineIcon style={{ color: "#1976d2", width: "15vw", height: "20vh" }} />
            </div>);
        }
        else {
            return (
                <div className="Icons" style={{ display: "flex", flexDirection: "column", width: "25%", height: "100%", justifyContent: "center", alignItems: "center", flex: 1 }}>
                    <input type="file"
                        onChange={(event) => uploadHandler(event, { status: props.status, files: file, setFile: setFile })}
                        style={{ cursor: "pointer", opacity: 0, width: "10vw", height: "15vh", zIndex: "2", position: "absolute" }} />
                    <button style={{ border: '10px solid #1976d2', borderRadius: '45px' }}>
                        <FileUploadIcon style={{ zIndex: 1, color: "#1976d2", width: "8vw", height: "12vh" }} />
                    </button>
                    {uploadedFile && (
                        <div>
                            <div onClick={handleFileOpen} style={{ cursor: 'pointer', marginTop: "10px", borderRadius: "3px", fontSize: "1vw", padding: "4px", border: "3px solid #1976d2" }}>
                                {uploadedFile.name}
                            </div>
                            <Button variant="contained" onClick={handleOpen} sx={{
                                backgroundColor: "#be3c3c", borderRadius: "2vw", width: "12vw", height: "4.5vh",
                                fontSize: "0.9vw", left: "4.5vw", top: "2vh"
                            }}>Submit Report</Button>
                        </div>
                    )}
                </div>);
        }
    }
}

function Buttons(status, goToReports) {


    if (status === "Waiting" || status === "1st Submission") {
        return (<div style={{ display: "flex", flexDirection: "column", flex: 1 }}>
            <Button variant="contained" sx={{
                borderRadius: "2vw", width: "12vw", height: "4.5vh",
                fontSize: "0.9vw", margin: "auto", left: "4.5vw"
            }}> {(status === "Waiting") ? "Previous Reports" : "Ask for Extension"}</Button>
        </div>);
    }
    else {
        if (status === "Completed") {
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
                <Button variant="contained"
                    sx={{
                        borderRadius: "2vw", width: "12vw", height: "4.5vh",
                        fontSize: "0.9vw", marginBottom: "0.5vh", left: "4.5vw", top: "5vh"
                    }}>See Reports</Button>
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
    const [open, setOpen] = useState(false);


    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div className="card" style={{
            backgroundColor: (props.status === "Waiting") ? "#fffac4" : (props.status === "Completed") ? "#b4d8c5" : "#c2dfff", width: "55vw", height: "32vh",
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
                    Status: {props.status} <br /> <br />
                    Deadline: {props.deadline}
                </div>
            </div>

            <React.Fragment>
                {Buttons(props)}
            </React.Fragment>

            <React.Fragment>
                {Icons(props, handleOpen)}
            </React.Fragment>

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
                    <textarea style={{width:"30vw",height:"10vh"}}/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleClose} autoFocus>
                        Submit
                    </Button>
                </DialogActions>
            </Dialog>
 
        </div>

    );
}

export default StudentCard;