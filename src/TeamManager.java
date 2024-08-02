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
            return false;
        }
    }

    public List<Object[]> readTeam(int teamNumber) {
        // method to read a team from the database
        String sql = "SELECT * FROM Team WHERE TeamNumber = ?";
        List<Object[]> teamDetails = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
        return teamDetails;
    }

    public boolean updateTeam(int teamNumber, String name, String city, String managerName) {
        // method to update a team in the database
        String sql = "UPDATE Team SET Name = ?, City = ?, ManagerName = ? WHERE TeamNumber = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, city);
            pstmt.setString(3, managerName);
            pstmt.setInt(4, teamNumber);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            return false;
        }
    }

    public boolean deleteTeam(int teamNumber) {
        // method to delete a team from the database
        String sql = "DELETE FROM Team WHERE TeamNumber = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, teamNumber);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            return false;
        }
    }

	public boolean teamExists(int teamNumber) {
		// check if the teamNumber exists in the database
        String sql = "SELECT TeamNumber FROM Team WHERE TeamNumber = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
                pstmt.setInt(1, teamNumber);
            
            return pstmt.executeQuery().next();

        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            return false;
        }
	}

    public List<Object[]> readAllTeams() {
        // method to read all teams from the database
        String sql = "SELECT * FROM Team";
        List<Object[]> teamDetails = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

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
    return teamDetails;
    }
}