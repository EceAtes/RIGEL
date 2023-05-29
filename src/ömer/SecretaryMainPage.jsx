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

const API_BASE_URL="http://localhost:8080"
export const fetchUserData = async (userId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/users/${userId}`);
    return response.data;
  } catch (error) {
    throw new Error('Failed to fetch user data');
  }
};

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

const FileUpload = () => {
  const fileInputRef = useRef(null);

  const handleButtonClick = () => {
    fileInputRef.current.click();
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    const reader = new FileReader();
  
    reader.onload = (e) => {
      const fileContent = e.target.result;
      // Send the file content to the backend
      sendFileToBackend(fileContent);
    };
  
    reader.readAsText(file);
  };

  const sendFileToBackend = (fileContent) => {
    // Prepare the request payload
    const payload = {
      fileContent: fileContent,
    };
  };
  
  return (
    <>
      <input
        type="file"
        ref={fileInputRef}
        style={{ display: 'none'}}
        onChange={handleFileChange}
      />
      <Button
        variant="contained"
        onClick={handleButtonClick}
        startIcon={<CloudUploadIcon />}
        style={{ marginTop: "15px", margin: "10px 30px 10px 30px", height: "4vh", borderRadius: 20}} // Add some margin-top to separate the button from the hidden input
      >
        Upload File
      </Button>
    </>
  );
};

function Row(props) {
  const { row } = props;
  const [open, setOpen] = React.useState(false);

  return (
    <React.Fragment>
      <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row">
          {row.name}
        </TableCell>
        <TableCell align="right">{row.email}</TableCell>
        <TableCell align="right">{row.role}</TableCell>
        <TableCell align="right">{row.password}</TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                History
              </Typography>
              <Table size="small" aria-label="purchases">
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

  function createData() {

      console.log("Secretary main page");
      const url = "http://localhost:8080/users";

      fetch(url, {
        method: 'GET',
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
                
        parsedData.forEach(struct => {
          rows.push(struct);
        });
        console.log("rowsa gelindi");
        console.log(rows);
      })  
      .catch(error => {
        console.log("hello");
        console.log("ERROR!!!");
      
        // Handle any errors that occurred during the request
      });
  }
 
  const rows = [

  ];

  const PlusButton = ({onClick}) => {
    return (
      <IconButton onClick={onClick} color="primary" aria-label="add" style = {{color: "black", marginLeft: 10, marginTop: 10, height: 50, width: 50}}>
        <AddIcon />
      </IconButton>
    ); 
  };

  const BasicModal = () => {
    const [userType, setUserType] = useState(""); // State for user type selection

    const handleUserTypeChange = (event) => {
      setUserType(event.target.value); // Update user type when selection changes
    };
    
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
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
        courses:selectedValues.courseSelect,
        password: inputValues.password
      };


  console.log(myObject);
  console.log(rows);
  axios.post("http://localhost:8080/users", myObject)
        .then(res => console.log("posting data", res))
        .catch(err => console.log(err))

  setInputValues({
    nameInput: '',
    surnameInput: '',
    emailInput: '',
  });
};

    return (
      <div>
        <PlusButton onClick={handleOpen}/>
        <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
      <Box sx={style}>
      <div style = {{display: "flex", flexDirection: "row"}}>

        <FormControl component= "form" onSubmit = {handleSubmit} sx={{ marginLeft:'2vw', width: "9vw" }} size="small">
          <InputLabel id="demo-select-small-label"  sx={{color: 'black'}}>Role</InputLabel>
          <Select
              value={userType} 
              onChange={handleUserTypeChange}
              labelId="demo-select-small-label"
              id="roleSelect"
              label="select-role"
              sx={{color: 'black'}}
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
        <FormGroup sx={{ m: 1, marginLeft:'4vw', marginTop: -1 }} >
          <h7>Course</h7>
          <FormControlLabel control={<Checkbox checked={selectedValues.courseSelect.includes("CS299")}
                                      onChange={(e) => handleCheckboxChange(e, 'courseSelect')}
                                      value="CS299"
                                      color="primary"/>} label="CS299" />
          <FormControlLabel control={
                                      <Checkbox
                                        checked={selectedValues.courseSelect.includes("CS399")}
                                        onChange={(e) => handleCheckboxChange(e, 'courseSelect')}
                                        value="CS399"
                                      />} label="CS399" />

        </FormGroup>
      )}
      </div>

      <Box sx={{marginLeft: 0}} >
      <Box sx = {{display: "flex", flexDirection: "row"}}>
        <CustomInput 
          value={inputValues.nameInput} 
          onChange={(e) => handleInputChange(e, 'nameInput')}
          sx ={{border: '1px solid black', marginTop: '1vw'}}
        >
          <StyledInputBase 
            placeholder="NAME" 
            inputProps={{ 'aria-label': 'search' }}
            sx = {{width: '15vw'}}
          />
        </CustomInput>

        
        <CustomInput 
          sx ={{border: '1px solid black', marginTop: '1vw'}}
          value={inputValues.password} 
          onChange={(e) => handleInputChange(e, 'password')}
        >
          <StyledInputBase
            placeholder="PASSWORD" 
            inputProps={{ 'aria-label': 'search' }}
            sx = {{width: '15vw'}}
          />
        
        </CustomInput>
        </Box>
        <Box sx = {{display: "flex", flexDirection: "row"}}>
        <CustomInput 
          sx ={{border: '1px solid black', marginTop: '1vw'}}
          value={inputValues.emailInput} 
          itemType="email"
          onChange={(e) => handleInputChange(e, 'emailInput')}
        >
          <StyledInputBase
            placeholder="EMAIL" 
            type = "email"
            inputProps={{ 'aria-label': 'search' }}
            sx = {{width: '15vw'}}
          />
        </CustomInput>
        {userType === "STUDENT" && (
        <CustomInput 
          sx ={{border: '1px solid black', marginTop: '1vw', height: "5vh", width: "12vw"}}
          value={inputValues.idSelect} 
          itemType="id"
          onChange={(e) => handleInputChange(e, 'idSelect')}
        >
          <StyledInputBase
            placeholder="STUDENT ID" 
            type = "id"
            inputProps={{ 'aria-label': 'search' }}
            sx = {{width: '15vw'}}
          />
        </CustomInput>
        )}
        </Box>
      </Box>
      <Button 
        onClick={handleClose}
        sx = {{marginTop: 3}}
      >Exit</Button>
      <Button 
        onClick={handleSubmit}
        sx = {{marginTop: 3, marginLeft: 3}}
      >Finish</Button>
    </Box>
  </Modal>
      </div>
    );
  }

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
const CustomTable = () => {
  return (
    <TableContainer component={Paper}>
      <Box sx ={{display: "flex", flexDirection: "row"}}>
        <h2 style={{marginLeft: 50}}>CS DEPARTMENT USERS</h2>
        <BasicModal></BasicModal>
        <FileUpload></FileUpload>
      </Box>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow>
            <TableCell />
            <TableCell align = "center">Name</TableCell>
            <TableCell align="center">Surname</TableCell>
            <TableCell align="center">Role</TableCell>
            <TableCell align="center">Mail</TableCell>
            <TableCell align="center"></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <Row row={row} />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

const Search = styled('div')(({ theme }) => ({
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

  const SearchIconWrapper = styled('div')(({ theme }) => ({
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  }));

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

const SecretaryMainPage = () => {
    const [isEditable, setIsEditable] = useState(false);
    const [text1, setText1] = useState('gg.aa.yyyy');
    const [text2, setText2] = useState('gg.aa.yyyy');
    const [text3, setText3] = useState('gg.aa.yyyy');
    const containerRef = useRef(null);
    
    const handleButtonClick = () => {
      setIsEditable((prevState) => !prevState);
    };

    const handleInputChange1 = (event) => {
      setText1(event.target.value);
    };
  
    const handleInputChange2 = (event) => {
      setText2(event.target.value);
    };
  
    const handleInputChange3 = (event) => {
      setText3(event.target.value);
    };

    const handleOutsideClick = (event) => {
      if (containerRef.current && !containerRef.current.contains(event.target)) {
        setIsEditable(false);
      }
    };
  

    
    useEffect(() => {
      document.addEventListener('mousedown', handleOutsideClick);
      createData();
      return () => {
        document.removeEventListener('mousedown', handleOutsideClick);
      };
    }, []);

      const navigate = useNavigate();
    
      const handleClick = () => {
        const url = "http://localhost:8080/start-courses";
        const secretaryId = localStorage.getItem("userId");
        console.log(secretaryId);

        fetch(url, {
          method: 'POST',
          body: JSON.stringify(secretaryId),
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
          if (parsedData.accessDenied) {
            alert('Access denied');
          }else if(parsedData.semesterStarted){
          alert('Semester already started!');
          } 
          else if (!parsedData.addDropPeriodFinished) {
          alert('Add drop period not finished yet!');
          }
          else if (!parsedData.atLeastOneInstructorExist) {
            alert('there is no instructor in the department! At least one required');
          } else if(!parsedData.atLeastOneStudentExist) {
            alert('there is no student in the deparment! At least one required');
          }
        })
      };
      const name = localStorage.getItem("name");

    return(
      <div className = "secretaryMainPage-all-items" style = {{width: "100vw"}}>
          <div className="instructorMainPage-header" style = {{width: "100vw"}}>
            <div className="instructorMainPage-image-div">
              <img src={logo} alt="Bilkent University logo" className="instructorMainPage-image" />
              <h2 className="instructorMainPage-header_title">INTERNSHIP MANAGEMENT SYSTEM</h2>
            </div>
            <img className="instructorMainPage-announcement_icon" />
            <img className="instructorMainPage-nofitication_icon" />
            <img className="instructorMainPage-logout_icon" />
          </div>
          <h3 className="instructorMainPage-header_welcome_message" style = {{width: "100vw",
    margin: "0px",
    textAlign: "center",
    padding: "15px 0px 15px 0px",
    backgroundColor: "#6CCFF6",
    color: "#4D5057"}}>Hello, {name}</h3>
          <div className = "items-container" style = {{width: "100vw"}}>  
            <Box sx = {{display: "flex", flexDirection: "row", paddingX: "40px", paddingY: "40px", backgroundColor: "#D4E7FF", alignItems: "center", justifyContent: "center"}}>
            <Box sx = {{height: "70vh", width: "60vh", backgroundColor: "#324966",  marginRight: "200px", borderRadius: 4, display: "flex", flexDirection: "column"}}>
              <div className = "container-size">  
                <Box sx = {{height: "40vh", width: "100%"}}>
                  <Box sx = {{width: "100%", height: "20%", display: "flex", flexDirection: "row"}}>
                    <h3 style = {{color: "white", margin: "3vh"}}>Semester Info</h3>
                    <Button onClick={handleButtonClick} disabled={isEditable} style={{ color: isEditable ? 'white' : 'white' }} sx = {{color: "white", alignContent: "center", height: "25px", borderRadius: 3, margin: "20px", backgroundColor: "#141532"}}>{isEditable ? 'Cancel' : 'Edit'}</Button>
                  </Box>
                  <h4 style = {{color: "white", marginTop: 0, marginLeft: 30, marginBottom: 30}}>2022-2023 Spring Semester</h4>
                  <div ref = {containerRef}>
                  <br />
                  {isEditable ? (
                    <>
                    <div>
                      <h5 style = {{color: "white", marginLeft: 20, marginTop: -20, marginBottom: 0}}>Add Drop Period Finishes:</h5>
                      <input
                        type="text"
                        value={text1}
                        onChange={handleInputChange1}
                        readOnly={!isEditable}
                        style={{
                          background: isEditable ? 'white' : 'transparent',
                          color: isEditable ? 'black' : 'white',
                          marginLeft: 20,
                          marginTop: -25,
                          height: 15
                        }}
                      />
                    </div>

                    <div>
                      <h5 style = {{color: "white", marginLeft: 20, marginTop: 15, marginBottom: 0}}>Withdrawal Period Finishes:</h5>
                    <input
                      type="text"
                      value={text2}
                      onChange={handleInputChange2}
                      style={{
                        background: isEditable ? 'white' : 'transparent',
                        color: isEditable ? 'black' : 'white',
                        marginLeft: 20,
                        marginTop: -25
                      }}
                    />
                    </div>
                    <div>
                      <h5 style = {{color: "white", marginLeft: 20, marginTop: 15, marginBottom: 0}}>Initial Submission Date:</h5>
                    <input
                      type="text"
                      value={text3}
                      onChange={handleInputChange3}
                      readOnly={!isEditable}
                      style={{
                        background: isEditable ? 'white' : 'transparent',
                        color: isEditable ? 'black' : 'white',
                        marginLeft: 20,
                        marginTop: -50
                      }}
                    />
                    </div>
                    </>
                    ) : (
                      <>
                      <div sx = {{display: "flex", flexDirection: "column"}}>
                        <h5 style = {{color: "white", marginLeft: 20, marginTop: -20}}>Add Drop Period Finishes:</h5>
                        <div style={{ color: 'white', marginLeft: 20, marginTop: -25}}>{text1}</div>
                      </div>
                      <div>
                        <h5 style = {{color: "white", marginLeft: 20, marginTop: 15}}>Withdrawal Period Finishes:</h5>
                        <div style={{ color: 'white', marginLeft: 20, marginTop: -25}}>{text2}</div>
                      </div>
                      <div>
                        <h5 style = {{color: "white", marginLeft: 20, marginTop: 15}}>Initial Submission Date:</h5>
                        <div style={{ color: 'white', marginLeft: 20, marginTop: -25}}>{text3}</div>
                      </div>
                      </>
                    )}
                    </div>
                </Box>
                <hr style = {{color: "white", backgroundColor: "white", height: 2, width: "100%"}}/>
                <Button onClick={handleClick} sx = {{color: "white" , width: "100%"}}>START SEMESTER</Button>
                <hr style = {{color: "white", backgroundColor: "white", height: 2, width: "100%"}}/>
                <Button sx = {{color: "white" , width: "100%"}}>Semester Statistics</Button>
                <hr style = {{color: "white", backgroundColor: "white", height: 2, width: "100%"}}/>
                <Button sx = {{color: "white" , width: "100%"}}>All Reports</Button>
                <hr style = {{color: "white", backgroundColor: "white", height: 2, width: "100%"}}/>
              </div>
            </Box>
            <CustomTable/>
            </Box>
          </div>
        </div>
    );
}
export default SecretaryMainPage;