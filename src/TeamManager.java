import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeamManager {
    public boolean addTeam(int teamNumber, String name, String city, String managerName) throws ClassNotFoundException {
        String sql = "INSERT INTO Team (TeamNumber, Name, City, ManagerName) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, teamNumber);
            pstmt.setString(2, name);
            pstmt.setString(3, city);
            pstmt.setString(4, managerName);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // method to add a player to the database
    public boolean addPlayer(int leagueWideNumber, String name, int age) throws ClassNotFoundException {
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
}