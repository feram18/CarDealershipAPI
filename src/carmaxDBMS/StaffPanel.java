package carmaxDBMS;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import net.proteanit.sql.DbUtils;

public class StaffPanel extends JPanel implements GUIPanel {
	private Connection connection = null;
	private String query;
	private int parameterCount;
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
	private JComboBox<String> textFieldState;
	private JTextField textFieldZIP;
	
	private final String[] sexOptions = {null, "F", "M"}; // TODO - Add Filter to search by sex
	
	private final String[] salaryOptions = {null, "< 10000", "< 20000", "< 30000", "< 40000", "< 50000",
			"< 60000", "< 70000", "< 80000", "< 90000", "< 100000", "< 110000", "< 120000", "> 120000"};
	
	private final String[] stateAbbreviations = {null, "AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT",
			"DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD",
			"ME", "MI", "MN", "MO", "MP", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY",
			"OH", "OK", "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UM", "UT", "VA", "VI", "VT",
			"WA", "WI", "WV", "WY"};
	
	private JTextField inputSSN = new JTextField();
	private JTextField inputFirstName = new JTextField();
	private JTextField inputLastName = new JTextField();
	private JTextField inputMiddleInitial = new JTextField();
	private JComboBox<String> inputSex = new JComboBox<String>(sexOptions);
	private JTextField inputDoB = new JTextField();
	private JTextField inputPhoneNumber= new JTextField();
	private JComboBox<String> inputEmployeeType = new JComboBox<String>();
	private JComboBox<String> inputWorkLocation = new JComboBox<String>();
	private JTextField inputSalary = new JTextField();
	private JTextField inputYearsWorked = new JTextField();
	private JTextField inputAddress = new JTextField();
	private JTextField inputHoursWorked = new JTextField();
	private JTextField inputUsername = new JTextField();
	private JTextField inputPassword = new JTextField();
	private JComboBox<String> inputManagerSSN = new JComboBox<String>();
	
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
	
	private final String[] addOptions = {"Add", "Cancel"};
	private final String[] updateOptions = {"Save Changes", "Cancel"};
	
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
				try {
					populateToUpdate();
					
					int choice = JOptionPane.showOptionDialog(null, inputFields, "Update Employee", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);
					
					if(choice == 0) {
						System.out.println("Updating Employee... ");
						update();
					}
					
					clearFields();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		menuItemDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					delete();
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
		labelSSN.setBounds(10, 48, 96, 15);
		add(labelSSN);
		
		textFieldSSN = new JTextField();
		labelSSN.setLabelFor(textFieldSSN);
		textFieldSSN.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldSSN.setBounds(116, 45, 86, 20);
		add(textFieldSSN);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFirstName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblFirstName.setBounds(10, 79, 96, 15);
		add(lblFirstName);
		
		textFieldFirstName = new JTextField();
		lblFirstName.setLabelFor(textFieldFirstName);
		textFieldFirstName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldFirstName.setBounds(116, 76, 86, 20);
		add(textFieldFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLastName.setBounds(10, 111, 96, 15);
		add(lblLastName);
		
		textFieldLastName = new JTextField();
		lblLastName.setLabelFor(textFieldLastName);
		textFieldLastName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldLastName.setBounds(116, 108, 86, 20);
		add(textFieldLastName);
		
		JLabel lblEmployeeType = new JLabel("Employee Type");
		lblEmployeeType.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmployeeType.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEmployeeType.setBounds(10, 142, 96, 15);
		add(lblEmployeeType);
		
		comboBoxEmpType = new JComboBox<String>();
		lblEmployeeType.setLabelFor(comboBoxEmpType);
		comboBoxEmpType.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxEmpType.setBounds(116, 139, 86, 20);
		add(comboBoxEmpType);
		
		JLabel lblWorkLocation = new JLabel("Work Location");
		lblWorkLocation.setHorizontalAlignment(SwingConstants.TRAILING);
		lblWorkLocation.setFont(new Font("Arial", Font.PLAIN, 12));
		lblWorkLocation.setBounds(10, 173, 96, 15);
		add(lblWorkLocation);
		
		comboBoxWorkLoc = new JComboBox<String>();
		lblWorkLocation.setLabelFor(comboBoxWorkLoc);
		comboBoxWorkLoc.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxWorkLoc.setBounds(116, 170, 86, 20);
		add(comboBoxWorkLoc);
		
		JLabel lblSalary = new JLabel("Salary");
		lblSalary.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSalary.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSalary.setBounds(10, 204, 96, 15);
		add(lblSalary);
		
		comboBoxSalary = new JComboBox<String>(salaryOptions);
		lblSalary.setLabelFor(comboBoxSalary);
		comboBoxSalary.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxSalary.setBounds(116, 201, 86, 20);
		add(comboBoxSalary);
		
		JLabel lblYearsWorked = new JLabel("Years Worked");
		lblYearsWorked.setHorizontalAlignment(SwingConstants.TRAILING);
		lblYearsWorked.setFont(new Font("Arial", Font.PLAIN, 12));
		lblYearsWorked.setBounds(10, 235, 96, 15);
		add(lblYearsWorked);
		
		textFieldYearsWorked = new JTextField();
		lblYearsWorked.setLabelFor(textFieldYearsWorked);
		textFieldYearsWorked.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldYearsWorked.setBounds(116, 232, 86, 20);
		add(textFieldYearsWorked);
		
		JLabel lblManager = new JLabel("Manager");
		lblManager.setHorizontalAlignment(SwingConstants.TRAILING);
		lblManager.setFont(new Font("Arial", Font.PLAIN, 12));
		lblManager.setBounds(10, 359, 96, 15);
		add(lblManager);
		
		comboBoxManager = new JComboBox<String>();
		comboBoxManager.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxManager.setBounds(116, 356, 86, 20);
		add(comboBoxManager);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCity.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCity.setBounds(10, 266, 96, 15);
		add(lblCity);
		
		textFieldCity = new JTextField();
		lblCity.setLabelFor(textFieldCity);
		textFieldCity.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldCity.setBounds(116, 263, 86, 20);
		add(textFieldCity);
		
		JLabel lblState = new JLabel("State");
		lblState.setHorizontalAlignment(SwingConstants.TRAILING);
		lblState.setFont(new Font("Arial", Font.PLAIN, 12));
		lblState.setBounds(10, 297, 96, 15);
		add(lblState);
		
		textFieldState = new JComboBox<String>(stateAbbreviations);
		lblState.setLabelFor(textFieldState);
		textFieldState.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldState.setBounds(116, 294, 86, 20);
		add(textFieldState);
		
		JLabel lblZipCode = new JLabel("ZIP Code");
		lblZipCode.setHorizontalAlignment(SwingConstants.TRAILING);
		lblZipCode.setFont(new Font("Arial", Font.PLAIN, 12));
		lblZipCode.setBounds(10, 328, 96, 15);
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
					search();
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
					populateToAdd();
					if(JOptionPane.showOptionDialog(null, inputFields, "Add Employee",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
							addOptions, null) == 0) {
						System.out.print("Adding new Employee to database...");
						add();
					}
					
					clearFields();
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
	
	@Override
	public void populateComboBoxes() {
		try {
			connection = SQLConnection.ConnectDb();
			query = "SELECT DISTINCT employeeType FROM Employee WHERE employeeType IS NOT NULL ORDER BY employeeType ASC";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			comboBoxEmpType.addItem(null);
			while(result.next()) {
				comboBoxEmpType.addItem(result.getString("employeeType"));
			}
			
			query = "SELECT DISTINCT workLocation FROM Employee WHERE workLocation IS NOT NULL ORDER BY workLocation ASC";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxWorkLoc.addItem(null);
			while(result.next()) {
				comboBoxWorkLoc.addItem(result.getString("workLocation"));
			}
			
			query = "SELECT DISTINCT managerSSN FROM Employee WHERE managerSSN IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxManager.addItem(null);
			while(result.next()) {
				comboBoxManager.addItem(result.getString("managerSSN"));
			}
			
			/* Populates Manager's LastName, FirstName instead of SSN
			query = "SELECT DISTINCT fName, lName FROM Employee WHERE SSN = managerSSN ORDER BY lName;";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxManager.addItem(null);
			while(result.next()) {
				comboBoxManager.addItem(result.getString("lName") + ", " + result.getString("fName"));
			}*/
			
			connection.close();
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
	
	
	@Override
	public void search() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			parameterCount = 0;
			query = "SELECT SSN, " +
					"`fName` AS `First Name`, " +
					"`lName` AS `Last Name`, " +
					"`mInit` AS `M.I.`, " +
					"`sex` AS `Sex`, " + "DOB, " + 
					"`phoneNo` AS `Phone Number`, " +
					"`employeeType` AS `Employee Type`, " +
					"`workLocation` AS `Work Location`, " +
					"`salary` AS `Salary`, " +
					"`yearsWorked` AS `Years Worked`, " +
					"`address` AS `Address`, " +
					"`hoursWorked` AS `Hours Worked`, " +
					"`username` AS `Username`, " +
					"`password` AS `Password`, " +
					"`managerSSN` AS `Manager SSN` " +
					"FROM Employee WHERE "; //Base query
			
			if (!textFieldSSN.getText().isEmpty()) {
				addToQuery();
				query += "SSN='" + textFieldSSN.getText() + "'";
			}
			
			if (!textFieldFirstName.getText().isEmpty()) {
				addToQuery();
				query += "fName='" + textFieldFirstName.getText() + "'";
			}
			
			if (!textFieldLastName.getText().isEmpty()) {
				addToQuery();
				query += "lName='" + textFieldLastName.getText() + "'";
			}
			
			if (comboBoxEmpType.getSelectedItem() != null) {
				addToQuery();
				query += "employeeType='" + comboBoxEmpType.getSelectedItem().toString() +"'";
			}
			
			if (comboBoxWorkLoc.getSelectedItem() != null) {
				addToQuery();
				query += "workLocation='" + comboBoxWorkLoc.getSelectedItem().toString() +"'";
			}
			
			if (comboBoxSalary.getSelectedItem() != null) {
				addToQuery();
				query += "salary " + comboBoxSalary.getSelectedItem().toString();
			}
			
			if (!textFieldYearsWorked.getText().isEmpty()) {
				addToQuery();
				query += "yearsWorked='" + textFieldYearsWorked.getText() + "'";
			}
			
			if (!textFieldCity.getText().isEmpty()) {
				addToQuery();
				query += "address LIKE '%" + textFieldCity.getText() + "%'";
			}
			
			if (textFieldState.getSelectedItem() != null) {
				addToQuery();
				query += "address LIKE '%" + textFieldState.getSelectedItem() + "%'";
			}
			
			if (!textFieldZIP.getText().isEmpty()) {
				addToQuery();
				query += "address LIKE '%" + textFieldZIP.getText() + "%'";
			}
			
			if (comboBoxManager.getSelectedItem() != null) {
				addToQuery();
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
			
			staffTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			EmployeeInterfaceFrame.resizeTableColumns(staffTable);
			connection.close();
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
	
	
	@Override
	public void add() throws SQLException  {
		try {
			connection = SQLConnection.ConnectDb();
			query = "INSERT INTO Employee (SSN, fName, lName, mInit, sex, DOB, phoneNo, employeeType," +
					" workLocation, salary, yearsWorked, address, hoursWorked, username, password, managerSSN)" +
					" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, inputSSN.getText());
			stmt.setString(2, inputFirstName.getText());
			stmt.setString(3, inputLastName.getText());
			stmt.setString(4, inputMiddleInitial.getText());
			stmt.setString(5, (String) inputSex.getSelectedItem());
			stmt.setString(6, inputDoB.getText());
			stmt.setString(7, inputPhoneNumber.getText());
			stmt.setString(8, (String) inputEmployeeType.getSelectedItem());
			stmt.setString(9, (String) inputWorkLocation.getSelectedItem());
			stmt.setString(10, inputSalary.getText());
			stmt.setString(11, inputYearsWorked.getText());
			stmt.setString(12, inputAddress.getText());
			stmt.setString(13, inputHoursWorked.getText());
			stmt.setString(14, inputUsername.getText());
			stmt.setString(15, inputPassword.getText());
			stmt.setString(16, (String) inputManagerSSN.getSelectedItem());
			
			stmt.execute();
			JOptionPane.showMessageDialog(null, "Record has been added.");
			
			// Add Employee to appropriate table (Mechanic, SalesAssociate, Manager, SiteManager)
			switch(inputEmployeeType.getSelectedItem().toString()) {
				case "Mechanic":
					query = "INSERT INTO Mechanic (mechanicSSN) VALUES (?)";
					stmt = connection.prepareStatement(query);
					stmt.setString(1, (String) inputSSN.getText());
					stmt.execute();
				case "SalesAssociate":
					query = "INSERT INTO SalesAssociate (associateSSN) VALUES (?)";
					stmt = connection.prepareStatement(query);
					stmt.setString(1, (String) inputSSN.getText());
					stmt.execute();
				case "Manager":
					query = "INSERT INTO Manager (managerSSN) VALUES (?)";
					stmt = connection.prepareStatement(query);
					stmt.setString(1, (String) inputSSN.getText());
					stmt.execute();
				case "Site Manager":
					query = "INSERT INTO SiteManager (siteManagerSSN) VALUES (?)";
					stmt = connection.prepareStatement(query);
					stmt.setString(1, (String) inputSSN.getText());
					stmt.execute();
			}
			
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
	
	@Override
	public void populateToUpdate() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = staffTable.getSelectedRow();
			if(selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				query = "SELECT DISTINCT locationName FROM Location WHERE locationName IS NOT NULL ORDER BY locationName ASC";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				
				inputWorkLocation.addItem(null);
				while(result.next()) {
					inputWorkLocation.addItem(result.getString("locationName"));
				}

				query = "SELECT DISTINCT managerSSN FROM Manager WHERE managerSSN IS NOT NULL";
				stmt = connection.prepareStatement(query);
				result = stmt.executeQuery();
				
				inputManagerSSN.addItem(null);
				while(result.next()) {
					inputManagerSSN.addItem(result.getString("managerSSN"));
				}
				
				inputEmployeeType.addItem("Manager");
				inputEmployeeType.addItem("Mechanic");
				inputEmployeeType.addItem("Sales Associate");
				inputEmployeeType.addItem("Site Manager");
				
				String SSN = (staffTable.getModel().getValueAt(selectedRow, 0)).toString();
				query = "SELECT * FROM Employee WHERE SSN ='" + SSN + "'";
				stmt = connection.prepareStatement(query);
				result = stmt.executeQuery();
				
				if(result.next()) {
					inputSSN.setText(result.getString("SSN"));
					inputFirstName.setText(result.getString("fName"));
					inputLastName.setText(result.getString("lName"));
					inputMiddleInitial.setText(result.getString("mInit"));
					inputSex.setSelectedItem(result.getString("sex"));
					inputDoB.setText(result.getString("DOB"));
					inputPhoneNumber.setText(result.getString("phoneNo"));
					inputEmployeeType.setSelectedItem(result.getString("employeeType"));
					inputWorkLocation.setSelectedItem(result.getString("workLocation"));
					inputSalary.setText(result.getString("salary"));
					inputYearsWorked.setText(result.getString("yearsWorked"));
					inputAddress.setText(result.getString("address"));
					inputHoursWorked.setText(result.getString("hoursWorked"));
					inputUsername.setText(result.getString("username"));
					inputPassword.setText(result.getString("password"));
					inputManagerSSN.setSelectedItem(result.getString("managerSSN"));
					
					System.out.println("Fields populated successfully.");
				} else {
					System.out.println("Fields were not populated successfully.");
				}
			}
			
			connection.close();
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
	
	@Override
	public void update() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = staffTable.getSelectedRow();
			String SSN = (staffTable.getModel().getValueAt(selectedRow, 0)).toString();
			query = "UPDATE Employee SET " +
					"SSN='" + inputSSN.getText() +
					"', fName ='" + inputFirstName.getText() +
					"', lName ='" + inputLastName.getText() +
					"', mInit ='" + inputMiddleInitial.getText() +
					"', sex ='" + inputSex.getSelectedItem() +
					"', DOB ='" + inputDoB.getText() +
					"', phoneNo ='" + inputPhoneNumber.getText() +
					"', employeeType ='" + inputWorkLocation.getSelectedItem() +
					"', salary ='" + inputSalary.getText() +
					"', yearsWorked ='" + inputYearsWorked.getText() +
					"', address ='" + inputAddress.getText() +
					"', hoursWorked ='" + inputHoursWorked.getText() +
					"', username ='" + inputUsername.getText() +
					"', password ='" + inputPassword.getText() +
					"', managerSSN ='" + inputManagerSSN.getSelectedItem() +
					"' WHERE SSN ='" + SSN + "';";
			PreparedStatement stmt = connection.prepareStatement(query);
			
			stmt.execute();
			JOptionPane.showMessageDialog(null, "Record updated successfully.");
			
			stmt.close();
			connection.close();
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
	
	@Override
	public void delete() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
		
		if(confirmation == 0) {
			try {
				int selectedRow = staffTable.getSelectedRow();
				String SSN = staffTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				query = "DELETE FROM Employee WHERE SSN='" + SSN + "';";
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
	
	@Override
	public void clearFields() {
		inputSSN.setText(null);
		inputFirstName.setText(null);
		inputLastName.setText(null);
		inputMiddleInitial.setText(null);
		inputSex.removeAllItems();
		inputDoB.setText(null);
		inputPhoneNumber.setText(null);
		inputEmployeeType.removeAllItems();
		inputWorkLocation.removeAllItems();
		inputSalary.setText(null);
		inputYearsWorked.setText(null);
		inputAddress.setText(null);
		inputHoursWorked.setText(null);
		inputUsername.setText(null);
		inputPassword.setText(null);
		inputManagerSSN.removeAllItems();
	}
	
	/**
	 * This method increases the parameter count, and adds the
	 * SQL keyword to allow an additional parameter to be added
	 * to SQL query
	 */

	@Override
	public void addToQuery() {
		parameterCount++;
		if (parameterCount > 1) {
			query += " AND ";
		}
	}
	
	/**
	 * This method populates the comboboxes on the Add Employee popup
	 * window, as a means of input validation.
	 */

	@Override
	public void populateToAdd() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			
			query = "SELECT DISTINCT employeeType FROM Employee WHERE employeeType IS NOT NULL ORDER BY employeeType;";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			while(result.next()) {
				inputEmployeeType.addItem(result.getString("employeeType"));
			}
			
			query = "SELECT DISTINCT locationName FROM Location WHERE locationName IS NOT NULL ORDER BY locationName;";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			while(result.next()) {
				inputWorkLocation.addItem(result.getString("locationName"));
			}
			
			query = "SELECT DISTINCT managerSSN FROM Manager WHERE managerSSN IS NOT NULL;";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			while(result.next()) {
				inputManagerSSN.addItem(result.getString("managerSSN"));
			}
			
			stmt.close();
			result.close();
			connection.close();
		} catch (Exception e) {
			System.out.print("Error retrieving data.");
			e.printStackTrace();
		}
	}
}
