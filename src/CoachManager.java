import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachManager {
    // method to add a coach to the database with columns CoachID, Name, TelephoneNumber, TeamNumber
    public boolean createCoach(String name, String telephoneNumber, int teamNumber) throws ClassNotFoundException {
        String sql = "INSERT INTO Coach (Name, TelephoneNumber, TeamNumber) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, coachID);
            try (var rs = pstmt.executeQuery()) {
                while (rs.next()) {
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
        return coachDetails;
    }

    // method to update a coach in the database
    public boolean updateCoach(int coachID, String name, String telephoneNumber, int teamNumber) {
        String sql = "UPDATE Coach SET Name = ?, TelephoneNumber = ?, TeamNumber = ? WHERE CoachID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, telephoneNumber);
            pstmt.setInt(3, teamNumber);
            pstmt.setInt(4, coachID);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            return false;
        }
    }

    // method to delete a coach from the database
    public boolean deleteCoach(int coachID) {
        String sql = "DELETE FROM workexperience WHERE CoachID = ?";
        String sql2 = "DELETE FROM Coach WHERE CoachID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

            pstmt.setInt(1, coachID);
            pstmt2.setInt(1, coachID);

            pstmt.executeUpdate();
            pstmt2.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            return false;
        }
    }

    // method to read all coaches from the database
    public List<Object[]> readAllCoaches() {
        String sql = "SELECT * FROM Coach";
        List<Object[]> coaches = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            var results = pstmt.executeQuery();

            while (results.next()) {
                Object[] coach = {
                        results.getInt("CoachID"),
                        results.getString("Name"),
                        results.getString("TelephoneNumber"),
                        results.getInt("TeamNumber")
                };
                coaches.add(coach);
            }
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
        }
        return coaches;
    }

    // method to check if a coach exists in the database
    public boolean coachExists(int coachID) {
        String sql = "SELECT * FROM Coach WHERE CoachID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, coachID);

            return pstmt.executeQuery().next();
        } catch (SQLException | ClassNotFoundException e) {
            Console console = System.console();
            console.printf("Error: %s\n", e.getMessage());
            return false;
        }
    }

}
