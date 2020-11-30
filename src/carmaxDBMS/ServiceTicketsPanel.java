package carmaxDBMS;
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

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class ServiceTicketsPanel extends JPanel {
	private Connection connection = null;
	private JTable ticketsTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldTicketNo;
	private JTextField textFieldVIN;
	private JComboBox<String> comboBoxMechanic;
	private JDatePickerImpl datePicker;
	
	private JTextField inputTicketNo = new JTextField();
	private JTextField inputVIN = new JTextField();
	private JTextField inputMechanicSSN = new JTextField();
	private JTextField inputComment = new JTextField();
	private JTextField inputServiceDate = new JTextField();
	
	Object[] inputFields = {
			"Service Ticket Number", inputTicketNo,
			"VIN", inputVIN,
			"Mechanic SSN", inputMechanicSSN,
			"Comments", inputComment,
			"Service Date", inputServiceDate
	};
	
	String[] addOptions = {"Add", "Cancel"};
	String[] updateOptions = {"Save Changes", "Cancel"};
	
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
		
		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");
		 
		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);
		
		ticketsTable.setComponentPopupMenu(popupMenu);
		
		menuItemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane updatePane = new JOptionPane();
				updatePane.setVisible(false);
				
				try {
					populateToUpdate();
					updatePane.setVisible(true);
					
					int choice = updatePane.showOptionDialog(null, inputFields, "Update Service Ticket", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, updateOptions, null);
					
					if(choice == 0) {
						System.out.println("Updating Service Ticket... ");
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
		
		JLabel lblTicketNumber = new JLabel("Ticket Number");
		lblTicketNumber.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTicketNumber.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTicketNumber.setBounds(10, 48, 80, 15);
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
		
		comboBoxMechanic = new JComboBox();
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
					searchTickets();
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
					if(JOptionPane.showOptionDialog(null, inputFields, "Add Location", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, addOptions, null) == 0) {
						System.out.print("Adding new Location to database...");
						addToDatabase();
					}
					
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
		add(btnAddNewTicket);
		
		populateComboBoxes();
	}
	
	/***
	 * This method populates the Mechanic combobox on ticketsTable with data
	 * retrieved from Mechanic table, which serve as a filter for the user
	 * and as a means of input validation.
	 */
	
	private void populateComboBoxes() {
		try {
			connection = SQLConnection.ConnectDb();
			
			String query = "SELECT DISTINCT mechanicSSN FROM lramos6db.Mechanic WHERE mechanicSSN IS NOT NULL";
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			comboBoxMechanic.addItem(null);
			while(result.next() == true) {
				comboBoxMechanic.addItem(result.getString("mechanicSSN"));
			}
		} catch (Exception e) {
			System.out.println("Error querying data for combobox");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method checks for the fields user entered data on and
	 * makes the SQL query with the parameters provided by the user.
	 * Results from ServiceTicket table are populated in the ticketsTable.
	 * @throws SQLException
	 */
	
	private void searchTickets() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int parameterCount = 0;
			String query = "SELECT * FROM lramos6db.ServiceTicket WHERE "; //Base query
			
			if(!textFieldTicketNo.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "serviceTicketNo ='" + textFieldTicketNo.getText() + "'";
			}
			
			if(!textFieldVIN.getText().isEmpty()) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "VIN_FK ='" + textFieldVIN.getText() + "'";
			}
			
			if(comboBoxMechanic.getSelectedItem() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "mechanicSSN_FK ='" + comboBoxMechanic.getSelectedItem().toString() +"'";
			}
			
			
			//TODO - SQL Date format
			if(datePicker.getModel().getValue() != null) {
				parameterCount++;
				if(parameterCount > 1) {
					query += " AND ";
				}
				
				query += "serviceDate ='" + datePicker.getModel().getValue() + "'";
			}
			
			query += ";";
			
			if(parameterCount > 0) {
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
				ticketsTable.setModel(DbUtils.resultSetToTableModel(result));
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
	 * to the database's ServiceTicket table.
	 * @throws SQLException
	 */
	
	private void addToDatabase() throws SQLException  {
		try {
			connection = SQLConnection.ConnectDb();
			String query = "INSERT INTO lramos6db.ServiceTicket (serviceTicketNo, VIN_FK, mechanicSSN_FK, comment, serviceDate)"
							+ " values (?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, inputTicketNo.getText());
			stmt.setString(2, inputVIN.getText());
			stmt.setString(3, inputMechanicSSN.getText());
			stmt.setString(4, inputComment.getText());
			stmt.setString(4, inputServiceDate.getText());
			
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
	 * This method populates the fields of the ServiceTicket object with the 
	 * current data to allow the user to edit the existing information.
	 * @throws SQLException
	 */
	private void populateToUpdate() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = ticketsTable.getSelectedRow();
			if(selectedRow < 0) {
				JOptionPane.showMessageDialog(null, "No rows selected. Select a row first.");
			} else {
				String ticketNo = (ticketsTable.getModel().getValueAt(selectedRow, 0)).toString();
				String query = "SELECT * FROM lramos6db.ServiceTicket WHERE serviceTicketNo='" + ticketNo + "'";
				PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();
							
				if(result.next() == true) {
					inputTicketNo.setText(result.getString("serviceTicketNo"));
					inputVIN.setText(result.getString("VIN_FK"));
					inputMechanicSSN.setText(result.getString("mechanicSSN_FK"));
					inputComment.setText(result.getString("comment"));
					inputServiceDate.setText(result.getString("serviceDate"));
				}
			}
		} catch (Exception e) {
			System.out.print("Error retrieving data.");
			e.printStackTrace();
		}
	}
	
	/***
	 * This method makes the SQL query to update the selected record in the
	 * database's ServiceTicket table.
	 * @throws SQLException
	 */
	
	private void updateDatabase() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = ticketsTable.getSelectedRow();
			String serviceTicketNo = (ticketsTable.getModel().getValueAt(selectedRow, 0)).toString();
			String query = "UPDATE lramos6db.ServiceTicket SET " +
							"serviceTicketNo ='" + inputTicketNo.getText() +
							"', VIN_FK ='" + inputVIN.getText() +
							"', mechanicSSN_FK ='" + inputMechanicSSN.getText() +
							"', comment ='" + inputComment.getText() +
							"', serviceDate ='" + inputServiceDate.getText() +
							"' WHERE serviceTicketNo='" + serviceTicketNo + "';";
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
	 * from the database's ServiceTicket table.
	 * @throws SQLException
	 */
	
	private void deleteFromDatabase() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
		
		if(confirmation == 0) {
			try {
				int selectedRow = ticketsTable.getSelectedRow();
				String serviceTicketNo = ticketsTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				String query = "DELETE FROM lramos6db.ServiceTicket WHERE serviceTicketNo='" + serviceTicketNo + "';";
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
		inputTicketNo = null;
		inputVIN = null;
		inputMechanicSSN = null;
		inputComment = null;
		inputServiceDate = null;
	}
}
