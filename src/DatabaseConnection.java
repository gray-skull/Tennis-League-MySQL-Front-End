import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    // jdbc:mysql://localhost:3306/tennisleague
    private static String URL = "";
    private static String USER = "";
    private static String PASSWORD = "";

    public static void getUserInput() {
        // get user input for url, user, and password
        URL = JOptionPane.showInputDialog("Enter the URL for the database:", "jdbc:mysql://localhost:3306/tennisleague");
        USER = JOptionPane.showInputDialog("Enter the username for the database:", "root");
        PASSWORD = JOptionPane.showInputDialog("Enter the password for the database:", "password");
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            throw e;
        }
        if(URL.equals("") || USER.equals("") || PASSWORD.equals("")){ 
            getUserInput();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
