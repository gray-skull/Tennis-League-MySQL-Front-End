import java.io.Console;             // console input/output
import java.sql.Connection;         // connection to the database
import java.sql.PreparedStatement;  // prepared statement for SQL queries
import java.sql.ResultSet;          // interface for result set from a database query
import java.sql.SQLException;       // exception handling for SQL queries
import java.util.ArrayList;         // dynamic array for storing player details
import java.util.List;              // interface for dynamic array

public class PlayerManager {
    
    // method to add a player to the database
    public boolean createPlayer(int leagueWideNumber, String name, int age) throws ClassNotFoundException {
        String sql = "INSERT INTO Player (LeagueWideNumber, Name, Age) VALUES (?, ?, ?)";
        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the parameters in the prepared statement
            pstmt.setInt(1, leagueWideNumber);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            // execute the query
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

    // method to update a player in the database
    public boolean updatePlayer(int PlayerID, int leagueWideNumber, String name, int age) {
        // method to update a player in the database
        String sql = "UPDATE Player SET Name = ?, Age = ?, LeagueWideNumber = ? WHERE PlayerID = ?";
        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the parameters in the prepared statement
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setInt(3, leagueWideNumber);
            pstmt.setInt(4, PlayerID);
            // execute the query
            pstmt.executeUpdate();
            // return true if the query is successful
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            // return false if the query is unsuccessful
            return false;
        }
    }

    // method to read a player from the database
    public List<Object[]> readPlayer(int PlayerID) {
        // method to read a player from the database
        String sql = "SELECT * FROM Player WHERE PlayerID = ?";
        // create a list to store the player details
        List<Object[]> playerDetails = new ArrayList<>();
        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the PlayerID parameter in the prepared statement
            pstmt.setInt(1, PlayerID);
            // execute the query
            try (ResultSet rs = pstmt.executeQuery()) {
                // add the player details to the list
                while (rs.next()) {
                    // add the player details to the list
                    playerDetails.add(new Object[]{
                        rs.getInt("PlayerID"),
                        rs.getInt("LeagueWideNumber"),
                        rs.getString("Name"),
                        rs.getInt("Age")
                    });
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
        }
        // return the list of player details
        return playerDetails;
    }

    // method to delete a player from the database
    public boolean deletePlayer(int PlayerID) {
        // method to delete a player from the playerteamassociation and player tables
        String sql = "DELETE FROM PlayerTeamAssociation WHERE PlayerID = ?"; 
        String sql2 = "DELETE FROM Player WHERE PlayerID = ?";
        
        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
                
            // set the PlayerID parameter in the prepared statement
            pstmt.setInt(1, PlayerID);
            pstmt2.setInt(1, PlayerID);
            
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
    
    // method to check if a player exists in the database
    public boolean playerExists(int PlayerID) {
        String sql = "SELECT * FROM Player WHERE PlayerID = ?";
        
        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // set the PlayerID parameter in the prepared statement
                pstmt.setInt(1, PlayerID);
                // execute the query and return true if the result set is not empty
                return pstmt.executeQuery().next();
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            // return false if the query is unsuccessful
            return false;
        }
    }
    // method to read all players from the database
    public List<Object[]> readAllPlayers() {
        String sql = "SELECT * FROM Player";
        List<Object[]> playerDetails = new ArrayList<>();
        // try-with-resources block to automatically close the connection
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            
                // iterate through the results and add the player details to the list
            while (rs.next()) {
                playerDetails.add(new Object[]{
                    rs.getInt("PlayerID"),
                    rs.getInt("LeagueWideNumber"),
                    rs.getString("Name"),
                    rs.getInt("Age")
                });
            }
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
        }
        // return the list of player details
        return playerDetails;
    }
}
