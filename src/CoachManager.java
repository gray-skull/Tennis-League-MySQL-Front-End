import java.io.Console;             // console input/output
import java.sql.Connection;         // connection to the database
import java.sql.PreparedStatement;  // prepared statement for SQL queries
import java.sql.SQLException;       // exception handling for SQL queries
import java.util.ArrayList;         // dynamic array for storing coach details
import java.util.List;              // interface for dynamic array

public class CoachManager {
    // method to add a coach to the database with columns CoachID, Name, TelephoneNumber, TeamNumber
    public boolean createCoach(String name, String telephoneNumber, int teamNumber) throws ClassNotFoundException {
        String sql = "INSERT INTO Coach (Name, TelephoneNumber, TeamNumber) VALUES (?, ?, ?)";

        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the parameters in the prepared statement
            pstmt.setString(1, name);
            pstmt.setString(2, telephoneNumber);
            pstmt.setInt(3, teamNumber);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            return false;
        }
    }

    // method to read a coach from the database
    public List<Object[]> readCoach(int coachID) {
        String sql = "SELECT * FROM Coach WHERE CoachID = ?";
        List<Object[]> coachDetails = new ArrayList<>();

        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // set the coachID parameter in the prepared statement
            pstmt.setInt(1, coachID);
            try (var rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // add the coach details to the list
                    coachDetails.add(new Object[]{
                            rs.getInt("CoachID"),
                            rs.getString("Name"),
                            rs.getString("TelephoneNumber"),
                            rs.getInt("TeamNumber")
                    });
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
        }
        // return the list of coach details
        return coachDetails;
    }

    // method to update a coach in the database
    public boolean updateCoach(int coachID, String name, String telephoneNumber, int teamNumber) {
        String sql = "UPDATE Coach SET Name = ?, TelephoneNumber = ?, TeamNumber = ? WHERE CoachID = ?";

        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // set the parameters in the prepared statement
            pstmt.setString(1, name);
            pstmt.setString(2, telephoneNumber);
            pstmt.setInt(3, teamNumber);
            pstmt.setInt(4, coachID);

            pstmt.executeUpdate();
            // return true if the update is successful
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            // return false if the update is unsuccessful
            return false;
        }
    }

    // method to delete a coach from the database
    public boolean deleteCoach(int coachID) {
        String sql = "DELETE FROM workexperience WHERE CoachID = ?";
        String sql2 = "DELETE FROM Coach WHERE CoachID = ?";

        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            
            // set the coachID parameter in the prepared statement
            pstmt.setInt(1, coachID);
            pstmt2.setInt(1, coachID);
            
            // execute the prepared statements
            pstmt.executeUpdate();
            pstmt2.executeUpdate();
            // return true if the deletion is successful
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            // return false if the deletion is unsuccessful
            return false;
        }
    }

    // method to read all coaches from the database
    public List<Object[]> readAllCoaches() {
        String sql = "SELECT * FROM Coach";
        List<Object[]> coaches = new ArrayList<>();
        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // execute the prepared statement
            var results = pstmt.executeQuery();
            // iterate through the results and add the coach details to the list
            while (results.next()) {
                Object[] coach = {
                        results.getInt("CoachID"),
                        results.getString("Name"),
                        results.getString("TelephoneNumber"),
                        results.getInt("TeamNumber")
                };
                // add the coach details to the list
                coaches.add(coach);
            }
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
        }
        // return the list of coach details
        return coaches;
    }

    // method to check if a coach exists in the database
    public boolean coachExists(int coachID) {
        String sql = "SELECT * FROM Coach WHERE CoachID = ?";
        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, coachID);

            // return true if the query returns a result
            return pstmt.executeQuery().next();
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            // return false if the query is unsuccessful
            return false;
        }
    }

}
