import Header, { NameHeader } from "./Headers.js";
import StudentCard from "./StudentCard.js";
import InfoCard from "./InformationCard.js";
import { useState, useEffect } from 'react'
import { fetchStudentCourseData, fetchUserData } from "./apiConnect.js";
import logo from './bilkent.png';
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
  
  const imgLogo = require("./bilkent.png")

  const [courseData, setCourseData] = useState([]);
  const [studentData, setStudentData] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const userId = localStorage.getItem('userId');
        
        const longValue = parseInt(userId, 10);
        console.log(`/n ${userId} /n`);

        const fetchedStudentData = await fetchUserData(longValue);
        const student = fetchedStudentData.user;
        setStudentData(student);
        const course = fetchedStudentData.course_info;
        localStorage.setItem("courses", course);

        setCourseData(prevCourseData => [...prevCourseData, course]);
        
        console.log(course);
        console.log(student);
      } catch (error) {
        // Handle error
      }
    };

    fetchData();
  }, []);

  function determineCourses(courseData) {
    let course = "";
    console.log(courseData);
    const course_array = courseData[0];
    console.log(course_array);
    course_array.map((item, key) => {
      console.log(item);
      course = (key === 1)
        ? `${course}, ${item.course_name}`
        : `${item.course_name}`
    });
    return course;
  }

  return (courseData && studentData) ? (
    <div className="StudentMainPage" style = {{width: "100vw", height: "80vh"}}>
      <div className="instructorMainPage-header">
        <div className="instructorMainPage-image-div">
          <img src={logo} alt="Bilkent University logo" className="instructorMainPage-image" />
          <h2 className="instructorMainPage-header_title">INTERNSHIP MANAGEMENT SYSTEM</h2>
        </div>
        <img className="instructorMainPage-announcement_icon" />
        <img className="instructorMainPage-nofitication_icon" />
        <img className="instructorMainPage-logout_icon" />
      </div>
      <NameHeader name= {studentData.name}/>
      <div className="courses" style={{ width: "100vw", height: "80vh", display: "flex", flexDirection: "row" }}>
        <div style={{ width: "35vw" }}>
          <InfoCard name={studentData.name} studentid={studentData.studentId} courses={determineCourses(courseData)} email={studentData.email} />
        </div>
        <div style={{ width: "65vw", marginLeft: "12vw", marginTop: "6vh", flex: "display", flexDirection: "column" }}>
        {courseData[0].map((course, index) => {
            console.log(course.lastInternshipReportID);
            return (
              <div key={index}>
                <StudentCard
                  courseName={course.course_name}
                  instructor={"eray tüzün"}
                  course_index={index}
                  status={course.status}
                  deadline={course.deadline}
                  courseId={course.course_id}
                  folderId={course.folder_id}
                  lastInternshipId={course.lastInternshipReportID}
                />
              </div>
            );
          })}
        </div>
      </div>
    </div>
  ) : null;
}

export default StudentMainPage;
