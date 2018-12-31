
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public abstract class User implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 366617396520476248L;
	private String username; 
	private String password; 
	private String firstname;
	private String lastname;
	private ArrayList<Course> myCourseList = new ArrayList<Course>(); 
	
	//Constructor
	User() {}
	public User(String username, String password, String firstname, String lastname) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	//Generate getters and setters 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	} 
	
	//other methods
	public void exit() {
		System.exit(0);
	}
	
	public void viewAll() {
		// TODO Auto-generated method stub
		ArrayList<Course> courseList = new ArrayList<Course>(); 
		courseList = null; 
		try {
			FileInputStream fis = new FileInputStream("Courses.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			courseList = (ArrayList<Course>)ois.readObject();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		for (Course x: courseList) {
			System.out.println("------------------------------------------");
			x.printCourse();
			System.out.println("------------------------------------------"); 
		}
	}
	public ArrayList<Course> getMyCourseList() {
		return myCourseList;
	}
	public void setMyCourseList(ArrayList<Course> myCourseList) {
		this.myCourseList = myCourseList;
	}
}

interface adminMethods {
	//course management methods 
	public void create(); 
	public void delete();
	public void edit(); 
	public void display();
	public void register();
	//report methods 
	public void viewAll(); 
	public void viewFull();
	public void writeFull();
	public void viewStudent();
	public void viewStudentCourse();
	public ArrayList<Course> sortCourse();
}

interface studentMethods {
	public void viewAll(); 
	public void viewAvailable(); 
	public void register(); 
	public void withdraw(); 
	public void viewmyCourse(); 
}

@SuppressWarnings("serial")
class Admin extends User implements adminMethods {

	public Admin() {
		this("Admin", "Admin001", "So-Young", "Choi");
	}

	public Admin(String username, String password) {
		this(username, password, "So-Young", "Choi");
	}
	public Admin(String username, String password, String firstname, String lastname) {
		super(username, password, firstname, lastname);
	}

	@Override
	//Creates a course
	public void create() {
		Scanner input = new Scanner(System.in);
		ArrayList<Course> courseList = new ArrayList<Course>();
		courseList = null; 
		
		try {
			FileInputStream fis = new FileInputStream("Courses.ser"); 
			ObjectInputStream ois = new ObjectInputStream(fis);
			courseList = (ArrayList<Course>)ois.readObject(); 
			ois.close();
			fis.close(); 
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		
		String courseName = null;
		String courseId = null; 
		int maxNum = 0; 
		int currentNum = 0; 
		String instructor = null; 
		ArrayList<Student> studentNames = new ArrayList<Student>(); 
		String section = null;
		String location = null;
		
		while (true) {
			System.out.println("Please enter course name: "); 
			courseName = input.nextLine(); 
			boolean notFound = true; 
			ArrayList<Course> same = new ArrayList<Course>();
			//if course name exists, make sure course ID is the same too, but the instructor has to be different! 
			for (Course x: courseList) {
				if (x.getCourseName().equals(courseName)) {
					notFound = false; 
					same.add(x);
				} 
			}
			//ask if user wants to create the same course 
			if (notFound != true) {
				System.out.println("This course already exists. You can add the course under a different instructor and section number.");
				
				courseId = same.get(0).getCourseId(); 
				System.out.println("Please enter the maximum number of students: "); 
					//check if it is an integer 
				while (true) {
					String max = input.nextLine(); 
					try {
						maxNum = Integer.parseInt(max); 
						break;
					}
					catch(NumberFormatException nfe) {
						System.out.println("Please enter an integer. ");
						continue;
					}
				}
					
				//Make sure the name is different 
				while (true) {
					System.out.println("Enter the name of instructor: ");
					instructor = input.nextLine();
					boolean donotExist = true; 
					for (Course x: same) {
						if (x.getInstructor().equals(instructor)) {
							donotExist = false;
							System.out.println("The course under this instructor already exists. Please enter a different name. ");
						}
					}
					if (donotExist == true) {
						break;
					}
				}
				//get all the other ones as well 
				
				while (true) {
					System.out.println("Please enter the section number: "); 
					section = input.nextLine(); 
					boolean doesnotExist = true; 
					for (Course x: same) {
						if (x.getSection().equals(section)) {
							doesnotExist = false; 
							System.out.println("The course under this section number already exists. Please enter a different section number. ");
						}
					}
					if (doesnotExist == true) {
						break;
					}
				}
				
				
				//location
				System.out.println("Please enter the location: "); 
				location = input.nextLine(); 
				
				//Create a new course object 
				Course createdCourse = new Course(courseName, courseId, maxNum, currentNum, studentNames, instructor, section, location);
				//Add it to the existing courseList 
				courseList.add(createdCourse);
				
				
				//serialize courselist 
				
				try {
					FileOutputStream fos = new FileOutputStream("Courses.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(courseList);
					
					System.out.println("The course was successfully created.");
					
					oos.close();
					fos.close();
				}
				catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				}
				catch(IOException ioe) {
					ioe.printStackTrace();
				}
				
				break;
			}
		
			else if (notFound == true) {
				//enter course ID 
				while (true) {
					System.out.println("Please enter course ID: "); 
					courseId = input.nextLine(); 
					//make sure courseId does not overlap 
					boolean notExist = true; 
					for (Course x: courseList) {
						if (x.getCourseId().equals(courseId)) {
							notExist = false;
							System.out.println("This course ID already exists. Please enter a different course ID.");
						}
					}
					if (notExist == false) {
						continue; 
					}
					break;
				}
				
				//enter max number 
				while (true) {
					System.out.println("Please enter the maximum number of students: "); 
					String max = input.nextLine(); 
					try {
						maxNum = Integer.parseInt(max); 
						break;
					}
					catch(NumberFormatException nfe) {
						System.out.println("Please enter an integer. ");
						continue;
					}
				}
				
				//enter name of instructor 
				System.out.println("Please enter the name of instructor: "); 
				instructor = input.nextLine();
				
				System.out.println("Please enter the section number: "); 
				section = input.nextLine(); 
				
				//location
				System.out.println("Please enter the location: "); 
				location = input.nextLine(); 

				//Create a new course object 
				Course createdCourse = new Course(courseName, courseId, maxNum, currentNum, studentNames, instructor, section, location);
				//Add it to the existing courseList 
				courseList.add(createdCourse); 
				
				try {
					FileOutputStream fos = new FileOutputStream("Courses.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(courseList);
					
					System.out.println("The course was successfully added.");
					
					oos.close();
					fos.close();
				}
				catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				}
				catch(IOException ioe) {
					ioe.printStackTrace();
				}
				break;
			}
		}		
	}

	@Override
	//Deletes a course
	public void delete() {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		ArrayList<Course> courseList = new ArrayList<Course>();
		courseList = null; 
		
		try {
			FileInputStream fis = new FileInputStream("Courses.ser"); 
			ObjectInputStream ois = new ObjectInputStream(fis);
			courseList = (ArrayList<Course>)ois.readObject(); 
			ois.close();
			fis.close(); 
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		ArrayList<Course> sameCourseId = new ArrayList<Course>();
		String courseId = null;
		while (true) {
			System.out.println("Please enter the course ID of the course you wish to delete: "); 
			courseId = input.nextLine(); 
			boolean courseNotExist = false; 
			for (Course x: courseList) {
				if (x.getCourseId().equals(courseId)) {
					courseNotExist = true; 
					sameCourseId.add(x); 
				}
			}
			if (courseNotExist == false) {
				System.out.println("The course ID you entered does not exist. Please enter an existing course ID.");
			}
			if (courseNotExist == true) {
				break;
			}
		}
		int num = 0; 
		Course deleteCourse = null; 
		while (true) {
			System.out.println("Enter the number of the course you would like to delete.");
			for (int i = 0; i < sameCourseId.size(); i++) {
				System.out.println(i+ ")" + sameCourseId.get(i).getCourseName() +" course under instructor " + sameCourseId.get(i).getInstructor());
			}
			try {
				num = Integer.parseInt(input.nextLine());
				deleteCourse = sameCourseId.get(num);
				courseList.remove(deleteCourse);
				break;
			}
			catch (NumberFormatException nfe) {
				System.out.println("Please enter a number between 0 and "+sameCourseId.size() + ".");
			}
			catch (ArrayIndexOutOfBoundsException aiobe) {
				System.out.println("Please enter a number between 0 and " + sameCourseId.size() +".");
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream("Courses.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(courseList);
				
			oos.close();
			fos.close();
		}
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void edit() {
		//Get courseID 
		Scanner input = new Scanner(System.in);
		ArrayList<Course> courseList = new ArrayList<Course>();
		courseList = null; 
		
		try {
			FileInputStream fis = new FileInputStream("Courses.ser"); 
			ObjectInputStream ois = new ObjectInputStream(fis);
			courseList = (ArrayList<Course>)ois.readObject(); 
			ois.close();
			fis.close(); 
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		//ask for course ID and course name 
		
		boolean NotFound = true; 
		while (true) {
			System.out.println("Enter the course ID of the course you wish to edit: ");
			String courseId = input.nextLine();
			System.out.println("Enter the instructor name of the course you wish to edit: "); 
			String instructor = input.nextLine(); 
			for (Course x: courseList) {
				if (x.getCourseId().equals(courseId) &&x.getInstructor().equals(instructor)) {
					NotFound = false; 
					
					System.out.println("You will be editing course " + x.getCourseName() + " under instructor "+ x.getInstructor()+".");
					while (true) {
						System.out.println("Enter the number of the option you wish to edit. "); 
						System.out.println("1. Maximum number of students");
						System.out.println("2. Current number of students"); 
						System.out.println("3. Add or remove students");
						System.out.println("4. Instructor");
						System.out.println("5. Section");
						System.out.println("6. Location");
						String number = input.nextLine(); 
						if (number.equals("1")) {
							System.out.println("The maximum number of students is " + x.getMaxNum());
							while (true) {
								System.out.println("Enter the new maximum number of students: ");
								try {
									int maxNum = Integer.parseInt(input.nextLine());
									if (maxNum == x.getMaxNum()) {
										System.out.println("You did not make any changes to the maximum number of students.");
									}
									if (maxNum >= 0){ 
										x.setMaxNum(maxNum);
										break; 
									}
									else {
										System.out.println("Please enter a positive integer.");
									}
								}
								catch(NumberFormatException nfe) {
									System.out.println("Please enter an integer.");
								}
							}
							break;
						}
						else if (number.equals("2")) {
							System.out.println("The current number of students is " + x.getCurrentNum());
							int difference = 0; 
							int currentNum = 0;
							while (true) {
								System.out.println("Enter the new current number of students: ");
								try {
									currentNum = Integer.parseInt(input.nextLine());
									if (currentNum >= 0 && currentNum <= x.getMaxNum()) {
										if (currentNum == x.getCurrentNum()) {
											System.out.println("You did not make any changes to the current number of students.");
										}
										difference = currentNum - x.getCurrentNum();
										break;
									}
									else {
										System.out.println("Please enter a positive integer less than the maximum number of students.");
									}
								}
								catch(NumberFormatException nfe) {
									System.out.println("Please enter an integer."); 
								}
							}
							if (difference <0) {
								for (int i = 0; i < Math.abs(difference); i ++) {
									x.removeStudent();
									
								}
							}
							else if (difference >0) {
								for (int i = 0; i < difference; i++ ) {
									x.addStudent(); 
									
								}
							}
							x.setCurrentNum(currentNum);
							break;
						}
						else if (number.equals("3")) {
							System.out.println("Would you like to add or remove students?"); 
							String addorRemove = input.nextLine(); 
							if (addorRemove.equalsIgnoreCase("add")) {
								x.addStudent();
								break;
							}
							else if (addorRemove.equalsIgnoreCase("remove")) {
								x.removeStudent();
								break;
							}
							else {
								System.out.println("Please enter 'add' or 'remove'. ");
							}
						}
						else if (number.equals("4")) {
							System.out.println("Enter the name of instructor."); 
							String courseInstructor = input.nextLine(); 
							//make sure that course Instructor == original instructor 
							if (x.getInstructor().equals(courseInstructor)) { 
								System.out.println("You did not make any changes to the instructor name.");
								break; 
							}
							else {
								x.setInstructor(courseInstructor);
								break; 
							}
						}
						//set section 
						else if (number.equals("5")) {
							//make sure same section number does not exist 
							String sectionnumber = null;
							while (true) {
								System.out.println("Enter the new section number."); 
								sectionnumber = input.nextLine(); 
								boolean cannot = true; 
								for (Course course: courseList) {
									if (course.getCourseId().equals(courseId)&&course.getSection().equals(sectionnumber)) {
										cannot = false;
									}
								}
								if (cannot == false) {
									System.out.println("You cannot have two courses with the same name under the same section number.");
									continue;
								}
								else {
									break;
								}
							}
							if (x.getSection().equals(sectionnumber)) {
								System.out.println("You did not make any changes to the section number.");
							}
							else {
								x.setSection(sectionnumber);
								System.out.println("The section number was changed to " + sectionnumber + ".");
								break;
							}
						}
						else if (number.equals("6")) {
							System.out.println("Enter the new location.");
							String newLocation = input.nextLine();
							if (x.getLocation().equals(newLocation)) {
								System.out.println("You did not make any changes to the location");
								break; 
							}
							else {
								x.setLocation(newLocation);
								break;
							}
						}
						else {
							System.out.println("Please enter a valid input. A valid input is an integer between 1 and 6.");
						}
						//serialize course back 
					}
				}
			}
			if (NotFound == true) {
				System.out.println("This course does not exist. Please enter a different course.");
			}
			else {
				break;
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream("Courses.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(courseList);
			
			oos.close();
			fos.close(); 
		}
		catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return;
		//serialize courseList again 	
	}
		
	@Override
	//Displays a course
	public void display() {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		ArrayList<Course> courseList = new ArrayList<Course>();
		try {
			FileInputStream fis = new FileInputStream("Courses.ser");
			ObjectInputStream ois = new ObjectInputStream(fis); 
			courseList = (ArrayList<Course>)ois.readObject();
			
			ois.close();
			fis.close();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		ArrayList<Course> displayCourse = new ArrayList<Course>(); 
		while (true) {
			System.out.println("Enter course ID of the course you wish to view: "); 
			boolean NotFound = true; 
			String courseId = input.nextLine();
			for (Course course: courseList) {
				if (course.getCourseId().equals(courseId)) {
					NotFound = false; 
					displayCourse.add(course); 
				}
			}
			if (NotFound == true) {
				System.out.println("Please enter an existing course ID. ");
			}
			else {
				for (Course display: displayCourse) {
					display.printCourse();
				}
				break;
			}
		}
	}

	@Override
	//Registers a student in a course
	public void register() {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		String newUsername = null;
		ArrayList<Student> studentList = new ArrayList<Student>();
		
		while (true) {
			//check if username exists 
			boolean notFound = true; 
			System.out.println("Please enter the username of the student: "); 
			newUsername = input.nextLine();
			if (new File("Student.ser").exists()) {
				try {
					studentList = null;
					FileInputStream fis = new FileInputStream("Student.ser");
					ObjectInputStream ois = new ObjectInputStream(fis);
					studentList = (ArrayList<Student>)ois.readObject();
					ois.close();
					fis.close(); 
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}
				catch(ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				}
				boolean notExist = true; 
				for (Student x: studentList) {
					if (x.getUsername().equals(newUsername)) {
						notExist = false; 
						System.out.println("This username already exists. Please pick another username"); 
					}
				}
				if (notExist) {
					break; 
				}
			}
			else {
				break;
			}
		}
		System.out.println("Please enter student password: ");
		String newPassword = input.nextLine(); 
		System.out.println("Please enter student's firstname: ");
		String firstname = input.nextLine(); 
		System.out.println("Please enter student's lastname: ");
		String lastname = input.nextLine(); 
		ArrayList<Course> studentCourse = new ArrayList<Course>(); 
		
		Student newStudent = new Student(newUsername, newPassword, firstname, lastname, studentCourse); 
		studentList.add(newStudent);
		
		try {
			FileOutputStream fosStudent = new FileOutputStream("Student.ser");
			ObjectOutputStream oosStudent = new ObjectOutputStream(fosStudent);
			oosStudent.writeObject(studentList);
			
			oosStudent.close();
			fosStudent.close();
		}
		catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("The student " + newStudent.getFirstname() + ", "+newStudent.getLastname()+" is registered.");
	}
	


	@Override
	//Views full courses
	public void viewFull() {
		// TODO Auto-generated method stub
		ArrayList<Course> courseList = new ArrayList<Course>(); 
		courseList = null; 
		try {
			FileInputStream fis = new FileInputStream("Courses.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			courseList = (ArrayList<Course>)ois.readObject();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		for (Course x: courseList) {
			if (x.getMaxNum() == x.getCurrentNum()) {
				System.out.println("Here are courses that are currently full."); 
				System.out.println("-----------------------------------------");
				x.printCourse();
				System.out.println("-----------------------------------------");
			}
		}
		
	}
	
	@Override
	//Writes full courses to a file 
	public void writeFull() {
		//fill in code 
		ArrayList<Course> courseList = new ArrayList<Course>(); 
		courseList = null; 
		try {
			FileInputStream fis = new FileInputStream("Courses.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			courseList = (ArrayList<Course>)ois.readObject();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		String fileName = "fullCourses.txt"; 
		Scanner input = new Scanner(System.in);
		try {
			FileWriter fileWriter = new FileWriter(fileName); 
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			String text = input.nextLine(); 
			bufferedWriter.write(text);
			for (Course course: courseList) {
				if (course.getMaxNum() == (course.getCurrentNum())) {
					bufferedWriter.write("Course ID: " + course.getCourseId() + "/n"+ "Course name "+ course.getCourseName()+ "Course Section" + course.getSection() + "Course Instructor" +course.getInstructor());
					
				}
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		}
		catch (IOException ioe) {
			System.out.println("Error writing file '" + fileName + "'");
			ioe.printStackTrace();
		}
	}

	@Override
	//Views student list of a particular course
	public void viewStudent() {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		ArrayList<Course> courseList = new ArrayList<Course>(); 
		courseList = null; 
		try {
			FileInputStream fis = new FileInputStream("Courses.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			courseList = (ArrayList<Course>)ois.readObject();
			ois.close();
			fis.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		while (true) {
			System.out.println("Please enter course ID: "); 
			boolean notFound = true; 
			String courseId = input.nextLine(); 
			System.out.println("Here are the names of students enrolled in the course.");
			for (Course x: courseList) {
				if (x.getCourseId().equals(courseId)) {
					notFound = false; 
					for(Student student: x.getStudentNames()) {
						System.out.println(student.getFirstname() +", "+ student.getLastname()); 
					}
				}
			}
			if (notFound == true) {
				System.out.println("Please enter an existing course ID. Try again. "); 
			}
			if (notFound == false) {
				break;
			}
		}
		
	}

	@Override
	//views course list of a particular student
	public void viewStudentCourse() {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		ArrayList<Student> studentList =new ArrayList<Student>();
		studentList = null;
		try {
			FileInputStream fis = new FileInputStream("Student.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			studentList = (ArrayList<Student>)ois.readObject();
			ois.close();
			fis.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		while (true) {
			System.out.println("Please enter student first name: ");
			String firstname = input.nextLine(); 
			System.out.println("Please enter student last name: "); 
			String lastname = input.nextLine(); 
			boolean NotFound = true; 
			for (Student student: studentList) {
				if (student.getFirstname().equals(firstname) && student.getLastname().equals(lastname)) {
					System.out.println("Here are the courses " + student.getFirstname() + ", " + student.getLastname() + " is enrolled in.");
					NotFound = false; 
					for (Course x: student.getMyCourseList()) {
						x.printCourse();
					}
				}
			}
			if (NotFound == true) {
				System.out.println("The student does not exist in our system. Please enter an existing student's first name and last name.");
			}
			else {
				break;
			}
		}
	}

	@Override
	//sort the course in descending order
	public ArrayList<Course> sortCourse() {
		ArrayList<Course> courseList = new ArrayList<Course>(); 
		courseList = null; 
		try {
			FileInputStream fis = new FileInputStream("Courses.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			courseList = (ArrayList<Course>)ois.readObject();
			ois.close();
			fis.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		Collections.sort(courseList);
		return courseList; 
	}
}

class Student extends User implements java.io.Serializable, studentMethods {
	private static final long serialVersionUID = -7476811726596272561L;
	private String username; 
	private String password; 
	private String firstname; 
	private String lastname; 
	private ArrayList<Course> myCourseList = new ArrayList<Course>();
	
	//constructor 
	public Student() {
		super();
	}

	public Student(String username, String password, String firstname, String lastname, ArrayList<Course> myCourseList) {
		this.username = username; 
		this.password = password; 
		this.firstname = firstname; 
		this.lastname = lastname;
		this.myCourseList = myCourseList;
	}
	
	//getters and setters 
	public ArrayList<Course> getMyCourseList() {
		return myCourseList;
	}

	public void setMyCourseList(ArrayList<Course> myCourseList) {
		this.myCourseList = myCourseList;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	//Implement additional methods
	 
	@Override
	//view available courses
	public void viewAvailable() {
		ArrayList<Course> courseList = new ArrayList<Course>(); 
		courseList = null; 
		try {
			FileInputStream fis = new FileInputStream("Courses.ser"); 
			ObjectInputStream ois = new ObjectInputStream(fis); 
			courseList = (ArrayList<Course>)ois.readObject();
			ois.close(); 
			fis.close();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		System.out.println("Here are the courses that are available: ");
		for (Course course: courseList) {
			if (course.getMaxNum() > course.getCurrentNum()) {
				course.printCourse();
			}
		}
	}

	@Override
	//register to a course
	public void register() {
		Scanner input = new Scanner(System.in);
		ArrayList<Course> courseList = new ArrayList<Course>(); 
		courseList = null;
		try {
			FileInputStream fis = new FileInputStream("Courses.ser"); 
			ObjectInputStream ois = new ObjectInputStream(fis); 
			courseList = (ArrayList<Course>)ois.readObject();
			ois.close(); 
			fis.close();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		while (true) {
			System.out.println("Please enter course name: "); 
			String courseName = input.nextLine();
			System.out.println("Please enter course section: "); 
			String section = input.nextLine();
			boolean NotFound = true; 
			for (Course course: courseList) {
				if (course.getCourseName().equals(courseName) && course.getSection().equals(section)) {
					//make sure the course is not full 
					//make sure that its not already in her list of courses
					boolean NotExist = true; 
					for (Course myCourse: this.getMyCourseList()) {
						if (myCourse.getCourseName().equals(course.getCourseName())&&(myCourse.getSection()).equals(course.getSection())) {
							NotExist = false;
							NotFound = false; 
							System.out.println("You are already enrolled in this course."); 
						}
					}
					if (NotExist == true) {
						if (course.getMaxNum() > course.getCurrentNum()) {
							NotFound = false; 
							ArrayList<Student> names = new ArrayList<Student>();
							names = course.getStudentNames(); 
							names.add(this);
							course.setStudentNames(names);
							course.setCurrentNum(course.getCurrentNum()+1);
							ArrayList<Course> mycourseList = new ArrayList<Course>();
							mycourseList = this.getMyCourseList();
							mycourseList.add(course);
							this.setMyCourseList(mycourseList);
							
							try {
								FileOutputStream fos = new FileOutputStream("Courses.ser"); 
								ObjectOutputStream oos = new ObjectOutputStream(fos);
								oos.writeObject(courseList);
								oos.close(); 
								fos.close();
							}
							catch(FileNotFoundException fnfe) {
								fnfe.printStackTrace();
							}
							catch(IOException ioe) {
								ioe.printStackTrace();
							}
							
							System.out.println("You have been successfully added " + this.getFirstname() + ", " + this.getLastname());
						}
						else {
							System.out.println("Sorry, that class is full. Please enter another class.");
						}
					}
					else if (NotExist == false) {
						break;
					}
				}
			}
			if (NotFound == true) {
				System.out.println("The course you entered does not exist. Please enter an existing course.");
			}
			if (NotFound == false) {
				break;
			}
		}
		
	}

	@Override
	//withdraw from a course
	public void withdraw() {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		ArrayList<Course> courseList = new ArrayList<Course>(); 
		courseList = null;
		try {
			FileInputStream fis = new FileInputStream("Courses.ser"); 
			ObjectInputStream ois = new ObjectInputStream(fis); 
			courseList = (ArrayList<Course>)ois.readObject();
			ois.close(); 
			fis.close();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		boolean notFound = true; 
		while (true) {
			System.out.println("Please enter course name: ");
			String courseName = input.nextLine(); 
			System.out.println("Please enter section number");
			String section = input.nextLine(); 
			Course withdraw = null;
			if (this.getMyCourseList().size() > 0) {
				for (Course myCourse : this.getMyCourseList()) {
					if (myCourse.getCourseName().equals(courseName)&&myCourse.getSection().equals(section)) {
						notFound = false; 
						withdraw = myCourse; 
					}
				}
			}
			if (notFound == true) {
				System.out.println("You are not enrolled in this course. Please enter a course you are enrolled in.");
			}
			else if (notFound == false) {
				ArrayList<Course> myList = new ArrayList<Course>(); 
				myList = this.getMyCourseList();
				myList.remove(withdraw);
				this.setMyCourseList(myList);
				
				//also change the course in the course list 
				ArrayList<Student> students = new ArrayList<Student>();
				students = withdraw.getStudentNames();
				students.remove(this);
				withdraw.setStudentNames(students);
				withdraw.setCurrentNum(withdraw.getCurrentNum()-1);
				
				for (Course x: courseList) {
					if (x.getCourseName().equals(courseName)&&x.getSection().equals(section)) {
						x.setStudentNames(withdraw.getStudentNames());
						x.setCurrentNum(withdraw.getCurrentNum());
					}
				}
				try {
					FileOutputStream fos = new FileOutputStream("Courses.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(courseList);
					fos.close();
					oos.close();
				}
				catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
				
				
				System.out.println("You have withdrawn from course "+ courseName + " section " + section +".");
				break;
			}	
		}
	}

	@Override
	//view the course that I am in 
	public void viewmyCourse() {
		// TODO Auto-generated method stub
		for (Course course: this.getMyCourseList()) {
			System.out.println("Here are the courses that " + this.getFirstname() + ", " + this.getLastname() + " is registered in: ");
			course.printCourse();
			System.out.println(" ");
		}
	}
	
}
