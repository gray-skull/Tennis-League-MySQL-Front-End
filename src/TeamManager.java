import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamManager {
    
    // method to add a team to the database
    public boolean createTeam(int teamNumber, String name, String city, String managerName) throws ClassNotFoundException {
        String sql = "INSERT INTO Team (TeamNumber, Name, City, ManagerName) VALUES (?, ?, ?, ?)";
        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // set the parameters in the prepared statement
            pstmt.setInt(1, teamNumber);
            pstmt.setString(2, name);
            pstmt.setString(3, city);
            pstmt.setString(4, managerName);

            pstmt.executeUpdate();
            // return true if the query is successful
            return true;
        } catch (SQLException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            // return false if the query is unsuccessful
            return false;
        }
    }
    // method to read a team from the database
    public List<Object[]> readTeam(int teamNumber) {
        // method to read a team from the database
        String sql = "SELECT * FROM Team WHERE TeamNumber = ?";
        List<Object[]> teamDetails = new ArrayList<>();
        
        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the teamNumber parameter in the prepared statement
            pstmt.setInt(1, teamNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    teamDetails.add(new Object[]{
                        rs.getInt("TeamNumber"),
                        rs.getString("Name"),
                        rs.getString("City"),
                        rs.getString("ManagerName")
                    });
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
        }
        // return the list of team details
        return teamDetails;
    }

    // method to update a team in the database
    public boolean updateTeam(int teamNumber, String name, String city, String managerName) {
        String sql = "UPDATE Team SET Name = ?, City = ?, ManagerName = ? WHERE TeamNumber = ?";

        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // set the parameters in the prepared statement
            pstmt.setString(1, name);
            pstmt.setString(2, city);
            pstmt.setString(3, managerName);
            pstmt.setInt(4, teamNumber);

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

    // method to delete a team from the database
    public boolean deleteTeam(int teamNumber) {
        String sql = "DELETE FROM Team WHERE TeamNumber = ?";
        
        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // set the teamNumber parameter in the prepared statement
            pstmt.setInt(1, teamNumber);

            pstmt.executeUpdate();
            // return true if the deletion is successful
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            // return false if the deletion is unsuccessful
            return false;
        }
    }

    // method to check if a team exists in the database
	public boolean teamExists(int teamNumber) {
        String sql = "SELECT TeamNumber FROM Team WHERE TeamNumber = ?";
        
        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
                pstmt.setInt(1, teamNumber);
            // return true if the query returns a result
            return pstmt.executeQuery().next();

        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            // return false if the query is unsuccessful
            return false;
        }
	}

    // method to read all teams from the database
    public List<Object[]> readAllTeams() {
        String sql = "SELECT * FROM Team";
        // list to store the team details
        List<Object[]> teamDetails = new ArrayList<>();

        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            
            // iterate through the results and add the team details to the list
            while (rs.next()) {
                teamDetails.add(new Object[]{
                    rs.getInt("TeamNumber"),
                    rs.getString("Name"),
                    rs.getString("City"),
                    rs.getString("ManagerName")
                });
            }
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
        }
    // return the list of team details
    return teamDetails;
    }
}