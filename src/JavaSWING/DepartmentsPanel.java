package JavaSWING;
import java.awt.Color;
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

import net.proteanit.sql.DbUtils;

public class DepartmentsPanel extends JPanel implements GUIPanel{
	// Utility variables
	private Connection connection = null;
	private String query;
	private int parameterCount;
	
	// UI components to filter data when searching
	private JTable departmentsTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldDptNo;
	private JTextField textFieldDptName;
	private JComboBox<String> comboBoxManager;

	// UI components to be shown when updating, or adding a new record to database
	private JTextField inputDptNo = new JTextField();
	private JTextField inputDptName = new JTextField();
	private JComboBox<String> inputManagerSSN = new JComboBox<String>();
	
	// Arrays to be passed into JOptionPane's
	private final Object[] inputFields = {
			"Department Number", inputDptNo,
			"Department Name", inputDptName,
			"Manager SSN", inputManagerSSN
	};
	private final String[] addOptions = {"Add", "Cancel"};
	private final String[] updateOptions = {"Save Changes", "Cancel"};
	
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
		departmentsTable.setEnabled(false);
		
		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");
		 
		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);
		
		departmentsTable.setComponentPopupMenu(popupMenu);
		
		menuItemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					populateToUpdate();
					
					int choice = JOptionPane.showOptionDialog(null, inputFields, "Update Department",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);
					
					if(choice == 0) {
						System.out.println("Updating Department... ");
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
		
		JLabel lblDepartmentNo = new JLabel("Number");
		lblDepartmentNo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDepartmentNo.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDepartmentNo.setBounds(10, 48, 83, 15);
		add(lblDepartmentNo);
		
		textFieldDptNo = new JTextField();
		lblDepartmentNo.setLabelFor(textFieldDptNo);
		textFieldDptNo.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldDptNo.setBounds(103, 45, 86, 20);
		add(textFieldDptNo);
		
		JLabel lblDepartmentName = new JLabel("Name");
		lblDepartmentName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDepartmentName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDepartmentName.setBounds(10, 77, 83, 15);
		add(lblDepartmentName);
		
		textFieldDptName = new JTextField();
		lblDepartmentName.setLabelFor(textFieldDptName);
		textFieldDptName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldDptName.setBounds(103, 74, 86, 20);
		add(textFieldDptName);
		
		JLabel lblManager = new JLabel("Manager");
		lblManager.setHorizontalAlignment(SwingConstants.TRAILING);
		lblManager.setFont(new Font("Arial", Font.PLAIN, 12));
		lblManager.setBounds(10, 106, 83, 15);
		add(lblManager);
		
		comboBoxManager = new JComboBox<String>();
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
					search();
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
					populateToAdd();
					
					if(JOptionPane.showOptionDialog(null, inputFields, "Add Department",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
							null, addOptions, null) == 0) {
						System.out.print("Adding new Department to database...");
						add();
					}
					
					clearFields();
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
	
	@Override
	public void populateComboBoxes() {
		// Delete current options
		comboBoxManager.removeAllItems();
										
		// Populate with updated options
		try {
			connection = SQLConnection.ConnectDb();
			
			query = "SELECT DISTINCT managerSSN_FK2 FROM Department WHERE managerSSN_FK2 IS NOT NULL";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			comboBoxManager.addItem(null);
			while(result.next()) {
				comboBoxManager.addItem(result.getString("managerSSN_FK2"));
			}
			
			connection.close();
		} catch (Exception e) {
			System.out.println("Error querying data for combobox");
			e.printStackTrace();
		}
	}
	
	/**
	 * *
	 * This method checks for the fields user entered data on and
	 * makes the SQL query with the parameters provided by the user.
	 * Results from Department table are populated in the departmentsTable.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void search() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			parameterCount = 0;
			query = "SELECT `departmentNo` AS `Dpt. No.`, " +
					"`departmentName` AS `Dpt. Name` , " +
					"`managerSSN_FK2` AS `Manager SSN`" +
					"FROM Department WHERE "; //Base query
			
			if (!textFieldDptNo.getText().isEmpty()) {
				addToQuery();
				query += "departmentNo ='" + textFieldDptNo.getText() + "'";
			}
			
			if (!textFieldDptName.getText().isEmpty()) {
				addToQuery();
				query += "departmentName ='" + textFieldDptName.getText() + "'";
			}
			
			if (comboBoxManager.getSelectedItem() != null) {
				addToQuery();
				query += "managerSSN_FK2 ='" + comboBoxManager.getSelectedItem().toString() +"'";
			}
			
			query += ";";
			
			if (parameterCount > 0) {
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				departmentsTable.setModel(DbUtils.resultSetToTableModel(result));
			} else {
				JOptionPane.showMessageDialog(null, "No criteria selected.");
			}
			
			parameterCount = 0;
			
			departmentsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			EmployeeInterfaceFrame.resizeTableColumns(departmentsTable);
			
			connection.close();
		} catch (Exception e) {
			System.out.println("Error: Invalid query.");
			e.printStackTrace();
		}
	}
	
	/**
	 * *
	 * This method makes the SQL query to add the a new row
	 * to the database's Department table.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void add() throws SQLException  {
		try {
			connection = SQLConnection.ConnectDb();
			query = "INSERT INTO Department (departmentNo, departmentName, managerSSN_FK2)" +
					" values (?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, inputDptNo.getText());
			stmt.setString(2, inputDptName.getText());
			stmt.setString(3, (String) inputManagerSSN.getSelectedItem());
			
			stmt.execute();
			JOptionPane.showMessageDialog(null, "Record has been added.");
			
			stmt.close();
			connection.close();
			
			populateComboBoxes(); // Re-populate ComboBoxes with updated data
		} catch (Exception exception) {
			System.out.println("Error inserting to database.");
			exception.printStackTrace();
		}
	}
	
	/**
	 * *
	 * This method populates the fields of the Departments object with the 
	 * current data to allow the user to edit the existing information.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void populateToUpdate() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = departmentsTable.getSelectedRow();
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				query = "SELECT DISTINCT managerSSN FROM Manager WHERE managerSSN";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				
				inputManagerSSN.addItem(null);
				while (result.next()) {
					inputManagerSSN.addItem(result.getString("managerSSN"));
				}
				
				String departmentNo = (departmentsTable.getModel().getValueAt(selectedRow, 0)).toString();
				query = "SELECT * FROM Department WHERE departmentNo='" + departmentNo + "';";
				stmt = connection.prepareStatement(query);
				result = stmt.executeQuery();

				if (result.next()) {
					inputDptNo.setText(result.getString("departmentNo"));
					inputDptName.setText(result.getString("departmentName"));
					inputManagerSSN.setSelectedItem(result.getString("managerSSN_FK2"));
				}
			}
			
			connection.close();
		} catch (Exception e) {
			System.out.print("Error retrieving data.");
			e.printStackTrace();
		}
	}
	
	/**
	 * *
	 * This method makes the SQL query to update the selected record in the
	 * database's Department table.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void update() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = departmentsTable.getSelectedRow();
			String departmentNo = (departmentsTable.getModel().getValueAt(selectedRow, 0)).toString();
			query = "UPDATE Department SET " + 
					"departmentNo =" + inputDptNo.getText() +
					", departmentName ='" + inputDptName.getText() +
					"', managerSSN_FK2 ='" + inputManagerSSN.getSelectedItem() +
					"' WHERE departmentNo='" + departmentNo + "';";
			PreparedStatement stmt = connection.prepareStatement(query);
			
			stmt.execute();
			JOptionPane.showMessageDialog(null, "Record updated successfully.");
			
			stmt.close();
			connection.close();
			
			populateComboBoxes(); // Re-populate ComboBoxes with updated data
		} catch (Exception e) {
			System.out.print("Error updating record on database.");
			JOptionPane.showMessageDialog(null, "Record failed to update.");
			e.printStackTrace();
		}
	}
	
	/**
	 * *
	 * This method makes the SQL query to delete the selected record (table row)
	 * from the database's Department table.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void delete() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?",
				"Delete", JOptionPane.YES_NO_OPTION);
		
		if (confirmation == 0) {
			try {
				int selectedRow = departmentsTable.getSelectedRow();
				String departmentNo = departmentsTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				query = "DELETE FROM Department WHERE departmentNo='" + departmentNo + "';";
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
		
		populateComboBoxes(); // Re-populate ComboBoxes with updated data
	}
	
	/**
	 * *
	 * This method clears the input fields to avoid incorrect
	 * data on following edit attempt.
	 */
	
	@Override
	public void clearFields() {
		inputDptNo.setText(null);
		inputDptName.setText(null);
		inputManagerSSN.removeAllItems();
	}
	
	/**
	 * This method increases the parameter count, and adds the
	 * SQL keyword to allow an additional parameter to be added
	 * to SQL query.
	 */

	@Override
	public void addToQuery() {
		parameterCount++;
		if (parameterCount > 1) {
			query += " AND ";
		}
	}
	
	/**
	 * This method populates the comboboxes on the Add Department popup
	 * window, as a means of input validation.
	 *
	 * @throws SQLException the SQL exception
	 */

	@Override
	public void populateToAdd() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			
			query = "SELECT DISTINCT managerSSN FROM Manager WHERE managerSSN IS NOT NULL;";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			while (result.next()) {
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
