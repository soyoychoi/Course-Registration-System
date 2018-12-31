

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdminViewFull {

	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminViewFull window = new AdminViewFull();
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
	public AdminViewFull() {
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
		
		JLabel label_1 = new JLabel("Search:");
		label_1.setBounds(16, 46, 61, 16);
		frame.getContentPane().add(label_1);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>((TableModel)table.getModel());
				sorter.setRowFilter(RowFilter.regexFilter("(?i)"+textField.getText()));
				table.setRowSorter(sorter);
			}
		});
		textField.setColumns(10);
		textField.setBounds(68, 41, 265, 26);
		frame.getContentPane().add(textField);
		
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
		ArrayList<Course> fullList = new ArrayList<Course>();
		
		for (Course x: courseList) {
			if (x.getMaxNum()==x.getCurrentNum()) {
				fullList.add(x);
			}
		}
		
		for (int i = 0; i < fullList.size(); i++) {
			data[i][0] = fullList.get(i).getCourseName();
			data[i][1] = fullList.get(i).getCourseId();
			data[i][2] = Integer.toString(fullList.get(i).getMaxNum());
			data[i][3] = Integer.toString(fullList.get(i).getStudentNames().size());
			data[i][4] = "";
			if (fullList.get(i).getStudentNames().size()!=0) {
				for (int j = 0; j < fullList.get(i).getStudentNames().size(); j++) {
					String firstName = fullList.get(i).getStudentNames().get(j).getFirstname(); 
					String lastName = fullList.get(i).getStudentNames().get(j).getLastname(); 
					String studentName = firstName + " " + lastName;
					data[i][4] += studentName + ", "; 
				}
			}
			data[i][5] = fullList.get(i).getInstructor();
			data[i][6] = fullList.get(i).getSection();
			data[i][7] = fullList.get(i).getLocation();
			
		}
		
		table = new JTable(data, columns);
		scrollPane = new JScrollPane(table);
		table.setAutoCreateRowSorter(true);
		
		
		
		scrollPane.setBounds(6, 67, 327, 177);
		frame.getContentPane().add(scrollPane);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminDelete newScreen = new AdminDelete();
				newScreen.newScreen();
				frame.setVisible(false);
			}
		});
		btnBack.setBounds(338, 226, 82, 18);
		frame.getContentPane().add(btnBack); 
	}

}
