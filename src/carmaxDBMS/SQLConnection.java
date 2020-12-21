package carmaxDBMS;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.swing.JOptionPane;

public class SQLConnection {
    Connection connection = null;
    private static Properties properties = new Properties();
    private final static String PATH = "D:\\Projects\\COSC457-Carmax-Database\\resources\\dbCredentials.properties";
    
    /**
     * Read credentials from properties file to create connection to SQL server. 
     * @return
     * @throws IOException 
     */
    public static Connection ConnectDb() throws IOException {
        try(InputStream input = new FileInputStream(PATH)) {
        	properties.load(input);
        	final String SERVER = properties.getProperty("SERVER");
            final String USER = properties.getProperty("USER");
            final String PASSWORD = properties.getProperty("PASSWORD");
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(SERVER, USER, PASSWORD);
            return connection;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("Database connection unsucessful. Database offline."); //log unsuccessful connection
            return null;
        }
    }
}
