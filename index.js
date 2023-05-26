import Createuserpage from './createuserpage.js';
import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import InstructorMainPage from './instructorMainPage.js';
import reportWebVitals from './reportWebVitals';
import PageMerge from './pageMerge.js';
import StudentMainPage from './StudentMainPage.js';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <PageMerge/>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
