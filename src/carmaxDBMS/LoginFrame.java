package carmaxDBMS;

import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

/**
 * The Class LoginFrame displays the login form for the user to authenticate
 * with company credentials and gain access to employee's interface.
 */
public class LoginFrame {
	private JFrame frmCarmax;
	private Connection connection = null;
	public static String loggedInUser;
	private JTextField username;
	private JPasswordField password;

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

	/**
	 * Create the application.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public LoginFrame() throws IOException {
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
		frmCarmax.setBounds(100, 100, 900, 640);
		frmCarmax.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCarmax.getContentPane().setLayout(null);

		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Montserrat", Font.BOLD, 30));
		lblWelcome.setBounds(354, 150, 213, 32);
		frmCarmax.getContentPane().add(lblWelcome);

		username = new JTextField();
		username.setFont(new Font("Arial", Font.PLAIN, 16));
		username.setBounds(354, 239, 213, 48);
		frmCarmax.getContentPane().add(username);
		username.setColumns(10);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogIn();
			}
		});

		btnLogin.setFont(new Font("Montserrat", Font.BOLD, 16));
		btnLogin.setBounds(409, 404, 101, 32);
		frmCarmax.getContentPane().add(btnLogin);

		password = new JPasswordField();
		password.setFont(new Font("Arial", Font.PLAIN, 16));
		password.setBounds(352, 324, 215, 48);
		frmCarmax.getContentPane().add(password);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUsername.setBounds(353, 214, 116, 14);
		frmCarmax.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPassword.setBounds(354, 299, 115, 14);
		frmCarmax.getContentPane().add(lblPassword);
	}

	/***
	 * This method grabs the data entered by the user on the username and password
	 * fields, and makes the SQL to authenticate the user into the system. If the
	 * user fails to enter correct credentials, an error message is displayed.
	 */

	private void LogIn() {
		try {
			String query = "SELECT * FROM Employee WHERE username=? AND password=?;";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, username.getText());
			stmt.setString(2, password.getText());

			ResultSet result = stmt.executeQuery();
			Boolean valid = false;

			if (result.next()) {
				valid = true;
				loggedInUser = result.getString("fName"); // Grab user's name for greeting
			}

			if (valid) {
				System.out.println("Login successful.");
				frmCarmax.dispose();
				EmployeeInterfaceFrame employeeInterface = new EmployeeInterfaceFrame();
				employeeInterface.setVisible(true);

				// Close database connection
				result.close();
				stmt.close();
				connection.close();
			} else {
				System.out.println("Login unsucessful: Incorrect username and password.");
				JOptionPane.showMessageDialog(null, "Incorrect username and password");
			}
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, exception);
			exception.printStackTrace();
		}
	}

	/**
	 * * This method returns the name of the user signed in which is then displayed
	 * on the Employee's interface.
	 *
	 * @return string
	 */

	public static String GetLoggedOnUserName() {
		return loggedInUser;
	}
}
