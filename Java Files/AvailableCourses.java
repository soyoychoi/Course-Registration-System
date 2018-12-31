

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AvailableCourses {
	
	private Student student;
	private JFrame frame;
	private JTable table;
	private JTextField textField;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void newScreen(Student s) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AvailableCourses window = new AvailableCourses(s);
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
	public AvailableCourses(Student s) {
		this.student = s;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Student s = this.student;
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
		ArrayList<Course> available = new ArrayList<Course>();
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
		
		for (Course x: courseList) {
			if (x.getCurrentNum() < x.getMaxNum()) {
				available.add(x);
			}
		}
		for (int i = 0; i < available.size(); i++) {
			data[i][0] = available.get(i).getCourseName();
			data[i][1] = available.get(i).getCourseId();
			data[i][2] = Integer.toString(available.get(i).getMaxNum());
			data[i][3] = Integer.toString(available.get(i).getStudentNames().size());
			data[i][4] = "";
			if (available.get(i).getStudentNames().size()!=0) {
				for (int j = 0; j < available.get(i).getStudentNames().size(); j++) {
					String firstName = available.get(i).getStudentNames().get(j).getFirstname(); 
					String lastName = available.get(i).getStudentNames().get(j).getLastname(); 
					String studentName = firstName + " " + lastName;
					data[i][4] += studentName + ", "; 
				}
			}
			data[i][5] = available.get(i).getInstructor();
			data[i][6] = available.get(i).getSection();
			data[i][7] = available.get(i).getLocation();
			
		}
		
		table = new JTable(data, columns);
		scrollPane = new JScrollPane(table);
		
		scrollPane.setBounds(6, 78, 326, 194);
		frame.getContentPane().add(scrollPane);
		
		
		JLabel lblSearch = new JLabel("Search: ");
		lblSearch.setBounds(16, 55, 61, 16);
		frame.getContentPane().add(lblSearch);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>((TableModel)table.getModel());
				sorter.setRowFilter(RowFilter.regexFilter("(?i)"+textField.getText()));
				table.setRowSorter(sorter);
			}
		});
		textField.setBounds(65, 50, 253, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentActions newScreen = new StudentActions(s);
				newScreen.newScreen(s);
				frame.setVisible(false);
			}
		});
		btnBack.setBounds(332, 246, 112, 26);
		frame.getContentPane().add(btnBack);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

}
