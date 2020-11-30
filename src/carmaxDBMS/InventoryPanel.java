package carmaxDBMS;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
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

public class InventoryPanel extends JPanel {
	private Connection connection = null;
	private JTable inventoryTable;
	//TODO - Make Jtable cells non editable
	//TODO - Change default column titles
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
	
	private JTextField inputVIN = new JTextField(); //TODO - populate fields Location, carBayID as comboBoxes
	private JTextField inputMake = new JTextField();
	private JTextField inputModel = new JTextField();
	private JTextField inputYear = new JTextField();
	private JTextField inputColor = new JTextField();
	private JTextField inputType = new JTextField();
	private JTextField inputTransmission = new JTextField();
	private JTextField inputMPG = new JTextField();
	private JTextField inputMileage = new JTextField();
	private JTextField inputPrice = new JTextField();
	private JTextField inputLocation = new JTextField();
	private JTextField inputCarBayID = new JTextField();
	
//	JTextFields to be passed to input dialog for adding, and updating a Vehicle
	Object[] inputFields = {
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
	
//	Button options for Add Vehicle dialog
	String[] addOptions = {"Add", "Cancel"};
	
//	Button options for Update Vehicle dialog 
	String[] updateOptions = {"Save Changes", "Cancel"}; 
	
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
		
		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");
		 
		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);
		
		inventoryTable.setComponentPopupMenu(popupMenu);
		
		menuItemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane updatePane = new JOptionPane();
				updatePane.setVisible(false);
				
				try {
					populateToUpdate();
					updatePane.setVisible(true);
					
					int choice = updatePane.showOptionDialog(null, inputFields, "Update Vehicle", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);
					
					if(choice == 0) {
						System.out.println("Updating Vehicle... ");
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
		
		JLabel lblSearchBy = new JLabel("Search by");
		lblSearchBy.setBounds(10, 11, 69, 18);
		lblSearchBy.setFont(new Font("Montserrat", Font.PLAIN, 14));
		add(lblSearchBy);
		
		JLabel lblVIN = new JLabel("VIN");
		lblVIN.setHorizontalAlignment(SwingConstants.TRAILING);
		lblVIN.setBounds(75, 48, 22, 15);
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
		lblMake.setBounds(68, 80, 29, 14);
		add(lblMake);
		
		comboBoxMake = new JComboBox<String>();
		comboBoxMake.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxMake.setBounds(107, 76, 86, 22);
		add(comboBoxMake);
		
		JLabel lblModel = new JLabel("Model");
		lblModel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblModel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblModel.setBounds(64, 113, 33, 14);
		add(lblModel);
		
		textFieldModel = new JTextField();
		textFieldModel.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldModel.setColumns(10);
		textFieldModel.setBounds(107, 109, 86, 20);
		add(textFieldModel);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setHorizontalAlignment(SwingConstants.TRAILING);
		lblYear.setFont(new Font("Arial", Font.PLAIN, 12));
		lblYear.setBounds(68, 146, 29, 14);
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
		labelColor.setBounds(64, 179, 33, 14);
		add(labelColor);
		
		comboBoxColor = new JComboBox<String>();
		comboBoxColor.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxColor.setBounds(107, 175, 86, 22);
		add(comboBoxColor);
		
		JLabel lblType = new JLabel("Type");
		lblType.setHorizontalAlignment(SwingConstants.TRAILING);
		lblType.setFont(new Font("Arial", Font.PLAIN, 12));
		lblType.setBounds(67, 212, 29, 14);
		add(lblType);
		
		comboBoxType = new JComboBox<String>();
		comboBoxType.setFont(new Font("Arial", Font.PLAIN, 12));
		lblType.setLabelFor(comboBoxType);
		comboBoxType.setBounds(106, 208, 86, 22);
		add(comboBoxType);
		
		JLabel lblTransmission = new JLabel("Transmission");
		lblTransmission.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTransmission.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTransmission.setBounds(19, 245, 77, 14);
		add(lblTransmission);
		
		comboBoxTransmission = new JComboBox<String>();
		comboBoxTransmission.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTransmission.setLabelFor(comboBoxTransmission);
		comboBoxTransmission.setBounds(106, 241, 86, 22);
		add(comboBoxTransmission);
		
		JLabel lblMPG = new JLabel("MPG");
		lblMPG.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMPG.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMPG.setBounds(67, 277, 29, 14);
		add(lblMPG);
		
		comboBoxMPG = new JComboBox<String>();
		comboBoxMPG.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMPG.setLabelFor(comboBoxMPG);
		comboBoxMPG.setBounds(106, 273, 87, 22);
		add(comboBoxMPG);
		
		JLabel lblMileage = new JLabel("Mileage");
		lblMileage.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMileage.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMileage.setBounds(50, 310, 46, 14);
		add(lblMileage);
		
		comboBoxMileage = new JComboBox<String>();
		comboBoxMileage.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMileage.setLabelFor(comboBoxMileage);
		comboBoxMileage.setBounds(106, 306, 86, 22);
		add(comboBoxMileage);
		
		JLabel lblMinPrice = new JLabel("Minimum Price");
		lblMinPrice.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMinPrice.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMinPrice.setBounds(10, 343, 86, 14);
		add(lblMinPrice);
		
		textFieldMinPrice = new JTextField();
		textFieldMinPrice.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMinPrice.setLabelFor(textFieldMinPrice);
		textFieldMinPrice.setBounds(106, 339, 86, 22);
		add(textFieldMinPrice);
		
		JLabel lblMaximumPrice = new JLabel("Maximum Price");
		lblMaximumPrice.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMaximumPrice.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMaximumPrice.setBounds(11, 376, 86, 14);
		add(lblMaximumPrice);
		
		textFieldMaxPrice = new JTextField();
		textFieldMaxPrice.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldMaxPrice.setBounds(107, 372, 86, 22);
		add(textFieldMaxPrice);
		
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLocation.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLocation.setBounds(50, 410, 47, 14);
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
					searchInventory();
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
					if(JOptionPane.showOptionDialog(null, inputFields, "Add Vehicle", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, addOptions, null) == 0) {
						System.out.print("Adding new Vehicle to database...");
						addToDatabase();
					}
					
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
	
	private void populateComboBoxes() {
		try {
			connection = SQLConnection.ConnectDb();

			String query = "SELECT DISTINCT make FROM lramos6db.Vehicle WHERE make IS NOT NULL";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			comboBoxMake.addItem(null);
			while(result.next() == true) {
				comboBoxMake.addItem(result.getString("make"));
			}
			
			query = "SELECT DISTINCT year FROM lramos6db.Vehicle WHERE year IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxYear.addItem(null);
			while(result.next() == true) {
				comboBoxYear.addItem(result.getString("year"));
			}
			
			query = "SELECT DISTINCT color FROM lramos6db.Vehicle WHERE color IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxColor.addItem(null);
			while(result.next() == true) {
				comboBoxColor.addItem(result.getString("color"));
			}
			
			query = "SELECT DISTINCT vehicleType FROM lramos6db.Vehicle WHERE vehicleType IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxType.addItem(null);
			while(result.next() == true) {
				comboBoxType.addItem(result.getString("vehicleType"));
			}
			
			query = "SELECT DISTINCT transmission FROM lramos6db.Vehicle WHERE transmission IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxTransmission.addItem(null);
			while(result.next() == true) {
				comboBoxTransmission.addItem(result.getString("transmission"));
			}
			
			comboBoxMPG.addItem(null);
			comboBoxMPG.addItem("> 10");
			comboBoxMPG.addItem("> 15");
			comboBoxMPG.addItem("> 20");
			comboBoxMPG.addItem("> 25");
			comboBoxMPG.addItem("> 30");
			comboBoxMPG.addItem("> 35");
			comboBoxMPG.addItem("> 40");
			comboBoxMPG.addItem("> 45");
			comboBoxMPG.addItem("> 50");
			
			comboBoxMileage.addItem(null);
			comboBoxMileage.addItem("< 10000");
			comboBoxMileage.addItem("< 20000");
			comboBoxMileage.addItem("< 30000");
			comboBoxMileage.addItem("< 40000");
			comboBoxMileage.addItem("< 50000");
			comboBoxMileage.addItem("< 60000");
			comboBoxMileage.addItem("< 70000");
			comboBoxMileage.addItem("< 80000");
			comboBoxMileage.addItem("< 90000");
			comboBoxMileage.addItem("< 100000");
			comboBoxMileage.addItem("< 110000");
			comboBoxMileage.addItem("< 120000");
			
			query = "SELECT DISTINCT location FROM lramos6db.Vehicle WHERE location IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxLocation.addItem(null);
			while(result.next() == true) {
				comboBoxLocation.addItem(result.getString("location"));
			}
			
		} catch (Exception e) {
			System.out.println("Error querying data for comboboxes");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method checks for the fields user entered data on and
	 * makes the SQL query with the parameters provided by the user.
	 * Results from Vehicle table are populated in the inventoryTable.
	 * @throws SQLException
	 */
	
	//TODO Refactor searchInventory() for optimization
	
	private void searchInventory() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int parameterCount = 0;
			String query = "SELECT * FROM lramos6db.Vehicle WHERE "; //Base query
			
			if(!textFieldVIN.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "VIN='" + textFieldVIN.getText() + "'";
			}
			
			if(comboBoxMake.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "make='" + comboBoxMake.getSelectedItem().toString() +"'";
			}
			
			if(!textFieldModel.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "model='" + textFieldModel.getText() + "'";
			}
			
			if(comboBoxYear.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "year='" + comboBoxYear.getSelectedItem().toString() + "'";
			}
			
			if(comboBoxColor.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "color='" + comboBoxColor.getSelectedItem().toString() + "'";
			}
			
			if(comboBoxType.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "vehicleType='" + comboBoxType.getSelectedItem().toString() + "'";
			}
			
			if(comboBoxTransmission.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "transmission='" + comboBoxTransmission.getSelectedItem().toString() + "'";
			}
			
			if(comboBoxMPG.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "MPG " + comboBoxMPG.getSelectedItem().toString();
			}
			
			if(comboBoxMileage.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "mileage " + comboBoxMileage.getSelectedItem().toString();
			}
			
			if(!textFieldMinPrice.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "price > " + textFieldMinPrice.getText().isEmpty();
			}
			
			if(!textFieldMaxPrice.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "price < " + textFieldMaxPrice.getText().isEmpty();
			}
			
			if(comboBoxLocation.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "location ='" + comboBoxLocation.getSelectedItem().toString() + "'";
			}
			
			query += ";";
			
			if(parameterCount > 0) {
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				inventoryTable.setModel(DbUtils.resultSetToTableModel(result));
				System.out.println(query);
			} else {
				JOptionPane.showMessageDialog(null, "No criteria selected.");
			}
			
			parameterCount = 0;
		} catch (Exception e) {
			System.out.println("Error: Invalid query.");
			e.printStackTrace();
		}
		
		connection.close();
	}
	
	/***
	 * This method makes the SQL query to add the a new row
	 * to the database's Vehicle table.
	 * @throws SQLException
	 */

	private void addToDatabase() throws SQLException  {
		try {
			connection = SQLConnection.ConnectDb();
			String query = "INSERT INTO lramos6db.Vehicle (VIN, make, model, year, color, vehicleType,"
					+ " transmission, location, MPG, mileage, price, carBayID_FK)"
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			stmt.setString(11, inputLocation.getText());
			stmt.setString(12, inputCarBayID.getText());
			
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
	 * This method populates the fields of the Vehicle object with the 
	 * current data to allow the user to edit the existing information.
	 * @throws SQLException
	 */
	private void populateToUpdate() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = inventoryTable.getSelectedRow();
			if(selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				String VIN = (inventoryTable.getModel().getValueAt(selectedRow, 0)).toString();
				String query = "SELECT * FROM lramos6db.Vehicle WHERE VIN='" + VIN + "'";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
							
				if(result.next() == true) {
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
					inputLocation.setText(result.getString("location"));
					inputCarBayID.setText(result.getString("carBayID_FK"));
					
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
	 * database's Vehicle table.
	 * @throws SQLException
	 */
	
	private void updateDatabase() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = inventoryTable.getSelectedRow();
			String VIN = (inventoryTable.getModel().getValueAt(selectedRow, 0)).toString();
			String query = "UPDATE lramos6db.Vehicle SET " +
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
							", carBayID_FK=" + inputCarBayID.getText() +
							" WHERE VIN='" + VIN + "';";
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
	 * from the database's Vehicle table.
	 * @throws SQLException
	 */
	
	private void deleteFromDatabase() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
		
		if(confirmation == 0) {
			try {
				int selectedRow = inventoryTable.getSelectedRow();
				String VIN = inventoryTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				String query = "DELETE FROM lramos6db.Vehicle WHERE VIN='" + VIN + "';";
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
		inputVIN = null;
		inputMake = null;
		inputModel = null;
		inputYear = null;
		inputColor = null;
		inputType = null;
		inputTransmission = null;
		inputMPG = null;
		inputMileage = null;
		inputPrice = null;
		inputLocation = null;
		inputCarBayID = null;
	}
}
