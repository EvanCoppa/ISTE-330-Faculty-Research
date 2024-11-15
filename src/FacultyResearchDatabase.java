import java.sql.*;

public class FacultyResearchDatabase {

    private Connection conn;

    // Connect to the database
    public boolean connect() {
        try {
 
            String url = "jdbc:mysql://localhost:3306/faculty_research?serverTimezone=UTC";
            String user = "root";
            String password = "youpasshere";
            conn = DriverManager.getConnection(url, user, password);
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
            return false;
        }
    }

    // Close the database connection
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed to close the database connection: " + e.getMessage());
        }
    }

    // Add a faculty member
    public boolean addFaculty(String firstName, String lastName, String building, String office, String email, String interest) {
        String sql = "INSERT INTO Faculty (firstName, lastName, buildingNumber, officeNumber, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, building);
            pstmt.setString(4, office);
            pstmt.setString(5, email);
            pstmt.executeUpdate();

            // Add interest
            return addInterest(email, interest);
        } catch (SQLException e) {
            System.out.println("Error adding faculty: " + e.getMessage());
            return false;
        }
    }

    // Add an interest for a faculty member
    private boolean addInterest(String email, String interest) {
        String sql = "INSERT INTO Faculty_Interests (facultyID, interestID) VALUES ((SELECT facultyID FROM Faculty WHERE email = ?), ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, interest);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding interest: " + e.getMessage());
            return false;
        }
    }

    // Add a student
    public boolean addStudent(String firstName, String lastName, String email, String interest) {
        String sql = "INSERT INTO Student (firstName, lastName, email) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.executeUpdate();

            // Add interest
            return addStudentInterest(email, interest);
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // Add an interest for a student
    private boolean addStudentInterest(String email, String interest) {
        String sql = "INSERT INTO Student_Interest (studentID, interestID) VALUES ((SELECT studentID FROM Student WHERE email = ?), ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, interest);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding student interest: " + e.getMessage());
            return false;
        }
    }

    // Search matches
    public ResultSet searchMatches(String keyword) {
        String sql = "SELECT f.firstName, f.lastName, f.email FROM Faculty f " +
                     "JOIN Faculty_Interests fi ON f.facultyID = fi.facultyID " +
                     "JOIN Interest i ON fi.interestID = i.ID " +
                     "WHERE i.name LIKE ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error searching matches: " + e.getMessage());
            return null;
        }
    }
}