

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.RowFilter.Entry;
import javax.swing.RowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Reports {

	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnViewFullCourses;
	private JButton btnWriteFullCourses;
	private JTextField textField;
	private JLabel lblSearch;
	private JButton button;

	/**
	 * Launch the application.
	 */
	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reports window = new Reports();
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
	public Reports() {
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
		
		table.setAutoCreateRowSorter(true);
		
		
		
		scrollPane.setBounds(6, 69, 424, 177);
		frame.getContentPane().add(scrollPane);
		
		btnViewFullCourses = new JButton("View Full Courses");
		btnViewFullCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminViewFull newScreen = new AdminViewFull();
				newScreen.newScreen();
				frame.setVisible(false);
				
			}
		});
		btnViewFullCourses.setBounds(6, 248, 140, 24);
		frame.getContentPane().add(btnViewFullCourses);
		
		btnWriteFullCourses = new JButton("Write Full Courses");
		btnWriteFullCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
							bufferedWriter.write("Course ID: " + course.getCourseId() + "/nCourse name: "+ course.getCourseName()+ "/nCourse Section: " + course.getSection() + "/nCourse Instructor: " +course.getInstructor());
							
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
		
		});
		btnWriteFullCourses.setBounds(290, 248, 140, 24);
		frame.getContentPane().add(btnWriteFullCourses);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>((TableModel)table.getModel());
				sorter.setRowFilter(RowFilter.regexFilter("(?i)"+textField.getText()));
				table.setRowSorter(sorter);
			}
			
		});
		
		textField.setBounds(91, 42, 265, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		lblSearch = new JLabel("Search:");
		lblSearch.setBounds(37, 47, 61, 16);
		frame.getContentPane().add(lblSearch);
		
		button = new JButton("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu newScreen = new AdminMenu();
				newScreen.newScreen();
				frame.setVisible(false);
			}
		});
		button.setBounds(327, 6, 117, 29);
		frame.getContentPane().add(button);
	}
	
	
	
	

	
}

