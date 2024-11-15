import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyResearchApp {

    public static void main(String[] args) {
        FacultyResearchDatabase db = new FacultyResearchDatabase();
        if (db.connect()) {
            try {
                boolean exit = false;
                while (!exit) {
                    String[] options = {"Add Faculty", "Add Student", "Search Matches", "Exit"};
                    int choice = JOptionPane.showOptionDialog(null, "Select an option:", "Main Menu",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                    switch (choice) {
                        case 0 -> addFaculty(db);
                        case 1 -> addStudent(db);
                        case 2 -> searchMatches(db);
                        case 3 -> exit = true;
                        default -> exit = true;
                    }
                }
            } finally {
                db.close();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add Faculty
    private static void addFaculty(FacultyResearchDatabase db) {
        String firstName = JOptionPane.showInputDialog("Enter first name:");
        String lastName = JOptionPane.showInputDialog("Enter last name:");
        String building = JOptionPane.showInputDialog("Enter building number:");
        String office = JOptionPane.showInputDialog("Enter office number:");
        String email = JOptionPane.showInputDialog("Enter email:");
        String interest = JOptionPane.showInputDialog("Enter interest:");

        if (db.addFaculty(firstName, lastName, building, office, email, interest)) {
            JOptionPane.showMessageDialog(null, "Faculty added successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add faculty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add Student
    private static void addStudent(FacultyResearchDatabase db) {
        String firstName = JOptionPane.showInputDialog("Enter first name:");
        String lastName = JOptionPane.showInputDialog("Enter last name:");
        String email = JOptionPane.showInputDialog("Enter email:");
        String interest = JOptionPane.showInputDialog("Enter research topic:");

        if (db.addStudent(firstName, lastName, email, interest)) {
            JOptionPane.showMessageDialog(null, "Student added successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add student.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Search Matches
    private static void searchMatches(FacultyResearchDatabase db) {
        String keyword = JOptionPane.showInputDialog("Enter keyword to search:");
        ResultSet rs = db.searchMatches(keyword);

        if (rs != null) {
            try {
                StringBuilder results = new StringBuilder("Matches:\n");
                while (rs.next()) {
                    results.append(rs.getString("firstName")).append(" ")
                           .append(rs.getString("lastName")).append(" (")
                           .append(rs.getString("email")).append(")\n");
                }
                JOptionPane.showMessageDialog(null, results.toString());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error processing search results: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No matches found.");
        }
    }
}