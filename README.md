# Course-Registration-System
Course Registration System (Java - Eclipse GUI)

![](https://user-images.githubusercontent.com/34804400/50555865-0fbf3280-0ca0-11e9-8c51-e163acf409e0.png)

Project Explanation: Interactive and graphic course registration system, where user can add, delete, register into, or withdraw from courses. 

Motivation: The program makes it easy for users to view and interact with courses registered at a school. The program was created as a school project.

Explanation & Demo: There are two types of users - Admin and Student. 

----------------------------------------------------------ADMIN---------------------------------------------------------

Admin can create, delete, and edit courses, and register a student into a course. 

Admin can sign in with the username "Admin" and password "Admin001." There is only one Admin for this system. 

![](https://user-images.githubusercontent.com/34804400/50555807-611af200-0c9f-11e9-85d8-f4afc0438606.gif)


Admin Methods:

1) Creating a course - Admin: 

![](https://user-images.githubusercontent.com/34804400/50555793-3cbf1580-0c9f-11e9-9e94-6117d79ce9a8.gif)


2) Deleting a course - Admin:

![](https://user-images.githubusercontent.com/34804400/50555795-48124100-0c9f-11e9-8b28-17346cd8df1a.gif)


3) Editing a course - Admin: 

![](https://user-images.githubusercontent.com/34804400/50555797-519ba900-0c9f-11e9-8a08-fad7ed523098.gif)


4) Registering a student into a course - Admin: 

![](https://user-images.githubusercontent.com/34804400/50555806-5b251100-0c9f-11e9-9713-c26f26b87630.gif)


When signed out, Admin is brought back to the starting page. 

![](https://user-images.githubusercontent.com/34804400/50555808-64ae7900-0c9f-11e9-87e4-3aa8965f236a.gif)


----------------------------------------------------------STUDENT------------------------------------------------------

Students can register or withdraw from courses. 

There are multiple students for this program, and students must register before signing in. 

![](https://user-images.githubusercontent.com/34804400/50555814-7c85fd00-0c9f-11e9-9559-835fe441c1bf.gif)


Student Methods: 

1) View available courses (courses that are not full) - Student:

![](https://user-images.githubusercontent.com/34804400/50555809-6a0bc380-0c9f-11e9-93ff-1726894751f4.gif)


2) View courses student is currently enrolled in - Student:

![](https://user-images.githubusercontent.com/34804400/50555811-709a3b00-0c9f-11e9-8413-3e22235af6d9.gif)


3) Register into a course - Student:

![](https://user-images.githubusercontent.com/34804400/50555813-7728b280-0c9f-11e9-9386-00d2404f731e.gif)


4) Withdraw from a course - Student: 

![](https://user-images.githubusercontent.com/34804400/50555818-87409200-0c9f-11e9-8b8a-7a27924feebe.gif)


When signed out, Student is brought back to the starting page. 

![](https://user-images.githubusercontent.com/34804400/50555817-814ab100-0c9f-11e9-8ca1-7cd8af907517.gif)

-----------------------------------------------------PROCEDURE--------------------------------------------------------

How it works: 

This program makes use of serialization. It first reads in the csv file "MyUniversityCourses.csv" which holds a list of already existing programming courses at New York University. 

It lets the user make changes to the data read from the csv file and serialize it into the file "Courses.ser." 

It also lets the user make changes to and serialize list of students into the file "Students.ser." 


Procedure: 

Make sure csv file is in the folder that holds the java files to execute the program properly. 

Must follow these steps to execute the program properly.

1) Run "javac User.java" on terminal
2) Run "javac Course.java" on terminal 
3) Run "javac CourseRegistration.java" on terminal 
4) Run "java CourseRegistration"


CourseRegistration.java is the only file with a main method. 

-------------------------------------------------------------------------------------------------------------------------

Comments - Debugging and Troubleshooting:

Any comments or advice will be appreciated.
