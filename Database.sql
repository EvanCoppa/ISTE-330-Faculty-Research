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

