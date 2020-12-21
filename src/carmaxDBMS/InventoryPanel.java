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

public class InventoryPanel extends JPanel implements GUIPanel {
	// Utility variables
	private Connection connection = null;
	private String query;
	private int parameterCount;
	
	// UI components to filter data when searching
	private JTable inventoryTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldVIN;
	private JComboBox<String> comboBoxMake;
	private JTextField textFieldModel;
	private JComboBox<String> comboBoxYear;
	private JComboBox<String> comboBoxColor;
	private JComboBox<String> comboBoxType;
	private JComboBox<String> comboBoxTransmission;
	private JComboBox<String> comboBoxMPG;
	private JComboBox<String> comboBoxMileage;
	private JTextField textFieldMinPrice;
	private JTextField textFieldMaxPrice;
	private JComboBox<String> comboBoxLocation;
	
	// UI components to be shown when updating, or adding a new record to database
	private JTextField inputVIN = new JTextField();
	private JTextField inputMake = new JTextField();
	private JTextField inputModel = new JTextField();
	private JTextField inputYear = new JTextField();
	private JTextField inputColor = new JTextField();
	private JTextField inputType = new JTextField();
	private JTextField inputTransmission = new JTextField();
	private JTextField inputMPG = new JTextField();
	private JTextField inputMileage = new JTextField();
	private JTextField inputPrice = new JTextField();
	private JComboBox<String> inputLocation = new JComboBox<String>();
	private JComboBox<String> inputCarBayID = new JComboBox<String>();
	
	// Array to populate comboBoxMPG
	private final String[] MPG = {null, "> 10", "> 15", "> 20", "> 25", "> 30", "> 35",
					"> 40", "> 45", "> 50"};
	
	// Array to populate comboBoxMileage
	private final String[] Mileage = {null, "< 10000", "< 20000", "< 30000", "< 40000",
						"< 50000", "< 60000", "< 70000", "< 80000", "< 90000",
						"< 100000", "< 110000", "< 110000", "< 120000"};
	

	// Arrays to be passed into JOptionPane's
	private final Object[] inputFields = {
			"VIN", inputVIN,
			"Make", inputMake,
			"Model", inputModel,
			"Year", inputYear,
			"Color", inputColor,
			"Type", inputType,
			"Transmission", inputTransmission,
			"MPG", inputMPG,
			"Mileage", inputMileage,
			"Price", inputPrice,
			"Location", inputLocation,
			"Car Bay ID", inputCarBayID
	};
	private final String[] addOptions = {"Add", "Cancel"};
	private final String[] updateOptions = {"Save Changes", "Cancel"}; 
	
	/**
	 * Create the panel.
	 */
	public InventoryPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JScrollPane scrollPaneInventory = new JScrollPane();
		scrollPaneInventory.setBounds(220, 45, 630, 380);
		add(scrollPaneInventory);
		
		inventoryTable = new JTable();
		inventoryTable.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPaneInventory.setViewportView(inventoryTable);
		inventoryTable.setEnabled(false);
		
		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");
		 
		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);
		
		inventoryTable.setComponentPopupMenu(popupMenu);
		
		menuItemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				try {
					populateToUpdate();
					
					int choice = JOptionPane.showOptionDialog(null, inputFields, "Update Vehicle",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);
					
					if(choice == 0) {
						System.out.println("Updating Vehicle... ");
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
		
		JLabel lblSearchBy = new JLabel("Search by");
		lblSearchBy.setBounds(10, 11, 69, 18);
		lblSearchBy.setFont(new Font("Montserrat", Font.PLAIN, 14));
		add(lblSearchBy);
		
		JLabel lblVIN = new JLabel("VIN");
		lblVIN.setHorizontalAlignment(SwingConstants.TRAILING);
		lblVIN.setBounds(34, 48, 63, 15);
		lblVIN.setFont(new Font("Arial", Font.PLAIN, 12));
		add(lblVIN);
		
		textFieldVIN = new JTextField();
		textFieldVIN.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldVIN.setBounds(107, 45, 86, 20);
		add(textFieldVIN);
		
		JLabel lblMake = new JLabel("Make");
		lblMake.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMake.setLabelFor(lblMake);
		lblMake.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMake.setBounds(34, 80, 63, 14);
		add(lblMake);
		
		comboBoxMake = new JComboBox<String>();
		comboBoxMake.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxMake.setBounds(107, 76, 86, 22);
		add(comboBoxMake);
		
		JLabel lblModel = new JLabel("Model");
		lblModel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblModel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblModel.setBounds(34, 113, 63, 14);
		add(lblModel);
		
		textFieldModel = new JTextField();
		textFieldModel.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldModel.setColumns(10);
		textFieldModel.setBounds(107, 109, 86, 20);
		add(textFieldModel);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setHorizontalAlignment(SwingConstants.TRAILING);
		lblYear.setFont(new Font("Arial", Font.PLAIN, 12));
		lblYear.setBounds(34, 146, 63, 14);
		add(lblYear);
		
		comboBoxYear = new JComboBox<String>();
		comboBoxYear.setFont(new Font("Arial", Font.PLAIN, 12));
		lblYear.setLabelFor(comboBoxYear);
		comboBoxYear.setBounds(107, 142, 86, 22);
		add(comboBoxYear);
		
		JLabel labelColor = new JLabel("Color");
		labelColor.setHorizontalAlignment(SwingConstants.TRAILING);
		labelColor.setLabelFor(comboBoxColor);
		labelColor.setFont(new Font("Arial", Font.PLAIN, 12));
		labelColor.setBounds(34, 179, 63, 14);
		add(labelColor);
		
		comboBoxColor = new JComboBox<String>();
		comboBoxColor.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxColor.setBounds(107, 175, 86, 22);
		add(comboBoxColor);
		
		JLabel lblType = new JLabel("Type");
		lblType.setHorizontalAlignment(SwingConstants.TRAILING);
		lblType.setFont(new Font("Arial", Font.PLAIN, 12));
		lblType.setBounds(34, 212, 62, 14);
		add(lblType);
		
		comboBoxType = new JComboBox<String>();
		comboBoxType.setFont(new Font("Arial", Font.PLAIN, 12));
		lblType.setLabelFor(comboBoxType);
		comboBoxType.setBounds(106, 208, 86, 22);
		add(comboBoxType);
		
		JLabel lblTransmission = new JLabel("Transmission");
		lblTransmission.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTransmission.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTransmission.setBounds(0, 245, 96, 14);
		add(lblTransmission);
		
		comboBoxTransmission = new JComboBox<String>();
		comboBoxTransmission.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTransmission.setLabelFor(comboBoxTransmission);
		comboBoxTransmission.setBounds(106, 241, 86, 22);
		add(comboBoxTransmission);
		
		JLabel lblMPG = new JLabel("MPG");
		lblMPG.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMPG.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMPG.setBounds(34, 277, 62, 14);
		add(lblMPG);
		
		comboBoxMPG = new JComboBox<String>(MPG);
		comboBoxMPG.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMPG.setLabelFor(comboBoxMPG);
		comboBoxMPG.setBounds(106, 273, 87, 22);
		add(comboBoxMPG);
		
		JLabel lblMileage = new JLabel("Mileage");
		lblMileage.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMileage.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMileage.setBounds(34, 310, 62, 14);
		add(lblMileage);
		
		comboBoxMileage = new JComboBox<String>(Mileage);
		comboBoxMileage.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMileage.setLabelFor(comboBoxMileage);
		comboBoxMileage.setBounds(106, 306, 86, 22);
		add(comboBoxMileage);
		
		JLabel lblMinPrice = new JLabel("Minimum Price");
		lblMinPrice.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMinPrice.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMinPrice.setBounds(0, 343, 96, 14);
		add(lblMinPrice);
		
		textFieldMinPrice = new JTextField();
		textFieldMinPrice.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMinPrice.setLabelFor(textFieldMinPrice);
		textFieldMinPrice.setBounds(106, 339, 86, 22);
		add(textFieldMinPrice);
		
		JLabel lblMaximumPrice = new JLabel("Maximum Price");
		lblMaximumPrice.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMaximumPrice.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMaximumPrice.setBounds(0, 376, 97, 14);
		add(lblMaximumPrice);
		
		textFieldMaxPrice = new JTextField();
		textFieldMaxPrice.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldMaxPrice.setBounds(107, 372, 86, 22);
		add(textFieldMaxPrice);
		
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLocation.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLocation.setBounds(19, 410, 78, 14);
		add(lblLocation);
		
		comboBoxLocation = new JComboBox<String>();
		lblLocation.setLabelFor(comboBoxLocation);
		comboBoxLocation.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxLocation.setBounds(107, 406, 86, 22);
		add(comboBoxLocation);
		
		JButton btnSearchInventory = new JButton("Search");
		btnSearchInventory.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSearchInventory.setBounds(75, 450, 77, 23);
		btnSearchInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					search();
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		});
		
		add(btnSearchInventory);
		
		JButton btnAddVehicle = new JButton("Add New Vehicle");
		btnAddVehicle.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAddVehicle.setBounds(460, 450, 128, 23);
		btnAddVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					populateToAdd();
					
					if(JOptionPane.showOptionDialog(null, inputFields, "Add Vehicle", 
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
							null, addOptions, null) == 0) {
						System.out.print("Adding new Vehicle to database...");
						add();
					}
					
					clearFields();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
		
		add(btnAddVehicle);
		
		populateComboBoxes();
	}
	
	/***
	 * This method populates the comboboxes on inventoryTable with data
	 * retrieved from Vehicle table, which serve as a filter for the user
	 * and as a means of input validation.
	 */
	
	@Override
	public void populateComboBoxes() {
		// Delete current options
		comboBoxMake.removeAllItems();
		comboBoxYear.removeAllItems();
		comboBoxColor.removeAllItems();
		comboBoxType.removeAllItems();
		comboBoxTransmission.removeAllItems();
		comboBoxLocation.removeAllItems();
												
		// Populate with updated options
		try {
			connection = SQLConnection.ConnectDb();

			query = "SELECT DISTINCT make FROM Vehicle WHERE make IS NOT NULL ORDER BY make ASC";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			comboBoxMake.addItem(null);
			while (result.next()) {
				comboBoxMake.addItem(result.getString("make"));
			}
			
			query = "SELECT DISTINCT year FROM Vehicle WHERE year IS NOT NULL ORDER BY year DESC";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxYear.addItem(null);
			while (result.next()) {
				comboBoxYear.addItem(result.getString("year"));
			}
			
			query = "SELECT DISTINCT color FROM Vehicle WHERE color IS NOT NULL ORDER BY color ASC";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxColor.addItem(null);
			while (result.next()) {
				comboBoxColor.addItem(result.getString("color"));
			}
			
			query = "SELECT DISTINCT vehicleType FROM Vehicle WHERE vehicleType IS NOT NULL ORDER BY vehicleType ASC";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxType.addItem(null);
			while (result.next()) {
				comboBoxType.addItem(result.getString("vehicleType"));
			}
			
			query = "SELECT DISTINCT transmission FROM Vehicle WHERE transmission IS NOT NULL ORDER BY transmission ASC";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxTransmission.addItem(null);
			while (result.next()) {
				comboBoxTransmission.addItem(result.getString("transmission"));
			}
			
			query = "SELECT DISTINCT location FROM Vehicle WHERE location IS NOT NULL ORDER BY location ASC";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxLocation.addItem(null);
			while (result.next()) {
				comboBoxLocation.addItem(result.getString("location"));
			}
			
			connection.close();
		} catch (Exception e) {
			System.out.println("Error querying data for comboboxes");
			e.printStackTrace();
		}
	}
	
	/**
	 * *
	 * This method checks for the fields user entered data on and
	 * makes the SQL query with the parameters provided by the user.
	 * Results from Vehicle table are populated in the inventoryTable.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void search() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			parameterCount = 0;
			query = "SELECT VIN, " +
					"`make` AS `Make`, " +
					"`model` AS `Model`, " +
					"`year` AS `Year`, " +
					"`color` AS `Color`, " +
					"`vehicleType` AS `Vehicle Type`, " +
					"`transmission` AS `Transmission`, " +
					"MPG, " +
					"`mileage` AS `Mileage`, " +
					"`price` AS `Price`, " +
					"`location` AS `Location`, " +
					"`carBayID_FK` AS `Car Bay ID` " +
					"FROM Vehicle WHERE "; //Base query
			
			if (!textFieldVIN.getText().isEmpty()) {
				addToQuery();
				query += "VIN='" + textFieldVIN.getText() + "'";
			}
			
			if (comboBoxMake.getSelectedItem() != null) {
				addToQuery();
				query += "make='" + comboBoxMake.getSelectedItem().toString() +"'";
			}
			
			if (!textFieldModel.getText().isEmpty()) {
				addToQuery();
				query += "model='" + textFieldModel.getText() + "'";
			}
			
			if (comboBoxYear.getSelectedItem() != null) {
				addToQuery();
				query += "year='" + comboBoxYear.getSelectedItem().toString() + "'";
			}
			
			if (comboBoxColor.getSelectedItem() != null) {
				addToQuery();
				query += "color='" + comboBoxColor.getSelectedItem().toString() + "'";
			}
			
			if (comboBoxType.getSelectedItem() != null) {
				addToQuery();
				query += "vehicleType='" + comboBoxType.getSelectedItem().toString() + "'";
			}
			
			if (comboBoxTransmission.getSelectedItem() != null) {
				addToQuery();
				query += "transmission='" + comboBoxTransmission.getSelectedItem().toString() + "'";
			}
			
			if (comboBoxMPG.getSelectedItem() != null) {
				addToQuery();
				query += "MPG " + comboBoxMPG.getSelectedItem().toString();
			}
			
			if (comboBoxMileage.getSelectedItem() != null) {
				addToQuery();
				query += "mileage " + comboBoxMileage.getSelectedItem().toString();
			}
			
			if (!textFieldMinPrice.getText().isEmpty()) {
				addToQuery();
				query += "price > " + textFieldMinPrice.getText();
			}
			
			if (!textFieldMaxPrice.getText().isEmpty()) {
				addToQuery();
				query += "price < " + textFieldMaxPrice.getText();
			}
			
			if(comboBoxLocation.getSelectedItem() != null) {
				addToQuery();
				query += "location ='" + comboBoxLocation.getSelectedItem().toString() + "'";
			}
			
			query += ";";
			
			if(parameterCount > 0) {
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				inventoryTable.setModel(DbUtils.resultSetToTableModel(result));
			} else {
				JOptionPane.showMessageDialog(null, "No criteria selected.");
			}
			
			parameterCount = 0;
			
			inventoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			EmployeeInterfaceFrame.resizeTableColumns(inventoryTable);
			
			connection.close();
		} catch (Exception e) {
			System.out.println("Error: Invalid query.");
			e.printStackTrace();
		}
		
		connection.close();
	}
	
	/**
	 * *
	 * This method makes the SQL query to add the a new row
	 * to the database's Vehicle table.
	 *
	 * @throws SQLException the SQL exception
	 */

	@Override
	public void add() throws SQLException  {
		try {
			connection = SQLConnection.ConnectDb();
			query = "INSERT INTO Vehicle (VIN, make, model, year, color, vehicleType," +
					" transmission, MPG, mileage, price, location, carBayID_FK)" +
					" values (?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, inputVIN.getText());
			stmt.setString(2, inputMake.getText());
			stmt.setString(3, inputModel.getText());
			stmt.setString(4, inputYear.getText());
			stmt.setString(5, inputColor.getText());
			stmt.setString(6, inputType.getText());
			stmt.setString(7, inputTransmission.getText());
			stmt.setString(8, inputMPG.getText());
			stmt.setString(9, inputMileage.getText());
			stmt.setString(10, inputPrice.getText());
			stmt.setString(11, (String) inputLocation.getSelectedItem());
			stmt.setString(12, (String) inputCarBayID.getSelectedItem());
			
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
	 * This method populates the fields of the Vehicle object with the 
	 * current data to allow the user to edit the existing information.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void populateToUpdate() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = inventoryTable.getSelectedRow();
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				query = "SELECT carBayID FROM CarBay ORDER BY carBayID ASC";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				
				while(result.next()) {
					inputCarBayID.addItem(result.getString("carBayID"));
				}
				
				query = "SELECT locationName FROM Location ORDER BY locationName ASC";
				stmt = connection.prepareStatement(query);
				result = stmt.executeQuery();
				
				while(result.next()) {
					inputLocation.addItem(result.getString("locationName"));
				}
				
				String VIN = (inventoryTable.getModel().getValueAt(selectedRow, 0)).toString();
				query = "SELECT * FROM Vehicle WHERE VIN='" + VIN + "'";
				stmt = connection.prepareStatement(query);
				result = stmt.executeQuery();
							
				if (result.next()) {
					inputVIN.setText(result.getString("VIN"));
					inputMake.setText(result.getString("make"));
					inputModel.setText(result.getString("model"));
					inputYear.setText(result.getString("year"));
					inputColor.setText(result.getString("color"));
					inputType.setText(result.getString("vehicleType"));
					inputTransmission.setText(result.getString("transmission"));
					inputMPG.setText(result.getString("MPG"));
					inputMileage.setText(result.getString("mileage"));
					inputPrice.setText(result.getString("price"));
					inputLocation.setSelectedItem(result.getString("location"));
					inputCarBayID.setSelectedItem(result.getString("carBayID_FK"));
					
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
	
	/**
	 * *
	 * This method makes the SQL query to update the selected record in the
	 * database's Vehicle table.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void update() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = inventoryTable.getSelectedRow();
			String VIN = (inventoryTable.getModel().getValueAt(selectedRow, 0)).toString();
			query = "UPDATE Vehicle SET " +
					"VIN ='" + inputVIN.getText() +
					"', make='" + inputMake.getText() +
					"', model='" + inputModel.getText() +
					"', year='" + inputYear.getText() +
					"', color='" + inputColor.getText() +
					"', vehicleType='" + inputType.getText() +
					"', transmission='" + inputTransmission.getText() +
					"', MPG='" + inputMPG.getText() +
					"', mileage=" + inputMileage.getText() +
					", price=" + inputPrice.getText() +
					", carBayID_FK=" + inputCarBayID.getSelectedItem() +
					" WHERE VIN='" + VIN + "';";
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
	 * from the database's Vehicle table.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void delete() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
		
		if (confirmation == 0) {
			try {
				int selectedRow = inventoryTable.getSelectedRow();
				String VIN = inventoryTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				query = "DELETE FROM Vehicle WHERE VIN='" + VIN + "';";
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
		inputVIN.setText(null);
		inputMake.setText(null);
		inputModel.setText(null);
		inputYear.setText(null);
		inputColor.setText(null);
		inputType.setText(null);
		inputTransmission.setText(null);
		inputMPG.setText(null);
		inputMileage.setText(null);
		inputPrice.setText(null);
		inputLocation.removeAllItems();
		inputCarBayID.removeAllItems();
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
	 * This method populates the comboboxes on the Add Vehicle popup
	 * window, as a means of input validation.
	 *
	 * @throws SQLException the SQL exception
	 */

	@Override
	public void populateToAdd() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			
			query = "SELECT DISTINCT locationName FROM Location WHERE locationName IS NOT NULL;";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			while (result.next()) {
				inputLocation.addItem(result.getString("locationName"));
			}
			
			query = "SELECT carBayID FROM CarBay;";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			while (result.next()) {
				inputCarBayID.addItem(result.getString("carBayID"));
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
