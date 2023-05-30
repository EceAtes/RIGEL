import React from 'react';

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useState, useEffect } from 'react';
import axios from "axios";
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
/*
const TabComponent = (Content, cs, ee, ie, me) => {
  const [value, setValue] = useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div className="Name">
      <Tabs value={value} onChange={handleChange} indicatorColor="primary" textColor="primary" centered>
        <Tab label="CS" />
        <Tab label="EE" />
        <Tab label="IE" />
        <Tab label="ME" />
      </Tabs>

      {/* Render the content based on the selected tab */
      /*{value === 0 && <div>{Content(cs)}</div>}
      {value === 1 && <div>{Content(ee)}</div>}
      {value === 2 && <div>{Content(ie)}</div>}
      {value === 3 && <div>{Content(me)}</div>}
    </div>
  );
};*/

function StudentTable(rows) {
  const score=[0,1,2,3,4,5,6,7,8,9,10];
  
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell align="right">Role</TableCell>
            <TableCell align="right">Email</TableCell>
            <TableCell align="right">Company Grade</TableCell>
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
              <TableCell align="right">{row.role}</TableCell>
              <TableCell align="right">{row.email}</TableCell>
              <TableCell align="right">
                <Autocomplete
                  disablePortal
                  id="combo-box-score"
                  value={row.grade === -1 ? null : row.grade}
                  onChange={(e, newValue) => {
                    row.grade = newValue;
                  }}
                  options={score}
                  sx={{ margin: "auto", width: "9vw", border: "3px solid rgba(25, 118, 210, 0.5)", borderRadius: "1vw" }}
                  renderInput={(params) => <TextField {...params} label="Score" />}
                />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

function SecretaryMainPage() {
  const [btnGrade, setGradeBtn] = useState();
  const [grade, setGrade] = useState();
  const [users, setUsers] = useState([]);
  const [students, setStudents] = useState([]);
  const [rows, setRows] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/users`);
        console.log(response.data);
        const userArray = response.data;
        setUsers(userArray);
      } catch (error) {
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    const filteredStudents = users.filter(user => user.role === "STUDENT");
    setStudents(filteredStudents);
  }, [users]);

  useEffect(() => {
    const createData = (student) => {
      return { name: student.name, role: student.role, email: student.email, grade: student.grade };
    };
    const updatedRows = students.map(student => createData(student));
    setRows(updatedRows);
  }, [students]);

  return (
    <div className="SecretaryMainPage" style={{width:"100vw", height:"100vh",backgroundColor:"white"}}>
      <div className="panel" style={{ display: "flex", displayDirection: "row", width: "100vw", height: "80vh" }}>
        <div className="secretary card" stye={{ display: "flex", displayDirection: "column", width: "20vw", height: "80vh" }}>
        CARD OLACAK
        </div>
        <div className="student table" style={{ width: "60vw", height: "100vh", margin: "auto", display: "flex" }}>
          {StudentTable(rows)}
        </div>
      </div>
    </div>
  );
}

export default SecretaryMainPage;