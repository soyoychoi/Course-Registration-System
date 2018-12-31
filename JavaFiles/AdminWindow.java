

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;

public class AdminWindow {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminWindow window = new AdminWindow();
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
	public AdminWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("NYU Course Registration System");
		label.setFont(new Font("Helvetica", Font.PLAIN, 14));
		label.setBackground(new Color(255, 255, 255));
		label.setBounds(110, 45, 271, 38);
		frame.getContentPane().add(label);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Helvetica", Font.PLAIN, 13));
		lblUsername.setBounds(110, 95, 75, 16);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Helvetica", Font.PLAIN, 13));
		lblPassword.setBounds(111, 121, 68, 16);
		frame.getContentPane().add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(188, 89, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(188, 118, 130, 20);
		frame.getContentPane().add(passwordField);
		
		
		JButton btnLogIn = new JButton("Sign In");
		btnLogIn.setFont(new Font("Helvetica", Font.PLAIN, 13));
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textField.getText();
				String password = passwordField.getText(); 
				
				if (username.equals("Admin") && password.equals("Admin001")) {
					
					Admin admin = new Admin(username, password, "So-Young", "Choi"); 
					
					JOptionPane.showMessageDialog(frame, "Welcome, Admin. You are signed in.");
					AdminDelete secondPage = new AdminDelete(); 
					secondPage.newScreen();
					frame.setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(frame, "The username or password you have entered is incorrect. Please try again.");
				}
				
			}
		});
		btnLogIn.setBounds(201, 158, 117, 29);
		frame.getContentPane().add(btnLogIn);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Helvetica", Font.PLAIN, 13));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CourseRegistration newScreen = new CourseRegistration();
				newScreen.newScreen();
				frame.setVisible(false);
			}
		});
		btnBack.setBounds(201, 186, 117, 29);
		frame.getContentPane().add(btnBack);
		
	}
}

