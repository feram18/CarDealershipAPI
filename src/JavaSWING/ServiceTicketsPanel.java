package JavaSWING;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;

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

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import net.proteanit.sql.DbUtils;

public class ServiceTicketsPanel extends JPanel implements GUIPanel {
	// Utility variables
	private Connection connection = null;
	private String query;
	private int parameterCount;
	
	// UI components to filter data when searching
	private JTable ticketsTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldTicketNo;
	private JTextField textFieldVIN;
	private JComboBox<String> comboBoxMechanic;
	private JDatePickerImpl datePicker;
	
	// UI components to be shown when updating, or adding a new record to database
	private JTextField inputTicketNo = new JTextField();
	private JComboBox<String> inputVIN = new JComboBox<String>();
	private JComboBox<String> inputMechanicSSN = new JComboBox<String>();
	private JTextField inputComment = new JTextField();
	private JTextField inputServiceDate = new JTextField();
	
	// Arrays to be passed into JOptionPane's
	private final Object[] inputFields = {
			"Service Ticket Number", inputTicketNo,
			"VIN", inputVIN,
			"Mechanic SSN", inputMechanicSSN,
			"Comments", inputComment,
			"Service Date", inputServiceDate
	};
	private final String[] addOptions = {"Add", "Cancel"};
	private final String[] updateOptions = {"Save Changes", "Cancel"};
	
	/**
	 * Create the panel.
	 */
	public ServiceTicketsPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JScrollPane scrollPaneStaff = new JScrollPane();
		scrollPaneStaff.setBounds(220, 45, 630, 380);
		add(scrollPaneStaff);
		
		ticketsTable = new JTable();
		ticketsTable.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPaneStaff.setViewportView(ticketsTable);
		ticketsTable.setEnabled(false);
		
		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");
		 
		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);
		
		ticketsTable.setComponentPopupMenu(popupMenu);
		
		menuItemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					populateToUpdate();
					
					int choice = JOptionPane.showOptionDialog(null, inputFields, "Update Service Ticket",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);
					
					if(choice == 0) {
						System.out.println("Updating Service Ticket... ");
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
		
		JLabel lblTicketNumber = new JLabel("Ticket Number");
		lblTicketNumber.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTicketNumber.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTicketNumber.setBounds(0, 48, 90, 15);
		add(lblTicketNumber);
		
		textFieldTicketNo = new JTextField();
		lblTicketNumber.setLabelFor(textFieldTicketNo);
		textFieldTicketNo.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldTicketNo.setBounds(100, 45, 105, 20);
		add(textFieldTicketNo);
		
		JLabel lblVin = new JLabel("VIN");
		lblVin.setHorizontalAlignment(SwingConstants.TRAILING);
		lblVin.setFont(new Font("Arial", Font.PLAIN, 12));
		lblVin.setBounds(10, 77, 80, 15);
		add(lblVin);
		
		textFieldVIN = new JTextField();
		lblVin.setLabelFor(textFieldVIN);
		textFieldVIN.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldVIN.setBounds(100, 74, 105, 20);
		add(textFieldVIN);
		
		JLabel lblMechanic = new JLabel("Mechanic");
		lblMechanic.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMechanic.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMechanic.setBounds(10, 106, 80, 15);
		add(lblMechanic);
		
		comboBoxMechanic = new JComboBox<String>();
		lblMechanic.setLabelFor(comboBoxMechanic);
		comboBoxMechanic.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxMechanic.setBounds(100, 103, 105, 20);
		add(comboBoxMechanic);
		
		JLabel lblServiceDate = new JLabel("Service Date");
		lblServiceDate.setHorizontalAlignment(SwingConstants.TRAILING);
		lblServiceDate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblServiceDate.setBounds(10, 135, 80, 15);
		add(lblServiceDate);
		
		UtilDateModel model = new UtilDateModel();
		model.setDate(Calendar.DAY_OF_MONTH,Calendar.MONTH,Calendar.YEAR);
		Properties properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.getJFormattedTextField().setFont(new Font("Arial", Font.PLAIN, 12));
		datePicker.setBounds(100, 132, 105, 23);
		add(datePicker);
		datePicker.setVisible(true);
		
		JButton btnSearchTicket = new JButton("Search");
		btnSearchTicket.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSearchTicket.setBounds(72, 177, 77, 23);
		btnSearchTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					search();
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			}
		});
		add(btnSearchTicket);
		
		JButton btnAddNewTicket = new JButton("Add New Ticket");
		btnAddNewTicket.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAddNewTicket.setBounds(460, 450, 128, 23);
		btnAddNewTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(JOptionPane.showOptionDialog(null, inputFields, "New Service Ticket",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, addOptions, null) == 0) {
						System.out.print("Adding new Service Ticket to database...");
						add();
					}
					
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
		add(btnAddNewTicket);
		
		populateComboBoxes();
	}
	
	/**
	 * This method populates the Mechanic combobox on ticketsTable with data
	 * retrieved from Mechanic table, which serve as a filter for the user
	 * and as a means of input validation.
	 */
	
	@Override
	public void populateComboBoxes() {
		// Delete current options
		comboBoxMechanic.removeAllItems();
						
		// Populate with updated options
		try {
			connection = SQLConnection.ConnectDb();
			
			query = "SELECT DISTINCT mechanicSSN FROM Mechanic WHERE mechanicSSN IS NOT NULL;";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			comboBoxMechanic.addItem(null);
			while(result.next()) {
				comboBoxMechanic.addItem(result.getString("mechanicSSN"));
			}
			
			connection.close();
		} catch (Exception e) {
			System.out.println("Error querying data for combobox");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method checks for the fields user entered data on and
	 * makes the SQL query with the parameters provided by the user.
	 * Results from ServiceTicket table are populated in the ticketsTable.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void search() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			parameterCount = 0;
			query = "SELECT `serviceTicketNo` AS `Ticket No.`, " +
					"`VIN_FK` AS `VIN`, " +
					"`mechanicSSN_FK` AS `Mechanic SSN`, " +
					"`comment` AS `Comment`, " +
					"`serviceDate` AS `Service Date` " +
					"FROM ServiceTicket WHERE "; //Base query
			
			if (!textFieldTicketNo.getText().isEmpty()) {
				addToQuery();
				query += "serviceTicketNo ='" + textFieldTicketNo.getText() + "'";
			}
			
			if (!textFieldVIN.getText().isEmpty()) {
				addToQuery();
				query += "VIN_FK ='" + textFieldVIN.getText() + "'";
			}
			
			if (comboBoxMechanic.getSelectedItem() != null) {
				addToQuery();
				query += "mechanicSSN_FK ='" + comboBoxMechanic.getSelectedItem().toString() +"'";
			}
			
			if (datePicker.getModel().getValue() != null) {
				addToQuery();
				
				String date = datePicker.getModel().getYear() + "-" +
								(datePicker.getModel().getMonth() + 1) + "-" +
								(datePicker.getModel().getDay() + 1);
				
				query += "serviceDate ='" + date + "'";
			}
			
			query += ";";
			
			if (parameterCount > 0) {
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				ticketsTable.setModel(DbUtils.resultSetToTableModel(result));
			} else {
				JOptionPane.showMessageDialog(null, "No criteria selected.");
			}
			
			parameterCount = 0;
			
			ticketsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			EmployeeInterfaceFrame.resizeTableColumns(ticketsTable);
			connection.close();
		} catch (Exception e) {
			System.out.println("Error: Invalid query.");
			e.printStackTrace();
		}
	}
	
	/**
	 * *
	 * This method makes the SQL query to add the a new row
	 * to the database's ServiceTicket table.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void add() throws SQLException  {
		try {
			connection = SQLConnection.ConnectDb();
			query = "INSERT INTO ServiceTicket (serviceTicketNo, VIN_FK, mechanicSSN_FK, comment, serviceDate)" +
					" VALUES (?, ?, ?, ?, ?);";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, inputTicketNo.getText());
			stmt.setString(2, (String) inputVIN.getSelectedItem());
			stmt.setString(3, (String) inputMechanicSSN.getSelectedItem());
			stmt.setString(4, inputComment.getText());
			stmt.setString(5, inputServiceDate.getText());
			
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
	 * This method populates the fields of the ServiceTicket object with the 
	 * current data to allow the user to edit the existing information.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void populateToUpdate() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = ticketsTable.getSelectedRow();
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				String ticketNo = (ticketsTable.getModel().getValueAt(selectedRow, 0)).toString();
				query = "SELECT DISTINCT VIN FROM Vehicle WHERE VIN IS NOT NULL;";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				
				while(result.next()) {
					inputVIN.addItem(result.getString("VIN"));
				}
				
				query = "SELECT DISTINCT mechanicSSN FROM Mechanic WHERE mechanicSSN IS NOT NULL;";
				stmt = connection.prepareStatement(query);
				result = stmt.executeQuery();
				
				while(result.next()) {
					inputMechanicSSN.addItem(result.getString("mechanicSSN"));
				}
				
				query = "SELECT * FROM ServiceTicket WHERE serviceTicketNo='" + ticketNo + "';";
				stmt = connection.prepareStatement(query);
				result = stmt.executeQuery();
							
				if (result.next()) {
					inputTicketNo.setText(result.getString("serviceTicketNo"));
					inputVIN.setSelectedItem(result.getString("VIN_FK"));
					inputMechanicSSN.setSelectedItem(result.getString("mechanicSSN_FK"));
					inputComment.setText(result.getString("comment"));
					inputServiceDate.setText(result.getString("serviceDate"));
				}
			}
			
			connection.close();
		} catch (Exception e) {
			System.out.print("Error retrieving data.");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method makes the SQL query to update the selected record in the
	 * database's ServiceTicket table.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void update() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = ticketsTable.getSelectedRow();
			String serviceTicketNo = (ticketsTable.getModel().getValueAt(selectedRow, 0)).toString();
			query = "UPDATE ServiceTicket SET " +
					"serviceTicketNo ='" + inputTicketNo.getText() +
					"', VIN_FK ='" + inputVIN.getSelectedItem() +
					"', mechanicSSN_FK ='" + inputMechanicSSN.getSelectedItem() +
					"', comment ='" + inputComment.getText() +
					"', serviceDate ='" + inputServiceDate.getText() +
					"' WHERE serviceTicketNo='" + serviceTicketNo + "';";
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
	 * from the database's ServiceTicket table.
	 *
	 * @throws SQLException the SQL exception
	 */
	
	@Override
	public void delete() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
		
		if (confirmation == 0) {
			try {
				int selectedRow = ticketsTable.getSelectedRow();
				String serviceTicketNo = ticketsTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				query = "DELETE FROM ServiceTicket WHERE serviceTicketNo='" + serviceTicketNo + "';";
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
	 * This method clears the input fields to avoid incorrect
	 * data on following edit attempt.
	 */
	
	@Override
	public void clearFields() {
		inputTicketNo.setText(null);
		inputVIN.removeAllItems();
		inputMechanicSSN.removeAllItems();
		inputComment.setText(null);
		inputServiceDate.setText(null);
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
	 * This method populates the comboboxes on the Add Service Ticket popup
	 * window, as a means of input validation.
	 *
	 * @throws SQLException the SQL exception
	 */

	@Override
	public void populateToAdd() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			
			query = "SELECT VIN FROM Vehicle;";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			while (result.next()) {
				inputVIN.addItem(result.getString("VIN"));
			}
			
			query = "SELECT mechanicSSN FROM Mechanic;";
			stmt = connection.prepareStatement(query);
			result = stmt.executeQuery();
			
			while (result.next()) {
				inputMechanicSSN.addItem(result.getString("mechanicSSN"));
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