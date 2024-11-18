// Irwin Lin 11/017/24

import javax.swing.*;
import java.sql.SQLException;

public class FacultyResearchApp {

    public static void main(String[] args) {
        String defaultPassword = "student"; 
        String inputPassword = JOptionPane.showInputDialog("Enter the database password (Leave blank for default):");
        String password = (inputPassword == null || inputPassword.trim().isEmpty()) ? defaultPassword : inputPassword;

        FacultyResearchDatabase db = new FacultyResearchDatabase(password);

        if (db.connect()) {
            boolean exit = false;

            while (!exit) {
                String[] options = {"Add Faculty", "Add Student", "Find Matches", "Keyword Search", "Exit"};
                int choice = JOptionPane.showOptionDialog(null, "Select an option", "Faculty Research App",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                try {
                    switch (choice) {
                        case 0:
                            addFaculty(db);
                            break;
                        case 1: 
                            addStudent(db);
                            break;
                        case 2: 
                            findMatches(db);
                            break;
                        case 3: 
                            keywordSearch(db);
                            break;
                        case 4: 
                            exit = true;
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database. Exiting.");
        }
    }
    
    // Add faculty functionality
    private static void addFaculty(FacultyResearchDatabase db) throws SQLException {
        String name = JOptionPane.showInputDialog("Enter Faculty Name:");
        String building = JOptionPane.showInputDialog("Enter Building:");
        String office = JOptionPane.showInputDialog("Enter Office Number:");
        String email = JOptionPane.showInputDialog("Enter Email:");
        String abstractText = JOptionPane.showInputDialog("Enter Abstract:");

        if (db.addFaculty(name, building, office, email, abstractText)) {
            JOptionPane.showMessageDialog(null, "Faculty added successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add faculty.");
        }
    }

    // Add student functionality
    private static void addStudent(FacultyResearchDatabase db) throws SQLException {
        String name = JOptionPane.showInputDialog("Enter Student Name:");
        String email = JOptionPane.showInputDialog("Enter Email:");
        String keywords = JOptionPane.showInputDialog("Enter Research Keywords (comma-separated):");

        String[] keywordArray = keywords.split(",");
        if (db.addStudent(name, email, keywordArray)) {
            JOptionPane.showMessageDialog(null, "Student added successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add student.");
        }
    }

    // Find matches functionality
    private static void findMatches(FacultyResearchDatabase db) throws SQLException {
        String studentEmail = JOptionPane.showInputDialog("Enter Student Email:");
        JOptionPane.showMessageDialog(null, "Matching faculty to student interests:");
        db.findMatches(studentEmail);
    }

    // Keyword search functionality
    private static void keywordSearch(FacultyResearchDatabase db) throws SQLException {
        String keyword = JOptionPane.showInputDialog("Enter Keyword for Search:");
        JOptionPane.showMessageDialog(null, "Search Results:");
        db.searchByKeyword(keyword);
    }
}
