
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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class StudentSignIn {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField textField_1;

	/**
	 * Launch the application.
	 */
	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentSignIn window = new StudentSignIn();
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
	public StudentSignIn() {
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
		lblUsername.setBounds(16, 50, 71, 16);
		frame.getContentPane().add(lblUsername);
		
		textField = new JTextField();
		textField.setBounds(89, 45, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(16, 77, 71, 16);
		frame.getContentPane().add(lblPassword);
		
		textField_1 = new JPasswordField();
		textField_1.setBounds(89, 72, 130, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnSignIn = new JButton("Sign in");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textField.getText();
				String password = textField_1.getText();
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
					catch (FileNotFoundException fnfe) {
						System.out.println("There are no students registered.");
					}
					catch (IOException ioe) {
						ioe.printStackTrace();
					}
					catch(ClassNotFoundException cnfe) {
						cnfe.printStackTrace();
					}
					boolean notFound = true; 
					for (Student x: studentList) {
						if (x.getUsername().equals(username)) {
							notFound = false; 
							if (!x.getPassword().equals(password)) {
								
								JOptionPane.showMessageDialog(frame, "Password is incorrect. Please try again.");
							}
							else {
								JOptionPane.showMessageDialog(frame, "Welcome " + x.getFirstname()+" " +x.getLastname()+". You are signed in.");
								
								StudentActions newScreen = new StudentActions(x); 
								newScreen.newScreen(x);
								frame.setVisible(false);
							}
						}
					}
					if (notFound==true) {
						JOptionPane.showMessageDialog(frame, "Username does not exist. Please enter a different username.");
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "Please register first before signing in.");
					CourseRegistration cr = new CourseRegistration();
					cr.newScreen();
					frame.setVisible(false);
				}
			}
		});
		btnSignIn.setBounds(6, 105, 117, 29);
		frame.getContentPane().add(btnSignIn);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentWindow sw = new StudentWindow();
				sw.newScreen();
				frame.setVisible(false);
			}
		});
		btnBack.setBounds(327, 6, 117, 29);
		frame.getContentPane().add(btnBack);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
