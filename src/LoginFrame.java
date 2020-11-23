import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame {

	private JFrame frmCarmax;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame window = new LoginFrame();
					window.frmCarmax.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;
	private JTextField username;
	private JPasswordField password;
	
	/**
	 * Create the application.
	 */
	public LoginFrame() {
		initialize();
		connection = SQLConnection.ConnectDb();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCarmax = new JFrame();
		frmCarmax.setTitle("CarMax");
		frmCarmax.getContentPane().setFont(new Font("Montserrat", Font.BOLD, 11));
		frmCarmax.setBounds(100, 100, 900, 500);
		frmCarmax.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCarmax.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome");
		lblNewLabel.setFont(new Font("Montserrat", Font.BOLD, 30));
		lblNewLabel.setBounds(382, 76, 151, 32);
		frmCarmax.getContentPane().add(lblNewLabel);
		
		username = new JTextField();
		username.setFont(new Font("Arial", Font.PLAIN, 16));
		username.setBounds(354, 165, 213, 48);
		frmCarmax.getContentPane().add(username);
		username.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "SELECT * FROM lramos6db.Employee WHERE username=? AND password=?";
					PreparedStatement stmt = connection.prepareStatement(query);
					stmt.setString(1, username.getText());
					stmt.setString(2, password.getText());
					
					ResultSet result = stmt.executeQuery();
					int count = 0;
					while(result.next()) {
						count += 1; 
					}
					
					if(count == 1) {
						System.out.println("Login successful.");
//						frmCarmax.dispose();
//						EmployeeInterfaceFrame employeeInterface = new EmployeeInterfaceFrame();
//						employeeInterface.setVisible(true);
					} else if (count > 1) {
						System.out.println("Login unsucessful: Duplicate credentials.");
						JOptionPane.showMessageDialog(null, "Login Error");
					} else {
						System.out.println("Login unsucessful: Incorrect username and password.");
						JOptionPane.showMessageDialog(null, "Incorrect username and password");
					}
					
					//Close database connection
					result.close();
					stmt.close();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(null, exception);
				}
			}
		});
		btnLogin.setFont(new Font("Montserrat", Font.BOLD, 16));
		btnLogin.setBounds(409, 330, 101, 32);
		frmCarmax.getContentPane().add(btnLogin);
		
		password = new JPasswordField();
		password.setFont(new Font("Arial", Font.PLAIN, 16));
		password.setBounds(352, 250, 215, 48);
		frmCarmax.getContentPane().add(password);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(353, 140, 77, 14);
		frmCarmax.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(354, 225, 70, 14);
		frmCarmax.getContentPane().add(lblNewLabel_2);
	}
}
