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
import { isVisible } from "@testing-library/user-event/dist/utils";
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
  
    // Send the request to the backend
    fetch('"http://localhost:8080/users"', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload),
    })
      .then((response) => {
        // Handle the response from the backend
        if (response.ok) {
          // File successfully uploaded
        } else {
          // Error occurred during file upload
        }
      })
      .catch((error) => {
        // Handle network or other errors
      });
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
        style={{ marginTop: 15, marginLeft: 570, height: "6vh", borderRadius: 20}} // Add some margin-top to separate the button from the hidden input
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
        <TableCell align="right">{row.surname}</TableCell>
        <TableCell align="right">{row.role}</TableCell>
        <TableCell align="right">{row.mail}</TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                History
              </Typography>
              <Table size="small" aria-label="purchases">
                <TableHead>
                  <TableRow>
                    <TableCell>Course</TableCell>
                    <TableCell>Assigned Instructor</TableCell>
                    <TableCell align="right">Company Name</TableCell>
                    <TableCell align="right">Status</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {row.history.map((historyRow) => (
                    <TableRow key={historyRow.course}>
                      <TableCell component="th" scope="row">
                        {historyRow.course}
                      </TableCell>
                      <TableCell>{historyRow.assignedInstructor}</TableCell>
                      <TableCell align="right">{historyRow.companyName}</TableCell>
                      <TableCell align="right">{historyRow.status}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

  function createData(name, surname, role, mail, course, status) {
    return {
      name,
      surname,
      role,
      mail,
      course,
      history: [
        {
          course: 'CS299',
          assignedInstructor: 'Eray Tüzün',
          companyName: 'Google',
          status: 'active',
        },
      ],
    };
  }

  const rows = [
    createData('Ömer Asım', 'Doğan', 'Student', 'asim@gmail.com', 3.99),
    createData('Dilay', 237, 9.0, 37, 4.3, 4.99),
    createData('Eclair', 262, 16.0, 24, 6.0, 3.79),
    createData('Cupcake', 305, 3.7, 67, 4.3, 2.5),
    createData('Gingerbread', 356, 16.0, 49, 3.9, 1.5),
  ];

  const PlusButton = ({onClick}) => {
    return (
      <IconButton onClick={onClick} color="primary" aria-label="add" style = {{color: "black", marginLeft: 10, marginTop: 10, height: 50, width: 50}}>
        <AddIcon />
      </IconButton>
    );
  };

  const BasicModal = () => {

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

    const handleCheckboxChange = (event, name) => {
      const isChecked = event.target.checked;
      const courseValue = event.target.value;
      setSelectedValues(prevValues => {
        if (isChecked) {
          // Add the selected course to the array
          return {
            ...prevValues,
            [name]: [...prevValues[name], courseValue],
          };
        } else {
          // Remove the deselected course from the array
          return {
            ...prevValues,
            [name]: prevValues[name].filter(course => course !== courseValue),
          };
        }
      });
    };
    // ...
  
    const handleSelectChange = (event, name) => {
      const selectedValue = event.target.value;
      setSelectedValues(prevValues => ({
        ...prevValues,
        [name]: selectedValue,
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
      const value = attributeName === 'companyGrade' || attributeName === 'idSelect' ? parseInt(event.target.value) : event.target.value;
      
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
const handleSubmit = (event) => {
  event.preventDefault();

  // Create your object using the form data
  const myObject = {
    nameInput: inputValues.nameInput,
    surnameInput: inputValues.surnameInput,
    emailInput: inputValues.emailInput,
    roleSelect: selectedValues.roleSelect,
    idSelect: inputValues.idSelect,
    courseSelect:selectedValues.courseSelect,
    companyGrade: inputValues.companyGrade,
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
              value={selectedValues.roleSelect} 
              onChange={(e) => handleSelectChange(e, 'roleSelect')}
              labelId="demo-select-small-label"
              id="roleSelect"
              label="select-role"
              sx={{color: 'black'}}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              <MenuItem value={"Student"}>Student</MenuItem>
              <MenuItem value={"Instructor"}>Instructor</MenuItem>
              <MenuItem value={"Secretary"}>Secretary</MenuItem>
          </Select>
        </FormControl>
        
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

        <CustomInput 
          value={inputValues.companyGrade} 
          onChange={(e) => handleInputChange(e, 'companyGrade')}
          sx ={{border: '1px solid black', marginTop: '0vw', height: '5vh'}}
        >
          <StyledInputBase 
            placeholder="GRADE" 
            inputProps={{ 'aria-label': 'search' }}
            sx = {{width: '10vw', height: '5vh'}}
          />
        </CustomInput>

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
          value={inputValues.surnameInput} 
          onChange={(e) => handleInputChange(e, 'surnameInput')}
        >
          <StyledInputBase
            placeholder="SURNAME" 
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
        
        <CustomInput 
          sx ={{border: '1px solid black', marginTop: '1vw', height: "5vh", width: "12vw"}}
          value={inputValues.idSelect} 
          itemType="id"
          onChange={(e) => handleInputChange(e, 'idSelect')}
        >
          <StyledInputBase
            placeholder="ID" 
            type = "id"
            inputProps={{ 'aria-label': 'search' }}
            sx = {{width: '15vw'}}
          />
        </CustomInput>
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
        <h2 style={{marginLeft: 50}}>USERS</h2>
        <BasicModal></BasicModal>
        <FileUpload></FileUpload>
      </Box>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow>
            <TableCell />
            <TableCell>Name</TableCell>
            <TableCell align="right">Surname</TableCell>
            <TableCell align="right">Role</TableCell>
            <TableCell align="right">Mail</TableCell>
            <TableCell align="right"></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <Row key={row.name} row={row} />
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

const AdminMainPage = () => {

  const fetchData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/statistics/get-statistics');
      // Process the response data
      console.log(response.data);
    } catch (error) {
      // Handle any errors
      console.error(error);
    }
  };

  const [statistics, setStatistics] = useState(null);  
  const handleStatistics = (event) =>{
  console.log("geldik buraya");
    fetchData();

  }
    const [isEditable, setIsEditable] = useState(false);
    const [text1, setText1] = useState('dd.mm.yyyy');
    const [text2, setText2] = useState('dd.mm.yyyy');
    const [text, setText] = useState('2022-2023');
    const containerRef = useRef(null);
    const [buttonVisible, setButtonVisible] = useState(true);
    const navigate = useNavigate();
    
    const handleButtonClick = () => {
      setIsEditable((prevState) => !prevState);
    };

    const handleInputChange1 = (event) => {
      setText1(event.target.value);
    };
  
    const handleInputChange2 = (event) => {
      setText2(event.target.value);
    };
  
    const handleInputChange = (event) => {
      setText(event.target.value);
    };

    const handleOutsideClick = (event) => {
      if (containerRef.current && !containerRef.current.contains(event.target)) {
        setIsEditable(false);
      }
    };
  
    useEffect(() => {
      document.addEventListener('mousedown', handleOutsideClick);
  
      return () => {
        document.removeEventListener('mousedown', handleOutsideClick);
      };
    }, []);
    

    const handleClick = async () => {
      const requestData = {
        folderName: 'Spring',
        userId: 1,
        firstDay: '2001-01-01',
        lastDay: '2004-05-04',
        addDropDeadline: '2002-05-12',
        withdrawDeadline: '2002-05-17'
      };
    
      try {
        const response = await axios.post('http://localhost:8080/create-semester', requestData);
        const responseData = response.data; 

        if (responseData.accessDenied) {
          alert('Access denied');
        } else if (responseData.missingSecretary) {
          alert('Missing secretary');
        } else if (responseData.semesterConflict) {
          alert('Semester already created');
        } else {
          alert('Semester successfully created!');
        }
        } catch (error) {
          console.log(`Error: ${error.errorMessage}`);
      }
    };
    
    const OpenFrame = () => {
      console.log("openframe geldi");
      //if (!data.semesterFolderKey) {
      //  alert('Semester has not been created yet, no semester found');
      //} else {
        navigate('/googledrive');
      //}
    };    

        const name = localStorage.getItem("name");
    return(
        <div style={{width: "100%", height: "100%", display: "flex", flexDirection: "column"}}>
          <div className="instructorMainPage-header">
            <div className="instructorMainPage-image-div">
              <img src={logo} alt="Bilkent University logo" className="instructorMainPage-image" />
              <h2 className="instructorMainPage-header_title">INTERNSHIP MANAGEMENT SYSTEM</h2>
            </div>
          <img className="instructorMainPage-announcement_icon" />
          <img className="instructorMainPage-nofitication_icon" />
          <img className="instructorMainPage-logout_icon" />
          </div>
          <h3 className="instructorMainPage-header_welcome_message" style = {{marginTop: "0px",marginBottom: "0px", width: "96%"}}>Hello, {name}!</h3>
          <div style = {{width: "100vw", height: "75vh"}}>
          <Box sx = {{display: "flex", flexDirection: "row", paddingX: "40px", paddingY: "40px", backgroundColor: "#D4E7FF", alignItems: "center", justifyContent: "center"}}>
            <Box sx = {{height: isVisible ? "50vh" : "30vh", width: "60vh", backgroundColor: "#324966",  marginRight: "200px", borderRadius: 4, display: "flex", flexDirection: "column"}}>
                <Box sx = {{height: "40vh", width: "100%"}}>
                  <Box sx = {{width: "100%", height: "15%", display: "flex", flexDirection: "row"}}>
                    <h3 style = {{color: "white", margin: "3vh"}}>Semester Information</h3>
                    <Button onClick={handleButtonClick} disabled={isEditable} style={{ color: isEditable ? 'white' : 'white' }} sx = {{color: "white", alignContent: "center", height: "25px", borderRadius: 3, margin: "20px", backgroundColor: "#141532"}}>{isEditable ? 'Cancel' : 'Edit'}</Button>
                  </Box>
                  <div ref = {containerRef}>
                  <br />
                  {isEditable ? (
                    <>
                    <Box sx = {{display: "flex", flexDirection: "row", }}>
                        <h3 style = {{color: "white", marginLeft: "3vh"}}>Semester:</h3>
                        <input
                        type="text"
                        value={text}
                        onChange={handleInputChange}
                        readOnly={!isEditable}
                        style={{
                          background: isEditable ? 'white' : 'transparent',
                          color: isEditable ? 'black' : 'white',
                          marginLeft: 20,
                          marginTop: "3vh",
                          height: 15,
                          width: 90
                        }}
                        />
                    </Box>

                    <div>
                      <h5 style = {{color: "white", marginLeft: 20, marginTop: 0, marginBottom: 0}}>Add Drop Period Finishes:</h5>
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
                    </>
                    ) : (
                      <>
                      <Box sx = {{display: "flex", flexDirection: "row", }}>
                      <h3 style = {{color: "white", marginLeft: "3vh"}}>Semester:</h3>
                      <h3 style = {{color: "white", marginTop: 18, marginLeft: 20, marginBottom: 30}}>{text}</h3>
                      </Box>
                      <div sx = {{display: "flex", flexDirection: "column"}}>
                        <h5 style = {{color: "white", marginLeft: 20, marginTop: -20}}>Semester Begins:</h5>
                        <div style={{ color: 'white', marginLeft: 20, marginTop: -25}}>{text1}</div>
                      </div>
                      <div sx = {{display: "flex", flexDirection: "column"}}>
                        <h5 style = {{color: "white", marginLeft: 20, marginTop: 5}}>Add Drop Period Finishes:</h5>
                        <div style={{ color: 'white', marginLeft: 20, marginTop: -25}}>{text1}</div>
                      </div>
                      <div>
                        <h5 style = {{color: "white", marginLeft: 20, marginTop: 3}}>Withdrawal Period Finishes:</h5>
                        <div style={{ color: 'white', marginLeft: 20, marginTop: -25}}>{text2}</div>
                      </div>
                      <div sx = {{display: "flex", flexDirection: "column"}}>
                        <h5 style = {{color: "white", marginLeft: 20, marginTop: 0}}>Semester Ends:</h5>
                        <div style={{ color: 'white', marginLeft: 20, marginTop: -25}}>{text1}</div>
                      </div>
                      </>
                    )}
                    </div>
                </Box>
                {( <><hr style = {{color: "white", backgroundColor: "white", height: 2, width: "100%", marginTop: -70}}/>
                    <Button onClick={handleClick} sx = {{color: "white" , width: "100%"}}>CREATE SEMESTER</Button> 
                    <hr style = {{color: "white", backgroundColor: "white", height: 2, width: "100%"}}/> </> )}
                {( <><hr style = {{color: "white", backgroundColor: "white", height: 2, width: "100%", marginTop: -10}}/>
                <Button onClick = {handleStatistics} sx = {{color: "white" , width: "100%"}}>Semester Statistics</Button>
                <hr style = {{color: "white", backgroundColor: "white", height: 2, width: "100%"}}/>
                <Button onClick={OpenFrame} sx = {{color: "white" , width: "100%"}}>SEMESTER FOLDERS</Button> 
                <hr style = {{color: "white", backgroundColor: "white", height: 2, width: "100%"}}/> </> )}
            </Box>
            <CustomTable/>
            </Box>
          </div>

        </div>
    );
}
export default AdminMainPage;