import React from 'react';
import './login.css';
import logo from './bilkent.png';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';


const API_BASE_URL="http://localhost:8080"
const isInstructor = false;   
const isStudent  = false;    
  const Login = ({ isInstructor, isStudent, isSecretary , isAdmin}) => {
    isInstructor = false;
    isStudent = false;
    isSecretary = false;
    isAdmin = false;
    const validate = () => {
  
      const emailRegex = /^\S+@\S+\.\S+/;
      const username = document.getElementById("email").value;
      const password = document.getElementById("password").value;
      console.log(username);
      console.log(password);
      if (username === "" || password === "") {
          alert("Please enter both username and password"); 
          return false;
      }
  
      if (!emailRegex.test(username)) {
          alert("Please enter a valid email address");
          return false;
      }
  
      console.log("Validate is true");
      return true;
    };
  
    function submitForm (event) {

      event.preventDefault();
      const username = document.getElementById("email").value;
      const password = document.getElementById("password").value;
      if (validate && isValid(username,password)) {
          console.log("Validate is true!!");
          const url = "http://localhost:8080/users/login";
          const myObject = {
          email: username,
          password: password,
          };
          
          fetch(url, {
            method: 'POST',
            body: JSON.stringify(myObject),
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
            localStorage.setItem('departmentId', parsedData.department_id);
            localStorage.setItem('email',parsedData.email);
            localStorage.setItem('password',parsedData.password);
            localStorage.setItem('isVerified', parsedData.isVerified);
            localStorage.setItem('name', parsedData.name);
            localStorage.setItem('notifToMail', parsedData.notifToMail);
            localStorage.setItem('role',parsedData.role);
            localStorage.setItem('userId', parsedData.userId);

            /*localStorage.setItem('status',parsedData.course_info[0].status);
            localStorage.setItem('course_name',parsedData.course_info[0].course_name);
            localStorage.setItem('student_name',parsedData.course_info[0].student_name);
            localStorage.setItem('TA_check',parsedData.course_info[0].TA_check);
*/
            const jsonString = JSON.stringify(parsedData.course_info);

    // Store the JSON string in localStorage
            localStorage.setItem('arrayOfStructs', jsonString);

            /*parsedData.course_info.map((element, index) => {
              localStorage.setItem(`status_${index}`, element.status);
              localStorage.setItem(`course_name_${index}`, element.course_name);
              localStorage.setItem(`student_name_${index}`, element.student_name);
              localStorage.setItem(`TA_check_${index}`, element.TA_check);
              return null; // Returning null to satisfy the map function requirements
            });*/


            if(parsedData.isVerified === true && parsedData.role === "INSTRUCTOR"){
              isInstructor = true;
            }
            else if(parsedData.isVerified === true && parsedData.role === "STUDENT"){
              isStudent = true;
            }
            else if (parsedData.isVerified === true && parsedData.role === "SECRETARY"){
              isSecretary = true;
            }
            else if(parsedData.isVerified === true && parsedData.role === "ADMIN"){
              isAdmin = true;
            }
            handleLogin({isInstructor, isStudent, isSecretary,isAdmin});
          })  
          .catch(error => {
            console.log("ERROR!!!");
            // Handle any errors that occurred during the request
          });
          
          /*const arr = [username,password];
          axios.post("http://localhost:8080/users/login", myObject)
          .then(res => console.log("uwuwuuw",res))
          .catch(err => console.log(err))
          console.log(arr[0]);
          console.log(arr[1]);*/
  
          
      }
    };
  
  
    const isValid = (email, password) => {
      const emailRegex = /^\S+@\S+\.\S+/;
    
      if (email === "" || password === "") {
        alert("Please enter both username and password");
        return false;
      }
    
      if (!emailRegex.test(email)) {
        alert("Please enter a valid email address");
        return false;
      }
    
      console.log("Validate is true");
      return true;
    };  
    const navigate = useNavigate();
  
    const handleLogin = () => {
      console.log("Handle login ENTER");
      if (isInstructor) {
        navigate('/instructorMainPage'); // Navigate to the dashboard if the user is logged in
      }
      else if (isStudent){
        navigate('/studentMainPage');
      }
      else if(isSecretary){
        navigate('/secretaryMainPage');
      }
      else if(isAdmin){
        navigate('/adminMainPage');
      }
       else {
        navigate('/login'); // Navigate back to the login page if the login fails
      }
    };

  
    const [inputValues, setInputValues] = React.useState({
      email: '',
      password: ''
    });
  
    const handleInputChange = (event, attributeName) => {
      setInputValues(prevValues => ({
        ...prevValues,
        [attributeName]: event.target.value
      }));
    };
  
    // ...rest of the component logic
  
  
  

  return (
    <html>
      <head>
        <script src="login.js"></script>
        <link rel="stylesheet" href="login.css" />
        <title>Login Page</title>
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link href="https://fonts.googleapis.com/css2?family=Bruno+Ace+SC&display=swap" rel="stylesheet" />
      </head>
      <body>
        <div className="login-all-items">
          <div className="login-image-div">
            <img src= {logo} alt="Bilkent University logo" className="login-image" />
          </div>
          <div className="login-form-div">
            <h2 className="login-Heading">RIGEL</h2>
            <form action="/login" method="POST" onSubmit={validate} className = "login-form">
              <label htmlFor="email">Email:</label>
              <input type="text" id="email" name="email" value={inputValues.email}  onChange={(e) => handleInputChange(e, 'email')}/><br /><br />
              <label  htmlFor="password">Password:</label>
              <input type="password" id="password" name="password" value={inputValues.password}  onChange={(e) => handleInputChange(e, 'password')} /><br /><br />
              <div className="login-container">
                <a className = "login-a" href="https://www.youtube.com/watch?v=dQw4w9WgXcQ" id="forgot-password">Forgot Password?</a>
                <button className="login-button" type="submit" onClick={submitForm}>Login</button>
              </div>
            </form>
          </div>
        </div>
      </body>
    </html>
  );
};

export default Login;