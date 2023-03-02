# Welcome to RIGEL

Hi! This project is a internship managment system for Bilkent University Students.

**Members**
> Aytekin İsmail 22003988

> Ece Ateş 22002908

> İzgi Nur Tamcı 22002682

> Ömer Asım Doğan 21903042

> Zeynep Begüm Kara 22003880


# Features

 -   **Login** page with authentication since system will check student ID.
    
-   Company information list will be available without authentication.
    
-   Retrieve student information to **check prerequisite** for taking CS299/CS399 (CS202).
    
-   Retrieve **previous internships** from the school database.
    
-   List previous internships with all sort of filters.
    
-   **Filters** for location of company, department, sector, rating.

	> *Rating:*  Students will be able to rate their company after their internships.
	> *Department:* Students will be able to see their departments related firms.

-   Ability to **search** by company name.

-   Direct students to the company page if applicable.

-   **Profile page**
	>  - previous internships
	> - necessary informations to automatically fill form
	> - progress bar

-   Automatically generate application forms for internship.
    
-   **Announcement page** will send e-mail to students if student wants.

-   System will send a warning e-mail before deadline for internship documents.
    
-   **FAQ** will be added to website to reach easier.
    
-   There exist 4 report:
   	> - **Company Report:** 
	> - **Evaluation Report:** 
	> - **Evaluation Criteria Report:**
	> - **Internship Report:**
	
-   There exist 5 roles:
	> -  **Admin user** role will maintain the system.
		* Will initialize the system in the beginning of the semester
		* Can create all users
		* Can see all users and change their user type in the system
		* Changes and sees everything from every department
		
	> - **Secratary user** role coordinates their department
		* Can see all students and instructors of their department
		* Matches instructors with students
		* Creates final Evaluation Report of the student and sends it to student's instructor
		* Sees Company Reports and enters company grades to Evaluation Report
		* Can create and delete new Instructors, TAs and Students of their department

	> -  **Instructor user** role can evaluate internship reports and view student profiles (only the ones they are responsible with)
		* Can see and edit Evaluation Reports of assigned students
		* Creates Evaluation Criteria Report of assigned students
		* Can see and annotate Internship Report of assigned students
		* Has e-signature
		
	> - **TA User** role checks reports initially 
		* Can see their mentor's students' reports
	 	* Quality check
		* Format checking
		* Turnitin check
	
	> - **Student User** role can upload internship reports to the system
		* Can see progress of their reports
		* Can see their Internship Report versions in their profile page
		* Can see their previous internships if any


	>- **Department Chair** role manages their department
		* Sees statistics about a their department
		* Can change matches between students and instructors (reassign for emergency)
		* Sees all Evaluation Criteria Reports of their department
		* Sees all Evaluation Reports
	

# Technologies
This project will be implemented using **Spring Boot** for back-end, **MongoDB** for database and **React** for front-end.
