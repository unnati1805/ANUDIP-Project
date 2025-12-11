CREATE DATABASE IF NOT EXISTS hostel_db;
USE hostel_db;

CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    branch VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS rooms (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    room_number VARCHAR(10),
    capacity INT,
    allocated INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS allocations (
    allocation_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    room_id INT,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);
USE hostel_db;

INSERT INTO students (name, branch) VALUES
('Ankit Sharma', 'CSE'),
('Priya Verma', 'ECE'),
('Rohit Singh', 'ME'),
('Neha Gupta', 'CSE');

INSERT INTO rooms (room_number, capacity, allocated) VALUES
('A101', 2, 0),
('A102', 3, 0),
('B201', 2, 0),
('C301', 1, 0);

INSERT INTO allocations (student_id, room_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3);


