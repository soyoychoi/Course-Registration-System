package course;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdminRegister {
	private Course course;
	private JFrame frame;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void newScreen(Course register) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminRegister window = new AdminRegister(register);
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
	public AdminRegister(Course register) {
		this.course = register;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Course register = this.course;
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("NYU Course Registration System");
		label.setBackground(Color.WHITE);
		label.setBounds(6, 6, 271, 48);
		frame.getContentPane().add(label);
		
		JLabel lblStudentUsername = new JLabel("Search Student:");
		lblStudentUsername.setBounds(19, 51, 126, 16);
		frame.getContentPane().add(lblStudentUsername);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>((TableModel)table.getModel());
				sorter.setRowFilter(RowFilter.regexFilter("(?i)"+textField.getText()));
				table.setRowSorter(sorter);
			}
		});
		textField.setBounds(140, 46, 215, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		ArrayList<Student> studentList = new ArrayList<Student>();
		ArrayList<Course> courseList = new ArrayList<Course>();
		studentList = null; 
		if (new File("Student.ser").exists()) {
			try {
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
		}
		else {
			JOptionPane.showMessageDialog(frame, "There are no students registered yet.");
			frame.setVisible(false);
		}
		
		
		
		String[] columns = {"Username", "Password", "First Name", "Last Name", "Courses"}; 
		String[][] data = new String[studentList.size()][columns.length];
		
		for (int i = 0; i < studentList.size(); i++) {
			data[i][0] = studentList.get(i).getUsername();
			data[i][1] = studentList.get(i).getPassword();
			data[i][2] = studentList.get(i).getFirstname();
			data[i][3] = studentList.get(i).getLastname();
			data[i][4] = ""; 
			if (!studentList.get(i).getMyCourseList().isEmpty()) {
				for (int j = 0; j < studentList.get(i).getMyCourseList().size(); j++) {
					String courseName = studentList.get(i).getMyCourseList().get(j).getCourseName();
					String section = studentList.get(i).getMyCourseList().get(j).getSection();
					String myCourse = courseName + " " + section; 
					data[i][4] += myCourse + "\n";
				}
			}
		}
		
		table = new JTable(data, columns);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(16, 77, 405, 150);
		frame.getContentPane().add(scrollPane);
		
		table.setAutoCreateRowSorter(true);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Student> studentList = new ArrayList<Student>();
				ArrayList<Course> courseList = new ArrayList<Course>();
				studentList = null; 
				if (new File("Student.ser").exists()) {
					try {
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
				}
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
				catch(ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				}
				int row = table.getSelectedRow(); 
				String username = (String)table.getValueAt(row, 0);
				boolean notFound = true;
				boolean success = true; 
				Student registerStudent = null; 
				
				for (int i = 0; i < studentList.size(); i++) {
					if (studentList.get(i).getUsername().equals(username)) {
						for (Student student: register.getStudentNames()) {
							if (student.getUsername().equals(studentList.get(i).getUsername())) {
								notFound = false; 
								JOptionPane.showMessageDialog(frame, "This student is already enrolled in the course. Please pick a different student.");
							}
						}
						for (Course course: studentList.get(i).getMyCourseList()) {
							if (course.getCourseId().equals(register.getCourseId()) && course.getInstructor().equals(register.getInstructor())) {
								notFound = false; 
								JOptionPane.showMessageDialog(frame, "This student is already enrolled in the course. Please pick a different student.");
							}
						}
						if (notFound==true) {
							
							
							ArrayList<Course> myList = studentList.get(i).getMyCourseList();
							myList.add(register);
							studentList.get(i).setMyCourseList(myList);
							
							for (Course addCourse : courseList) {
								if (addCourse.getCourseId().equals(register.getCourseId()) && addCourse.getInstructor().equals(register.getInstructor())) {
									ArrayList<Student> students = addCourse.getStudentNames();
									students.add(studentList.get(i));
									addCourse.setStudentNames(students); 
									addCourse.setCurrentNum(students.size());
									
									if ((addCourse.getCurrentNum()-1) == addCourse.getMaxNum()) {
										JOptionPane.showMessageDialog(frame, "This course is full. You cannot register any more students into this course.");
										success = false; 
										
										AdminDelete ad = new AdminDelete();
										ad.newScreen();
										frame.setVisible(false);
									}
									
								}
							}
						}
					}
				}
				if (notFound==true&& success == true) {
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
					JOptionPane.showMessageDialog(frame, "You have successfully enrolled Student Username: " + username+ " in Course: " + register.getCourseName() + ".");
					
					AdminRegister newScreen = new AdminRegister(register);
					newScreen.newScreen(register);
					frame.setVisible(false);
				}
				

			}
		});
		btnRegister.setBounds(19, 231, 117, 29);
		frame.getContentPane().add(btnRegister);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminDelete ad = new AdminDelete();
				ad.newScreen();
				frame.setVisible(false);
			}
		});
		btnBack.setBounds(308, 17, 117, 29);
		frame.getContentPane().add(btnBack);
		
		
	}

}
