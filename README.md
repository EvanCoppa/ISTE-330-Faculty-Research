
# ISTE-330 Faculty Research Database

The Faculty Research Database is a Java-based application that helps students, faculty, and public users collaborate and connect based on shared research interests. The database is implemented using MySQL, and the application features a graphical user interface (GUI) for intuitive interaction.

## Features

Faculty can add, update, and delete personal research abstracts and interests. They can also search for students with matching research interests.

Students can add their research topics and find faculty with similar interests for capstone or thesis projects.

Public users can add keywords and search for faculty or students with expertise in specific areas.

The application allows for search matching to find intersections between faculty abstracts, student interests, and public keywords to foster collaboration.

## Prerequisites

You need the Java Development Kit (JDK) 11 or higher, MySQL Server, and Visual Studio Code with Java extensions. Update the `FacultyResearchDatabase` class to use your database password directly in the connection string.

## Installation

Clone the repository:

```bash
git clone https://github.com/evancoppa/ISTE-330-Faculty-Research-.git
cd ISTE-330-Faculty-Research-
```
Set up the database by running the SQL file:

mysql -u root -p < src/resources/Database.sql

Update the FacultyResearchDatabase class in your source code to include your MySQL password in the connection string.

Compile and run the application using Visual Studio Code or the command line:

javac -d bin src/*.java
java -cp bin FacultyResearchApp

Folder Structure

The src folder contains the source code, split into database and presentation layers. The FacultyResearchDatabase.java file handles database operations, while FacultyResearchApp.java manages the GUI and user interactions. The resources folder contains the SQL file for setting up the database, and the bin folder contains compiled .class files.

Usage

Faculty can input their name, building, office, email, and research interest through the GUI. They can search for students with overlapping research topics.

Students can input their personal details and research topics (1-3 keywords) and search for faculty members with matching interests.

Public users can add keywords to find faculty or students for workshops or presentations.

Contributing

Contributions are welcome. Fork the repository and submit a pull request with your changes. Ensure that your code is well-documented and follows the existing coding standards.


Contact

For any inquiries, please contact:

Author: Evan Coppa
Email: evancoppa@gmail.com
GitHub: evancoppa
