

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StudentActions {
	private Student student;
	private JFrame frame;
	private JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void newScreen(Student x) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentActions window = new StudentActions(x);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentActions(Student x) {
		this.student = x; 
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Student x = this.student;
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("NYU Course Registration System");
		label.setBackground(Color.WHITE);
		label.setBounds(6, 6, 271, 48);
		frame.getContentPane().add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		
		ArrayList<Course> courseList = new ArrayList<Course>();
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
		ArrayList<Student> studentList = new ArrayList<Student>();
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
		
		String[] columns = {"Course Name", "Course ID", "Maximum Number of Students", "Current Number of Students", "List of Students Enrolled", "Instructor", "Section Number", "Location"} ; 
		String[][] data = new String[courseList.size()][columns.length];
	
		
		for (int i = 0; i < courseList.size(); i++) {
			data[i][0] = courseList.get(i).getCourseName();
			data[i][1] = courseList.get(i).getCourseId();
			data[i][2] = Integer.toString(courseList.get(i).getMaxNum());
			data[i][3] = Integer.toString(courseList.get(i).getCurrentNum());
			data[i][4] = "";
			if (!courseList.get(i).getStudentNames().isEmpty()) {
				for (int j = 0; j < courseList.get(i).getStudentNames().size(); j++) {
					String firstName = courseList.get(i).getStudentNames().get(j).getFirstname(); 
					String lastName = courseList.get(i).getStudentNames().get(j).getLastname(); 
					String studentName = firstName + " " + lastName;
					data[i][4] += studentName + "\n"; 
				}
			}
			else {
				data[i][4] = "";
			}
			data[i][5] = courseList.get(i).getInstructor();
			data[i][6] = courseList.get(i).getSection();
			data[i][7] = courseList.get(i).getLocation();
		}
		
		table = new JTable(data, columns);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(6, 79, 314, 193);
		frame.getContentPane().add(scrollPane);
		
		table.setAutoCreateRowSorter(true);
		
		JButton btnNewButton = new JButton("Available Courses");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AvailableCourses newScreen = new AvailableCourses(x);
				newScreen.newScreen(x);
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(327, 81, 117, 22);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				ArrayList<Student> studentList = new ArrayList<Student>();
				studentList = null; 
				try {
					FileInputStream fis = new FileInputStream("Student.ser"); 
					ObjectInputStream ois = new ObjectInputStream(fis); 
					studentList = (ArrayList<Student>)ois.readObject();
					ois.close(); 
					fis.close();
				}
				catch(IOException ex) {
					ex.printStackTrace();
				}
				catch(ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				}
				int row = table.getSelectedRow(); 
				String courseName = (String)table.getValueAt(row, 0);
				String instructor = (String)table.getValueAt(row, 5);
				boolean notFound = true;
				boolean success = true;
				Course registerCourse = new Course(); 
				for (Course course: courseList) {
					if (course.getCourseName().equals(courseName)&&course.getInstructor().equals(instructor)) {
						registerCourse = course;
					}
				}
				
				for (Student student: registerCourse.getStudentNames()) {
					if (student.getUsername().equals(x.getUsername())) {
						notFound = false; 
						JOptionPane.showMessageDialog(frame, "You are already enrolled in this course. Please choose another course.");
					}
				}
				
				if (registerCourse.getMaxNum() == registerCourse.getCurrentNum()) {
					success = false;
					JOptionPane.showMessageDialog(frame, "This course is full. Please choose a different course.");
				}
				
				else if (notFound==true && success == true) {
					for (Student student: studentList) {
						if (student.getUsername().equals(x.getUsername())) {
							ArrayList<Course> myList = student.getMyCourseList();
							myList.add(registerCourse);
							student.setMyCourseList(myList);
							x.setMyCourseList(myList);
							
							
							for (Course register: courseList) {
								if (register.getCourseId().equals(registerCourse.getCourseId()) && register.getInstructor().equals(registerCourse.getInstructor())) {
									ArrayList<Student> studentEnrolled = register.getStudentNames();
									studentEnrolled.add(student);
									register.setStudentNames(studentEnrolled);
									register.setCurrentNum(studentEnrolled.size());
								}
							}
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
					try {
						FileOutputStream fos = new FileOutputStream("Student.ser"); 
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(studentList);
						oos.close(); 
						fos.close();
					}
					catch(FileNotFoundException fnfe) {
						fnfe.printStackTrace();
					}
					catch(IOException ioe) {
						ioe.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(frame, "You have successfully enrolled in Course: " + registerCourse.getCourseName() + ".");
					
					StudentActions newScreen = new StudentActions(x);
					newScreen.newScreen(x);
					frame.setVisible(false);
				}
				
			}
		});
		btnRegister.setBounds(327, 132, 117, 22);
		frame.getContentPane().add(btnRegister);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String courseName = (String)table.getValueAt(row, 0);
				String instructor = (String)table.getValueAt(row, 5);
				ArrayList<Course> courseList = new ArrayList<Course>();
				ArrayList<Student> studentList = new ArrayList<Student>();
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
				try {
					FileInputStream fis1 = new FileInputStream("Student.ser");
					ObjectInputStream ois1 = new ObjectInputStream(fis1);
					studentList = (ArrayList<Student>)ois1.readObject();
					ois1.close();
					fis1.close();
				}
				catch(IOException ioe) {
					ioe.printStackTrace();
				}
				catch(ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				}
				
				ArrayList<Course> myList = new ArrayList<Course>();
				ArrayList<Student> students = new ArrayList<Student>();
				boolean notFound = true; 
				for (Course course: courseList) {
					if (course.getCourseName().equals(courseName)&&course.getInstructor().equals(instructor)) {
						for (Student st: course.getStudentNames()) {
							if (st.getUsername().equals(x.getUsername())) {
								notFound = false;
								students = course.getStudentNames();
								students.remove(x);
								course.setStudentNames(students);
								course.setCurrentNum(students.size());
								myList = x.getMyCourseList();
								myList.remove(course);
								x.setMyCourseList(myList);
								
								for (Student student: studentList) {
									if (student.getUsername().equals(x.getUsername())) {
										student.setMyCourseList(myList);
										student.getMyCourseList().remove(course);
									}
								}
							}
						}
					}
				}
				
				for (int i = 0; i < courseList.size(); i++) {
					if (courseList.get(i).getCourseName().equals(courseName) && courseList.get(i).getInstructor().equals(instructor)) {
						for (int j = 0; j < courseList.get(i).getStudentNames().size(); j++) {
							if (courseList.get(i).getStudentNames().get(j).getUsername().equals(x.getUsername())) {
								courseList.get(i).getStudentNames().remove(j);
								courseList.get(i).setCurrentNum(courseList.get(i).getStudentNames().size());
							}
						}
					}
				}
			
				
				if (notFound) {
					JOptionPane.showMessageDialog(frame, "You are not enrolled in this course. Please choose a different course to withdraw from.");
				}
				else {	
					JOptionPane.showMessageDialog(frame, "You have successfully withdrawn from the Course: " + courseName+".");
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
					try {
						FileOutputStream fos1 = new FileOutputStream("Student.ser");
						ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
						oos1.writeObject(studentList);
						
						oos1.close();
						fos1.close();
					}
					catch(FileNotFoundException fnfe) {
						fnfe.printStackTrace();
					}
					catch(IOException ioe) {
						ioe.printStackTrace();
					}
				}
				
				StudentActions newScreen = new StudentActions(x);
				newScreen.newScreen(x);
				frame.setVisible(false);
			}
		});
		
		btnWithdraw.setBounds(327, 158, 117, 22);
		frame.getContentPane().add(btnWithdraw);
		
		JButton btnMyCourses = new JButton("My Courses");
		btnMyCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>((TableModel)table.getModel());
				sorter.setRowFilter(RowFilter.regexFilter(x.getFirstname()+" " +x.getLastname(),4));
				table.setRowSorter(sorter);
			}
		});
		btnMyCourses.setBounds(327, 106, 117, 22);
		frame.getContentPane().add(btnMyCourses);
		
		JButton btnSignOut = new JButton("Sign Out");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(frame, "Thank you " + x.getFirstname() + " " + x.getLastname() + ".\nDo you wish to sign out?", "Sign Out", 0); 
				if (option == 0) {
					CourseRegistration newScreen = new CourseRegistration();
					newScreen.newScreen();
					frame.setVisible(false);
				}
			}
		});
		btnSignOut.setBounds(327, 250, 117, 22);
		frame.getContentPane().add(btnSignOut);
		
		JLabel lblSearch = new JLabel("Search:");
		lblSearch.setBounds(16, 55, 61, 16);
		frame.getContentPane().add(lblSearch);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>((TableModel)table.getModel());
				sorter.setRowFilter(RowFilter.regexFilter("(?i)"+textField.getText()));
				table.setRowSorter(sorter);
			}
		});
		textField.setBounds(69, 50, 239, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
