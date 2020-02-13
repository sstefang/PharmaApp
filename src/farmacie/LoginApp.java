package farmacie;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;

public class LoginApp {

	private JFrame frmLoginApp;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginApp window = new LoginApp();
					window.frmLoginApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLoginApp = new JFrame();
		frmLoginApp.setTitle("Login App");
		frmLoginApp.setBounds(100, 100, 525, 375);
		frmLoginApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLoginApp.getContentPane().setLayout(null);
		
		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(27, 50, 56, 16);
		frmLoginApp.getContentPane().add(lblUser);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(27, 79, 69, 16);
		frmLoginApp.getContentPane().add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(112, 47, 116, 22);
		frmLoginApp.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(112, 76, 131, 22);
		frmLoginApp.getContentPane().add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String password = passwordField.getText();
				String username = textField.getText();
				
				boolean exists = checkCredentials(username, password);
				
				if(exists) {
					
					JOptionPane.showMessageDialog(null, "Login ok!");
					//open another window
					DrugApp.main(null);
					UserApp.main(null);
					
				} else {
					
					JOptionPane.showMessageDialog(null, "Login not ok!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			
				
			}
			
		});
		btnLogin.setBounds(101, 129, 97, 25);
		frmLoginApp.getContentPane().add(btnLogin);
	}
		
		boolean checkCredentials(String user, String pass) { 
			try {	
				Class.forName("com.mysql.jdbc.Driver");
				
				
				Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemployee","root","");
				
				
				Statement stmt = con.createStatement();
				
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM dbemployee.users");
				
				while(rs.next()) {
					String username = rs.getString("username");
					String password = rs.getString("password");
					
					
				if (username.equalsIgnoreCase(user) && password.equals(pass)) {
					return true;
				}
					
					
				}
				
				
				con.close();
			} catch (Exception ex ) {
				System.err.println(ex);
			}
				return false;
			}
}
