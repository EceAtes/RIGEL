import Navbar from "../components/navbar/navbar";
import {Box, Collapse, IconButton, Table, Typography, Button} from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import { Paper, TableContainer, TableCell, TableHead, TableRow, TableBody} from "@mui/material";
import React, {useState, useRef, useEffect} from "react";
import PropTypes from 'prop-types';
import InputBase from '@mui/material/InputBase';
import { styled, alpha } from '@mui/material/styles';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';

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
        <TableCell align="right">{row.calories}</TableCell>
        <TableCell align="right">{row.fat}</TableCell>
        <TableCell align="right">{row.carbs}</TableCell>
        <TableCell align="right">{row.protein}</TableCell>
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
                    <TableCell>Date</TableCell>
                    <TableCell>Customer</TableCell>
                    <TableCell align="right">Amount</TableCell>
                    <TableCell align="right">Total price ($)</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {row.history.map((historyRow) => (
                    <TableRow key={historyRow.date}>
                      <TableCell component="th" scope="row">
                        {historyRow.date}
                      </TableCell>
                      <TableCell>{historyRow.customerId}</TableCell>
                      <TableCell align="right">{historyRow.amount}</TableCell>
                      <TableCell align="right">
                        {Math.round(historyRow.amount * row.price * 100) / 100}
                      </TableCell>
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

Row.propTypes = {
  row: PropTypes.shape({
    calories: PropTypes.number.isRequired,
    carbs: PropTypes.number.isRequired,
    fat: PropTypes.number.isRequired,
    history: PropTypes.arrayOf(
      PropTypes.shape({
        amount: PropTypes.number.isRequired,
        customerId: PropTypes.string.isRequired,
        date: PropTypes.string.isRequired,
      }),
    ).isRequired,
    name: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
    protein: PropTypes.number.isRequired,
  }).isRequired,
};
  
  function createData(name, calories, fat, carbs, protein, price) {
    return {
      name,
      calories,
      fat,
      carbs,
      protein,
      price,
      history: [
        {
          date: '2020-01-05',
          customerId: '11091700',
          amount: 3,
        },
        {
          date: '2020-01-02',
          customerId: 'Anonymous',
          amount: 1,
        },
      ],
    };
  }

  const rows = [
    createData('Frozen yoghurt', 159, 6.0, 24, 4.0, 3.99),
    createData('Ice cream sandwich', 237, 9.0, 37, 4.3, 4.99),
    createData('Eclair', 262, 16.0, 24, 6.0, 3.79),
    createData('Cupcake', 305, 3.7, 67, 4.3, 2.5),
    createData('Gingerbread', 356, 16.0, 49, 3.9, 1.5),
  ];

  const PlusButton = () => {
    return (
      <IconButton color="primary" aria-label="add" style = {{color: "black", marginLeft: 10, marginTop: 10, height: 50, width: 50}}>
        <AddIcon />
      </IconButton>
    );
  };

const CustomTable = () => {
  return (
    <TableContainer component={Paper}>
      <Box sx ={{display: "flex", flexDirection: "row"}}>
        <h2 style={{marginLeft: 50}}>CS DEPARTMENT USERS</h2>
        <PlusButton></PlusButton>
      </Box>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow>
            <TableCell />
            <TableCell>Dessert (100g serving)</TableCell>
            <TableCell align="right">Calories</TableCell>
            <TableCell align="right">Fat&nbsp;(g)</TableCell>
            <TableCell align="right">Carbs&nbsp;(g)</TableCell>
            <TableCell align="right">Protein&nbsp;(g)</TableCell>
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
  
      return () => {
        document.removeEventListener('mousedown', handleOutsideClick);
      };
    }, []);

    return(
        <div>
            <Navbar/>
            <Box sx={ {display: 'flex', flexDirection: "row", height:'8vh', backgroundColor:'darkblue',  alignItems:'center', paddingX: "40px", paddingY: '10px'} }>
                <Typography  variant="h6" sx={{color: 'white'}} >Hello Begüm Çınar!</Typography>
                <div>
      </div>
                
            </Box>
            <Box sx = {{display: "flex", flexDirection: "row", paddingX: "40px", paddingY: "40px", alignItems: "center", backgroundColor: "#D4E7FF", alignItems: "center", justifyContent: "center"}}>
            <Box sx = {{height: "70vh", width: "60vh", backgroundColor: "#324966",  marginRight: "200px", borderRadius: 4, display: "flex", flexDirection: "column"}}>
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
                <Button sx = {{color: "white" , width: "100%"}}>Semester Statistics</Button>
                <hr style = {{color: "white", backgroundColor: "white", height: 2, width: "100%"}}/>
                <Button sx = {{color: "white" , width: "100%"}}>All Reports</Button>
                <hr style = {{color: "white", backgroundColor: "white", height: 2, width: "100%"}}/>
            </Box>
            <CustomTable/>
            </Box>
        </div>
    );
}
export default SecretaryMainPage;