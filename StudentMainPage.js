import Header, { NameHeader } from "./common_mainpage_comp/Headers.js";
import ProgressBar_Menu from "./student_mainpage_comp/ProgressBar_Menu.js";
import MainPagePanel from "./student_mainpage_comp/MainPagePanel.js";
import StudentCard from "./stu_mp_comp/StudentCard.js";
import InfoCard from "./stu_mp_comp/InformationCard.js";
import { useState, useEffect } from 'react'
import { fetchStudentCourseData, fetchUserData } from "./api_connection/apiConnect.js";
/*
{courseData.map((student, index) => (
  <div key={index} style={{ marginLeft: "12vw", marginTop: "6vh", marginBottom:"6vh" }}>
    <StudentCard
      courseName={student.courseName}
      instructor={student.instructor}
      status={student.status}
      deadline={student.deadline}
    />
  </div>
))}*/
//<StudentCard courseName={courseData.name} instructor={courseData.instructor} status={courseData.status} deadline={courseData.deadline} />

function StudentMainPage(props) {
  const imgLogo = require("./images/bilkent logo.png")

  const [courseData, setCourseData] = useState([]);
  const [studentData, setStudentData] = useState(null);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const fetchedCourseData = await fetchStudentCourseData(4);
        setCourseData(prevCourseData => [...prevCourseData, fetchedCourseData]);
        
        const fetchedStudentData = await fetchUserData(7);
        setStudentData(fetchedStudentData);

        console.log(fetchedCourseData);
        console.log(fetchedStudentData);
      } catch (error) {
        // Handle error
      }
    };

    fetchData();
  }, []);

  return (courseData && studentData) ? (
    <div className="StudentMainPage">
      <Header />
      <NameHeader name={studentData.name} />
      <div className="courses" style={{ width: "100vw", height: "80vh", display: "flex", flexDirection: "row" }}>
        <div style={{ width: "35vw" }}>
          <InfoCard name={studentData.name} studentid={studentData.studentId} courses={studentData.courses} email={studentData.email} />
        </div>
        <div style={{ width: "65vw", marginLeft: "12vw", marginTop: "6vh", flex: "display", flexDirection: "column" }}>
          {courseData.map((course, index) => (
            <div key={index} >
              <StudentCard
                courseName={course.name}
                instructor={course.instructor}
                status={course.status}
                deadline={course.deadline}
              />
            </div>
          ))}
        </div>
      </div>
    </div>
  ) : null;
}

export default StudentMainPage;
