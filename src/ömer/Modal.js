import Navbar from "./navbar";
import {Box, Collapse, IconButton, Table, Typography, Button, InputLabel, Select, MenuItem, FormGroup} from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import { Paper, TableContainer, TableCell, TableHead, TableRow, TableBody, Checkbox, FormControlLabel} from "@mui/material";
import React, {useState, useRef, useEffect} from "react";
import InputBase from '@mui/material/InputBase';
import { styled, alpha } from '@mui/material/styles';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import { useNavigate } from "react-router-dom";
import Modal from '@mui/material/Modal';
import FormControl from '@mui/material/FormControl';
import { UploadFile } from "@mui/icons-material";
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import axios from "axios";
import logo from "../bilkent.png";
import SecretaryListPage from "../SecretaryPage.js";

const StyledInputBase = styled(InputBase)(({ theme }) => ({
    color: 'inherit',
    '& .MuiInputBase-input': {
      padding: theme.spacing(1, 1, 1, 0),
      // vertical padding + font size from searchIcon
      paddingLeft: `calc(1em + ${theme.spacing(4)})`,
      transition: theme.transitions.create('width'),
      width: '100%',
      [theme.breakpoints.up('md')]: {
        width: '40ch',
      },
    },
  }));

const CustomInput = styled('div')(({ theme }) => ({
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: alpha(theme.palette.common.white, 0.15),
    '&:hover': {
      backgroundColor: alpha(theme.palette.common.white, 0.25),
    },
    marginRight: theme.spacing(2),
    marginLeft: 0,
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      marginLeft: theme.spacing(3),
      width: 'auto',
    },
  }));


const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    maxWidth: 1000,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
  };

export function BasicModal (modal, setModal) {
    const [userType, setUserType] = useState(""); // State for user type selection

    const handleUserTypeChange = (event) => {
        setUserType(event.target.value); // Update user type when selection changes
    };

    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setModal(false);
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    const [selectedValues, setSelectedValues] = React.useState({
        roleSelect: '',
        courseSelect: [],
        idSelect: 0,
        companyGrade: 0,

    });

    const handleCheckboxChange = (event, userName) => {
        const isChecked = event.target.checked;
        const courseValue = event.target.value;
        setSelectedValues(prevValues => {
            if (isChecked) {
                // Add the selected course to the array
                return {
                    ...prevValues,
                    [userName]: [...prevValues[userName], courseValue],
                };
            } else {
                // Remove the deselected course from the array
                return {
                    ...prevValues,
                    [userName]: prevValues[userName].filter(course => course !== courseValue),
                };
            }
        });
    };
    // ...

    const handleSelectChange = (event, userName) => {
        const selectedValue = event.target.value;
        setSelectedValues(prevValues => ({
            ...prevValues,
            [userName]: selectedValue,
            idSelect: selectedValue
        }));
    };

    const [inputValues, setInputValues] = React.useState({
        nameInput: '',
        surnameInput: '',
        emailInput: '',

    });
    const [finalObject, setFinalObject] = React.useState({});

    const handleInputChange = (event, attributeName) => {
        const value = event.target.value;

        setInputValues(prevValues => ({
            ...prevValues,
            [attributeName]: value,
        }));
    };

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    const uwu = localStorage.getItem("departmentId");
    const handleSubmit = (event) => {
        event.preventDefault();

        // Create your object using the form data
        const myObject = {
            name: inputValues.nameInput,
            department_id: uwu,
            email: inputValues.emailInput,
            role: userType,
            studentId: inputValues.idSelect,
            courses: selectedValues.courseSelect,
            password: inputValues.password
        };


        console.log(myObject);
        axios.post("http://localhost:8080/users", myObject)
            .then(res => console.log("posting data", res))
            .catch(err => console.log(err))

        setInputValues({
            nameInput: '',
            surnameInput: '',
            emailInput: '',
        });
    };

    return (modal) ? (
        <div>
            <Modal
                open={modal}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <div style={{ display: "flex", flexDirection: "row" }}>

                        <FormControl component="form" onSubmit={handleSubmit} sx={{ marginLeft: '2vw', width: "9vw" }} size="small">
                            <InputLabel id="demo-select-small-label" sx={{ color: 'black' }}>Role</InputLabel>
                            <Select
                                value={userType}
                                onChange={handleUserTypeChange}
                                labelId="demo-select-small-label"
                                id="roleSelect"
                                label="select-role"
                                sx={{ color: 'black' }}
                            >
                                <MenuItem value="">
                                    <em>None</em>
                                </MenuItem>
                                <MenuItem value={"STUDENT"}>Student</MenuItem>
                                <MenuItem value={"INSTRUCTOR"}>Instructor</MenuItem>
                                <MenuItem value={"SECRETARY"}>Secretary</MenuItem>
                            </Select>
                        </FormControl>
                        {userType === "STUDENT" && (
                            <FormGroup sx={{ m: 1, marginLeft: '4vw', marginTop: -1 }} >
                                <h7>Course</h7>
                                <FormControlLabel control={<Checkbox checked={selectedValues.courseSelect.includes("CS299")}
                                    onChange={(e) => handleCheckboxChange(e, 'courseSelect')}
                                    value="CS299"
                                    color="primary" />} label="CS299" />
                                <FormControlLabel control={
                                    <Checkbox
                                        checked={selectedValues.courseSelect.includes("CS399")}
                                        onChange={(e) => handleCheckboxChange(e, 'courseSelect')}
                                        value="CS399"
                                    />} label="CS399" />

                            </FormGroup>
                        )}
                    </div>

                    <Box sx={{ marginLeft: 0 }} >
                        <Box sx={{ display: "flex", flexDirection: "row" }}>
                            <CustomInput
                                value={inputValues.nameInput}
                                onChange={(e) => handleInputChange(e, 'nameInput')}
                                sx={{ border: '1px solid black', marginTop: '1vw' }}
                            >
                                <StyledInputBase
                                    placeholder="NAME"
                                    inputProps={{ 'aria-label': 'search' }}
                                    sx={{ width: '15vw' }}
                                />
                            </CustomInput>


                            <CustomInput
                                sx={{ border: '1px solid black', marginTop: '1vw' }}
                                value={inputValues.password}
                                onChange={(e) => handleInputChange(e, 'password')}
                            >
                                <StyledInputBase
                                    placeholder="PASSWORD"
                                    inputProps={{ 'aria-label': 'search' }}
                                    sx={{ width: '15vw' }}
                                />

                            </CustomInput>
                        </Box>
                        <Box sx={{ display: "flex", flexDirection: "row" }}>
                            <CustomInput
                                sx={{ border: '1px solid black', marginTop: '1vw' }}
                                value={inputValues.emailInput}
                                itemType="email"
                                onChange={(e) => handleInputChange(e, 'emailInput')}
                            >
                                <StyledInputBase
                                    placeholder="EMAIL"
                                    type="email"
                                    inputProps={{ 'aria-label': 'search' }}
                                    sx={{ width: '15vw' }}
                                />
                            </CustomInput>
                            {userType === "STUDENT" && (
                                <CustomInput
                                    sx={{ border: '1px solid black', marginTop: '1vw', height: "5vh", width: "12vw" }}
                                    value={inputValues.idSelect}
                                    itemType="id"
                                    onChange={(e) => handleInputChange(e, 'idSelect')}
                                >
                                    <StyledInputBase
                                        placeholder="STUDENT ID"
                                        type="id"
                                        inputProps={{ 'aria-label': 'search' }}
                                        sx={{ width: '15vw' }}
                                    />
                                </CustomInput>
                            )}
                        </Box>
                    </Box>
                    <Button
                        onClick={handleClose}
                        sx={{ marginTop: 3 }}
                    >Exit</Button>
                    <Button
                        onClick={handleSubmit}
                        sx={{ marginTop: 3, marginLeft: 3 }}
                    >Finish</Button>
                </Box>
            </Modal>
        </div>
    ): null;
}
