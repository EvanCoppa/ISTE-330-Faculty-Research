-- Create the database and tables
DROP DATABASE IF EXISTS faculty_research;
CREATE DATABASE faculty_research;

USE faculty_research;

CREATE TABLE Account (
    accountID INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(55) UNIQUE NOT NULL,
    password VARCHAR(40) NOT NULL,
    type ENUM('Faculty', 'Student', 'Public') NOT NULL
);

CREATE TABLE Faculty (
    facultyID INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(30) NOT NULL,
    phoneNumber VARCHAR(15),
    buildingNumber VARCHAR(10),
    officeNumber VARCHAR(10),
    email VARCHAR(55) UNIQUE NOT NULL
);

CREATE TABLE Abstract (
    abstractID INT AUTO_INCREMENT PRIMARY KEY,
    abstractFile MEDIUMTEXT NOT NULL
);

CREATE TABLE Faculty_Abstract (
    facultyID INT NOT NULL,
    abstractID INT NOT NULL,
    PRIMARY KEY (facultyID, abstractID)
);

CREATE TABLE Interest (
    interestID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(55) NOT NULL,
    interestDescription MEDIUMTEXT
);

CREATE TABLE Faculty_Interests (
    facultyID INT NOT NULL,
    interestID INT NOT NULL,
    PRIMARY KEY (facultyID, interestID)
);

CREATE TABLE Student (
    studentID INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(30) NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(55) UNIQUE NOT NULL,
    majorID INT
);

CREATE TABLE Student_Interest (
    studentID INT NOT NULL,
    interestID INT NOT NULL,
    PRIMARY KEY (studentID, interestID)
);

CREATE TABLE Public_Users (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(30) NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(55) UNIQUE NOT NULL
);

-- Insert example data into Account table
INSERT INTO Account (email, password, type) VALUES
('jdoe@university.edu', 'securepass123', 'Faculty'),
('asmith@university.edu', 'password321', 'Student'),
('mjones@public.com', 'publicuser', 'Public');

-- Insert example data into Faculty table
INSERT INTO Faculty (firstName, lastName, phoneNumber, buildingNumber, officeNumber, email) VALUES
('John', 'Doe', '5551234567', 'B12', 'R207', 'jdoe@university.edu');

-- Insert example data into Abstract table
INSERT INTO Abstract (abstractFile) VALUES
('This is an abstract about machine learning.'),
('This is an abstract about data visualization.');

-- Insert example data into Faculty_Abstract table
INSERT INTO Faculty_Abstract (facultyID, abstractID) VALUES
(1, 1),
(1, 2);

-- Insert example data into Interest table
INSERT INTO Interest (name, interestDescription) VALUES
('Machine Learning', 'Study of algorithms and statistical models.'),
('Data Visualization', 'Techniques for visual representation of data.');

-- Insert example data into Faculty_Interests table
INSERT INTO Faculty_Interests (facultyID, interestID) VALUES
(1, 1),
(1, 2);

-- Insert example data into Student table
INSERT INTO Student (firstName, lastName, phone, email, majorID) VALUES
('Alice', 'Smith', '5559876543', 'asmith@university.edu', 101);

-- Insert example data into Student_Interest table
INSERT INTO Student_Interest (studentID, interestID) VALUES
(1, 1);

-- Insert example data into Public_Users table
INSERT INTO Public_Users (firstName, lastName, phone, email) VALUES
('Michael', 'Jones', '5557654321', 'mjones@public.com');