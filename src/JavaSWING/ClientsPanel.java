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

public class ClientsPanel extends JPanel implements GUIPanel {
	// Utility variables
	private Connection connection = null;
	private String query;
	private int parameterCount;
	
	// UI components to filter data when searching
	private JTable clientsTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldSSN;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JComboBox<String> comboBoxSex;
	private JTextField textFieldEmail;
	private JTextField textFieldPhoneNo;
	private JTextField textFieldCity;
	private JComboBox<String> comboBoxState;
	private JTextField textFieldZIP;
	private JComboBox<String> comboBoxAssociateSSN;

	// Array to populate comboBoxSex, and inputSex
	private final String[] sexOptions = { null, "F", "M" };

	// Array to populate comboBoxState
	private final String[] stateAbbreviations = { null, "AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT", "DC", "DE",
			"FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MP",
			"MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR", "RI", "SC", "SD",
			"TN", "TX", "UM", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY" };

	// UI components to be shown when updating, or adding a new record to database
	private JTextField inputSSN = new JTextField();
	private JTextField inputFirstName = new JTextField();
	private JTextField inputLastName = new JTextField();
	private JComboBox<String> inputSex = new JComboBox<String>(sexOptions);
	private JTextField inputEmail = new JTextField();
	private JTextField inputPhoneNumber = new JTextField();
	private JTextField inputAddress = new JTextField();
	private JComboBox<String> inputAssociateSSN = new JComboBox<String>();
	private JTextField inputMinimumPrice = new JTextField();
	private JTextField inputMaximumPrice = new JTextField();

	// Arrays to be passed into JOptionPane's
	private final Object[] inputFields = {
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
	private final String[] addOptions = { "Add", "Cancel" };
	private final String[] updateOptions = { "Save Changes", "Cancel" };

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
		clientsTable.setEnabled(false);

		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");

		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);

		clientsTable.setComponentPopupMenu(popupMenu);

		menuItemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					populateToUpdate();

					int choice = JOptionPane.showOptionDialog(null, inputFields, "Update Client",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);

					if (choice == 0) {
						System.out.println("Updating Client... ");
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

		JLabel lblSex = new JLabel("Sex");
		lblSex.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSex.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSex.setBounds(10, 140, 83, 15);
		add(lblSex);

		comboBoxSex = new JComboBox<String>(sexOptions);
		comboBoxSex.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxSex.setBounds(103, 137, 86, 20);
		add(comboBoxSex);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEmail.setBounds(10, 169, 83, 15);
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
		lblCity.setBounds(10, 227, 83, 15);
		add(lblCity);

		textFieldCity = new JTextField();
		textFieldCity.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldCity.setBounds(103, 224, 86, 20);
		add(textFieldCity);

		JLabel lblState = new JLabel("State");
		lblState.setHorizontalAlignment(SwingConstants.TRAILING);
		lblState.setFont(new Font("Arial", Font.PLAIN, 12));
		lblState.setBounds(10, 256, 83, 15);
		add(lblState);

		comboBoxState = new JComboBox<String>(stateAbbreviations);
		comboBoxState.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxState.setBounds(103, 253, 86, 20);
		add(comboBoxState);

		JLabel lblZipCode = new JLabel("ZIP Code");
		lblZipCode.setHorizontalAlignment(SwingConstants.TRAILING);
		lblZipCode.setFont(new Font("Arial", Font.PLAIN, 12));
		lblZipCode.setBounds(10, 285, 83, 15);
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

		comboBoxAssociateSSN = new JComboBox<String>();
		comboBoxAssociateSSN.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxAssociateSSN.setBounds(103, 311, 86, 20);
		add(comboBoxAssociateSSN);

		JButton btnSearchClients = new JButton("Search");
		btnSearchClients.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSearchClients.setBounds(68, 360, 77, 23);
		btnSearchClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					search();
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
					populateToAdd();

					if (JOptionPane.showOptionDialog(null, inputFields, "Add Client", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, addOptions, null) == 0) {
						System.out.print("Adding new Client to database... ");
						add();
					}
					clearFields();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});

		add(btnAddNewClient);

		populateComboBoxes();
	}

	/**
	 * This method populates the AssociateSSN combobox on clientsTable with data
	 * retrieved from SalesAssociate table, which serve as a filter for the user and
	 * as a means of input validation.
	 */

	@Override
	public void populateComboBoxes() {
		// Delete current options
		comboBoxAssociateSSN.removeAllItems();
								
		// Populate with updated options
		try {
			connection = SQLConnection.ConnectDb();

			query = "SELECT DISTINCT associateSSN FROM SalesAssociate WHERE associateSSN IS NOT NULL";
			// query = "SELECT DISTINCT fName, lName FROM Employee, SalesAssociate WHERE
			// SSN=associateSSN ORDER BY lName;";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();

			comboBoxAssociateSSN.addItem(null);
			while (result.next()) {
				comboBoxAssociateSSN.addItem(result.getString("associateSSN"));
			}

			/*
			 * comboBoxAssociateSSN.addItem(null); while (result.next()) {
			 * comboBoxAssociateSSN.addItem(result.getString("lName") + ", " +
			 * result.getString("fName")); }
			 */

			connection.close();
		} catch (Exception e) {
			System.out.println("Error querying data for combobox");
			e.printStackTrace();
		}
	}

	/**
	 * This method checks for the fields user entered data on and makes the SQL
	 * query with the parameters provided by the user. Results from Client table are
	 * populated in the clientsTable.
	 * 
	 * @throws SQLException
	 */

	@Override
	public void search() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			parameterCount = 0;
			query = "SELECT `clientSSN` AS `SSN`, " + "`fName` AS `First Name`, " + "`lName` AS `Last Name`, "
					+ "`sex` AS `Sex`, " + "`email` AS `Email`, " + "`phoneNo` AS `Phone Number`, "
					+ "`address` AS `Adress`, " + "`associateSSN_FK2` AS `Associate SSN`, "
					+ "`minimumPrice` AS `Min. Price`, " + "`maximumPrice` AS `Max. Price` " + "FROM Client WHERE "; // Base
																														// query

			if (!textFieldSSN.getText().isEmpty()) {
				addToQuery();
				query += "clientSSN='" + textFieldSSN.getText() + "'";
			}

			if (!textFieldFirstName.getText().isEmpty()) {
				addToQuery();
				query += "fName='" + textFieldFirstName.getText() + "'";
			}

			if (!textFieldLastName.getText().isEmpty()) {
				addToQuery();
				query += "lName='" + textFieldLastName.getText() + "'";
			}

			if (comboBoxSex.getSelectedItem() != null) {
				addToQuery();
				query += "sex='" + comboBoxSex.getSelectedItem() + "'";
			}

			if (!textFieldEmail.getText().isEmpty()) {
				addToQuery();
				query += "email='" + textFieldEmail.getText() + "'";
			}

			if (!textFieldPhoneNo.getText().isEmpty()) {
				addToQuery();
				query += "phoneNo='" + textFieldPhoneNo.getText() + "'";
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

			if (comboBoxAssociateSSN.getSelectedItem() != null) {
				addToQuery();
				query += "associateSSN_FK2='" + comboBoxAssociateSSN.getSelectedItem().toString() + "'";
			}

			query += ";"; // end SQL statement

			if (parameterCount > 0) {
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				clientsTable.setModel(DbUtils.resultSetToTableModel(result));
			} else {
				JOptionPane.showMessageDialog(null, "No criteria selected.");
			}

			parameterCount = 0; // reset parameter count for next query

			clientsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			EmployeeInterfaceFrame.resizeTableColumns(clientsTable);
			connection.close();
		} catch (Exception e) {
			System.out.println("Error: Invalid query.");
			e.printStackTrace();
		}
	}

	/**
	 * This method makes the SQL query to add the a new row to the database's Client
	 * table.
	 * 
	 * @throws SQLException
	 */

	@Override
	public void add() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			query = "INSERT INTO Client (clientSSN, fName, lName, sex, email, phoneNo," +
					"address, associateSSN_FK2, minimumPrice, maximumPrice)" +
					" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, inputSSN.getText());
			stmt.setString(2, inputFirstName.getText());
			stmt.setString(3, inputLastName.getText());
			stmt.setString(4, (String) inputSex.getSelectedItem());
			stmt.setString(5, inputEmail.getText());
			stmt.setString(6, inputPhoneNumber.getText());
			stmt.setString(7, inputAddress.getText());
			stmt.setString(8, (String) inputAssociateSSN.getSelectedItem());
			stmt.setString(9, inputMinimumPrice.getText());
			stmt.setString(10, inputMaximumPrice.getText());

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
	 * This method populates the fields of the Client object with the current data
	 * to allow the user to edit the existing information.
	 * 
	 * @throws SQLException
	 */

	@Override
	public void populateToUpdate() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = clientsTable.getSelectedRow();
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				query = "SELECT DISTINCT associateSSN FROM SalesAssociate WHERE associateSSN IS NOT NULL;";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();

				while (result.next()) {
					inputAssociateSSN.addItem(result.getString("associateSSN"));
				}

				inputSex.addItem("F");
				inputSex.addItem("M");

				String SSN = (clientsTable.getModel().getValueAt(selectedRow, 0)).toString();
				query = "SELECT * FROM Client WHERE clientSSN='" + SSN + "';";
				stmt = connection.prepareStatement(query);
				result = stmt.executeQuery();

				if (result.next()) {
					inputSSN.setText(result.getString("clientSSN"));
					inputFirstName.setText(result.getString("fName"));
					inputLastName.setText(result.getString("lName"));
					inputSex.setSelectedItem(result.getString("sex"));
					inputEmail.setText(result.getString("email"));
					inputPhoneNumber.setText(result.getString("phoneNo"));
					inputAddress.setText(result.getString("address"));
					inputAssociateSSN.setSelectedItem(result.getString("associateSSN_FK2"));
					inputMinimumPrice.setText(result.getString("minimumPrice"));
					inputMaximumPrice.setText(result.getString("maximumPrice"));
				}

				stmt.close();
				result.close();
			}

			connection.close();
		} catch (Exception e) {
			System.out.print("Error retrieving data.");
			e.printStackTrace();
		}
	}

	/**
	 * This method makes the SQL query to update the selected record in the
	 * database's Client table.
	 * 
	 * @throws SQLException
	 */

	@Override
	public void update() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = clientsTable.getSelectedRow();
			String SSN = (clientsTable.getModel().getValueAt(selectedRow, 0)).toString();
			query = "UPDATE Client SET " + "clientSSN ='" + inputSSN.getText() + "', fName ='"
					+ inputFirstName.getText() + "', lName ='" + inputLastName.getText() + "', sex ='"
					+ inputSex.getSelectedItem() + "', email ='" + inputEmail.getText() + "', phoneNo ='"
					+ inputPhoneNumber.getText() + "', address ='" + inputAddress.getText() + "', associateSSN_FK2 ='"
					+ inputAssociateSSN.getSelectedItem() + "', minimumPrice ='" + inputMinimumPrice.getText()
					+ "', maximumPrice ='" + inputMaximumPrice.getText() + "' WHERE clientSSN='" + SSN + "';";
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
	 * This method makes the SQL query to delete the selected record (table row)
	 * from the database's Client table.
	 * 
	 * @throws SQLException
	 */

	@Override
	public void delete() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete",
				JOptionPane.YES_NO_OPTION);

		if (confirmation == 0) {
			try {
				int selectedRow = clientsTable.getSelectedRow();
				String SSN = clientsTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				query = "DELETE FROM Client WHERE clientSSN='" + SSN + "';";
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
	 * This method clears the input fields to avoid incorrect data on following edit
	 * attempt
	 */

	@Override
	public void clearFields() {
		inputSSN.setText(null);
		inputFirstName.setText(null);
		inputLastName.setText(null);
		inputSex.removeAllItems();
		inputEmail.setText(null);
		inputPhoneNumber.setText(null);
		inputAddress.setText(null);
		inputAssociateSSN.removeAllItems();
		inputMinimumPrice.setText(null);
		inputMaximumPrice.setText(null);
	}

	/**
	 * This method increases the parameter count, and adds the SQL keyword to allow
	 * an additional parameter to be added to SQL query
	 */

	@Override
	public void addToQuery() {
		parameterCount++;
		if (parameterCount > 1) {
			query += " AND ";
		}
	}

	/**
	 * This method populates the comboboxes on the Add Client popup window, as a
	 * means of input validation.
	 */

	@Override
	public void populateToAdd() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();

			query = "SELECT DISTINCT associateSSN FROM SalesAssociate WHERE associateSSN IS NOT NULL;";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				inputAssociateSSN.addItem(result.getString("associateSSN"));
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
