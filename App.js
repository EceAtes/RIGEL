import { Typography, Box, Table, FormControl, InputLabel, Select, MenuItem, Input} from "@mui/material";
import { Paper, TableContainer, TableCell, TableHead, TableRow, TableBody, TablePagination} from "@mui/material";
import React from "react";
import SearchIcon from '@mui/icons-material/Search';
import InputBase from '@mui/material/InputBase';
import { styled, alpha } from '@mui/material/styles';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import { green } from "@mui/material/colors";

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};


  const columns = [
    { id: 'name', label: 'Name Surname', minWidth: 170 },
    { id: 'roles', label: 'Roles', minWidth: 100 },
    { id: 'email', label: 'Email', minWidth: 100 },
    { id: 'password', label: 'Password', minWidth: 100 },
   
  ];
  
  function createData(name, roles, email, password) {
    return { name, roles, email, password };
  }
  
  const rows = [
    createData('Eray Tüzün', 'Instructor', "eraytuzun@bilkent.edu.tr", "5SdkgJ2h"),
    createData('Aytekin Ismail', 'Student', "aytekin@bilkent.edu.tr", "5SdkgJ2h"),
    createData('Ece Ateş', 'Student', "e.ates@bilkent.edu.tr", "5SdkgJ2h"),
    createData('Yahya Elnouby', 'TA', "yahyaelnouby@bilkent.edu.tr", "5SdkgJ2h"),
    createData('Yahya Elnouby', 'TA', "yahyaelnouby@bilkent.edu.tr", "5SdkgJ2h"),
    createData('Yahya Elnouby', 'TA', "yahyaelnouby@bilkent.edu.tr", "5SdkgJ2h"),
    createData('Yahya Elnouby', 'TA', "yahyaelnouby@bilkent.edu.tr", "5SdkgJ2h"),
    createData('Yahya Elnouby', 'TA', "yahyaelnouby@bilkent.edu.tr", "5SdkgJ2h"),
    createData('Yahya Elnouby', 'TA', "yahyaelnouby@bilkent.edu.tr", "5SdkgJ2h"),
    createData('Yahya Elnouby', 'TA', "yahyaelnouby@bilkent.edu.tr", "5SdkgJ2h"),
    createData('Yahya Elnouby', 'TA', "yahyaelnouby@bilkent.edu.tr", "5SdkgJ2h"),
    createData('Yahya Elnouby', 'TA', "yahyaelnouby@bilkent.edu.tr", "5SdkgJ2h"),
    createData('Yahya Elnouby', 'TA', "yahyaelnouby@bilkent.edu.tr", "5SdkgJ2h"),
  ];

const CustomTable = () => {

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);
  const [selectedValues, setSelectedValues] = React.useState({
    roleSelect: '',
    departmentSelect: '',
    courseSelect: '',
  });

  const [inputValues, setInputValues] = React.useState({
    nameInput: '',
    surnameInput: '',
    emailInput: '',
  });
  const [finalObject, setFinalObject] = React.useState({});

  const handleInputChange = (event, attributeName) => {
    setInputValues(prevValues => ({
      ...prevValues,
      [attributeName]: event.target.value
    }));
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const handleSelectChange = (event, selectKey) => {
    setSelectedValues(prevValues => ({
      ...prevValues,
      [selectKey]: event.target.value
    }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    // Create your object using the form data
    const myObject = {
      nameInput: inputValues.nameInput,
      surnameInput: inputValues.surnameInput,
      emailInput: inputValues.emailInput,
      roleSelect: selectedValues.roleSelect,
      departmentSelect: selectedValues.departmentSelect,
      courseSelect: selectedValues.courseSelect,
    };

    // Do something with the object, such as sending it to an API or updating state
    console.log(myObject);
    setInputValues({
      nameInput: '',
      surnameInput: '',
      emailInput: '',
    });
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
                  sx ={{backgroundColor: '#5BA3F8'}}
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
      <Button onClick={handleOpen}>Create new User</Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
      <Box sx={style}>
      <div>
        <FormControl component= "form" onSubmit = {handleSubmit} sx={{ m: 1, minWidth: 100, marginLeft:'1vw' }} size="small">
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
  
        <FormControl sx={{ m: 1, minWidth: 130, marginLeft:'1vw' }} size="small">
          <InputLabel id="demo-select-small-label"  sx={{color: 'black'}}>Department</InputLabel>
          <Select 
              value={selectedValues.departmentSelect}
              onChange={(e) => handleSelectChange(e, 'departmentSelect')}
              labelId="demo-select-small-label"
              id="departmentSelect"
              label="select-department"
              sx={{color: 'black'}}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              <MenuItem value={"CS"}>CS</MenuItem>
              <MenuItem value={"EE"}>EE</MenuItem>
              <MenuItem value={"IE"}>IE</MenuItem>
              <MenuItem value={"ME"}>ME</MenuItem>
          </Select>
        </FormControl>


        <FormControl sx={{ m: 1, minWidth: 100, marginLeft:'1vw' }} size="small">
          <InputLabel id="demo-select-small-label"  sx={{color: 'black'}}>Course</InputLabel>
          <Select
              value={selectedValues.courseSelect} 
              onChange={(e) => handleSelectChange(e, 'courseSelect')}
              labelId="demo-select-small-label"
              id="courseSelect"
              label="select-course"
              sx={{color: 'black'}}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              <MenuItem value={"299"}>299</MenuItem>
              <MenuItem value={"399"}>399</MenuItem>
          </Select>
        </FormControl>
      </div>

      <Box sx={{marginLeft: '10vw'}} >
        <CustomInput 
          value={inputValues.nameInput} 
          onChange={(e) => handleInputChange(e, 'nameInput')}
          sx ={{border: '1px solid black', marginTop: '1vw'}}
        >
          <StyledInputBase 
            placeholder="NAME" 
            inputProps={{ 'aria-label': 'search' }}
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
          />
        </CustomInput>

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
          />
        </CustomInput>
      </Box>
      <Button 
        onClick={handleClose}
      >Exit</Button>
      <Button 
        onClick={handleSubmit}
      >Finish</Button>
    </Box>
  </Modal>
</Paper>);
}

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

const App = () => {

  return(
    <div>
      <Box sx={ {height:'8vh', backgroundColor:'#105ba5',  alignItems:'center', paddingX: "40px", paddingY: '10px'} }>
        <Typography  align= "center" variant="h6" sx={{color: 'white'}} >USER LIST</Typography>
      </Box>
      <CustomTable/>
    </div>
  );
}

export default App;