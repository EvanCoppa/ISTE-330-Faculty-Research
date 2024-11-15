-- Raika Kamalaraj, Renny Lin
-- 11/08/24
-- ISTE 330 
-- Faculty Research Database

-- create and use the database
DROP DATABASE IF EXISTS faculty_research;
CREATE DATABASE faculty_research;

USE faculty_research;

-- creating the tables
CREATE TABLE Account (
    accountID INT PRIMARY KEY,
    email VARCHAR(55) UNIQUE,
    password VARCHAR(40),
    type ENUM('Faculty', 'Student')
);

CREATE TABLE Faculty (
    facultyID INT PRIMARY KEY,
    firstName VARCHAR(30),
    lastName VARCHAR(30),
    phoneNumber INT,
    buildingNumber VARCHAR(10),
    officeNumber VARCHAR(10),
    email VARCHAR(55),
    CONSTRAINT faculty_ibfk_1 FOREIGN KEY (email) REFERENCES Account(email)
);

CREATE TABLE Abstract (
    abstractID INT PRIMARY KEY,
    abstractFile MEDIUMTEXT
);

CREATE TABLE Faculty_Abstract (
    facultyID INT,
    abstractID INT,
    CONSTRAINT fa_pk PRIMARY KEY (facultyID, abstractID),
    CONSTRAINT fa_facultyID_fk FOREIGN KEY (facultyID) REFERENCES Faculty(facultyID),
    CONSTRAINT fa_abstractID_fk FOREIGN KEY (abstractID) REFERENCES Abstract(abstractID)
);

CREATE TABLE Interest (
    ID INT PRIMARY KEY,
    name VARCHAR(55),
    interestDescription MEDIUMTEXT
);

CREATE TABLE Faculty_Interests (
    facultyID INT,
    interestID INT,
    CONSTRAINT fi_pk PRIMARY KEY (facultyID, interestID),
    CONSTRAINT fi_facultyID_fk FOREIGN KEY (facultyID) REFERENCES Faculty(facultyID),
    CONSTRAINT fi_interestID_fk FOREIGN KEY (interestID) REFERENCES Interest(ID)
);

CREATE TABLE Student (
    studentID INT PRIMARY KEY,
    firstName VARCHAR(30),
    lastName VARCHAR(30),
    phone VARCHAR(30),
    email VARCHAR(55),
    majorID INT,
    CONSTRAINT student_fk FOREIGN KEY (email) REFERENCES Account(email)
);

CREATE TABLE Student_Interest (
    studentID INT,
    interestID INT,
    CONSTRAINT si_pk PRIMARY KEY (studentID, interestID),
    CONSTRAINT si_studentID_fk FOREIGN KEY (studentID) REFERENCES Student(studentID),
    CONSTRAINT si_interestID_fk FOREIGN KEY (interestID) REFERENCES Interest(ID)
);

CREATE TABLE Public_Users (
    userID INT PRIMARY KEY,
    firstName VARCHAR(30),
    lastName VARCHAR(30),
    phone VARCHAR(30),
    email VARCHAR(55),
    CONSTRAINT pu_fk FOREIGN KEY (email) REFERENCES Account(email)
);

-- inserting into the tables

-- Insert into Account table
INSERT INTO Account (accountID, email, password, type) VALUES
(1, 'jdoe@university.edu', 'password123', 'Faculty'),
(2, 'asmith@university.edu', 'password456', 'Student'),
(3, 'jane.public@domain.com', 'guestpass', 'Public');

-- Insert into Faculty table
INSERT INTO Faculty (facultyID, firstName, lastName, phoneNumber, buildingNumber, officeNumber, email) VALUES
(1, 'John', 'Doe', 1234567890, 'Bldg1', '101', 'jdoe@university.edu');

-- Insert into Abstract table
INSERT INTO Abstract (abstractID, abstractFile) VALUES
(1, 'Research on quantum computing breakthroughs.'),
(2, 'Study on AI impact on education.');

-- Insert into Faculty_Abstract table
INSERT INTO Faculty_Abstract (facultyID, abstractID) VALUES
(1, 1),
(1, 2);

-- Insert into Interest table
INSERT INTO Interest (ID, name, interestDescription) VALUES
(1, 'Artificial Intelligence', 'Study and application of AI techniques.'),
(2, 'Quantum Computing', 'Exploring advancements in quantum technologies.');

-- Insert into Faculty_Interests table
INSERT INTO Faculty_Interests (facultyID, interestID) VALUES
(1, 1),
(1, 2);

-- Insert into Student table
INSERT INTO Student (studentID, firstName, lastName, phone, email, majorID) VALUES
(1, 'Alice', 'Smith', '9876543210', 'asmith@university.edu', 101);

-- Insert into Student_Interest table
INSERT INTO Student_Interest (studentID, interestID) VALUES
(1, 1);

-- Insert into Public_Users table
INSERT INTO Public_Users (userID, firstName, lastName, phone, email) VALUES
(1, 'Jane', 'Public', '5551234567', 'jane.public@domain.com');