package farmacie;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JComboBox;

public class UserApp {

	private JFrame frmEmployeesApp;
	private JTable table;
	DefaultTableModel model;
	private JTextField Name;
	private JTextField Surname;
	private JTextField Phone;
	private JTextField Pass;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserApp window = new UserApp();
					window.frmEmployeesApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEmployeesApp = new JFrame();
		frmEmployeesApp.getContentPane().setBackground(Color.PINK);
		frmEmployeesApp.setTitle("Users App");
		frmEmployeesApp.setBounds(100, 100, 663, 444);
		frmEmployeesApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEmployeesApp.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new TitledBorder(null, "Users", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 13, 534, 167);
		frmEmployeesApp.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 510, 141);
		panel.add(scrollPane);
		
		table = new JTable();
		
		model = new DefaultTableModel();
		model.addColumn("Name");
		model.addColumn("Address");
		model.addColumn("Phone");
		
		scrollPane.setViewportView(table);
		
		JButton btnListEmployees = new JButton("List Users");
		btnListEmployees.setBackground(Color.GREEN);
		btnListEmployees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model = new DefaultTableModel();
				model.addColumn("Name");
				//model.addColumn("Pass");
				model.addColumn("Address");
				model.addColumn("Phone");
				
				
				getEmployeesFromDB();
				
				
				
				
				
				
				
				
				
				
				
			}
		});
		btnListEmployees.setBounds(465, 289, 143, 25);
		frmEmployeesApp.getContentPane().add(btnListEmployees);
		
		JButton btnNewButton = new JButton("Insert Users");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name = Name.getText();
				String password= Pass.getText();
				String surname = Surname.getText();
				String phone = Phone.getText();
				//int age = (int) Age.getValue();
				
				
				insertEmployeeIntoDB(name, password, surname, phone);			}
		});
		btnNewButton.setBounds(479, 359, 143, 25);
		frmEmployeesApp.getContentPane().add(btnNewButton);
		
		Name = new JTextField();
		Name.setBounds(175, 216, 116, 22);
		frmEmployeesApp.getContentPane().add(Name);
		Name.setColumns(10);
		
		Pass = new JTextField();
		Pass.setBounds(175, 255, 116, 22);
		frmEmployeesApp.getContentPane().add(Pass);
		Pass.setColumns(10);
		
		Surname = new JTextField();
		Surname.setBounds(175, 303, 116, 22);
		frmEmployeesApp.getContentPane().add(Surname);
		Surname.setColumns(10);
		
		Phone = new JTextField();
		Phone.setBounds(175, 338, 116, 22);
		frmEmployeesApp.getContentPane().add(Phone);
		Phone.setColumns(10);
		
		JTextPane txtpnName = new JTextPane();
		txtpnName.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtpnName.setBackground(Color.PINK);
		txtpnName.setText("Name");
		txtpnName.setBounds(72, 216, 72, 22);
		frmEmployeesApp.getContentPane().add(txtpnName);
		
		JTextPane txtpnPassword = new JTextPane();
		txtpnPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtpnPassword.setBackground(Color.PINK);
		txtpnPassword.setText("Password");
		txtpnPassword.setBounds(73, 255, 90, 22);
		frmEmployeesApp.getContentPane().add(txtpnPassword);
		
		JTextPane txtpnAddress = new JTextPane();
		txtpnAddress.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtpnAddress.setBackground(Color.PINK);
		txtpnAddress.setText("Address");
		txtpnAddress.setBounds(73, 303, 90, 22);
		frmEmployeesApp.getContentPane().add(txtpnAddress);
		
		JTextPane txtpnPhone = new JTextPane();
		txtpnPhone.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtpnPhone.setBackground(Color.PINK);
		txtpnPhone.setText("Phone");
		txtpnPhone.setBounds(72, 338, 72, 22);
		frmEmployeesApp.getContentPane().add(txtpnPhone);
	}
	
	protected void insertEmployeeIntoDB(String name2, String password, String address, String phone2) {
		// TODO Auto-generated method stub
		try {
		
			
			Class.forName("com.mysql.jdbc.Driver");
			
			
			Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemployee","root","");
			
			
			PreparedStatement stmt = 
					con.prepareStatement("INSERT INTO `dbemployee`.`users` (`username`, `password`, `address`, `phone`) VALUES (?,?,?,?);");
			
				stmt.setString(1, name2);
				stmt.setString(2, password);
				stmt.setString(3, address);
				stmt.setString(4, phone2);
				
				stmt.executeUpdate();
			con.close();
		}catch(Exception ex) {
			System.err.println(ex);
		}
		
	}
// metoda if isAdmin
	void getEmployeesFromDB(){
		
		try {
		
			Class.forName("com.mysql.jdbc.Driver");
		
		
		Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemployee","root","");
		
		
		Statement stmt = con.createStatement();
		
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM dbemployee.users");
		
		while(rs.next()) {
			String name = rs.getString("username");
			
			String address = rs.getString("address");
			
			//int age = rs.getInt("age");
			
			String tel = rs.getString("phone");
			String[] row = new String[3];
			row[0] = name;
			row[1] = address;
			row[2] = tel;
			model.addRow(row);
			table.setModel(model);
			
	
		}
		
		
		con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
		
	}
	

}

