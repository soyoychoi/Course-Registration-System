
import java.awt.EventQueue;


import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdminDelete {
	
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnCreate;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton btnViewFullCourses;
	private JLabel lblSearch;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	
	
	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminDelete window = new AdminDelete();
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
	public AdminDelete() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("NYU Course Registration System");
		label.setBackground(Color.WHITE);
		label.setBounds(17, 6, 271, 48);
		frame.getContentPane().add(label);
		
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
		String[] columns = {"Course Name", "Course ID", "Maximum Number of Students", "Current Number of Students", "List of Students Enrolled", "Instructor", "Section Number", "Location"} ; 
		String[][] data = new String[courseList.size()][columns.length];
	
		
		for (int i = 0; i < courseList.size(); i++) {
			data[i][0] = courseList.get(i).getCourseName();
			data[i][1] = courseList.get(i).getCourseId();
			data[i][2] = Integer.toString(courseList.get(i).getMaxNum());
			data[i][3] = Integer.toString(courseList.get(i).getStudentNames().size());
			data[i][4] = "";
			if (courseList.get(i).getStudentNames().size()!=0) {
				for (int j = 0; j < courseList.get(i).getStudentNames().size(); j++) {
					String firstName = courseList.get(i).getStudentNames().get(j).getFirstname(); 
					String lastName = courseList.get(i).getStudentNames().get(j).getLastname(); 
					String studentName = firstName + " " + lastName;
					data[i][4] += studentName + ", "; 
				}
			}
			data[i][5] = courseList.get(i).getInstructor();
			data[i][6] = courseList.get(i).getSection();
			data[i][7] = courseList.get(i).getLocation();
			
		}
		
		table = new JTable(data, columns);
		scrollPane = new JScrollPane(table);
		
		scrollPane.setBounds(17, 66, 324, 203);
		frame.getContentPane().add(scrollPane);
		
		btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminCreate newWindow = new AdminCreate(); 
				newWindow.newScreen();
				frame.setVisible(false);
			}
		});
		btnCreate.setBounds(346, 90, 84, 21);
		frame.getContentPane().add(btnCreate);
		
		button = new JButton("Delete");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); 
				String courseId = (String)table.getValueAt(row, 1);
				String instructor = (String)table.getValueAt(row, 5);
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
				Course deleteCourse = new Course();
				for (Course x: courseList) {
					if (x.getCourseId().equals(courseId)&&x.getInstructor().equals(instructor)) {
						deleteCourse = x; 
					}
				}
				int output = JOptionPane.showConfirmDialog(frame, "Are you sure you wish to delete this course?\nCourse Name: " + deleteCourse.getCourseName() + "\nCourse ID: " + deleteCourse.getCourseId() + "\nSection Number: " + deleteCourse.getSection() + "\nInstructor: " + deleteCourse.getInstructor()+ "\nLocation: "+deleteCourse.getLocation());
				if (output == 0) {
					
					courseList.remove(deleteCourse);
					
					for (int i = 0; i < studentList.size(); i++) {
						for (int j = 0; j < studentList.get(i).getMyCourseList().size(); j++) {
							if (studentList.get(i).getMyCourseList().get(j).getCourseId().equals(courseId) && studentList.get(i).getMyCourseList().get(j).getInstructor().equals(instructor)) {
								ArrayList<Course> myList = studentList.get(i).getMyCourseList();
								
								myList.remove(j);
								studentList.get(i).setMyCourseList(myList);
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
					catch (FileNotFoundException fnfe) {
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
					catch (FileNotFoundException fnfe) {
						fnfe.printStackTrace();
					}
					catch(IOException ioe) {
						ioe.printStackTrace();
					}
					
					AdminDelete newWindow = new AdminDelete(); 
					newWindow.newScreen();
					frame.setVisible(false);
				}
			}
		});
		
		table.setAutoCreateRowSorter(true);
		
		button.setBounds(346, 112, 84, 21);
		frame.getContentPane().add(button);
		
		button_1 = new JButton("Edit");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				int row = table.getSelectedRow(); 
				String courseId = (String) table.getValueAt(row, 1);
				String instructor =  (String) table.getValueAt(row, 5);
				Course editCourse = new Course();
				for (Course x: courseList) {
					if (x.getCourseId().equals(courseId) && x.getInstructor().equals(instructor)) {
						editCourse = x; 
					}
				}
				AdminEdit newWindow = new AdminEdit(editCourse); 
				newWindow.newScreen(editCourse); 
				frame.setVisible(false);
				
			}
		});
		button_1.setBounds(346, 133, 84, 21);
		frame.getContentPane().add(button_1);
		
		button_2 = new JButton("Register");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			
				int row = table.getSelectedRow(); 
				String courseId = (String)table.getValueAt(row, 1);
				String instructor = (String)table.getValueAt(row, 5);
				Course register = new Course();
				for (Course x: courseList) {
					if (x.getCourseId().equals(courseId) && x.getInstructor().equals(instructor)) {
						register = x; 
					}
				}
				if (register.getMaxNum()== register.getCurrentNum()) {
					JOptionPane.showMessageDialog(frame, "This class is full. Please choose a different course.");
				}
				else {
					int option = JOptionPane.showConfirmDialog(frame, "Do you wish to register a student into Course: " + register.getCourseName()+"?", "Register", 0);
					if (option == 0) {
						AdminRegister newScreen = new AdminRegister(register);
						newScreen.newScreen(register);
						frame.setVisible(false);
					}
				}
				
			}
		});
		button_2.setBounds(346, 155, 84, 21);
		frame.getContentPane().add(button_2);
		
		button_3 = new JButton("Sign Out");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(frame, "Thank you Admin.\nDo you wish to sign out?", "Sign Out", 0);
				if (option==0) {
					CourseRegistration newScreen = new CourseRegistration();
					newScreen.newScreen();
					frame.setVisible(false);
				}
			}
		});
		button_3.setBounds(346, 248, 84, 21);
		frame.getContentPane().add(button_3);
		
		btnViewFullCourses = new JButton("Full Courses");
		btnViewFullCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminViewFull newScreen = new AdminViewFull();
				newScreen.newScreen();
				frame.setVisible(false);
			}
		});
		btnViewFullCourses.setBounds(346, 67, 84, 21);
		frame.getContentPane().add(btnViewFullCourses);
		
		lblSearch = new JLabel("Search:");
		lblSearch.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblSearch.setBounds(17, 45, 61, 16);
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
		textField.setBounds(71, 40, 244, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
	}
	
}
