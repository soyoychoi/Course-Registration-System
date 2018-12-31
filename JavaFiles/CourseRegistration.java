
import java.io.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.TextField;
import java.awt.Window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class CourseRegistration {

	private JFrame frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseRegistration window = new CourseRegistration();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		ArrayList<Course> courseList = new ArrayList<Course>(); 
		if (new File("Courses.ser").exists()) {
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
			catch(ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
		}
		else {
			String fileName = "MyUniversityCourses.csv";
			ArrayList<String[]> allCourses = new ArrayList<String[]>(); 
			
			//make an ArrayList here for students as well 
			ArrayList<Student> studentList = new ArrayList<Student>(); 
			try {
				//Reading the course file into arrays 
				FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = null; 
				while ((line = bufferedReader.readLine()) != null) {
					String[] splitLine = line.split(",");
					allCourses.add(splitLine); 
				}
				//Serializing to file courses.ser
				FileOutputStream fos = new FileOutputStream("Courses.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos); 
				
				//Write each object into the oos (serializing the arraylist with objects)
				for (int i = 1; i < allCourses.size(); i++) {
					String courseName = allCourses.get(i)[0]; 
					String courseId = allCourses.get(i)[1];
					int maxNum = Integer.parseInt(allCourses.get(i)[2]);
					int currentNum = Integer.parseInt(allCourses.get(i)[3]);
					ArrayList<Student> studentNames= new ArrayList<Student>();
					String instructor = allCourses.get(i)[5]; 
					String section = allCourses.get(i)[6]; 
					String location = allCourses.get(i)[7];
					
					Course newCourse = new Course(courseName, courseId, maxNum, currentNum, studentNames, instructor, section, location); 
					courseList.add(newCourse); 
				}
				oos.writeObject(courseList);
				oos.close(); 
				fos.close(); 
				bufferedReader.close(); 
			}
			catch(FileNotFoundException ex) {
				System.out.println("Unable to open file '" + fileName + "'");
				ex.printStackTrace();
			}
			catch(IOException ex) {
				System.out.println("Error reading file '" + fileName + "'");
				ex.printStackTrace();
			}
		}		
	}

	/**
	 * Create the application.
	 */
	public CourseRegistration() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setForeground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NYU Course Registration System");
		lblNewLabel.setBounds(133, 62, 201, 29);
		lblNewLabel.setFont(new Font("Helvetica", Font.PLAIN, 13));
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnAdmin = new JButton("Admin");
		btnAdmin.setBounds(181, 103, 89, 29);
		btnAdmin.setFont(new Font("Helvetica", Font.PLAIN, 13));
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminWindow newWindow = new AdminWindow(); 
				newWindow.NewScreen();
				frame.setVisible(false);
			}
		});
		frame.getContentPane().add(btnAdmin);
		
		JButton btnStudent = new JButton("Student");
		btnStudent.setBounds(181, 131, 89, 29);
		btnStudent.setFont(new Font("Helvetica", Font.PLAIN, 13));
		btnStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentWindow newScreen = new StudentWindow();
				newScreen.newScreen();
				frame.setVisible(false);
			}
		});
		frame.getContentPane().add(btnStudent);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void newScreen() {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseRegistration window = new CourseRegistration();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	
}
