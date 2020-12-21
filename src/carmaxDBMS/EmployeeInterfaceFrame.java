package carmaxDBMS;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The Class EmployeeInterfaceFrame holds all the panels shown
 * on the UI. Made visible once user has entered valid credentials
 */
public class EmployeeInterfaceFrame extends JFrame {
	private JPanel contentPane;
	
	InventoryPanel inventory = new InventoryPanel();
	StaffPanel staff = new StaffPanel();
	ClientsPanel clients = new ClientsPanel();
	DealersPanel dealers = new DealersPanel();
	LocationsPanel locations = new LocationsPanel();
	DepartmentsPanel departments = new DepartmentsPanel();
	ServiceTicketsPanel tickets = new ServiceTicketsPanel();
	
	private static JLabel lblClock;
	private final String PATH_TO_ICON = "/CarmaxDBMS/resources/img/dealership_app_icon.png";
	
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
		//setIcon(PATH_TO_ICON); // TODO - Add Icon to Frame
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 700);
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
		lblUserGreeting.setBounds(720, 11, 142, 25);
		lblUserGreeting.setFont(new Font("Montserrat", Font.BOLD, 25));
		topPanel.add(lblUserGreeting);
		
		lblClock = new JLabel("[Clock]");
		lblClock.setVerticalAlignment(SwingConstants.BOTTOM);
		lblClock.setHorizontalAlignment(SwingConstants.TRAILING);
		lblClock.setFont(new Font("Montserrat", Font.PLAIN, 25));
		lblClock.setBounds(697, 26, 165, 37);
		topPanel.add(lblClock);
		clock();

		tabbedPane.addTab("Inventory", inventory);
		tabbedPane.addTab("Staff", staff);
		tabbedPane.addTab("Clients", clients);
		tabbedPane.addTab("Dealers", dealers);
		tabbedPane.addTab("Locations", locations);
		tabbedPane.addTab("Departments", departments);
		tabbedPane.addTab("Service Tickets", tickets);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Logging out...");
				dispose();
				LoginFrame.main(null); //Call to LoginFrame.java's main method to initialize and set JFrame visible
			}
		});
		btnLogOut.setFont(new Font("Arial", Font.PLAIN, 12));
		btnLogOut.setBounds(407, 607, 89, 23);
		contentPane.add(btnLogOut);
	}
	
	/**
	 * This method initiates a thread used to run a loop.
	 * The loop gets the current time every second and updates
	 * the UI' lblClock component with the value.
	 */
	
	public static void clock() {
		Thread clock = new Thread() {
			public void run() {
				try {
					while(!Thread.currentThread().isInterrupted()) {
						String time = new SimpleDateFormat("hh:mm:ss a").format(new Date());
						lblClock.setText(time);
						sleep(1000);
					}
				} catch (InterruptedException e) {
					System.out.print("Error starting clock thread");
					e.printStackTrace();
				}
			}
		};
		
		clock.start();
	}
	
	
	/**
	 * This method takes in the path of the image to be converted
	 * into an ImageIcon to be used as the application's icon.
	 *
	 * @param pathToIcon the new icon
	 */
	private void setIcon(String pathToIcon) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(pathToIcon)));
	}
	
	
	/**
	 * This method resizes the size of the column on a table
	 * in order to display the data correctly on without needing 
	 * to manually resize the column.
	 *
	 * @param table
	 */
	
	public static void resizeTableColumns(JTable table) {
		for (int column = 0; column < table.getColumnCount(); column++) {
		    TableColumn tableColumn = table.getColumnModel().getColumn(column);
		    int preferredWidth = tableColumn.getMinWidth();
		    int maxWidth = tableColumn.getMaxWidth();
		 
		    for (int row = 0; row < table.getRowCount(); row++) {
		        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
		        Component c = table.prepareRenderer(cellRenderer, row, column);
		        int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
		        preferredWidth = Math.max(preferredWidth, width);
		 
		        //  We've exceeded the maximum width, no need to check other rows
		        if (preferredWidth >= maxWidth) {
		            preferredWidth = maxWidth;
		            break;
		        }
		    }
		    
		    tableColumn.setPreferredWidth(preferredWidth + 15);
		}
	}
}
