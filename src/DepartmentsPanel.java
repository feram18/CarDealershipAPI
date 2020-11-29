import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class DepartmentsPanel extends JPanel {
	private Connection connection = null;
	private JTable departmentsTable;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemEdit;
	private JMenuItem menuItemDelete;
	private JTextField textFieldDptNo;
	private JTextField textFieldDptName;
	private JComboBox comboBoxManager;

	/**
	 * Create the panel.
	 */
	public DepartmentsPanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JScrollPane scrollPaneStaff = new JScrollPane();
		scrollPaneStaff.setBounds(220, 45, 630, 444);
		add(scrollPaneStaff);
		
		departmentsTable = new JTable();
		departmentsTable.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPaneStaff.setViewportView(departmentsTable);
		
		popupMenu = new JPopupMenu();
		menuItemEdit = new JMenuItem("Edit Record");
		menuItemDelete = new JMenuItem("Delete Record");
		 
		popupMenu.add(menuItemEdit);
		popupMenu.add(menuItemDelete);
		
		departmentsTable.setComponentPopupMenu(popupMenu);
		
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
		
		JLabel lblDepartmentNo = new JLabel("Number");
		lblDepartmentNo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDepartmentNo.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDepartmentNo.setBounds(40, 48, 53, 15);
		add(lblDepartmentNo);
		
		textFieldDptNo = new JTextField();
		lblDepartmentNo.setLabelFor(textFieldDptNo);
		textFieldDptNo.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldDptNo.setBounds(103, 45, 86, 20);
		add(textFieldDptNo);
		
		JLabel lblDepartmentName = new JLabel("Name");
		lblDepartmentName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDepartmentName.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDepartmentName.setBounds(47, 77, 46, 15);
		add(lblDepartmentName);
		
		textFieldDptName = new JTextField();
		lblDepartmentName.setLabelFor(textFieldDptName);
		textFieldDptName.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldDptName.setBounds(103, 74, 86, 20);
		add(textFieldDptName);
		
		JLabel lblManager = new JLabel("Manager");
		lblManager.setHorizontalAlignment(SwingConstants.TRAILING);
		lblManager.setFont(new Font("Arial", Font.PLAIN, 12));
		lblManager.setBounds(31, 106, 62, 15);
		add(lblManager);
		
		comboBoxManager = new JComboBox();
		lblManager.setLabelFor(comboBoxManager);
		comboBoxManager.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBoxManager.setBounds(103, 103, 86, 20);
		add(comboBoxManager);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSearch.setBounds(68, 152, 77, 23);
		add(btnSearch);
	}
	
	/***
	 * This method makes the SQL query to update the selected record in the
	 * database's Department table.
	 * @throws SQLException
	 */
	
	private void updateDatabase() throws SQLException {
		try {
			connection = SQLConnection.ConnectDb();
			int selectedRow = departmentsTable.getSelectedRow();
			String departmentNo = (departmentsTable.getModel().getValueAt(selectedRow, 0)).toString();
			String query = "UPDATE lramos6db.Department SET WHERE departmentNo='" + departmentNo + "';";
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
	 * from the database's Department table.
	 * @throws SQLException
	 */
	
	private void deleteFromDatabase() throws SQLException {
		int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete", JOptionPane.YES_NO_OPTION);
		
		if(confirmation == 0) {
			try {
				int selectedRow = departmentsTable.getSelectedRow();
				String departmentNo = departmentsTable.getModel().getValueAt(selectedRow, 0).toString();
				connection = SQLConnection.ConnectDb();
				String query = "DELETE FROM lramos6db.Department WHERE departmentNo='" + departmentNo + "';";
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
