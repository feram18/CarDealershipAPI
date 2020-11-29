import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EmployeeInterfaceFrame extends JFrame {

	private JPanel contentPane;
	private Connection connection = null;
	InventoryPanel inventory = new InventoryPanel();
	StaffPanel staff = new StaffPanel();
	ClientsPanel clients = new ClientsPanel();
	LocationsPanel locations = new LocationsPanel();
	DepartmentsPanel departments = new DepartmentsPanel();
	ServiceTicketsPanel tickets = new ServiceTicketsPanel();
	
	private static JLabel lblClock;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeInterfaceFrame frame = new EmployeeInterfaceFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmployeeInterfaceFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 60, 874, 530);
		contentPane.add(tabbedPane);
		
		JPanel topPanel = new JPanel();
		topPanel.setBounds(10, 11, 872, 74);
		contentPane.add(topPanel);
		topPanel.setLayout(null);
		
		JLabel lblUserGreeting = new JLabel("Hi, " + LoginFrame.GetLoggedOnUserName());
		lblUserGreeting.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserGreeting.setBounds(710, 25, 106, 25);
		lblUserGreeting.setFont(new Font("Montserrat", Font.BOLD, 25));
		topPanel.add(lblUserGreeting);
		
//		lblClock = new JLabel("[Clock]");
//		lblClock.setFont(new Font("Montserrat", Font.PLAIN, 16));
//		lblClock.setBounds(10, 11, 83, 37);
//		topPanel.add(lblClock);

		tabbedPane.addTab("Inventory", inventory);
		tabbedPane.addTab("Staff", staff);
		tabbedPane.addTab("Clients", clients);
		tabbedPane.addTab("Locations", locations);
		tabbedPane.addTab("Departments", departments);
		tabbedPane.addTab("Service Tickets", tickets);
	}
}
