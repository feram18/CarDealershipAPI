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

public class LocationsPanel extends JPanel implements GUIPanel {
	// Utility variables
	private Connection connection = null;
	private String query;
	private int parameterCount;
	
	// UI components to filter data when searching
	private JTable locationsTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldLocationID;
	private JTextField textFieldLocationName;
	private JTextField textFieldCity;
	private JComboBox<String> comboBoxState;
	private JTextField textFieldZIP;
	private JComboBox<String> comboBoxManager;
	
	// Array to populate comboBoxState
	private final String[] stateAbbreviations = {null, "AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT",
			"DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD",
			"ME", "MI", "MN", "MO", "MP", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY",
			"OH", "OK", "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UM", "UT", "VA", "VI", "VT",
			"WA", "WI", "WV", "WY"};
	
	// UI components to be shown when updating, or adding a new record to database
	private JTextField inputLocationID = new JTextField();
	private JTextField inputLocationName = new JTextField();
	private JTextField inputAddress = new JTextField();
	private JComboBox<String> inputManagerSSN = new JComboBox<String>();
	
	// Arrays to be passed into JOptionPane's
	private final Object[] inputFields = {
			"Location ID", inputLocationID,
			"Location Name", inputLocationName,
			"Address", inputAddress,
			"Manager SSN", inputManagerSSN
	};
	private final String[] addOptions = {"Add", "Cancel"};
	private final String[] updateOptions = {"Save Changes", "Cancel"};
	
	/**
	 * Create the panel.
	 */
	public LocationsPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JScrollPane scrollPaneStaff = new JScrollPane();
		scrollPaneStaff.setBounds(220, 45, 630, 380);
		add(scrollPaneStaff);
		
		locationsTable = new JTable();
		locationsTable.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPaneStaff.setViewportView(locationsTable);
		locationsTable.setEnabled(false);
		
		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");
		 
		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);
		
		locationsTable.setComponentPopupMenu(popupMenu);
		
		menuItemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					populateToUpdate();
					
					int choice = JOptionPane.showOptionDialog(null, inputFields, "Update Location", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);
					
					if(choice == 0) {
						System.out.println("Updating Location... ");
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
		
		JLabel lblLocationID = new JLabel("Location ID");
		lblLocationID.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLocationID.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLocationID.setBounds(10, 48, 97, 15);
		add(lblLocationID);
		
		textFieldLocationID = new JTextField();
		lblLocationID.setLabelFor(textFieldLocationID);
		textFieldLocationID.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldLocationID.setBounds(117, 45, 86, 20);
		add(textFieldLocationID);
		
		JLabel lblLocationName = new JLabel("Location Name");
		lblLocationName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLocationName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLocationName.setBounds(10, 77, 97, 15);
		add(lblLocationName);
		
		textFieldLocationName = new JTextField();
		lblLocationName.setLabelFor(textFieldLocationName);
		textFieldLocationName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldLocationName.setBounds(117, 74, 86, 20);
		add(textFieldLocationName);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCity.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCity.setBounds(10, 106, 97, 15);
		add(lblCity);
		
		textFieldCity = new JTextField();
		lblCity.setLabelFor(textFieldCity);
		textFieldCity.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldCity.setBounds(117, 103, 86, 20);
		add(textFieldCity);
		
		JLabel lblState = new JLabel("State");
		lblState.setHorizontalAlignment(SwingConstants.TRAILING);
		lblState.setFont(new Font("Arial", Font.PLAIN, 12));
		lblState.setBounds(10, 135, 97, 15);
		add(lblState);
		
		comboBoxState = new JComboBox<String>(stateAbbreviations);
		lblState.setLabelFor(comboBoxState);
		comboBoxState.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxState.setBounds(117, 132, 86, 20);
		add(comboBoxState);
		
		JLabel lblZipCode = new JLabel("ZIP Code");
		lblZipCode.setHorizontalAlignment(SwingConstants.TRAILING);
		lblZipCode.setFont(new Font("Arial", Font.PLAIN, 12));
		lblZipCode.setBounds(10, 164, 97, 15);
		add(lblZipCode);
		
		textFieldZIP = new JTextField();
		lblZipCode.setLabelFor(textFieldZIP);
		textFieldZIP.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldZIP.setBounds(117, 161, 86, 20);
		add(textFieldZIP);
		
		JLabel lblManager = new JLabel("Manager");
		lblManager.setHorizontalAlignment(SwingConstants.TRAILING);
		lblManager.setFont(new Font("Arial", Font.PLAIN, 12));
		lblManager.setBounds(10, 193, 97, 15);
		add(lblManager);
		
		comboBoxManager = new JComboBox<String>();
		lblManager.setLabelFor(comboBoxManager);
		comboBoxManager.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxManager.setBounds(117, 190, 86, 20);
		add(comboBoxManager);
		
		JButton btnSearchLocations = new JButton("Search");
		btnSearchLocations.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSearchLocations.setBounds(82, 239, 77, 23);
		btnSearchLocations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					search();
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		});
		add(btnSearchLocations);
		
		JButton btnAddLocation = new JButton("Add New Location");
		btnAddLocation.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAddLocation.setBounds(450, 450, 137, 23);
		btnAddLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					populateToAdd();
					
					if(JOptionPane.showOptionDialog(null, inputFields, "Add Location",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
							null, addOptions, null) == 0) {
						System.out.print("Adding new Location to database...");
						add();
					}
					
					clearFields();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
		add(btnAddLocation);
		
		populateComboBoxes();
	}
	
	/***
	 * This method populates the Manager combobox on locationsTable with data
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
			
			query = "SELECT DISTINCT siteManagerSSN_FK FROM Location WHERE siteManagerSSN_FK IS NOT NULL;";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			comboBoxManager.addItem(null);
			while (result.next()) {
				comboBoxManager.addItem(result.getString("siteManagerSSN_FK"));
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
	 * Results from Location table are populated in the locationsTable.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void search() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			parameterCount = 0;
			query = "SELECT `locationID` as `Location ID`, " +
					"`locationName` AS `Location Name`, " +
					"`address` AS `Address`, " +
					"`siteManagerSSN_FK` AS `Site Manager SSN` " +
					"FROM Location WHERE "; //Base query
			
			if (!textFieldLocationID.getText().isEmpty()) {
				addToQuery();
				query += "locationID ='" + textFieldLocationID.getText() + "'";
			}
			
			if (!textFieldLocationName.getText().isEmpty()) {
				addToQuery();
				query += "locationName ='" + textFieldLocationName.getText() + "'";
			}
			
			if (!textFieldCity.getText().isEmpty()) {
				addToQuery();
				query += "address LIKE '%" + textFieldCity.getText() + "%'";
			}
			
			if (comboBoxState.getSelectedItem() != null) {
				addToQuery();
				query += "address LIKE '%" + comboBoxState.getSelectedItem() + "%'";
			}
			
			if (!textFieldZIP.getText().isEmpty()) {
				addToQuery();
				query += "address LIKE '%" + textFieldZIP.getText() + "%'";
			}
			
			if (comboBoxManager.getSelectedItem() != null) {
				addToQuery();
				query += "siteManagerSSN_FK ='" + comboBoxManager.getSelectedItem().toString() +"'";
			}
			
			query += ";"; //end SQL statement
			
			if(parameterCount > 0) {
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				locationsTable.setModel(DbUtils.resultSetToTableModel(result));
			} else {
				JOptionPane.showMessageDialog(null, "No criteria selected.");
			}
			
			parameterCount = 0;
			
			locationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			EmployeeInterfaceFrame.resizeTableColumns(locationsTable);
			
			connection.close();
		} catch (Exception e) {
			System.out.println("Error: Invalid query.");
			e.printStackTrace();
		}
	}
	
	/**
	 * *
	 * This method makes the SQL query to add the a new row
	 * to the database's Location table.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void add() throws SQLException  {
		try {
			connection = SQLConnection.ConnectDb();
			query = "INSERT INTO Location (locationID, locationName, address, siteManagerSSN_FK)" +
					" VALUES (?, ?, ?, ?);";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, inputLocationID.getText());
			stmt.setString(2, inputLocationName.getText());
			stmt.setString(3, inputAddress.getText());
			stmt.setString(4, (String) inputManagerSSN.getSelectedItem());
			
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
	 * This method populates the fields of the Location object with the 
	 * current data to allow the user to edit the existing information.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void populateToUpdate() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = locationsTable.getSelectedRow();
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				query = "SELECT DISTINCT siteManagerSSN FROM SiteManager WHERE siteManagerSSN;";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				
				inputManagerSSN.addItem(null);
				while (result.next()) {
					inputManagerSSN.addItem(result.getString("siteManagerSSN"));
				}
				
				String locationID = (locationsTable.getModel().getValueAt(selectedRow, 0)).toString();
				query = "SELECT * FROM Location WHERE locationID='" + locationID + "'";
				stmt = connection.prepareStatement(query);
				result = stmt.executeQuery();
				
				if (result.next()) {
					inputLocationID.setText(result.getString("locationID"));
					inputLocationName.setText(result.getString("locationName"));
					inputAddress.setText(result.getString("address"));
					inputManagerSSN.setSelectedItem(result.getString("siteManagerSSN_FK"));
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
	 * database's Location table.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void update() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = locationsTable.getSelectedRow();
			String locationID = (locationsTable.getModel().getValueAt(selectedRow, 0)).toString();
			query = "UPDATE Location SET " +
					"locationID ='" + inputLocationID.getText() +
					"', locationName ='" + inputLocationName.getText() +
					"', address ='" + inputAddress.getText() +
					"', siteManagerSSN_FK ='" + inputManagerSSN.getSelectedItem() +
					"' WHERE locationID='" + locationID + "';";
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
	 * from the database's Location table.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void delete() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?",
				"Delete", JOptionPane.YES_NO_OPTION);
		
		if (confirmation == 0) {
			try {
				int selectedRow = locationsTable.getSelectedRow();
				String locationID = locationsTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				query = "DELETE FROM Location WHERE locationID='" + locationID + "';";
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
		inputLocationID.setText(null);
		inputLocationName.setText(null);
		inputAddress.setText(null);
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
	 * This method populates the comboboxes on the Add Location popup
	 * window, as a means of input validation.
	 *
	 * @throws SQLException the SQL exception
	 */

	@Override
	public void populateToAdd() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			
			query = "SELECT siteManagerSSN FROM SiteManager;";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			while (result.next()) {
				inputManagerSSN.addItem(result.getString("siteManagerSSN"));
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
