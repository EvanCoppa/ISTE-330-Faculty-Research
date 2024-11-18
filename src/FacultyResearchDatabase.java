// Irwin Lin 11/17/24

import java.sql.*;

public class FacultyResearchDatabase {

    private Connection conn;
    private String password;
    
    public FacultyResearchDatabase(String password) {
        this.password = password;
    }
    
    // Connect to the database
    public boolean connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/faculty_research?serverTimezone=UTC";
            String user = "root";
            conn = DriverManager.getConnection(url, user, password);
            return true;
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return false;
        }
    }

    // Add faculty with abstract
    public boolean addFaculty(String name, String building, String office, String email, String abstractText) {
        String sql = "INSERT INTO Faculty (name, building, office, email, abstract) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, building);
            stmt.setString(3, office);
            stmt.setString(4, email);
            stmt.setString(5, abstractText);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding faculty: " + e.getMessage());
            return false;
        }
    }

    // Add student with research interests
    public boolean addStudent(String name, String email, String[] keywords) {
        String sql = "INSERT INTO Student (name, email) VALUES (?, ?)";
        String keywordSql = "INSERT INTO Interest (student_email, keyword) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();

            try (PreparedStatement keywordStmt = conn.prepareStatement(keywordSql)) {
                for (String keyword : keywords) {
                    keywordStmt.setString(1, email);
                    keywordStmt.setString(2, keyword);
                    keywordStmt.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // Match student interests with faculty abstracts
    public void findMatches(String studentEmail) {
        String sql = "SELECT Faculty.name, Faculty.building, Faculty.office, Faculty.email "
                   + "FROM Faculty "
                   + "INNER JOIN Interest "
                   + "ON Faculty.abstract LIKE CONCAT('%', Interest.keyword, '%') "
                   + "WHERE Interest.student_email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentEmail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Matched Faculty: " + rs.getString("name") +
                                   " | Building: " + rs.getString("building") +
                                   " | Office: " + rs.getString("office") +
                                   " | Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Error finding matches: " + e.getMessage());
        }
    }

    // Search for faculty or students based on a keyword (External user functionality)
    public void searchByKeyword(String keyword) {
        String sql = "SELECT name, 'Faculty' AS type FROM Faculty WHERE abstract LIKE CONCAT('%', ?, '%') "
                   + "UNION "
                   + "SELECT name, 'Student' AS type FROM Student "
                   + "INNER JOIN Interest ON Student.email = Interest.student_email "
                   + "WHERE Interest.keyword = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, keyword);
            stmt.setString(2, keyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Matched " + rs.getString("type") + ": " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error searching by keyword: " + e.getMessage());
        }
    }
}
