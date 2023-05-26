import { Typography, Box, Table, FormControl, InputLabel, Select, MenuItem} from "@mui/material";
import { Paper, TableContainer, TableCell, TableHead, TableRow, TableBody, TablePagination} from "@mui/material";
import React from "react";
import InputBase from '@mui/material/InputBase';
import { styled, alpha } from '@mui/material/styles';

const columns = [
    { id: 'name', label: 'Name Surname', minWidth: 170 },
    { id: 'roles', label: 'Roles', minWidth: 100 },
    { id: 'withdraw', label: 'Withdraw', minWidth: 100 },
    { id: 'progress_status', label: 'Progress Status', minWidth: 100 },
   
  ];
    
  function createData(name, roles, withdraw, progress_status) {
    return { name, roles, withdraw, progress_status };
  }
  
  const rows = [
    createData('Eray Tüzün', 'Instructor', "", "16 Done 12 Under Revision"),
    createData('Aytekin Ismail', 'Student', "", "Under Revision"),
    createData('Ece Ateş', 'Student', "X", "Submitted"),
    createData('Yahya Elnouby', 'TA', "", "14 Done 11 Under Revision"),
    
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
                <TableCell
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
  
const Createuserpage = () => {
  const [age, setAge] = React.useState('keyword');  
  const handleChange = (event) => {
    setAge(event.target.value);
  };

    return(
        <div>
            <Box sx={ {display: 'flex', flexDirection: "row", height:'8vh', backgroundColor:'darkblue',  alignItems:'center', paddingX: "40px", paddingY: '10px'} }>
                <Typography  variant="h6" sx={{color: 'white'}} >Match</Typography>
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
        <MenuItem value={"role"}>Role</MenuItem>
        <MenuItem value={"progress_status"}>Progress Status</MenuItem>
      </Select>
    </FormControl>
      </div>
      <Box sx={{marginLeft: '10vw'}} >
                <Search>
            <StyledInputBase
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

export default Createuserpage;