
import Navbar from "../components/navbar/navbar";
import { Typography, Box, Table, FormControl, InputLabel, Select, MenuItem} from "@mui/material";
import { Paper, TableContainer, TableCell, TableHead, TableRow, TableBody, TablePagination} from "@mui/material";
import React from "react";
import SearchIcon from '@mui/icons-material/Search';
import InputBase from '@mui/material/InputBase';
import { styled, alpha } from '@mui/material/styles';

const columns = [
    { id: 'course_code', label: 'Course Code', minWidth: 100 },
    { id: 'name', label: 'Name/Surname', minWidth: 160 },
    { id: 'company_name', label: 'Company Name', minWidth: 100 },
    { id: 'grading_status', label: 'Grading Status', minWidth: 170 },
    { id: 'grade', label: 'Grade', minWidth: 100 },
   
  ];
  
  function createData(course_code, name, company_name, grading_status, grade) {
    return { course_code, name, company_name, grading_status, grade };
  }
  
  const rows = [
    createData('CS299', 'Ömer Asım', 'GOOGLE', "Submitted", "100"),
    createData('CS299','Aytekin Ismail', 'GOOGLE', "Submitted", "100"),
    createData('CS399','Ece Ateş', 'ULKER', "Submitted", "100"),
    createData('CS299','Ömer Asım', 'GOOGLE', "Submitted", "100"),
    createData('CS399','Aytekin Ismail', 'ETI', "Submitted", "100"),
    createData('CS399','Ece Ateş', 'GOOGLE', "Needs Grading", "100"),
    createData('CS299','Ömer Asım', 'GETIR', "Submitted", "100"),
    createData('CS399','Aytekin Ismail', 'GOOGLE', "Needs Grading", "100"),
    createData('CS299','Ece Ateş', 'GOOGLE', "Submitted", "100"),
    
  ];

const CustomTable = () => {

    const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

    return(<Paper sx={{ width: '100%', overflow: 'hidden' }}>
      <TableContainer sx={{ maxHeight: 440 }}>
        <Table stickyHeader aria-label="sticky table">
          <TableHead>
            <TableRow>
              {columns.map((column) => (
                <TableCell sx = {{backgroundColor: "#0875DB"}}
                  key={column.id}
                  align={column.align}
                  style={{ minWidth: column.minWidth }}
                >
                  {column.label}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {rows
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((row) => {
                return (
                  <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                    {columns.map((column) => {
                      const value = row[column.id];
                      return (
                        <TableCell key={column.id} align={column.align}>
                          {column.format && typeof value === 'number'
                            ? column.format(value)
                            : value}
                        </TableCell>
                      );
                    })}
                  </TableRow>
                );
              })}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[10, 25, 100]}
        component="div"
        count={rows.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </Paper>);
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

  const EnterCompany = () =>  {
    const [age, setAge] = React.useState('keyword');

  const handleChange = (event) => {
    setAge(event.target.value);
  };

    return(
        <div>
            <Navbar/>
            <Box sx={ {display: 'flex', flexDirection: "row", height:'15vh', backgroundColor:'darkblue',  alignItems:'center', paddingX: "40px", paddingY: '10px'} }>
                <Box sx={ {display: 'flex', flexDirection: "column", height:'15vh', backgroundColor:'darkblue',  alignItems:'center'} }>
                <Typography  variant="h6" sx={{color: 'white'}} >Grading Summary</Typography>
                <Typography  variant="h9" sx={{color: 'white'}} >Students: 200</Typography>
                <Typography  variant="h9" sx={{color: 'white'}} >Submitted: 80</Typography>
                <Typography  variant="h9" sx={{color: 'white'}} >Needs Grading: 120</Typography>
                </Box>
                <div>


        <FormControl sx={{ m: 1, minWidth: 120, marginLeft:'10vw' }} size="small">
      <InputLabel id="demo-select-small-label"  sx={{color: 'white'}}>Filter</InputLabel>
      <Select
        labelId="demo-select-small-label"
        id="demo-select-small"
        value={age}
        label="Filter"
        onChange={handleChange}
        sx={{color: 'white'}}
      >
        <MenuItem value={"keyword"}>Keyword</MenuItem>
        <MenuItem value={"company"}>Company</MenuItem>
        <MenuItem value={"course_code"}>Course Code</MenuItem>
      </Select>
    </FormControl>
      </div>
      <Box sx={{marginLeft: '10vw'}} >
                <Search>
            <SearchIconWrapper>
              <SearchIcon />
            </SearchIconWrapper>
            <StyledInputBase sx = {{color: "white"}}
              placeholder="TYPE VALUE"
              inputProps={{ 'aria-label': 'search' }}
            />
          </Search>
          </Box>
                
            </Box>
            <CustomTable/>
        </div>
    );
}

export default EnterCompany;