

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentWindow window = new StudentWindow();
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
	public StudentWindow() {
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
		label.setBounds(6, 6, 271, 48);
		frame.getContentPane().add(label);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentRegister newScreen = new StudentRegister();
				newScreen.newScreen();
				frame.setVisible(false);
			}
		});
		btnRegister.setBounds(6, 66, 117, 29);
		frame.getContentPane().add(btnRegister);
		
		JLabel lblFirstTime = new JLabel("First Time? ");
		lblFirstTime.setBounds(16, 50, 117, 16);
		frame.getContentPane().add(lblFirstTime);
		
		JLabel lblReturner = new JLabel("Returner?");
		lblReturner.setBounds(201, 50, 61, 16);
		frame.getContentPane().add(lblReturner);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentSignIn newScreen = new StudentSignIn();
				newScreen.newScreen();
				frame.setVisible(false);
			}
		});
		btnSignIn.setBounds(190, 66, 117, 29);
		frame.getContentPane().add(btnSignIn);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CourseRegistration cr = new CourseRegistration();
				cr.newScreen();
				frame.setVisible(false);
			}
		});
		btnBack.setBounds(327, 6, 117, 29);
		frame.getContentPane().add(btnBack);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
