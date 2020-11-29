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
	private JComboBox comboBoxEmpType;
	private JComboBox comboBoxWorkLoc;
	private JComboBox comboBoxSalary;
	private JTextField textFieldYearsWorked;
	private JComboBox comboBoxManager;
	private JTable staffTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldCity;
	private JTextField textFieldState;
	private JTextField textFieldZIP;
	
	/**
	 * Create the panel.
	 */
	public StaffPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JScrollPane scrollPaneStaff = new JScrollPane();
		scrollPaneStaff.setBounds(220, 45, 630, 444);
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
					updateDatabase();
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
			
			while(result.next() == true) {
				comboBoxEmpType.addItem(result.getString("employeeType"));
			}
			
			query = "SELECT DISTINCT workLocation FROM lramos6db.Staff WHERE workLocation IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			while(result.next() == true) {
				comboBoxWorkLoc.addItem(result.getString("workLocation"));
			}
			
			query = "SELECT DISTINCT salary FROM lramos6db.Staff WHERE salary IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			while(result.next() == true) {
				comboBoxSalary.addItem(result.getString("salary"));
			}
			
			//TODO fill in yearsWorked
			
			query = "SELECT DISTINCT managerID FROM lramos6db.Staff WHERE managerID IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			while(result.next() == true) {
				comboBoxManager.addItem(result.getString("managerID"));
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
			
			if(comboBoxEmpType.getSelectedItem().toString() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "employeeType='" + comboBoxEmpType.getSelectedItem().toString() +"'";
			}
			
			if(comboBoxWorkLoc.getSelectedItem().toString() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "workLocation='" + comboBoxWorkLoc.getSelectedItem().toString() +"'";
			}
			
			if(comboBoxSalary.getSelectedItem().toString() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "salary" + comboBoxWorkLoc.getSelectedItem().toString(); //TODO fix parameter input
			}
			
			//TODO Handle YearsWorked input
			
			if(comboBoxManager.getSelectedItem().toString() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "managerID='" + comboBoxManager.getSelectedItem().toString() +"'";
			}
			
			query += ";";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			staffTable.setModel(DbUtils.resultSetToTableModel(result));
			System.out.println(query);
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
							+ " workLocation, salary, yearsWorked, address, hoursWorked, managerSSN)"
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, )";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, "");
			stmt.setString(2, "");
			stmt.setString(3, "");
			stmt.setString(4, "");
			stmt.setString(5, "");
			stmt.setString(6, "");
			stmt.setString(7, "");
			stmt.setString(8, "");
			stmt.setString(9, "");
			stmt.setString(10, "");
			stmt.setString(11, "");
			stmt.setString(12, "");
			stmt.setString(13, "");
			stmt.setString(14, "");
			
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
				String query = "SELECTED * FROM lramos6db.Employee WHERE SSN='" + SSN + "'";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
							
				if(result.next() == true) {
					//TODO - Fill in fields on screen where update happens
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
			String query = "UPDATE lramos6db.Employee SET WHERE SSN='" + SSN + "';";
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
	
	private void deleteFromDatabase() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
		
		if(confirmation == 0) {
			try {
				int selectedRow = staffTable.getSelectedRow();
				String SSN = staffTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				String query = "DELETE FROM lramos6db.Vehicle WHERE SSN='" + SSN + "';";
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
}
