import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

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
	private JComboBox comboBoxMechanic;
	private JDatePickerImpl datePicker;
	
	/**
	 * Create the panel.
	 */
	public ServiceTicketsPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JScrollPane scrollPaneStaff = new JScrollPane();
		scrollPaneStaff.setBounds(220, 45, 630, 444);
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
		textFieldTicketNo.setBounds(100, 45, 86, 20);
		add(textFieldTicketNo);
		
		JLabel lblVin = new JLabel("VIN");
		lblVin.setHorizontalAlignment(SwingConstants.TRAILING);
		lblVin.setFont(new Font("Arial", Font.PLAIN, 12));
		lblVin.setBounds(10, 77, 80, 15);
		add(lblVin);
		
		textFieldVIN = new JTextField();
		lblVin.setLabelFor(textFieldVIN);
		textFieldVIN.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldVIN.setBounds(100, 74, 86, 20);
		add(textFieldVIN);
		
		JLabel lblMechanic = new JLabel("Mechanic");
		lblMechanic.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMechanic.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMechanic.setBounds(10, 106, 80, 15);
		add(lblMechanic);
		
		comboBoxMechanic = new JComboBox();
		lblMechanic.setLabelFor(comboBoxMechanic);
		comboBoxMechanic.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxMechanic.setBounds(100, 103, 86, 20);
		add(comboBoxMechanic);
		
		JLabel lblServiceDate = new JLabel("Service Date");
		lblServiceDate.setHorizontalAlignment(SwingConstants.TRAILING);
		lblServiceDate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblServiceDate.setBounds(10, 135, 80, 15);
		add(lblServiceDate);
		
		UtilDateModel model = new UtilDateModel();
		model.setDate(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH); //Get current date
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.getJFormattedTextField().setFont(new Font("Arial", Font.PLAIN, 12));
		datePicker.setBounds(100, 132, 86, 20);
		add(datePicker);
		
		//java.sql.Date selectedDate = (java.sql.Date) datePicker.getModel().getValue();
		
		JButton button = new JButton("Search");
		button.setFont(new Font("Arial", Font.PLAIN, 12));
		button.setBounds(72, 177, 77, 23);
		add(button);
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
			String query = "UPDATE lramos6db.ServiceTicket SET WHERE serviceTicketNo='" + serviceTicketNo + "';";
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
}
