import Header, { NameHeader } from "./Headers.js";
import StudentCard from "./StudentCard.js";
import InfoCard from "./InformationCard.js";
import { useState, useEffect } from 'react'
import { fetchStudentCourseData, fetchUserData } from "./apiConnect.js";

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
    <div className="StudentMainPage" style = {{width: "100vw", height: "100vh"}}>
      <Header />
      <NameHeader name={studentData.name} />
      <div className="courses" style={{ width: "100vw", height: "80vh", display: "flex", flexDirection: "row" }}>
        <div style={{ width: "35vw" }}>
          <InfoCard name={studentData.name} studentid={studentData.studentId} courses={determineCourses(courseData)} email={studentData.email} />
        </div>
        <div style={{ width: "65vw", marginLeft: "12vw", marginTop: "6vh", flex: "display", flexDirection: "column" }}>
          {courseData[0].map((course, index) => (
            <div key={index} >
              <StudentCard
                courseName={course.course_name}
                instructor={"eray tüzün"}
                status={course.status}
                deadline={"01.06.2023"}
                courseId= {course.course_id}
                folderId={"1W6MkuCBhdnjjw5C7RtZsAixmj40ZAXYc"}
              />
            </div>
          ))}
        </div>
      </div>
    </div>
  ) : null;
}

export default StudentMainPage;
