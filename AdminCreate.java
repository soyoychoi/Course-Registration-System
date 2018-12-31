
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
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

public class AdminCreate {

	private JFrame frame;
	private JTextField textField;
	private JLabel lblCourseId;
	private JTextField textField_1;
	private JLabel lblMaximumNumberOf;
	private JTextField textField_2;
	private JLabel lblInstructor;
	private JTextField textField_3;
	private JLabel lblSection;
	private JTextField textField_4;
	private JLabel lblLocation;
	private JTextField textField_5;
	private JButton btnCreateCourse;
	private JButton btnMenu;

	/**
	 * Launch the application.
	 */
	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminCreate window = new AdminCreate();
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
	public AdminCreate() {
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
		label.setBounds(6, 6, 271, 48);
		frame.getContentPane().add(label);
		
		JLabel lblCourseName = new JLabel("Course Name: ");
		lblCourseName.setBounds(6, 48, 92, 16);
		frame.getContentPane().add(lblCourseName);

		textField = new JTextField();
		textField.setBounds(96, 43, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		lblCourseId = new JLabel("Course ID: ");
		lblCourseId.setBounds(6, 71, 92, 16);
		frame.getContentPane().add(lblCourseId);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(96, 66, 130, 26);
		frame.getContentPane().add(textField_1);
		
		lblMaximumNumberOf = new JLabel("Max Number of Students: ");
		lblMaximumNumberOf.setBounds(6, 99, 164, 16);
		frame.getContentPane().add(lblMaximumNumberOf);
		
		textField_2 = new JTextField();
		textField_2.setBounds(169, 94, 57, 26);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		lblInstructor = new JLabel("Instructor:");
		lblInstructor.setBounds(6, 127, 74, 16);
		frame.getContentPane().add(lblInstructor);
		
		textField_3 = new JTextField();
		textField_3.setBounds(96, 122, 130, 26);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		lblSection = new JLabel("Section:");
		lblSection.setBounds(6, 151, 61, 16);
		frame.getContentPane().add(lblSection);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(96, 146, 130, 26);
		frame.getContentPane().add(textField_4);
		
		lblLocation = new JLabel("Location:");
		lblLocation.setBounds(6, 177, 61, 16);
		frame.getContentPane().add(lblLocation);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(96, 172, 130, 26);
		frame.getContentPane().add(textField_5);
		
		btnCreateCourse = new JButton("Create Course");
		btnCreateCourse.setBounds(6, 205, 117, 29);
		frame.getContentPane().add(btnCreateCourse);
		btnCreateCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean success = true; 
				String message = "";
				String courseName = textField.getText(); 
				if (courseName.isEmpty()) {
					success = false;
					message+= "Please enter a course name.\n";
				}
				String courseId = textField_1.getText(); 
				if (courseId.isEmpty()) {
					success = false;
					message +=  "Please enter a course ID.\n";
				}
				int maxNum = 0;
				try {
					maxNum = Integer.parseInt(textField_2.getText());
					if (maxNum <= 0) {
						success = false;
						message += "Enter a positive integer for maximum number of students.\n";
					}
				}
				catch (NumberFormatException ex) {
					success = false;
					message+="Enter a positive integer for maximum number of students.\n";
				}
				int currentNum = 0; 
				String instructor = textField_3.getText(); 
				if (instructor.isEmpty()) {
					success = false;
					message += "Enter an instructor.\n";
				}
				ArrayList<Student> studentNames = new ArrayList<Student>();
				String section = textField_4.getText(); 
				if (section.isEmpty()) {
					success = false;
					message+= "Enter a section number.\n";
				}
				String location = textField_5.getText(); 
				if (location.isEmpty()) {
					success = false;
					message+= "Enter a location.\n";
				}
				
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
				
				boolean notFound = true; 
				ArrayList<Course> same = new ArrayList<Course>();
				for (Course x: courseList) {
					if (x.getCourseName().equals(courseName)) { 
						same.add(x);
						notFound = false; 
					}
				}
				if (notFound != true) {
					for (Course s: same) {
						if (s.getCourseName().equals(courseName)) {
							if (!s.getCourseId().equals(courseId)) {
								success = false; 
								message += "You have entered the wrong course ID for an existing course.\n";
								break;
							}
							if (s.getInstructor().equals(instructor)) {
								success = false;
								message += "Enter a different instructor for an existing course.\n";
								break;
							}
							if (s.getSection().equals(section)) {
								success = false; 
								message += "Enter a different section for an existing course.\n";
								break;
							}
						}
					}
				}
				if (success == true) {
					Course newCourse = new Course(courseName, courseId, maxNum, currentNum, studentNames, instructor, section, location);
					courseList.add(newCourse);
					try {
						FileOutputStream fos = new FileOutputStream("Courses.ser");
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(courseList);
						
						message = "This course is successfully created.\n";
						
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
				JOptionPane.showMessageDialog(frame, message);
				if (success == true) {
					AdminDelete newWindow = new AdminDelete(); 
					newWindow.newScreen(); 
					frame.setVisible(false);
				}
			}
		});
		
		
		btnMenu = new JButton("Back");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminDelete newWindow = new AdminDelete(); 
				newWindow.newScreen(); 
				frame.setVisible(false);
			}
		});
		btnMenu.setBounds(327, 6, 117, 29);
		frame.getContentPane().add(btnMenu);
	}
}
