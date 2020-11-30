package carmaxDBMS;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class DepartmentsPanel extends JPanel {
	private Connection connection = null;
	private JTable departmentsTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldDptNo;
	private JTextField textFieldDptName;
	private JComboBox<String> comboBoxManager;

	private JTextField inputDptNo = new JTextField();
	private JTextField inputDptName = new JTextField();
	private JTextField inputManagerSSN = new JTextField();
	
	Object[] inputFields = {
			"Department Number", inputDptNo,
			"Department Name", inputDptName,
			"Manager SSN", inputManagerSSN
	};
	
	String[] addOptions = {"Add", "Cancel"};
	String[] updateOptions = {"Save Changes", "Cancel"};
	
	/**
	 * Create the panel.
	 */
	public DepartmentsPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JScrollPane scrollPaneStaff = new JScrollPane();
		scrollPaneStaff.setBounds(220, 45, 630, 380);
		add(scrollPaneStaff);
		
		departmentsTable = new JTable();
		departmentsTable.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPaneStaff.setViewportView(departmentsTable);
		
		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");
		 
		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);
		
		departmentsTable.setComponentPopupMenu(popupMenu);
		
		menuItemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane updatePane = new JOptionPane();
				updatePane.setVisible(false);
				
				try {
					populateToUpdate();
					updatePane.setVisible(true);
					
					int choice = updatePane.showOptionDialog(null, inputFields, "Update Department", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);
					
					if(choice == 0) {
						System.out.println("Updating Department... ");
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
		
		JLabel lblDepartmentNo = new JLabel("Number");
		lblDepartmentNo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDepartmentNo.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDepartmentNo.setBounds(40, 48, 53, 15);
		add(lblDepartmentNo);
		
		textFieldDptNo = new JTextField();
		lblDepartmentNo.setLabelFor(textFieldDptNo);
		textFieldDptNo.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldDptNo.setBounds(103, 45, 86, 20);
		add(textFieldDptNo);
		
		JLabel lblDepartmentName = new JLabel("Name");
		lblDepartmentName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDepartmentName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDepartmentName.setBounds(47, 77, 46, 15);
		add(lblDepartmentName);
		
		textFieldDptName = new JTextField();
		lblDepartmentName.setLabelFor(textFieldDptName);
		textFieldDptName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldDptName.setBounds(103, 74, 86, 20);
		add(textFieldDptName);
		
		JLabel lblManager = new JLabel("Manager");
		lblManager.setHorizontalAlignment(SwingConstants.TRAILING);
		lblManager.setFont(new Font("Arial", Font.PLAIN, 12));
		lblManager.setBounds(31, 106, 62, 15);
		add(lblManager);
		
		comboBoxManager = new JComboBox();
		lblManager.setLabelFor(comboBoxManager);
		comboBoxManager.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxManager.setBounds(103, 103, 86, 20);
		add(comboBoxManager);
		
		JButton btnSearchDepartments = new JButton("Search");
		btnSearchDepartments.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSearchDepartments.setBounds(68, 152, 77, 23);
		btnSearchDepartments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					searchDepartments();
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		});
		add(btnSearchDepartments);
		
		JButton btnAddNewDepartment = new JButton("Add New Department");
		btnAddNewDepartment.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAddNewDepartment.setBounds(432, 450, 156, 23);
		btnAddNewDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(JOptionPane.showOptionDialog(null, inputFields, "Add Department", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, addOptions, null) == 0) {
						System.out.print("Adding new Department to database...");
						addToDatabase();
					}
					
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
		add(btnAddNewDepartment);
		
		populateComboBoxes();
	}
	
	/***
	 * This method populates the Manager combobox on clientsTable with data
	 * retrieved from Location table, which serve as a filter for the user
	 * and as a means of input validation.
	 */
	
	private void populateComboBoxes() {
		try {
			connection = SQLConnection.ConnectDb();
			
			String query = "SELECT DISTINCT managerSSN_FK2 FROM lramos6db.Department WHERE managerSSN_FK2 IS NOT NULL";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			comboBoxManager.addItem(null);
			while(result.next() == true) {
				comboBoxManager.addItem(result.getString("managerSSN_FK2"));
			}
		} catch (Exception e) {
			System.out.println("Error querying data for combobox");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method checks for the fields user entered data on and
	 * makes the SQL query with the parameters provided by the user.
	 * Results from Department table are populated in the departmentsTable.
	 * @throws SQLException
	 */
	
	private void searchDepartments() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int parameterCount = 0;
			String query = "SELECT * FROM lramos6db.Department WHERE "; //Base query
			
			if(!textFieldDptNo.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "departmentNo ='" + textFieldDptNo.getText() + "'";
			}
			
			if(!textFieldDptName.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "departmentName ='" + textFieldDptName.getText() + "'";
			}
			
			if(comboBoxManager.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "managerSSN_FK2 ='" + comboBoxManager.getSelectedItem().toString() +"'";
			}
			
			query += ";";
			
			if(parameterCount > 0) {
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				departmentsTable.setModel(DbUtils.resultSetToTableModel(result));
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
	 * to the database's Department table.
	 * @throws SQLException
	 */
	
	private void addToDatabase() throws SQLException  {
		try {
			connection = SQLConnection.ConnectDb();
			String query = "INSERT INTO lramos6db.Department (departmentNo, departmentName, managerSSN_FK2)"
							+ " values (?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, inputDptNo.getText());
			stmt.setString(2, inputDptName.getText());
			stmt.setString(3, inputManagerSSN.getText());
			
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
	 * This method populates the fields of the Client object with the 
	 * current data to allow the user to edit the existing information.
	 * @throws SQLException
	 */
	private void populateToUpdate() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = departmentsTable.getSelectedRow();
			if(selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				String departmentNo = (departmentsTable.getModel().getValueAt(selectedRow, 0)).toString();
				String query = "SELECT * FROM lramos6db.Department WHERE departmentNo='" + departmentNo + "';";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();

				if(result.next() == true) {
					inputDptNo.setText(result.getString("departmentNo"));
					inputDptName.setText(result.getString("departmentName"));
					inputManagerSSN.setText(result.getString("managerSSN_FK2"));
				}
			}
		} catch (Exception e) {
			System.out.print("Error retrieving data.");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method makes the SQL query to update the selected record in the
	 * database's Department table.
	 * @throws SQLException
	 */
	
	private void updateDatabase() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = departmentsTable.getSelectedRow();
			String departmentNo = (departmentsTable.getModel().getValueAt(selectedRow, 0)).toString();
			String query = "UPDATE lramos6db.Department SET " + 
							"departmentNo =" + inputDptNo.getText() +
							", departmentName ='" + inputDptName.getText() +
							"', managerSSN_FK2 ='" + inputManagerSSN.getText() +
							"' WHERE departmentNo='" + departmentNo + "';";
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
	 * from the database's Department table.
	 * @throws SQLException
	 */
	
	private void deleteFromDatabase() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
		
		if(confirmation == 0) {
			try {
				int selectedRow = departmentsTable.getSelectedRow();
				String departmentNo = departmentsTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				String query = "DELETE FROM lramos6db.Department WHERE departmentNo='" + departmentNo + "';";
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
		inputDptNo = null;
		inputDptName = null;
		inputManagerSSN = null;
	}
}
