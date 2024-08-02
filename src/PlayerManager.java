import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerManager {
    // method to add a player to the database
    public boolean createPlayer(int leagueWideNumber, String name, int age) throws ClassNotFoundException {
        String sql = "INSERT INTO Player (LeagueWideNumber, Name, Age) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, leagueWideNumber);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePlayer(int PlayerID, int leagueWideNumber, String name, int age) {
        // method to update a player in the database
        String sql = "UPDATE Player SET Name = ?, Age = ?, LeagueWideNumber = ? WHERE PlayerID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setInt(3, leagueWideNumber);
            pstmt.setInt(4, PlayerID);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean readPlayer(int PlayerID) {
        // method to read a player from the database
        String sql = "SELECT * FROM Player WHERE PlayerID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, PlayerID);

            return pstmt.executeQuery().next();
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePlayer(int PlayerID) {
        // method to delete a player from the playerteamassociation and player tables
        String sql = "DELETE FROM PlayerTeamAssociation WHERE PlayerID = ?"; 
        String sql2 = "DELETE FROM Player WHERE PlayerID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

            pstmt.setInt(1, PlayerID);
            pstmt2.setInt(1, PlayerID);

            pstmt.executeUpdate();
            pstmt2.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean playerExists(int PlayerID) {
        // method to check if a player exists in the database
        String sql = "SELECT * FROM Player WHERE PlayerID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, PlayerID);

            return pstmt.executeQuery().next();
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
