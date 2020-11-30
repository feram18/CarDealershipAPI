package carmaxDBMS;
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

public class LocationsPanel extends JPanel {
	private Connection connection = null;
	private JTable locationsTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldLocationID;
	private JTextField textFieldLocationName;
	private JTextField textFieldCity;
	private JTextField textFieldState;
	private JTextField textFieldZIP;
	private JComboBox<String> comboBoxManager;
	
	private JTextField inputLocationID = new JTextField();
	private JTextField inputLocationName = new JTextField();
	private JTextField inputAddress = new JTextField();
	private JTextField inputManagerSSN = new JTextField();
	
	Object[] inputFields = {
			"Location ID", inputLocationID,
			"Location Name", inputLocationName,
			"Address", inputAddress,
			"Manager SSN", inputManagerSSN
	};
	
	String[] addOptions = {"Add", "Cancel"};
	String[] updateOptions = {"Save Changes", "Cancel"};
	
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
		
		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");
		 
		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);
		
		locationsTable.setComponentPopupMenu(popupMenu);
		
		menuItemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane updatePane = new JOptionPane();
				updatePane.setVisible(false);
				
				try {
					populateToUpdate();
					updatePane.setVisible(true);
					
					int choice = updatePane.showOptionDialog(null, inputFields, "Update Location", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);
					
					if(choice == 0) {
						System.out.println("Updating Location... ");
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
		
		JLabel lblLocationID = new JLabel("Location ID");
		lblLocationID.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLocationID.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLocationID.setBounds(40, 48, 67, 15);
		add(lblLocationID);
		
		textFieldLocationID = new JTextField();
		lblLocationID.setLabelFor(textFieldLocationID);
		textFieldLocationID.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldLocationID.setBounds(117, 45, 86, 20);
		add(textFieldLocationID);
		
		JLabel lblLocationName = new JLabel("Location Name");
		lblLocationName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLocationName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLocationName.setBounds(20, 77, 87, 15);
		add(lblLocationName);
		
		textFieldLocationName = new JTextField();
		lblLocationName.setLabelFor(textFieldLocationName);
		textFieldLocationName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldLocationName.setBounds(117, 74, 86, 20);
		add(textFieldLocationName);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCity.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCity.setBounds(82, 106, 25, 15);
		add(lblCity);
		
		textFieldCity = new JTextField();
		lblCity.setLabelFor(textFieldCity);
		textFieldCity.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldCity.setBounds(117, 103, 86, 20);
		add(textFieldCity);
		
		JLabel lblState = new JLabel("State");
		lblState.setHorizontalAlignment(SwingConstants.TRAILING);
		lblState.setFont(new Font("Arial", Font.PLAIN, 12));
		lblState.setBounds(60, 135, 47, 15);
		add(lblState);
		
		textFieldState = new JTextField();
		lblState.setLabelFor(textFieldState);
		textFieldState.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldState.setBounds(117, 132, 86, 20);
		add(textFieldState);
		
		JLabel lblZipCode = new JLabel("ZIP Code");
		lblZipCode.setHorizontalAlignment(SwingConstants.TRAILING);
		lblZipCode.setFont(new Font("Arial", Font.PLAIN, 12));
		lblZipCode.setBounds(40, 164, 67, 15);
		add(lblZipCode);
		
		textFieldZIP = new JTextField();
		lblZipCode.setLabelFor(textFieldZIP);
		textFieldZIP.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldZIP.setBounds(117, 161, 86, 20);
		add(textFieldZIP);
		
		JLabel lblManager = new JLabel("Manager");
		lblManager.setHorizontalAlignment(SwingConstants.TRAILING);
		lblManager.setFont(new Font("Arial", Font.PLAIN, 12));
		lblManager.setBounds(20, 193, 87, 15);
		add(lblManager);
		
		comboBoxManager = new JComboBox();
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
					searchLocations();
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
					if(JOptionPane.showOptionDialog(null, inputFields, "Add Location", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, addOptions, null) == 0) {
						System.out.print("Adding new Location to database...");
						addToDatabase();
					}
					
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
	
	private void populateComboBoxes() {
		try {
			connection = SQLConnection.ConnectDb();
			
			String query = "SELECT DISTINCT siteManagerSSN_FK FROM lramos6db.Location WHERE siteManagerSSN_FK IS NOT NULL";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			comboBoxManager.addItem(null);
			while(result.next() == true) {
				comboBoxManager.addItem(result.getString("siteManagerSSN_FK"));
			}
		} catch (Exception e) {
			System.out.println("Error querying data for combobox");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method checks for the fields user entered data on and
	 * makes the SQL query with the parameters provided by the user.
	 * Results from Location table are populated in the locationsTable.
	 * @throws SQLException
	 */
	
	private void searchLocations() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int parameterCount = 0;
			String query = "SELECT * FROM lramos6db.Location WHERE "; //Base query
			
			if(!textFieldLocationID.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "locationID ='" + textFieldLocationID.getText() + "'";
			}
			
			if(!textFieldLocationName.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "locationName ='" + textFieldLocationName.getText() + "'";
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
				
				query += "siteManagerSSN_FK ='" + comboBoxManager.getSelectedItem().toString() +"'";
			}
			
			query += ";";
			
			if(parameterCount > 0) {
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				locationsTable.setModel(DbUtils.resultSetToTableModel(result));
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
	 * to the database's Location table.
	 * @throws SQLException
	 */
	
	private void addToDatabase() throws SQLException  {
		try {
			connection = SQLConnection.ConnectDb();
			String query = "INSERT INTO lramos6db.Location (locationID, locationName, address, siteManagerSSN_FK)"
							+ " values (?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, inputLocationID.getText());
			stmt.setString(2, inputLocationName.getText());
			stmt.setString(3, inputAddress.getText());
			stmt.setString(4, inputManagerSSN.getText());
			
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
	 * This method populates the fields of the Location object with the 
	 * current data to allow the user to edit the existing information.
	 * @throws SQLException
	 */
	private void populateToUpdate() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = locationsTable.getSelectedRow();
			if(selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				String locationID = (locationsTable.getModel().getValueAt(selectedRow, 0)).toString();
				String query = "SELECT * FROM lramos6db.Location WHERE locationID='" + locationID + "'";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
							
				if(result.next() == true) {
					inputLocationID.setText(result.getString("locationID"));
					inputLocationName.setText(result.getString("locationName"));
					inputAddress.setText(result.getString("address"));
					inputManagerSSN.setText(result.getString("siteManagerSSN_FK"));
				}
			}
		} catch (Exception e) {
			System.out.print("Error retrieving data.");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method makes the SQL query to update the selected record in the
	 * database's Location table.
	 * @throws SQLException
	 */
	
	private void updateDatabase() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = locationsTable.getSelectedRow();
			String locationID = (locationsTable.getModel().getValueAt(selectedRow, 0)).toString();
			String query = "UPDATE lramos6db.Location SET " +
					"locationID ='" + inputLocationID.getText() +
					"', locationName ='" + inputLocationName.getText() +
					"', address ='" + inputAddress.getText() +
					"', siteManagerSSN_FK ='" + inputManagerSSN.getText() +
					"' WHERE locationID='" + locationID + "';";
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
	 * from the database's Location table.
	 * @throws SQLException
	 */
	
	private void deleteFromDatabase() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
		
		if(confirmation == 0) {
			try {
				int selectedRow = locationsTable.getSelectedRow();
				String locationID = locationsTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				String query = "DELETE FROM lramos6db.Location WHERE locationID='" + locationID + "';";
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
		inputLocationID = null;
		inputLocationName = null;
		inputAddress = null;
		inputManagerSSN = null;
	}
}
