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

public class InventoryPanel extends JPanel {
	private Connection connection = null;
	private JTable inventoryTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldVIN;
	private JComboBox comboBoxMake;
	private JTextField textFieldModel;
	private JComboBox comboBoxYear;
	private JComboBox comboBoxColor;
	private JComboBox comboBoxType;
	private JComboBox comboBoxTransmission;
	private JComboBox<String> comboBoxMPG;
	private JComboBox<String> comboBoxMileage;
	private JTextField textFieldMinPrice;
	private JTextField textFieldMaxPrice;
	private JComboBox comboBoxLocation;

	/**
	 * Create the panel.
	 */
	
	public InventoryPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JScrollPane scrollPaneInventory = new JScrollPane();
		scrollPaneInventory.setBounds(220, 45, 630, 444);
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
		
		comboBoxMake = new JComboBox();
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
		
		comboBoxYear = new JComboBox();
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
		
		comboBoxColor = new JComboBox();
		comboBoxColor.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxColor.setBounds(107, 175, 86, 22);
		add(comboBoxColor);
		
		JLabel lblType = new JLabel("Type");
		lblType.setHorizontalAlignment(SwingConstants.TRAILING);
		lblType.setFont(new Font("Arial", Font.PLAIN, 12));
		lblType.setBounds(67, 212, 29, 14);
		add(lblType);
		
		comboBoxType = new JComboBox();
		comboBoxType.setFont(new Font("Arial", Font.PLAIN, 12));
		lblType.setLabelFor(comboBoxType);
		comboBoxType.setBounds(106, 208, 86, 22);
		add(comboBoxType);
		
		JLabel lblTransmission = new JLabel("Transmission");
		lblTransmission.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTransmission.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTransmission.setBounds(19, 245, 77, 14);
		add(lblTransmission);
		
		comboBoxTransmission = new JComboBox();
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
		
		comboBoxLocation = new JComboBox();
		lblLocation.setLabelFor(comboBoxLocation);
		comboBoxLocation.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxLocation.setBounds(107, 406, 86, 22);
		add(comboBoxLocation);
		
		JButton btnSearchInventory = new JButton("Search");
		btnSearchInventory.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSearchInventory.setBounds(65, 466, 77, 23);
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
			
			//TODO Update
			String query = "SELECT DISTINCT DNO FROM lramos6db.EMPLOYEE WHERE DNO IS NOT NULL";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			while(result.next() == true) {
				comboBoxMake.addItem(result.getString("DNO"));
			}
			
			//TODO Update
			query = "SELECT DISTINCT SUPERSSN FROM lramos6db.EMPLOYEE WHERE SUPERSSN IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxYear.addItem(null);
			while(result.next() == true) {
				comboBoxYear.addItem(result.getString("SUPERSSN"));
			}
			
			query = "SELECT DISTINCT color FROM lramos6db.Vehicle WHERE color IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			while(result.next() == true) {
				comboBoxColor.addItem(result.getString("color"));
			}
			
			query = "SELECT DISTINCT type FROM lramos6db.Vehicle WHERE type IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxType.addItem(null);
			while(result.next() == true) {
				comboBoxType.addItem(result.getString("type"));
			}
			
			query = "SELECT DISTINCT transmission FROM lramos6db.Vehicle WHERE transmission IS NOT NULL";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			comboBoxTransmission.addItem(null);
			while(result.next() == true) {
				comboBoxTransmission.addItem(result.getString("transmission"));
			}
			
			//TODO
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
			String query = "SELECT * FROM lramos6db.EMPLOYEE WHERE "; //Base query
			
			if(!textFieldVIN.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "SEX='" + textFieldVIN.getText() + "'";
			}
			
			if(comboBoxMake.getSelectedItem().toString() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "DNO='" + comboBoxMake.getSelectedItem().toString() +"'";
			}
			
			if(!textFieldModel.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "model='" + textFieldModel.getText() + "'";
			}
			
			if(comboBoxYear.getSelectedItem().toString() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "SUPERSSN='" + comboBoxYear.getSelectedItem().toString() + "'";
			}
			
			if(comboBoxColor.getSelectedItem().toString() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "color='" + comboBoxColor.getSelectedItem().toString() + "'";
			}
			
			if(comboBoxType.getSelectedItem().toString() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "type='" + comboBoxType.getSelectedItem().toString() + "'";
			}
			
			if(comboBoxTransmission.getSelectedItem().toString() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "transmission='" + comboBoxTransmission.getSelectedItem().toString() + "'";
			}
			
			if(comboBoxMPG.getSelectedItem().toString() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "MPG " + comboBoxMPG.getSelectedItem().toString();
			}
			
			if(comboBoxMileage.getSelectedItem().toString() != null) {
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
			
			if(comboBoxLocation.getSelectedItem().toString() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "location='" + comboBoxLocation.getSelectedItem().toString() + "'";
			}
			
			query += ";";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			inventoryTable.setModel(DbUtils.resultSetToTableModel(result));
			System.out.println(query);
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
					+ " transmission, features, location, MPG, mileage, price, carBayID)"
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
				String query = "SELECTED * FROM lramos6db.Vehicle WHERE VIN='" + VIN + "'";
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
	 * database's Vehicle table.
	 * @throws SQLException
	 */
	
	private void updateDatabase() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = inventoryTable.getSelectedRow();
			String VIN = (inventoryTable.getModel().getValueAt(selectedRow, 0)).toString();
			String query = "UPDATE lramos6db.Vehicle SET WHERE VIN='" + VIN + "';";
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
}
