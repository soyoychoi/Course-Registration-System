
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminMenu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenu window = new AdminMenu();
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
	public AdminMenu() {
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
		
		JButton btnNewButton = new JButton("Course Management");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminDelete newScreen = new AdminDelete(); 
				newScreen.newScreen();
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(6, 39, 172, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel label = new JLabel("NYU Course Registration System");
		label.setBackground(Color.WHITE);
		label.setBounds(6, 0, 271, 48);
		frame.getContentPane().add(label);
		
		JButton button = new JButton("Reports");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reports newScreen = new Reports(); 
				newScreen.newScreen();
				frame.setVisible(false);
			}
		});
		button.setBounds(6, 68, 117, 29);
		frame.getContentPane().add(button);
	}
}
