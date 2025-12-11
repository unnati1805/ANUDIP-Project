# Hostel Room Allotment System (Java + MySQL)

This is a simple, beginner-friendly console application that demonstrates:
- Java (JDBC) connection to MySQL
- Basic CRUD operations for students & rooms
- Simple room allocation logic and capacity checks

## Files included
- `schema.sql` : SQL script to create database and tables
- `src/DBConnection.java` : JDBC connection helper (update DB credentials)
- `src/HostelSystem.java` : Main console application (menu-driven)
- `run.sh` / `run.bat` : helper scripts to compile & run
- `README.md` : this file

## Setup (quick)
1. Install Java JDK 11+ and MySQL server.
2. Run `schema.sql` in MySQL Workbench or via CLI:
   `mysql -u root -p < schema.sql`
3. Put MySQL Connector/J jar (mysql-connector-java-8.0.x.jar) into `lib/` or add it to your classpath.
4. Edit `src/DBConnection.java` to set your DB user & password.
5. Compile & run:
   - On Linux/macOS:
     ```
     mkdir -p out
     javac -cp "lib/mysql-connector-java-8.0.x.jar" -d out src/*.java
     java -cp "out:lib/mysql-connector-java-8.0.x.jar" HostelSystem
     ```
   - On Windows (CMD):
     ```
     mkdir out
     javac -cp "lib\mysql-connector-java-8.0.x.jar" -d out src\*.java
     java -cp "out;lib\mysql-connector-java-8.0.x.jar" HostelSystem
     ```

## Notes / Improvements
- Add validation (prevent duplicate room numbers, etc).
- Add deallocation feature and unique constraint on allocations.student_id if desired.
- Convert to Maven project or add GUI (Swing/JavaFX).

Enjoy — modify and extend as per your project needs!
