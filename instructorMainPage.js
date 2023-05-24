import React from 'react';
import './instructorMainPage.css';
import logo from './bilkent.png';

const InstructorMainPage = () => {
  return (
    <div className="all-items">  
      <div className="header">
        <div className="image-div">
          <img src={logo} alt="Bilkent University logo" className="image-instructor" />
          <h2 className="header_title">INTERNSHIP MANAGEMENT SYSTEM</h2>
        </div>
        <img className="announcement_icon" />
        <img className="nofitication_icon" />
        <img className="logout_icon" />
      </div>
      <h3 className="header_welcome_message">Welcome H. Altay Güvenir!</h3>
      <div className="body_class">
        <div className="Profile_Information">
          <p className="user_name">H.Altay Güvenir!</p>
          <p className="email">turing@ug.bilkent.edu.tr</p>
          <div className="switch_div">
            <p className="notify">Notify me with mail</p>
            <label className="switch">
              <input type="checkbox" />
              <span className="slider round"></span>
            </label>
          </div>
          <form action="/action_page.php">
            <input type="file" id="myFile" name="filename" />
            <input type="submit" />
          </form>
          <hr />
          <p className="announcements">ANNOUNCEMENTS</p>
          <hr />
          <p className="semester_statistics">SEMESTER STATISTICS</p>
          <hr />
        </div>

        <div className="student_profile_body">
        <div className="student_profile">
            <div className="display_inline">
              <h4 className="student_name">Name: Ece Ateş</h4>
              <button className="all_reports">ALL REPORTS</button>
            </div>
            <h4 className="student_course">Course: CS299</h4>
            <h4 className="student_check">Initial Check: Passed</h4>
            <h4 className="student_status">Status: Revision Asked</h4>
            <button className="evaluate">EVALUATE</button>
            <button className="criteria_mode">CRITERIA MODE</button>
            <button className="sign_grade_form">SIGN GRADE FORM</button>
          </div>
          <div className="student_profile">
            <div className="display_inline">
              <h4 className="student_name">Name: Ece Ateş</h4>
              <button className="all_reports">ALL REPORTS</button>
            </div>
            <h4 className="student_course">Course: CS299</h4>
            <h4 className="student_check">Initial Check: Passed</h4>
            <h4 className="student_status">Status: Revision Asked</h4>
            <button className="evaluate">EVALUATE</button>
            <button className="criteria_mode">CRITERIA MODE</button>
            <button className="sign_grade_form">SIGN GRADE FORM</button>
          </div>
          <div className="student_profile">
            <div className="display_inline">
              <h4 className="student_name">Name: Ece Ateş</h4>
              <button className="all_reports">ALL REPORTS</button>
            </div>
            <h4 className="student_course">Course: CS299</h4>
            <h4 className="student_check">Initial Check: Passed</h4>
            <h4 className="student_status">Status: Revision Asked</h4>
            <button className="evaluate">EVALUATE</button>
            <button className="criteria_mode">CRITERIA MODE</button>
            <button className="sign_grade_form">SIGN GRADE FORM</button>
          </div>
          <div className="student_profile">
            <div className="display_inline">
              <h4 className="student_name">Name: Ece Ateş</h4>
              <button className="all_reports">ALL REPORTS</button>
            </div>
            <h4 className="student_course">Course: CS299</h4>
            <h4 className="student_check">Initial Check: Passed</h4>
            <h4 className="student_status">Status: Revision Asked</h4>
            <button className="evaluate">EVALUATE</button>
            <button className="criteria_mode">CRITERIA MODE</button>
            <button className="sign_grade_form">SIGN GRADE FORM</button>
          </div>
          {<div className="student_profile">
            <div className="display_inline">
              <h4 className="student_name">Name: Ece Ateş</h4>
              <button className="all_reports">ALL REPORTS</button>
            </div>
            <h4 className="student_course">Course: CS299</h4>
            <h4 className="student_check">Initial Check: Passed</h4>
            <h4 className="student_status">Status: Revision Asked</h4>
            <button className="evaluate">EVALUATE</button>
            <button className="criteria_mode">CRITERIA MODE</button>
            <button className="sign_grade_form">SIGN GRADE FORM</button>
          </div>
          }
        </div>
      </div>
    </div>
  );
}

export default InstructorMainPage;
