import React from 'react';
import './introduction.css';
import { useNavigate } from 'react-router-dom';

const Introduction = () => {
  const navigate = useNavigate();
  const handleClick = () => {
    navigate('/login');
  }
  const handleAnnouncements = () =>{
    navigate('/announcements');
  }
  return (
    <div className = "introduction-all-items">
      <div className="introduction-container">
        <div className="introduction-textnbutton">
          <h2 className="introduction-title fadeIn zoomIn">RIGEL SOFTWARE</h2>
          <button className="introduction-button login-btn fadeInUp" onClick = {handleClick}>Login</button>
          <button className="introduction-button announcement-btn fadeInUp" onClick = {handleAnnouncements}>Announcements</button>
        </div>
        <div className="ring ring1 fadeOut"></div>
        <div className="ring ring2 fadeOut"></div>
        <div className="ring ring3 fadeOut"></div>
      </div>
    </div>

  );
}

export default Introduction;
