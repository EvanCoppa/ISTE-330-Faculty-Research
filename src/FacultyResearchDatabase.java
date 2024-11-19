// Irwin Lin 11/17/24

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public boolean addFaculty(String firstName, String lastName, String buildingNumber, String officeNumber, String email, String phoneNumber) {
        String sql = "INSERT INTO Faculty (firstName, lastName, buildingNumber, officeNumber, email, phoneNumber) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, buildingNumber);
            stmt.setString(4, officeNumber);
            stmt.setString(5, email);
            stmt.setString(6, phoneNumber);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding faculty: " + e.getMessage());
            return false;
        }
    }

    // Add student with research interests
    public boolean addStudent(String firstName, String lastName, String phone, String email, String[] keywords) {
        String studentSql = "INSERT INTO Student (firstName, lastName, phone, email) VALUES (?, ?, ?, ?)";
        String interestSql = "INSERT INTO Interest (name) VALUES (?) ON DUPLICATE KEY UPDATE interestID=LAST_INSERT_ID(interestID)";
        String studentInterestSql = "INSERT INTO Student_Interest (studentID, interestID) VALUES (?, ?)";
    
        try (PreparedStatement studentStmt = conn.prepareStatement(studentSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement interestStmt = conn.prepareStatement(interestSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement studentInterestStmt = conn.prepareStatement(studentInterestSql)) {
    
            // Insert student into Student table
            studentStmt.setString(1, firstName);
            studentStmt.setString(2, lastName);
            studentStmt.setString(3, phone);
            studentStmt.setString(4, email);
            studentStmt.executeUpdate();
    
            // Retrieve the generated studentID
            ResultSet studentKeys = studentStmt.getGeneratedKeys();
            if (!studentKeys.next()) {
                throw new SQLException("Failed to retrieve studentID.");
            }
            int studentID = studentKeys.getInt(1);
    
            // Add keywords and link to the student
            for (String keyword : keywords) {
                keyword = keyword.trim(); // Remove extra spaces
    
                // Insert keyword into Interest table
                interestStmt.setString(1, keyword);
                interestStmt.executeUpdate();
    
                // Retrieve the generated or existing interestID
                ResultSet interestKeys = interestStmt.getGeneratedKeys();
                if (!interestKeys.next()) {
                    throw new SQLException("Failed to retrieve interestID for keyword: " + keyword);
                }
                int interestID = interestKeys.getInt(1);
    
                // Link studentID and interestID in Student_Interest table
                studentInterestStmt.setInt(1, studentID);
                studentInterestStmt.setInt(2, interestID);
                studentInterestStmt.executeUpdate();
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
    

public String[] searchByKeyword(String keyword) {
    String sql = """
        SELECT 'Faculty' AS type, Faculty.firstName, Faculty.lastName
        FROM Faculty
        JOIN Faculty_Interests ON Faculty.facultyID = Faculty_Interests.facultyID
        JOIN Interest ON Faculty_Interests.interestID = Interest.interestID
        WHERE Interest.name = ?
        UNION
        SELECT 'Student' AS type, Student.firstName, Student.lastName
        FROM Student
        JOIN Student_Interest ON Student.studentID = Student_Interest.studentID
        JOIN Interest ON Student_Interest.interestID = Interest.interestID
        WHERE Interest.name = ?
    """;

    List<String> results = new ArrayList<>();

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, keyword);
        stmt.setString(2, keyword);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String type = rs.getString("type");
            String name = rs.getString("firstName") + " " + rs.getString("lastName");
            results.add(type + ": " + name);
        }
    } catch (SQLException e) {
        System.out.println("Error searching by keyword: " + e.getMessage());
    }

    if (results.isEmpty()) {
        results.add("No matches found for the keyword: " + keyword);
    }

    return results.toArray(new String[0]);
}

    
}
