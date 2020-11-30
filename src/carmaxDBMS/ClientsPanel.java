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

import net.proteanit.sql.DbUtils;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class ClientsPanel extends JPanel {
	private Connection connection = null;
	private JTable clientsTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldSSN;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldSex;
	private JTextField textFieldEmail;
	private JTextField textFieldPhoneNo;
	private JTextField textFieldCity;
	private JTextField textFieldState;
	private JTextField textFieldZIP;
	private JComboBox<String> comboBoxAssociate;
	
	private JTextField inputSSN = new JTextField();
	private JTextField inputFirstName = new JTextField();
	private JTextField inputLastName = new JTextField();
	private JTextField inputSex = new JTextField();
	private JTextField inputEmail = new JTextField();
	private JTextField inputPhoneNumber = new JTextField();
	private JTextField inputAddress = new JTextField();
	private JTextField inputAssociateSSN = new JTextField();
	private JTextField inputMinimumPrice = new JTextField();
	private JTextField inputMaximumPrice = new JTextField();
	
	Object[] inputFields = {
			"SSN", inputSSN,
			"First Name", inputFirstName,
			"Last Name", inputLastName,
			"Sex", inputSex,
			"Email", inputEmail,
			"Phone Number", inputPhoneNumber,
			"Address", inputAddress,
			"Associate SSN", inputAssociateSSN,
			"Minimum Price", inputMinimumPrice,
			"Maximum Price", inputMaximumPrice
	};
	
	String[] addOptions = {"Add", "Cancel"};
	String[] updateOptions = {"Save Changes", "Cancel"};
	
	/**
	 * Create the panel.
	 */
	public ClientsPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JScrollPane scrollPaneInventory = new JScrollPane();
		scrollPaneInventory.setBounds(220, 45, 630, 380);
		add(scrollPaneInventory);
		
		clientsTable = new JTable();
		clientsTable.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPaneInventory.setViewportView(clientsTable);
		
		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");
		 
		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);
		
		clientsTable.setComponentPopupMenu(popupMenu);
		
		menuItemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane updatePane = new JOptionPane();
				updatePane.setVisible(false);
				
				try {
					populateToUpdate();
					updatePane.setVisible(true);
					
					int choice = updatePane.showOptionDialog(null, inputFields, "Update Client", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);
					
					if(choice == 0) {
						System.out.println("Updating Client... ");
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
		
		JLabel lblSSN = new JLabel("SSN");
		lblSSN.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSSN.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSSN.setBounds(68, 48, 25, 15);
		add(lblSSN);
		
		textFieldSSN = new JTextField();
		lblSSN.setLabelFor(textFieldSSN);
		textFieldSSN.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldSSN.setBounds(103, 45, 86, 20);
		add(textFieldSSN);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFirstName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblFirstName.setBounds(32, 79, 61, 15);
		add(lblFirstName);
		
		textFieldFirstName = new JTextField();
		lblFirstName.setLabelFor(textFieldFirstName);
		textFieldFirstName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldFirstName.setBounds(103, 76, 86, 20);
		add(textFieldFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLastName.setBounds(32, 111, 61, 15);
		add(lblLastName);
		
		textFieldLastName = new JTextField();
		lblLastName.setLabelFor(textFieldLastName);
		textFieldLastName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldLastName.setBounds(103, 108, 86, 20);
		add(textFieldLastName);
		
		JLabel lblSex = new JLabel("Sex");
		lblSex.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSex.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSex.setBounds(32, 140, 61, 15);
		add(lblSex);
		
		textFieldSex = new JTextField();
		textFieldSex.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldSex.setBounds(103, 137, 86, 20);
		add(textFieldSex);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEmail.setBounds(32, 169, 61, 15);
		add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldEmail.setBounds(103, 166, 86, 20);
		add(textFieldEmail);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPhoneNumber.setFont(new Font("Arial", Font.PLAIN, 12));
		lblPhoneNumber.setBounds(0, 198, 93, 15);
		add(lblPhoneNumber);
		
		textFieldPhoneNo = new JTextField();
		textFieldPhoneNo.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldPhoneNo.setBounds(103, 195, 86, 20);
		add(textFieldPhoneNo);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCity.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCity.setBounds(32, 227, 61, 15);
		add(lblCity);
		
		textFieldCity = new JTextField();
		textFieldCity.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldCity.setBounds(103, 224, 86, 20);
		add(textFieldCity);
		
		JLabel lblState = new JLabel("State");
		lblState.setHorizontalAlignment(SwingConstants.TRAILING);
		lblState.setFont(new Font("Arial", Font.PLAIN, 12));
		lblState.setBounds(32, 256, 61, 15);
		add(lblState);
		
		textFieldState = new JTextField();
		textFieldState.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldState.setBounds(103, 253, 86, 20);
		add(textFieldState);
		
		JLabel lblZipCode = new JLabel("ZIP Code");
		lblZipCode.setHorizontalAlignment(SwingConstants.TRAILING);
		lblZipCode.setFont(new Font("Arial", Font.PLAIN, 12));
		lblZipCode.setBounds(32, 285, 61, 15);
		add(lblZipCode);
		
		textFieldZIP = new JTextField();
		textFieldZIP.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldZIP.setBounds(103, 282, 86, 20);
		add(textFieldZIP);
		
		JLabel lblAssociate = new JLabel("Associate");
		lblAssociate.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAssociate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblAssociate.setBounds(10, 314, 83, 15);
		add(lblAssociate);
		
		comboBoxAssociate = new JComboBox();
		comboBoxAssociate.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxAssociate.setBounds(103, 311, 86, 20);
		add(comboBoxAssociate);
		
		JButton btnSearchClients = new JButton("Search");
		btnSearchClients.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSearchClients.setBounds(68, 360, 77, 23);
		btnSearchClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					searchClients();
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		});
		
		add(btnSearchClients);
		
		JButton btnAddNewClient = new JButton("Add New Client");
		btnAddNewClient.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAddNewClient.setBounds(460, 450, 128, 23);
		btnAddNewClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(JOptionPane.showOptionDialog(null, inputFields, "Add Client", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, addOptions, null) == 0) {
						System.out.print("Adding new Client to database...");
						addToDatabase();
					}
					
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
		
		add(btnAddNewClient);
		
		populateComboBoxes();
	}
	
	/***
	 * This method populates the AssociateSSN combobox on clientsTable with data
	 * retrieved from SalesAssociate table, which serve as a filter for the user
	 * and as a means of input validation.
	 */
	
	private void populateComboBoxes() {
		try {
			connection = SQLConnection.ConnectDb();
			
			String query = "SELECT DISTINCT associateSSN FROM lramos6db.SalesAssociate WHERE associateSSN IS NOT NULL";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			comboBoxAssociate.addItem(null);
			while(result.next() == true) {
				comboBoxAssociate.addItem(result.getString("associateSSN"));
			}
		} catch (Exception e) {
			System.out.println("Error querying data for combobox");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method checks for the fields user entered data on and
	 * makes the SQL query with the parameters provided by the user.
	 * Results from Client table are populated in the clientsTable.
	 * @throws SQLException
	 */
	
	private void searchClients() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int parameterCount = 0;
			String query = "SELECT * FROM lramos6db.Client WHERE "; //Base query
			
			if(!textFieldSSN.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "clientSSN='" + textFieldSSN.getText() + "'";
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
			
			if(!textFieldSex.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "sex='" + textFieldSex.getText() + "'";
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
			
			if(comboBoxAssociate.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "associateSSN_FK2='" + comboBoxAssociate.getSelectedItem().toString() +"'";
			}
			
			query += ";";
			
			if(parameterCount > 0) {
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				clientsTable.setModel(DbUtils.resultSetToTableModel(result));
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
	 * to the database's Client table.
	 * @throws SQLException
	 */

	private void addToDatabase() throws SQLException  {
		try {
			connection = SQLConnection.ConnectDb();
			String query = "INSERT INTO lramos6db.Client (clientSSN, fName, lName, sex, email, phoneNo, address,"
							+ " associateSSN_FK2, minimumPrice, maximumPrice)"
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, inputSSN.getText());
			stmt.setString(2, inputFirstName.getText());
			stmt.setString(3, inputLastName.getText());
			stmt.setString(4, inputSex.getText());
			stmt.setString(5, inputEmail.getText());
			stmt.setString(6, inputPhoneNumber.getText());
			stmt.setString(7, inputAddress.getText());
			stmt.setString(8, inputAssociateSSN.getText());
			stmt.setString(9, inputMinimumPrice.getText());
			stmt.setString(10, inputMaximumPrice.getText());
			
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
			int selectedRow = clientsTable.getSelectedRow();
			if(selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				String SSN = (clientsTable.getModel().getValueAt(selectedRow, 0)).toString();
				String query = "SELECT * FROM lramos6db.Client WHERE clientSSN='" + SSN + "'";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
							
				if(result.next() == true) {
					inputSSN.setText(result.getString("clientSSN"));
					inputFirstName.setText(result.getString("fName"));
					inputLastName.setText(result.getString("lName"));
					inputSex.setText(result.getString("sex"));
					inputEmail.setText(result.getString("email"));
					inputPhoneNumber.setText(result.getString("phoneNo"));
					inputAddress.setText(result.getString("address"));
					inputAssociateSSN.setText(result.getString("associateSSN_FK2"));
					inputMinimumPrice.setText(result.getString("minimumPrice"));
					inputMaximumPrice.setText(result.getString("maximumPrice"));
				}
			}
		} catch (Exception e) {
			System.out.print("Error retrieving data.");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method makes the SQL query to update the selected record in the
	 * database's Client table.
	 * @throws SQLException
	 */
	
	private void updateDatabase() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = clientsTable.getSelectedRow();
			String SSN = (clientsTable.getModel().getValueAt(selectedRow, 0)).toString();
			String query = "UPDATE lramos6db.Client SET " +
							"clientSSN ='" + inputSSN.getText() +
							"', fName ='" + inputFirstName.getText() +
							"', lName ='" + inputLastName.getText() +
							"', sex ='" + inputSex.getText() +
							"', email ='" + inputEmail.getText() +
							"', phoneNo ='" + inputPhoneNumber.getText() +
							"', address ='" + inputAddress.getText() +
							"', associateSSN_FK2 ='" + inputAssociateSSN.getText() +
							"', minimumPrice ='" + inputMinimumPrice.getText() +
							"', maximumPrice ='" + inputMaximumPrice.getText() +
							"' WHERE clientSSN='" + SSN + "';";
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
	 * from the database's Client table.
	 * @throws SQLException
	 */
	
	private void deleteFromDatabase() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
		
		if(confirmation == 0) {
			try {
				int selectedRow = clientsTable.getSelectedRow();
				String SSN = clientsTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				String query = "DELETE FROM lramos6db.Client WHERE clientSSN='" + SSN + "';";
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
		inputSSN = null;
		inputFirstName = null;
		inputLastName = null;
		inputSex = null;
		inputEmail = null;
		inputPhoneNumber = null;
		inputAddress = null;
		inputAssociateSSN = null;
		inputMinimumPrice = null;
		inputMaximumPrice = null;
	}
}
