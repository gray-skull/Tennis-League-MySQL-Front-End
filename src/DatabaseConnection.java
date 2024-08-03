import java.sql.Connection;         // interface for connection to the database
import java.sql.DriverManager;     // class to manage the JDBC drivers
import java.sql.SQLException;       // class for handling SQL exceptions
import javax.swing.JOptionPane;     // class for creating dialog boxes

public class DatabaseConnection {
    // variables to store the URL, user, and password for the database
    private static String URL = "";
    private static String USER = "";
    private static String PASSWORD = "";

    public static void getUserInput() {
        // get user input for url, user, and password using dialog boxes
        URL = JOptionPane.showInputDialog("Enter the URL for the database:", "jdbc:mysql://localhost:3306/tennisleague");
        USER = JOptionPane.showInputDialog("Enter the username for the database:", "root");
        PASSWORD = JOptionPane.showInputDialog("Enter the password for the database:", "password");
    }

    // method to get a connection to the database
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            throw e;
        }
        // if the URL, user, or password is empty, get user input
        if(URL.equals("") || USER.equals("") || PASSWORD.equals("")){ 
            getUserInput();
        }
        // return a connection to the database
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
