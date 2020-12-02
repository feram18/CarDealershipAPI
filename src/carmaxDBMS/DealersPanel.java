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

public class DealersPanel extends JPanel {
	private Connection connection = null;
	private JTable dealersTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldSSN;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldEmail;
	private JTextField textFieldPhoneNo;
	private JTextField textFieldCity;
	private JTextField textFieldState;
	private JTextField textFieldZIP;
	private JComboBox<String> comboBoxAssociateSSN;
	
	private JTextField inputSSN = new JTextField();
	private JTextField inputFirstName = new JTextField();
	private JTextField inputLastName = new JTextField();
	private JTextField inputEmail = new JTextField();
	private JTextField inputPhoneNumber = new JTextField();
	private JTextField inputAddress = new JTextField();
	private JComboBox<String> inputAssociateSSN = new JComboBox<String>();
	
	Object[] inputFields = {
			"SSN", inputSSN,
			"First Name", inputFirstName,
			"Last Name", inputLastName,
			"Email", inputEmail,
			"Phone Number", inputPhoneNumber,
			"Address", inputAddress,
			"Associate SSN", inputAssociateSSN
	};
	
	String[] addOptions = {"Add", "Cancel"};
	String[] updateOptions = {"Save Changes", "Cancel"};
	
	/**
	 * Create the panel.
	 */

	/**
	 * Create the panel.
	 */
	public DealersPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JScrollPane scrollPaneInventory = new JScrollPane();
		scrollPaneInventory.setBounds(220, 45, 630, 380);
		add(scrollPaneInventory);
		
		dealersTable = new JTable();
		dealersTable.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPaneInventory.setViewportView(dealersTable);
		
		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");
		 
		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);
		
		dealersTable.setComponentPopupMenu(popupMenu);
		
		menuItemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane updatePane = new JOptionPane();
				updatePane.setVisible(false);
				
				try {
					populateToUpdate();
					updatePane.setVisible(true);
					
					int choice = updatePane.showOptionDialog(null, inputFields, "Update Dealer", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);
					
					if(choice == 0) {
						System.out.println("Updating Dealer... ");
						updateDatabase();
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
		
		JLabel lblSSN = new JLabel("SSN");
		lblSSN.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSSN.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSSN.setBounds(10, 48, 83, 15);
		add(lblSSN);
		
		textFieldSSN = new JTextField();
		lblSSN.setLabelFor(textFieldSSN);
		textFieldSSN.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldSSN.setBounds(103, 45, 86, 20);
		add(textFieldSSN);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFirstName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblFirstName.setBounds(10, 79, 83, 15);
		add(lblFirstName);
		
		textFieldFirstName = new JTextField();
		lblFirstName.setLabelFor(textFieldFirstName);
		textFieldFirstName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldFirstName.setBounds(103, 76, 86, 20);
		add(textFieldFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLastName.setBounds(10, 111, 83, 15);
		add(lblLastName);
		
		textFieldLastName = new JTextField();
		lblLastName.setLabelFor(textFieldLastName);
		textFieldLastName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldLastName.setBounds(103, 108, 86, 20);
		add(textFieldLastName);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEmail.setBounds(10, 140, 83, 15);
		add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldEmail.setBounds(103, 137, 86, 20);
		add(textFieldEmail);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPhoneNumber.setFont(new Font("Arial", Font.PLAIN, 12));
		lblPhoneNumber.setBounds(0, 169, 93, 15);
		add(lblPhoneNumber);
		
		textFieldPhoneNo = new JTextField();
		textFieldPhoneNo.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldPhoneNo.setBounds(103, 166, 86, 20);
		add(textFieldPhoneNo);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCity.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCity.setBounds(10, 198, 83, 15);
		add(lblCity);
		
		textFieldCity = new JTextField();
		textFieldCity.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldCity.setBounds(103, 195, 86, 20);
		add(textFieldCity);
		
		JLabel lblState = new JLabel("State");
		lblState.setHorizontalAlignment(SwingConstants.TRAILING);
		lblState.setFont(new Font("Arial", Font.PLAIN, 12));
		lblState.setBounds(10, 227, 83, 15);
		add(lblState);
		
		textFieldState = new JTextField();
		textFieldState.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldState.setBounds(103, 224, 86, 20);
		add(textFieldState);
		
		JLabel lblZipCode = new JLabel("ZIP Code");
		lblZipCode.setHorizontalAlignment(SwingConstants.TRAILING);
		lblZipCode.setFont(new Font("Arial", Font.PLAIN, 12));
		lblZipCode.setBounds(10, 256, 83, 15);
		add(lblZipCode);
		
		textFieldZIP = new JTextField();
		textFieldZIP.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldZIP.setBounds(103, 253, 86, 20);
		add(textFieldZIP);
		
		JLabel lblAssociate = new JLabel("Associate");
		lblAssociate.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAssociate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblAssociate.setBounds(10, 285, 83, 15);
		add(lblAssociate);
		
		comboBoxAssociateSSN = new JComboBox<String>();
		comboBoxAssociateSSN.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxAssociateSSN.setBounds(103, 282, 86, 20);
		add(comboBoxAssociateSSN);
		
		JButton btnSearchDealers = new JButton("Search");
		btnSearchDealers.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSearchDealers.setBounds(68, 331, 77, 23);
		btnSearchDealers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					searchDealers();
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		});
		
		add(btnSearchDealers);
		
		JButton btnAddNewDealer = new JButton("Add New Dealer");
		btnAddNewDealer.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAddNewDealer.setBounds(460, 450, 128, 23);
		btnAddNewDealer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(JOptionPane.showOptionDialog(null, inputFields, "New Dealer", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, addOptions, null) == 0) {
						System.out.print("Adding new Dealer to database... ");
						addToDatabase();
					}
					
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
		
		add(btnAddNewDealer);
		
		populateComboBoxes();
	}
	
	/***
	 * This method populates the AssociateSSN combobox on dealersTable with data
	 * retrieved from SalesAssociate table, which serve as a filter for the user
	 * and as a means of input validation.
	 */
	
	private void populateComboBoxes() {
		try {
			connection = SQLConnection.ConnectDb();
			
			String query = "SELECT DISTINCT associateSSN FROM lramos6db.SalesAssociate WHERE associateSSN IS NOT NULL";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			comboBoxAssociateSSN.addItem(null);
			while(result.next() == true) {
				comboBoxAssociateSSN.addItem(result.getString("associateSSN"));
			}
		} catch (Exception e) {
			System.out.println("Error querying data for combobox");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method checks for the fields user entered data on and
	 * makes the SQL query with the parameters provided by the user.
	 * Results from Dealer table are populated in the dealersTable.
	 * @throws SQLException
	 */
	
	private void searchDealers() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int parameterCount = 0;
			String query = "SELECT * FROM lramos6db.Dealer WHERE "; //Base query
			
			if(!textFieldSSN.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "dealerSSN='" + textFieldSSN.getText() + "'";
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
			
			if(!textFieldEmail.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "email='" + textFieldEmail.getText() + "'";
			}
			
			if(!textFieldPhoneNo.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "phoneNo='" + textFieldPhoneNo.getText() + "'";
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
			
			if(comboBoxAssociateSSN.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "associateSSN_FK='" + comboBoxAssociateSSN.getSelectedItem().toString() +"'";
			}
			
			query += ";";
			
			if(parameterCount > 0) {
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				dealersTable.setModel(DbUtils.resultSetToTableModel(result));
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
	 * to the database's Dealer table.
	 * @throws SQLException
	 */

	private void addToDatabase() throws SQLException  {
		try {
			connection = SQLConnection.ConnectDb();
			String query = "INSERT INTO lramos6db.Client (dealerSSN, fName, lName, email, phoneNo, address, associateSSN_FK2)"
							+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, inputSSN.getText());
			stmt.setString(2, inputFirstName.getText());
			stmt.setString(3, inputLastName.getText());
			stmt.setString(5, inputEmail.getText());
			stmt.setString(6, inputPhoneNumber.getText());
			stmt.setString(7, inputAddress.getText());
			stmt.setString(8, (String) inputAssociateSSN.getSelectedItem());
			
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
	 * This method populates the fields of the Dealer object with the 
	 * current data to allow the user to edit the existing information.
	 * @throws SQLException
	 */
	private void populateToUpdate() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = dealersTable.getSelectedRow();
			if(selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				String query = "SELECT DISTINCT associateSSN FROM lramos6db.SalesAssociate WHERE associateSSN IS NOT NULL";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				
				while(result.next() == true) {
					inputAssociateSSN.addItem(result.getString("associateSSN"));
				}
				
				String SSN = (dealersTable.getModel().getValueAt(selectedRow, 0)).toString();
				query = "SELECT * FROM lramos6db.Dealer WHERE dealerSSN='" + SSN + "'";
				stmt = connection.prepareStatement(query);
				result = stmt.executeQuery();
							
				if(result.next() == true) {
					inputSSN.setText(result.getString("dealerSSN"));
					inputFirstName.setText(result.getString("fName"));
					inputLastName.setText(result.getString("lName"));
					inputEmail.setText(result.getString("email"));
					inputPhoneNumber.setText(result.getString("phoneNo"));
					inputAddress.setText(result.getString("address"));
					inputAssociateSSN.setSelectedItem(result.getString("associateSSN_FK"));
				}
			}
		} catch (Exception e) {
			System.out.print("Error retrieving data.");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method makes the SQL query to update the selected record in the
	 * database's Dealer table.
	 * @throws SQLException
	 */
	
	private void updateDatabase() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = dealersTable.getSelectedRow();
			String SSN = (dealersTable.getModel().getValueAt(selectedRow, 0)).toString();
			String query = "UPDATE lramos6db.Dealer SET " +
							"dealerSSN ='" + inputSSN.getText() +
							"', fName ='" + inputFirstName.getText() +
							"', lName ='" + inputLastName.getText() +
							"', email ='" + inputEmail.getText() +
							"', phoneNo ='" + inputPhoneNumber.getText() +
							"', address ='" + inputAddress.getText() +
							"', associateSSN_FK ='" + inputAssociateSSN.getSelectedItem() +
							"' WHERE dealerSSN ='" + SSN + "';";
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
	 * from the database's Dealer table.
	 * @throws SQLException
	 */
	
	private void deleteFromDatabase() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
		
		if(confirmation == 0) {
			try {
				int selectedRow = dealersTable.getSelectedRow();
				String SSN = dealersTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				String query = "DELETE FROM lramos6db.Dealer WHERE dealerSSN='" + SSN + "';";
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
		inputSSN.setText(null);
		inputFirstName.setText(null);
		inputLastName.setText(null);
		inputEmail.setText(null);
		inputPhoneNumber.setText(null);
		inputAddress.setText(null);
		inputAssociateSSN.removeAllItems();
	}
}