import {BrowserRouter as Router, Route,Routes, Link} from 'react-router-dom';
import Login from './login.js';
import InstructorMainPage from './instructorMainPage';
import Introduction from './introduction.js';
import AnnouncementsPage from './announcements.js';
import GoogleDrive from './googledrive.js';
import StudentCard from './StudentCard.js';
import StudentMainPage from './StudentMainPage.js';
import SecretaryMainPage from './ömer/SecretaryMainPage.jsx';
import AdminMainPage from './ömer/AdminMainPage.jsx';
import FeedbackReportPage from './FeedbackReportPage.js';
import CriteriaReportPage from './CriteriReportPage.js';
function Navbar() {
  return (
    <nav>
      <ul>
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/instructorMainPage">About</Link>
        </li>
      </ul>
    </nav>
  );
}

const pageMerge = () => {
    return(
        <Router>
            <>
                <Routes>
                    <Route path = "/" element = {<Introduction/>}/>
                    <Route path = "/login" element = {<Login/>} />
                    <Route path = "/announcements" element = {<AnnouncementsPage/>}/>
                    <Route path = "/instructorMainPage" element = {<InstructorMainPage/>}/>
                    <Route path = "/googledrive" element = {<GoogleDrive/>}/>
                    <Route path = "/studentCard" element = {<StudentCard/>}/>
                    <Route path = "/studentMainPage" element = {<StudentMainPage/>}/>
                    <Route path = "/secretaryMainPage" element = {<SecretaryMainPage/>}/>
                    <Route path = "/adminMainPage" element = {<AdminMainPage/>}/>
                    <Route path = "/feedbackMode" element = {<FeedbackReportPage/>}/>
                    <Route path = "/criteriaMode" element = {<CriteriaReportPage/>}/>
                </Routes>
            </>
        </Router>
    );
}

export default pageMerge;