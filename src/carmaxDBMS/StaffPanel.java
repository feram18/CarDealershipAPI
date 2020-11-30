package carmaxDBMS;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.proteanit.sql.DbUtils;

public class StaffPanel extends JPanel {
	private Connection connection = null;
	private JTextField textFieldSSN;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JComboBox<String> comboBoxEmpType;
	private JComboBox<String> comboBoxWorkLoc;
	private JComboBox<String> comboBoxSalary;
	private JTextField textFieldYearsWorked;
	private JComboBox<String> comboBoxManager;
	private JTable staffTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldCity;
	private JTextField textFieldState;
	private JTextField textFieldZIP;
	
	private JTextField inputSSN = new JTextField();
	private JTextField inputFirstName = new JTextField();
	private JTextField inputLastName = new JTextField();
	private JTextField inputMiddleInitial = new JTextField();
	private JTextField inputSex = new JTextField();
	private JTextField inputDoB = new JTextField();
	private JTextField inputPhoneNumber= new JTextField();
	private JTextField inputEmployeeType = new JTextField();
	private JTextField inputWorkLocation = new JTextField();
	private JTextField inputSalary = new JTextField();
	private JTextField inputYearsWorked = new JTextField();
	private JTextField inputAddress = new JTextField();
	private JTextField inputHoursWorked = new JTextField();
	private JTextField inputUsername = new JTextField();
	private JTextField inputPassword = new JTextField();
	private JTextField inputManagerSSN = new JTextField();
	
	Object[] inputFields = {
			"SSN", inputSSN,
			"First Name", inputFirstName,
			"Last Name", inputLastName,
			"M.I.", inputMiddleInitial,
			"Sex", inputSex,
			"Date of Birth", inputDoB,
			"Phone Number", inputPhoneNumber,
			"Employee Type", inputEmployeeType,
			"Work Location", inputWorkLocation,
			"Salary", inputSalary,
			"Years Worked", inputYearsWorked,
			"Address", inputAddress,
			"Hours Worked", inputHoursWorked,
			"Username", inputUsername,
			"Password", inputPassword,
			"Manager SSN", inputManagerSSN
	};
	
	String[] addOptions = {"Add", "Cancel"};
	String[] updateOptions = {"Save Changes", "Cancel"};
	
	/**
	 * Create the panel.
	 */
	public StaffPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JScrollPane scrollPaneStaff = new JScrollPane();
		scrollPaneStaff.setBounds(220, 45, 630, 380);
		add(scrollPaneStaff);
		
		staffTable = new JTable();
		staffTable.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPaneStaff.setViewportView(staffTable);
		
		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");
		 
		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);
		
		staffTable.setComponentPopupMenu(popupMenu);
		
		menuItemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane updatePane = new JOptionPane();
				updatePane.setVisible(false);
				
				try {
					populateToUpdate();
					updatePane.setVisible(true);
					
					int choice = updatePane.showOptionDialog(null, inputFields, "Update Employee", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);
					
					if(choice == 0) {
						System.out.println("Updating Employee... ");
						updateDatabase();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		menuItemDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					deleteFromDatabase();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		JLabel labelSearchBy = new JLabel("Search by");
		labelSearchBy.setFont(new Font("Montserrat", Font.PLAIN, 14));
		labelSearchBy.setBounds(10, 11, 69, 18);
		add(labelSearchBy);
		
		JLabel labelSSN = new JLabel("SSN");
		labelSSN.setHorizontalAlignment(SwingConstants.TRAILING);
		labelSSN.setFont(new Font("Arial", Font.PLAIN, 12));
		labelSSN.setBounds(81, 48, 25, 15);
		add(labelSSN);
		
		textFieldSSN = new JTextField();
		labelSSN.setLabelFor(textFieldSSN);
		textFieldSSN.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldSSN.setBounds(116, 45, 86, 20);
		add(textFieldSSN);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFirstName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblFirstName.setBounds(45, 79, 61, 15);
		add(lblFirstName);
		
		textFieldFirstName = new JTextField();
		lblFirstName.setLabelFor(textFieldFirstName);
		textFieldFirstName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldFirstName.setBounds(116, 76, 86, 20);
		add(textFieldFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLastName.setBounds(45, 111, 61, 15);
		add(lblLastName);
		
		textFieldLastName = new JTextField();
		lblLastName.setLabelFor(textFieldLastName);
		textFieldLastName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldLastName.setBounds(116, 108, 86, 20);
		add(textFieldLastName);
		
		JLabel lblEmployeeType = new JLabel("Employee Type");
		lblEmployeeType.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmployeeType.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEmployeeType.setBounds(20, 142, 86, 15);
		add(lblEmployeeType);
		
		comboBoxEmpType = new JComboBox();
		lblEmployeeType.setLabelFor(comboBoxEmpType);
		comboBoxEmpType.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxEmpType.setBounds(116, 139, 86, 20);
		add(comboBoxEmpType);
		
		JLabel lblWorkLocation = new JLabel("Work Location");
		lblWorkLocation.setHorizontalAlignment(SwingConstants.TRAILING);
		lblWorkLocation.setFont(new Font("Arial", Font.PLAIN, 12));
		lblWorkLocation.setBounds(20, 173, 86, 15);
		add(lblWorkLocation);
		
		comboBoxWorkLoc = new JComboBox();
		lblWorkLocation.setLabelFor(comboBoxWorkLoc);
		comboBoxWorkLoc.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxWorkLoc.setBounds(116, 170, 86, 20);
		add(comboBoxWorkLoc);
		
		JLabel lblSalary = new JLabel("Salary");
		lblSalary.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSalary.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSalary.setBounds(72, 204, 34, 15);
		add(lblSalary);
		
		comboBoxSalary = new JComboBox();
		lblSalary.setLabelFor(comboBoxSalary);
		comboBoxSalary.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxSalary.setBounds(116, 201, 86, 20);
		add(comboBoxSalary);
		
		JLabel lblYearsWorked = new JLabel("Years Worked");
		lblYearsWorked.setHorizontalAlignment(SwingConstants.TRAILING);
		lblYearsWorked.setFont(new Font("Arial", Font.PLAIN, 12));
		lblYearsWorked.setBounds(27, 235, 79, 15);
		add(lblYearsWorked);
		
		textFieldYearsWorked = new JTextField();
		lblYearsWorked.setLabelFor(textFieldYearsWorked);
		textFieldYearsWorked.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldYearsWorked.setBounds(116, 232, 86, 20);
		add(textFieldYearsWorked);
		
		JLabel lblManager = new JLabel("Manager");
		lblManager.setHorizontalAlignment(SwingConstants.TRAILING);
		lblManager.setFont(new Font("Arial", Font.PLAIN, 12));
		lblManager.setBounds(27, 359, 79, 15);
		add(lblManager);
		
		comboBoxManager = new JComboBox();
		comboBoxManager.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxManager.setBounds(116, 356, 86, 20);
		add(comboBoxManager);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCity.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCity.setBounds(27, 266, 79, 15);
		add(lblCity);
		
		textFieldCity = new JTextField();
		lblCity.setLabelFor(textFieldCity);
		textFieldCity.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldCity.setBounds(116, 263, 86, 20);
		add(textFieldCity);
		
		JLabel lblState = new JLabel("State");
		lblState.setHorizontalAlignment(SwingConstants.TRAILING);
		lblState.setFont(new Font("Arial", Font.PLAIN, 12));
		lblState.setBounds(27, 297, 79, 15);
		add(lblState);
		
		textFieldState = new JTextField();
		lblState.setLabelFor(textFieldState);
		textFieldState.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldState.setBounds(116, 294, 86, 20);
		add(textFieldState);
		
		JLabel lblZipCode = new JLabel("ZIP Code");
		lblZipCode.setHorizontalAlignment(SwingConstants.TRAILING);
		lblZipCode.setFont(new Font("Arial", Font.PLAIN, 12));
		lblZipCode.setBounds(27, 328, 79, 15);
		add(lblZipCode);
		
		textFieldZIP = new JTextField();
		textFieldZIP.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldZIP.setBounds(116, 325, 86, 20);
		add(textFieldZIP);
		
		JButton btnSearchStaff = new JButton("Search");
		btnSearchStaff.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSearchStaff.setBounds(72, 399, 77, 23);
		btnSearchStaff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					searchStaff();
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		});
		
		add(btnSearchStaff);
		
		JButton btnAddEmployee = new JButton("Add New Employee");
		btnAddEmployee.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAddEmployee.setBounds(449, 450, 146, 23);
		btnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(JOptionPane.showOptionDialog(null, inputFields, "Add Employee", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, addOptions, null) == 0) {
						System.out.print("Adding new Employee to database...");
						addToDatabase();
					}
					
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
		
		add(btnAddEmployee);
		
		populateComboBoxes();
	}
	
	/***
	 * This method populates the comboboxes on staffTable with data
	 * retrieved from Employee table, which serve as a filter for the user
	 * and as a means of input validation.
	 */
	
	private void populateComboBoxes() {
		try {
			connection = SQLConnection.ConnectDb();
			
			String query = "SELECT DISTINCT employeeType FROM lramos6db.Employee WHERE employeeType IS NOT NULL";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			comboBoxEmpType.addItem(null);
			while(result.next() == true) {
				comboBoxEmpType.addItem(result.getString("employeeType"));
			}
			
			query = "SELECT DISTINCT workLocation FROM lramos6db.Employee WHERE workLocation IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxWorkLoc.addItem(null);
			while(result.next() == true) {
				comboBoxWorkLoc.addItem(result.getString("workLocation"));
			}
			
			comboBoxSalary.addItem(null);
			comboBoxSalary.addItem("< 10000");
			comboBoxSalary.addItem("< 20000");
			comboBoxSalary.addItem("< 30000");
			comboBoxSalary.addItem("< 40000");
			comboBoxSalary.addItem("< 50000");
			comboBoxSalary.addItem("< 60000");
			comboBoxSalary.addItem("< 70000");
			comboBoxSalary.addItem("< 80000");
			comboBoxSalary.addItem("< 90000");
			comboBoxSalary.addItem("< 100000");
			comboBoxSalary.addItem("< 110000");
			comboBoxSalary.addItem("< 120000");
			comboBoxSalary.addItem("> 120000");
			
			query = "SELECT DISTINCT managerSSN FROM lramos6db.Employee WHERE managerSSN IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxManager.addItem(null);
			while(result.next() == true) {
				comboBoxManager.addItem(result.getString("managerSSN"));
			}

		} catch (Exception e) {
			System.out.println("Error querying data for comboboxes");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method checks for the fields user entered data on and
	 * makes the SQL query with the parameters provided by the user.
	 * Results from Employee table are populated in the staffTable.
	 * @throws SQLException
	 */
	
	private void searchStaff() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int parameterCount = 0;
			String query = "SELECT * FROM lramos6db.Employee WHERE "; //Base query
			
			if(!textFieldSSN.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "SSN='" + textFieldSSN.getText() + "'";
			}
			
			if(!textFieldFirstName.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "fName='" + textFieldFirstName.getText() + "'";
			}
			
			if(!textFieldLastName.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "lName='" + textFieldLastName.getText() + "'";
			}
			
			if(comboBoxEmpType.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "employeeType='" + comboBoxEmpType.getSelectedItem().toString() +"'";
			}
			
			if(comboBoxWorkLoc.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "workLocation='" + comboBoxWorkLoc.getSelectedItem().toString() +"'";
			}
			
			if(comboBoxSalary.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "salary " + comboBoxSalary.getSelectedItem().toString();
			}
			
			if(!textFieldYearsWorked.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "yearsWorked='" + textFieldYearsWorked.getText() + "'";
			}
			
			if(!textFieldCity.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "address LIKE '%" + textFieldCity.getText() + "%'";
			}
			
			if(!textFieldState.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "address LIKE '%" + textFieldState.getText() + "%'";
			}
			
			if(!textFieldZIP.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "address LIKE '%" + textFieldZIP.getText() + "%'";
			}
			
			if(comboBoxManager.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "managerSSN='" + comboBoxManager.getSelectedItem().toString() +"'";
			}
			
			query += ";";
			
			if(parameterCount > 0) {
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				staffTable.setModel(DbUtils.resultSetToTableModel(result));
				System.out.println(query);
			} else {
				JOptionPane.showMessageDialog(null, "No criteria selected.");
			}
			
			parameterCount = 0;
		} catch (Exception e) {
			System.out.println("Error: Invalid query.");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method makes the SQL query to add the a new row
	 * to the database's Employee table.
	 * @throws SQLException
	 */
	
	private void addToDatabase() throws SQLException  {
		try {
			connection = SQLConnection.ConnectDb();
			String query = "INSERT INTO lramos6db.Employee (SSN, fName, lName, mInit, sex, DOB, phoneNo, employeeType,"
							+ " workLocation, salary, yearsWorked, address, hoursWorked, username, password, managerSSN)"
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, inputSSN.getText());
			stmt.setString(2, inputFirstName.getText());
			stmt.setString(3, inputLastName.getText());
			stmt.setString(4, inputMiddleInitial.getText());
			stmt.setString(5, inputSex.getText());
			stmt.setString(6, inputDoB.getText());
			stmt.setString(7, inputPhoneNumber.getText());
			stmt.setString(8, inputEmployeeType.getText());
			stmt.setString(9, inputWorkLocation.getText());
			stmt.setString(10, inputSalary.getText());
			stmt.setString(11, inputYearsWorked.getText());
			stmt.setString(12, inputAddress.getText());
			stmt.setString(13, inputHoursWorked.getText());
			stmt.setString(14, inputUsername.getText());
			stmt.setString(15, inputPassword.getText());
			stmt.setString(16, inputManagerSSN.getText());
			
			stmt.execute();
			JOptionPane.showMessageDialog(null, "Record has been added.");
			
			stmt.close();
			connection.close();
		} catch (Exception exception) {
			System.out.println("Error inserting to database.");
			exception.printStackTrace();
		}
	}
	
	/***
	 * This method populates the fields of the Employee object with the 
	 * current data to allow the user to edit the existing information.
	 * @throws SQLException
	 */
	private void populateToUpdate() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = staffTable.getSelectedRow();
			if(selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				String SSN = (staffTable.getModel().getValueAt(selectedRow, 0)).toString();
				String query = "SELECT * FROM lramos6db.Employee WHERE SSN ='" + SSN + "'";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
							
				if(result.next() == true) {
					inputSSN.setText(result.getString("SSN"));
					inputFirstName.setText(result.getString("fName"));
					inputLastName.setText(result.getString("lName"));
					inputMiddleInitial.setText(result.getString("mInit"));
					inputSex.setText(result.getString("sex"));
					inputDoB.setText(result.getString("DOB"));
					inputPhoneNumber.setText(result.getString("phoneNo"));
					inputEmployeeType.setText(result.getString("employeeType"));
					inputWorkLocation.setText(result.getString("workLocation"));
					inputSalary.setText(result.getString("salary"));
					inputYearsWorked.setText(result.getString("yearsWorked"));
					inputAddress.setText(result.getString("address"));
					inputHoursWorked.setText(result.getString("hoursWorked"));
					inputUsername.setText(result.getString("username"));
					inputPassword.setText(result.getString("password"));
					inputManagerSSN.setText(result.getString("managerSSN"));
					
					System.out.println("Fields populated successfully.");
				} else {
					System.out.println("Fields were not populated successfully.");
				}
			}
		} catch (Exception e) {
			System.out.print("Error retrieving data.");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method makes the SQL query to update the selected record in the
	 * database's Employee table.
	 * @throws SQLException
	 */
	
	private void updateDatabase() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = staffTable.getSelectedRow();
			String SSN = (staffTable.getModel().getValueAt(selectedRow, 0)).toString();
			String query = "UPDATE lramos6db.Employee SET " +
								"SSN='" + inputSSN.getText() +
								"', fName ='" + inputFirstName.getText() +
								"', lName ='" + inputLastName.getText() +
								"', mInit ='" + inputMiddleInitial.getText() +
								"', sex ='" + inputSex.getText() +
								"', DOB ='" + inputDoB.getText() +
								"', phoneNo ='" + inputPhoneNumber.getText() +
								"', employeeType ='" + inputWorkLocation.getText() +
								"', salary ='" + inputSalary.getText() +
								"', yearsWorked ='" + inputYearsWorked.getText() +
								"', address ='" + inputAddress.getText() +
								"', hoursWorked ='" + inputHoursWorked.getText() +
								"', username ='" + inputUsername.getText() +
								"', password ='" + inputPassword.getText() +
								"', managerSSN ='" + inputManagerSSN.getText() +
								"' WHERE SSN ='" + SSN + "';";
			PreparedStatement stmt = connection.prepareStatement(query);
			
			stmt.execute();
			JOptionPane.showMessageDialog(null, "Record updated successfully.");
			
			stmt.close();
			connection.close();
			
			clearFields();
		} catch (Exception e) {
			System.out.print("Error updating record on database.");
			JOptionPane.showMessageDialog(null, "Record failed to update.");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method makes the SQL query to delete the selected record (table row)
	 * from the database's Employee table.
	 * @throws SQLException
	 */
	
	private void deleteFromDatabase() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
		
		if(confirmation == 0) {
			try {
				int selectedRow = staffTable.getSelectedRow();
				String SSN = staffTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				String query = "DELETE FROM lramos6db.Employee WHERE SSN='" + SSN + "';";
				System.out.println("QUERY " + query);
				PreparedStatement stmt = connection.prepareStatement(query);
				
				stmt.execute();
				System.out.println("Executed:" + query);
				JOptionPane.showMessageDialog(null, "Record deleted successfully.");
			} catch (Exception exception) {
				System.out.print("Error deleting record from database.");
				JOptionPane.showMessageDialog(null, "Record failed to delete.");
				exception.printStackTrace();
			}
		}
		
		connection.close();
	}
	
	/***
	 * This method clears the input fields to avoid incorrect
	 * data on following edit attempt
	 */
	
	private void clearFields() {
		inputSSN = null;
		inputFirstName = null;
		inputLastName = null;
		inputMiddleInitial = null;
		inputSex = null;
		inputDoB = null;
		inputPhoneNumber = null;
		inputEmployeeType = null;
		inputWorkLocation = null;
		inputSalary = null;
		inputYearsWorked = null;
		inputAddress = null;
		inputHoursWorked = null;
		inputUsername = null;
		inputPassword = null;
		inputManagerSSN = null;
	}
}
