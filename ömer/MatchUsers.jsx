import Navbar from "../components/navbar/navbar";
import React from "react";
import { Typography, Box, TableContainer, Paper, Table, TableHead, TableRow, TableCell, TableBody } from "@mui/material";
import { useState } from "react";
import { Collapse } from "react-bootstrap";
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';

function createData(name, surname, course, id, rematch) {
    return { name, surname, course, id, rematch };
  }
  
  const rows = [
    createData('Asim', 'Dogan', 'CS299', 21903042),
    createData('Ece', 'Ates', 'CS299', 21902042),
    createData('Begüm', 'Kara', 'CS299', 22003042),
    createData('Aytekin', 'Ismail', 'CS299', 22003042),
    createData('İzgi', 'Tamci', 'CS299', 22003562),
  ];
  
  const DenseTable = () => {
    const tableButtonClick = () => {
        
    };
    const [open, setOpen] = React.useState(false);
    const handleClick = () => {
        setOpen(!open);
    };

    return (
      <TableContainer component={Paper}>
        <ListItemButton onClick={handleClick}>
        <ListItemText primary="Altay Güvenir" />
        {open ? <ExpandLess /> : <ExpandMore />}
      </ListItemButton>
        <Collapse in={open} timeout="auto" unmountOnExit>
        <Table sx={{ minWidth: 300 }} size="small" aria-label="a dense table">
          <TableHead>   
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell align="right">Surname</TableCell>
              <TableCell align="right">Course</TableCell>
              <TableCell align="right">Id</TableCell>
              <TableCell/>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map((row) => (
              <TableRow
                key={row.name}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  {row.name}
                </TableCell>
                <TableCell align="right">{row.surname}</TableCell>
                <TableCell align="right">{row.course}</TableCell>
                <TableCell align="right">{row.id}</TableCell>
                <TableCell><button onClick={tableButtonClick}>Click Me</button></TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
        </Collapse>
      </TableContainer>
    );
  }

const UserMatchButton = () => {
    const [showTable, setShowTable] = useState(false);
    const [userNumber, setUserNumber] = useState(0);
  
    const handleUserNumberChange = (event) => {
      setUserNumber(Number(event.target.value));
    };

  
    const handleButtonClick = () => {
      setShowTable(true);
    };
  
    return (
        <div>
        {showTable ? ( 
            <div>
                <input type="number" value={userNumber} onChange={handleUserNumberChange} />
                {Array.from({ length: userNumber }, (_, index) => (
                    <DenseTable key = {index}></DenseTable>
                ))}
            </div>

        ) : (
            <div>
                <Box sx={ {display: 'flex', flexDirection: "column", backgroundColor: "#D4E7FF", height: "80vh", width: "100vw", alignItems: "center"}}>
                <Typography  variant="h5" sx={{color: '#3975FF', fontWeight: "bold", marginTop: 8}} >Course Has Not Been Started</Typography>
                <button onClick={handleButtonClick} style={{height: "8vh", width: "20vw", fontSize: "30px", backgroundColor: "#6D84CE", color: "white", borderRadius: 30, marginTop: 40}}>Start Courses</button>
                <Typography  variant="h7" sx={{ marginTop: 8}} >Please make sure that all student accounts are registered to the system</Typography>
                </Box>
            </div>
        )}
        </div>
    );
};
const MatchUser = () =>  {
        
    return(
        <div sx = {{height: "100%"}}>
            <Navbar/>
            <Box sx={ {display: 'flex', flexDirection: "row", height:'8vh', backgroundColor:'darkblue',  alignItems:'center', paddingX: "40px", paddingY: '10px'} }>
                <Typography  variant="h6" sx={{color: 'white'}} >Matchings</Typography>  
            </Box>
            <UserMatchButton></UserMatchButton>
        </div>
    );
};

export default MatchUser;