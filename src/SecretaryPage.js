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
import Button from '@mui/material/Button';
import Checkbox from '@mui/material/Checkbox';
import { updateGradeAndStatus } from './apiConnect';

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
  const score = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell align="right">Course Name</TableCell>
            <TableCell align="right">Status</TableCell>
            <TableCell align="right">Company Grade</TableCell>
            <TableCell align="right">Withdraw</TableCell>
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
              <TableCell align="right">{row.course_name}</TableCell>
              <TableCell align="right">{row.status}</TableCell>
              <TableCell align="right">
                <Autocomplete
                  disablePortal
                  id="combo-box-score"
                  value={row.grade}
                  onChange={(e, newValue) => {
                    row.grade = newValue;
                  }}
                  options={score}
                  sx={{ margin: "auto", width: "9vw", border: "3px solid rgba(25, 118, 210, 0.5)", borderRadius: "1vw" }}
                  renderInput={(params) => <TextField {...params} label="Score" />}
                />
              </TableCell>
              <TableCell align="right">
                <Checkbox
                  checked={row.status==="withdrawn"? true: false}
                  onChange={(event) => {
                    if(event.target.checked){
                      row.status="withdrawn"
                    }
                  }}
                  inputProps={{ 'aria-label': 'controlled' }}
                />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

function SecretaryListPage() {
  const [btnGrade, setGradeBtn] = useState();
  const [grade, setGrade] = useState();
  const [users, setUsers] = useState([]);
  const [courses, setCourses] = useState([]);
  const [students, setStudents] = useState([]);
  const [rows, setRows] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/users/get`);
        console.log(response.data);
        const userArray = response.data;
        setUsers(userArray);

        const courseArrays = userArray.map(user => user.course_info);
        const flattenedCourses = [].concat(...courseArrays);
        setCourses(flattenedCourses);
        console.log(courses);
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
    const createData = (student, course) => {
      return { name: student.name, course_id: course.course_id, course_name: course.course_name, grade: course.companyScore, status: course.status };
    };
    const updatedRows = students.map(student => {
      const coursesArray = student.course_info.map(course => {
        return createData(student, course);
      });
      return coursesArray;
    });
    const flattenedRows = [].concat(...updatedRows);
    setRows(flattenedRows);
    console.log(rows);
  }, [students]);

  const handleSave  = async () => {
    try {
      for (const row of rows) {
        await updateGradeAndStatus(row);
      }
    } catch (error) {
      console.error('Error sending PATCH requests:', error);
    }
  };

  return (
        <div className="student table" style={{ width: "60vw", height: "100vh", margin: "auto", display: "flex", displayDirection: "row" }}>
          {StudentTable(rows)}
          <Button className="Submit" variant="contained" onClick={handleSave}
            sx={{
              "width": "10vw",
              "height": "8vh",
              "font-size": "20px",
              "font-weight": "bold",
              "border-radius": "15px"
            }}
          >Save</Button>
        </div>
  );
}

export default SecretaryListPage;