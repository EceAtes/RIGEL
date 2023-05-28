import React from 'react';
import './announcements.css';
import image from './announcements-image.png';

const AnnouncementsPage = () => {
  return (
    <div className = "announcements-all-items">
        <div className="announcements-headerContainer">
        <img src = {image} className="announcements-image" alt="image of megaphone on a purple background" />
        <h1 className="announcements-title">Announcements</h1>
        </div>

        <h2 className="announcements-header">From the Faculty</h2>

        <div className="announcements-announcement">
        <div className="announcements-announcementText">
            <h4 className = "announcements-text-title">Phone Number of Summer Training Division at Dean's office</h4>
            <p className="announcements-date">28.08.2022 ~ 21.10.2023</p>
        </div>
        <p className="announcements-paragraph">Phone number of Summer Training Division at Dean's Office is x1261.</p>
        </div>

        <hr />

        <div className="announcements-announcement">
        <div className="announcements-announcementText">
            <h4 className = "announcements-text-title">Summer Training Information</h4>
            <p className="announcements-date">11.05.2022 ~ 11.05.2023</p>
        </div>
        <p className="announcements-paragraph">All the information together with the needed forms and statements are accessible at mf.bilkent.edu.tr under the heading "Summer Training".</p>
        </div>

        <h2 className="announcements-header">From the Instructors</h2>

        <div className="announcements-announcement">
        <div className="announcements-announcementText">
            <h4 className = "announcements-text-title">Deadline of the all summer training reports</h4>
            <p className="announcements-date">28.02.2023 ~ 28.04.2023</p>
        </div>
        <p className="announcements-paragraph">You need to upload your reports until 28.04.23. The students who did not upload their reports until the deadline will fail the course!</p>
        </div>

        <hr />

        <div className="announcements-announcement">
        <div className="announcements-announcementText">
            <h4 className = "announcements-text-title">All the files you might need for the report</h4>
            <p className="announcements-date">28.02.2023 ~ 01.06.2023</p>
        </div>
        <p className="announcements-paragraph">I have uploaded example report files and instructions to Moodle. I strongly suggest checking those files before writing your reports.</p>
        </div>
    </div>
  );
};

export default AnnouncementsPage;
