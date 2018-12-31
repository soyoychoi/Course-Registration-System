
import java.io.*;
import java.util.*; 

public class Course implements java.io.Serializable, java.lang.Comparable<Course>{
	private String courseName; 
	private String courseId;
	private int maxNum; 
	private ArrayList<Student> studentNames = new ArrayList<Student>(); 

	private int currentNum = studentNames.size(); 
	private String instructor; 
	private String section; 
	private String location; 
	//add student list later on (like the actual object) 
	
	// Generate Constructors
	public Course() {}
	public Course(String courseName, String courseId, int maxNum, int currentNum, ArrayList<Student> studentNames,
			String instructor, String section, String location) {
		this.courseName = courseName;
		this.courseId = courseId;
		this.maxNum = maxNum;
		this.currentNum = currentNum;
		this.studentNames = studentNames;
		this.instructor = instructor;
		this.section = section;
		this.location = location;
	}
	
	//Getters and Setters
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	
	public int getCurrentNum() {
		return currentNum;
	}
	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}
	
	public ArrayList<Student> getStudentNames() {
		return studentNames;
	}
	public void setStudentNames(ArrayList<Student> studentNames) {
		this.studentNames = studentNames;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	//course methods
	
	//interface
	interface Comparable<Course> {
		public int compareTo(Course x);
	}
	public void addStudent() {
		
		//Deserialize student list and make sure the username exists
		Scanner input = new Scanner(System.in);
		ArrayList<Student> studentList = new ArrayList<Student>(); 
		studentList = null; 
		if (new File("Student.ser").exists()) {
			try {
				FileInputStream fis = new FileInputStream("Student.ser"); 
				ObjectInputStream ois = new ObjectInputStream(fis); 
				studentList = (ArrayList<Student>)ois.readObject();
				
				ois.close();
				fis.close(); 
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			catch(ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
		}
		else {
			System.out.println("There are no students registered yet.");
			return;
		}
		//make sure username exists 
		boolean doesNotExist = true;
		String username = null; 
		while (true) {
			boolean notFound = true; 
			System.out.println("Please enter student username: ");
			username = input.nextLine();
			for (Student y: studentList) {
				if (y.getUsername().equals(username)) {
					doesNotExist = false; 
					for (Student enrolled: this.getStudentNames()) {
						if (enrolled.getUsername().equals(username)) {
							notFound = false; 
						}
					}
					if (notFound == false) {
						System.out.println("This student is already enrolled in the course.");
						break;
					}
					if (notFound == true) {
						if (this.getMaxNum() > this.getCurrentNum()) {
							doesNotExist = false; 
							ArrayList<Student> myStudent = new ArrayList<Student>();
							myStudent = this.getStudentNames();
							myStudent.add(y);
							this.setStudentNames(myStudent);
							this.setCurrentNum(this.getCurrentNum()+1);
							ArrayList<Course> myCourseList = new ArrayList<Course>();
							myCourseList = y.getMyCourseList();
							myCourseList.add(this);
							y.setMyCourseList(myCourseList);
							
							try {
								FileOutputStream fos = new FileOutputStream("Student.ser");
								ObjectOutputStream oos = new ObjectOutputStream(fos);
								oos.writeObject(studentList);
								fos.close();
								oos.close(); 
							}
							catch (FileNotFoundException fnfe) {
								fnfe.printStackTrace();
							}
							catch (IOException ioe) {
								ioe.printStackTrace();
							}
							System.out.println("Student " + y.getFirstname()+", "+y.getLastname()+" was added to the course.");
							
						}
						else {
							System.out.println("Sorry, that class was full. Please enter another class.");
							continue;
						}
					}
					break;
				}
			}
			if (doesNotExist == true) {
				System.out.println("The username does not exist. Please enter an existing student username."); 
			}
			if (doesNotExist == false) {
				break; 
			}
		}
		
	}
	
	//remove student 
	public void removeStudent() {
		//deserialize student list and make sure username exists 
		ArrayList<Student> studentList = new ArrayList<Student>(); 
		studentList = null;
		if (new File("Student.ser").exists()) {
			try {
				FileInputStream fis = new FileInputStream("Student.ser"); 
				ObjectInputStream ois = new ObjectInputStream(fis); 
				studentList = (ArrayList<Student>)ois.readObject();
				
				ois.close();
				fis.close(); 
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			catch(ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
		}
		else {
			System.out.println("There are no students registered yet.");
			return;
		}
		Scanner input = new Scanner(System.in);
		// make sure username exists
		boolean doesNotExist = true;
		String username = null; 
		while (true) {
			System.out.println("Please enter student username: ");
			username = input.nextLine();
			Student student = null;
			for (Student x: this.getStudentNames()) {
				if (x.getUsername().equals(username)) {
					doesNotExist = false; 
					student = x; 
				}
			}
			if (doesNotExist == true) {
				System.out.println("The username does not exist under the course. Please enter an existing student username enrolled in the course.");
				continue;
			}
			else if (doesNotExist == false) {
				ArrayList<Student> myList = new ArrayList<Student>();
				myList = this.getStudentNames();
				myList.remove(student);
				this.setStudentNames(myList);
				this.setCurrentNum(this.getCurrentNum()-1);
			}
			//also change the course in the student list 
			ArrayList<Course> courses = new ArrayList<Course>();
			courses = student.getMyCourseList();
			courses.remove(this);
			
			for (Student myStudent: studentList) {
				if (myStudent.getUsername().equals(username)) {
					myStudent.setMyCourseList(courses);
				}
			}
			try {
				FileOutputStream fos = new FileOutputStream("Student.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(studentList);
				fos.close();
				oos.close(); 
			}
			catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			System.out.println("You have removed " + student.getFirstname() + ", " + student.getLastname()+".");
			break;
		}
	}
	
	public void printCourse() {
		System.out.println(" ");
		System.out.println("Course Name: " +this.getCourseName());
		System.out.println("CourseID: " + this.getCourseId());
		System.out.println("Maximum number of students possible in the course: "+this.getMaxNum()); 
		System.out.println("Current number of students in the course: " + this.getCurrentNum());
		System.out.println("Students in the course: ");
		for (Student student: this.getStudentNames()) {
			System.out.println(student.getFirstname()+", "+student.getLastname());
		}
		System.out.println("");
		System.out.println("Instructor: " + this.getInstructor());
		System.out.println("Section number: " +this.getSection()); 
		System.out.println("Location: " +this.getLocation()); 
		System.out.println(" ");
	}
	//compare to method 
	public int compareTo(Course x) {
		return (x.getCurrentNum() - this.getCurrentNum());
	}
}
	

		


