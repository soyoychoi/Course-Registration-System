

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
import javax.swing.JPasswordField;

public class StudentRegister {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentRegister window = new StudentRegister();
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
	public StudentRegister() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("NYU Course Registration System");
		label.setBackground(Color.WHITE);
		label.setBounds(6, 6, 271, 48);
		frame.getContentPane().add(label);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(16, 48, 76, 16);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(16, 71, 76, 16);
		frame.getContentPane().add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(97, 43, 120, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JPasswordField();
		textField_1.setBounds(97, 66, 120, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(16, 104, 76, 16);
		frame.getContentPane().add(lblFirstName);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(97, 99, 120, 26);
		frame.getContentPane().add(textField_2);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(16, 125, 76, 16);
		frame.getContentPane().add(lblLastName);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(97, 120, 120, 26);
		frame.getContentPane().add(textField_3);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean success = true; 
				String message = "";
				String username = textField.getText(); 
				String password = textField_1.getText(); 
				String firstname = textField_2.getText();
				String lastname = textField_3.getText();
				if (username.isEmpty()) {
					success = false; 
					message += "Please enter a username.\n";
				}
				if (password.isEmpty()) {
					success = false; 
					message += "Please enter a password.\n";
				}
				if (firstname.isEmpty()) {
					success = false; 
					message+= "Please enter your first name.\n";
				}
				if (lastname.isEmpty()) {
					success = false; 
					message+= "Please enter your last name.\n";
				}
				if (!success) {
					JOptionPane.showMessageDialog(frame, message);
				}
				ArrayList<Course> studentCourse = new ArrayList<Course>();
				ArrayList<Student> studentList = new ArrayList<Student>();
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
				}
				boolean notExist = true; 
				for (Student x: studentList) {
					if (x.getUsername().equals(username)) {
						notExist = false; 
					}
				}
				if (notExist==true && success == true) {
					Student newStudent = new Student(username, password, firstname, lastname, studentCourse);
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
					JOptionPane.showMessageDialog(frame, "Welcome " + firstname + " " + lastname+ ". You are registered.\nPlease sign in to continue.");
					
					StudentSignIn newScreen = new StudentSignIn();
					newScreen.newScreen();
					frame.setVisible(false);
				}
				else if (!notExist){
					JOptionPane.showMessageDialog(frame, "This username exists. Please choose a different username.");
				}
				
				
			}
		});
		btnRegister.setBounds(6, 153, 117, 29);
		frame.getContentPane().add(btnRegister);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentWindow sw = new StudentWindow();
				sw.newScreen();
				frame.setVisible(false);
			}
		});
		btnBack.setBounds(316, 6, 117, 29);
		frame.getContentPane().add(btnBack);
		
		
	}

}
