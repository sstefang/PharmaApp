package farmacie;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import javax.swing.UIManager;

public class DrugApp {

	private JFrame frmEmployeesApp;
	private JTable table;
	DefaultTableModel model;
	private JTextField Name;
	private JTextField Price;
	private JTextField Description;
	private JSpinner Quantity;
	public double fprice = 0;
	
	
	class Drug{
		
		String name;
		String description;
		int quantity;
		double price;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrugApp window = new DrugApp();
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
	public DrugApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEmployeesApp = new JFrame();
		frmEmployeesApp.getContentPane().setBackground(Color.PINK);
		frmEmployeesApp.setTitle("Drug Interface");
		frmEmployeesApp.setBounds(100, 100, 663, 444);
		frmEmployeesApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEmployeesApp.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Drugs", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(12, 13, 534, 167);
		frmEmployeesApp.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 510, 141);
		panel.add(scrollPane);
		
		table = new JTable();
		
		model = new DefaultTableModel();
		
		
		scrollPane.setViewportView(table);
		
		JButton btnListEmployees = new JButton("List Drugs");
		btnListEmployees.setBackground(Color.GREEN);
		btnListEmployees.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model = new DefaultTableModel();
				model.addColumn("Name");
				model.addColumn("Description");
				model.addColumn("Quantity");
				model.addColumn("Price");
				
				
				getDrugsFromDB();
				
				
				
				
				
				
				
				
				
				
				
			}
		});
		
		
		
		
		btnListEmployees.setBounds(465, 289, 143, 25);
		frmEmployeesApp.getContentPane().add(btnListEmployees);
		
		JButton btnNewButton = new JButton("Add Drugs");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name = Name.getText();
				String description = Description.getText();
				double price = Double.valueOf( Price.getText());
				int quantity = (int) Quantity.getValue();
				
				
				//insertProductIntoDB(name, description, quantity, price);
				
				Drug drug = getDrug(name);
				if(drug == null) {
					System.out.println("a");
					insertProductIntoDB(name, description, quantity, price);
					//getDrugsFromDB();
				} else {
					System.out.println("b");
				
					updateProductIntoDB(name, description, quantity, price);
					//getDrugsFromDB();
				}
				}
		});
		btnNewButton.setBounds(465, 321, 143, 25);
		frmEmployeesApp.getContentPane().add(btnNewButton);
		
		
		JButton btnNewButton_1 = new JButton("Order Drugs");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String name = Name.getText();
				String description = Description.getText();
				double price = Double.valueOf( Price.getText());
				int quantity = (int) Quantity.getValue();
				
				
				//insertProductIntoDB
				
				Drug drug = getDrug(name);
				if(drug == null) {
					JOptionPane.showMessageDialog(null, "We don't have it!");
					
				} else {
					
					orderProductDB(name, description, quantity, price);
					
				
					
					JOptionPane.showMessageDialog(null, "You ordered "+ quantity + " "+  name + " THE TOTAL PRICE IS "+ fprice);
					//getDrugsFromDB();
					
					System.out.println(fprice);
				}
				
				
				
				
			}
		});
		btnNewButton_1.setBounds(465, 359, 143, 38);
		frmEmployeesApp.getContentPane().add(btnNewButton_1);
		
		
		
		
		Name = new JTextField();
		Name.setBounds(61, 193, 116, 22);
		frmEmployeesApp.getContentPane().add(Name);
		Name.setColumns(10);
		
		Description = new JTextField();
		Description.setBounds(61, 233, 116, 22);
		frmEmployeesApp.getContentPane().add(Description);
		Description.setColumns(10);
		
		Price= new JTextField();
		Price.setBounds(61, 303, 116, 22);
		frmEmployeesApp.getContentPane().add(Price);
		Price.setColumns(10);
		
		Quantity = new JSpinner();
		Quantity.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		Quantity.setBounds(111, 268, 55, 22);
		frmEmployeesApp.getContentPane().add(Quantity);
		
		
	}
	
	protected Drug getDrug(String name) {
		Drug drug=null;
		try {
		
			Class.forName("com.mysql.jdbc.Driver");
		
		
		Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemployee","root","Antonica01*");
		
		
		Statement stmt = con.createStatement();
		
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM dbemployee.drug");
		
		while(rs.next()) {
			
			String name2 = rs.getString("name");
			
			if( name2.equals(name)) {
			drug = new Drug();
			drug.setName(name);
			}
	
		}
		
		
		con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
		
		return drug;
	}

	protected void insertProductIntoDB(String name2, String description, int quantity, double price) {
		// TODO Auto-generated method stub
		
		try {
		
			
			Class.forName("com.mysql.jdbc.Driver");
			
			
			Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemployee","root","Antonica01*");
			
		
			
			PreparedStatement stmt = 
					con.prepareStatement("INSERT INTO `dbemployee`.`drug` (`name`, `description`, `quantity`, `price`) VALUES (?,?,?,?);");
			
				stmt.setString(1, name2);
				stmt.setString(2,description);
				stmt.setInt(3, quantity);
				stmt.setDouble(4, price);
				
				stmt.executeUpdate();
			con.close();
		}catch(Exception ex) {
			System.err.println(ex);
		}
		
	}
// metoda if isAdmin
	void getDrugsFromDB(){
		
		try {
		
			Class.forName("com.mysql.jdbc.Driver");
		
		
		Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemployee","root","Antonica01*");
		
		
		Statement stmt = con.createStatement();
		
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM dbemployee.drug");
		
		while(rs.next()) {
			String name = rs.getString("name");
			String description = rs.getString("description");
			String quantity = rs.getString("quantity");
			String price =rs.getString("price");
			String[] row = new String[4];
			row[0] = name;
			row[1] = description;
			row[2] = quantity;
			row[3] = price;
			model.addRow(row);
			table.setModel(model);
			
	
		}
		
		
		con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
		
	}
	
	
	
	
	protected void updateProductIntoDB(String name, String description, int quantity, double price) {
		// TODO Auto-generated method stub
		try {
		
			
			Class.forName("com.mysql.jdbc.Driver");
			
			
			Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemployee","root","Antonica01*");
			
			String query = "update drug set quantity = quantity + ? where name = ?";
			
			PreparedStatement stmt = 
					
					con.prepareStatement(query);
			
			  	stmt.setInt(1, quantity);
				
				stmt.setString(2, name);
				
				
				stmt.executeUpdate();
			con.close();
		}catch(Exception ex) {
			System.err.println(ex);
		}
		
	}
	
	protected void orderProductDB(String name, String description, int quantity, double price) {
		// TODO Auto-generated method stub
		
		
		try {
		
			
			Class.forName("com.mysql.jdbc.Driver");
			
			
			Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemployee","root","Antonica01*");
			
			String query = "update drug set quantity = quantity - ? where name = ?";
			
			PreparedStatement stmt = 
					
					con.prepareStatement(query);
			
			  	stmt.setInt(1, quantity);
				
				stmt.setString(2, name);
				
			
				stmt.executeUpdate();
				
				fprice += quantity*price;
			con.close();
		}catch(Exception ex) {
			System.err.println(ex);
		}
		
	}
	
	protected void checksDeducted(String name2, String description, int quantity, double price) {
		// TODO Auto-generated method stub
	
		
		
		
	}
}

