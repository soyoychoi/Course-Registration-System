

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

public class AdminEdit {
	private Course course;
	private JFrame frame;
	private JTextField MaxNum;
	private JTextField Instructor;
	private JTextField Section;
	private JTextField Location;

	/**
	 * Launch the application.
	 */
	public static void newScreen(Course x) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminEdit window = new AdminEdit(x);
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

	public AdminEdit(Course x) {
		initialize(x);
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Course x) {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("NYU Course Registration System");
		label.setBackground(Color.WHITE);
		label.setBounds(6, 6, 271, 48);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Course Name: ");
		label_1.setBounds(6, 52, 92, 16);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("Course ID: ");
		label_2.setBounds(6, 76, 74, 16);
		frame.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("Max Number of Students: ");
		label_3.setBounds(6, 104, 164, 16);
		frame.getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("Instructor:");
		label_4.setBounds(6, 137, 74, 16);
		frame.getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("Section:");
		label_5.setBounds(6, 161, 61, 16);
		frame.getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("Location:");
		label_6.setBounds(6, 184, 61, 16);
		frame.getContentPane().add(label_6);
		
		MaxNum = new JTextField();
		MaxNum.setBounds(350, 99, 48, 26);
		frame.getContentPane().add(MaxNum);
		MaxNum.setColumns(10);
		
		Instructor = new JTextField();
		Instructor.setBounds(268, 132, 130, 26);
		frame.getContentPane().add(Instructor);
		Instructor.setColumns(10);
		
		Section = new JTextField();
		Section.setBounds(268, 156, 130, 26);
		frame.getContentPane().add(Section);
		Section.setColumns(10);
		
		Location = new JTextField();
		Location.setColumns(10);
		Location.setBounds(269, 179, 129, 26);
		frame.getContentPane().add(Location);
		
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
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
				catch (IOException ex) {
					ex.printStackTrace();
				}
				catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				}
				
				boolean notChange = true;
				boolean success = true;
				String message = "";
				
				String courseId = x.getCourseId();
				String instructor = x.getInstructor();
				for (Course s: courseList) {
					if (s.getCourseId().equals(courseId)&&(s.getInstructor().equals(instructor))) {
						if (!MaxNum.getText().isEmpty()) {
							try {
								int maxNum = Integer.parseInt(MaxNum.getText().trim());
								if (maxNum!=s.getMaxNum()) {
									
									if (maxNum > 0) {
										if (maxNum < x.getCurrentNum()) {
											notChange = false; 
											success = false; 
											message+= "Maximum number of students must be larger than current number of students. Please try again.\n";
										}
										else {
											notChange = false;
											s.setMaxNum(maxNum); 
										}
									}
									
									else {
										success = false;
										message += "Please enter a positive integer for maximum number of students.\n";
									}
								}
							}
							catch (NumberFormatException nfe) {
								success = false;
								message += "Please enter a positive integer for maximum number of students.\n";
							}
						}
						String instructor1 = Instructor.getText().trim(); 
						if ((!s.getInstructor().equals(instructor1))&&(!instructor1.isEmpty())) {
							s.setInstructor(instructor1);
							notChange = false;
						}
						String section = Section.getText().trim(); 
						if ((!s.getSection().equals(section))&&(!section.isEmpty())) {
							s.setSection(section);
							notChange = false;
						}
						String location = Location.getText().trim(); 
						if ((!s.getLocation().equals(location))&&(!location.isEmpty())) {
							s.setLocation(location);
							notChange = false;
						}
					}
				}
				if (success == false) {
					JOptionPane.showMessageDialog(frame, message);
				}
				
				else if (success == true && notChange == true) {
					message = "You did not make any changes to the course."; 
					
					JOptionPane.showMessageDialog(frame, message);
					
					AdminDelete newScreen = new AdminDelete();
					newScreen.newScreen(); 
					frame.setVisible(false);
				}
				
				else if (success==true && notChange==false) {
					message = "You have successfully edited the course.";
					
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
					
					JOptionPane.showMessageDialog(frame, message);
					
					AdminDelete newScreen = new AdminDelete();
					newScreen.newScreen(); 
					frame.setVisible(false);
					
				}
				
				
				
				
			}
		});
		btnEdit.setBounds(113, 231, 117, 29);
		frame.getContentPane().add(btnEdit);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminDelete newScreen = new AdminDelete(); 
				newScreen.newScreen();
				frame.setVisible(false);
			}
		});
		btnBack.setBounds(327, 17, 117, 29);
		frame.getContentPane().add(btnBack);
		
		JLabel lblNewLabel = new JLabel(x.getCourseName());
		lblNewLabel.setBounds(100, 52, 130, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(x.getCourseId());
		lblNewLabel_1.setBounds(100, 76, 130, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel MaxNumLabel = new JLabel(Integer.toString(x.getMaxNum()));
		MaxNumLabel.setBounds(169, 104, 61, 16);
		frame.getContentPane().add(MaxNumLabel);
		
		JLabel InstructorLabel = new JLabel(x.getInstructor());
		InstructorLabel.setBounds(78, 137, 130, 16);
		frame.getContentPane().add(InstructorLabel);
		
		JLabel SectionLabel = new JLabel(x.getSection());
		SectionLabel.setBounds(61, 161, 147, 16);
		frame.getContentPane().add(SectionLabel);
		
		JLabel LocationLabel = new JLabel(x.getLocation());
		LocationLabel.setBounds(71, 184, 136, 16);
		frame.getContentPane().add(LocationLabel);
	}
	
}
