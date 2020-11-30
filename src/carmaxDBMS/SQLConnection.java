package carmaxDBMS;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class SQLConnection {
    Connection connection = null;
    private static final String SERVER = "jdbc:mysql://triton.towson.edu:3360/?serverTimezone=EST#/lramos6db";
    private static final String USER = "lramos6";
    private static final String PASSWORD = "Cosc*qyk2";

    public static Connection ConnectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(SERVER, USER, PASSWORD);
            System.out.println("Database connection successful."); //log successful connection
            return connection;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("Database connection unsucessful."); //log unsuccessful connection
            return null;
        }
    }
}