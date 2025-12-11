import java.sql.*;
import java.util.Scanner;

public class HostelSystem {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== HOSTEL ROOM ALLOCATION SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Room");
            System.out.println("3. Allocate Room to Student");
            System.out.println("4. Show Available Rooms");
            System.out.println("5. Show All Students");
            System.out.println("6. Show Allocations");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> addStudent();
                case 2 -> addRoom();
                case 3 -> allocateRoom();
                case 4 -> showAvailableRooms();
                case 5 -> showAllStudents();
                case 6 -> showAllocations();
                case 7 -> { System.out.println("Goodbye!"); System.exit(0); }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    private static void addStudent() {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) { System.out.println("Cannot connect to DB"); return; }
            sc.nextLine();
            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Branch: ");
            String branch = sc.nextLine();

            String query = "INSERT INTO students(name, branch) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, branch);
            ps.executeUpdate();
            System.out.println("Student Added Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addRoom() {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) { System.out.println("Cannot connect to DB"); return; }
            sc.nextLine();
            System.out.print("Enter Room Number: ");
            String roomNo = sc.nextLine();
            System.out.print("Enter Room Capacity: ");
            int cap = sc.nextInt();

            String query = "INSERT INTO rooms(room_number, capacity) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, roomNo);
            ps.setInt(2, cap);
            ps.executeUpdate();
            System.out.println("Room Added Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void allocateRoom() {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) { System.out.println("Cannot connect to DB"); return; }
            System.out.print("Enter Student ID: ");
            int sid = sc.nextInt();
            System.out.print("Enter Room ID: ");
            int rid = sc.nextInt();

            // Check student exists
            PreparedStatement psStu = con.prepareStatement("SELECT * FROM students WHERE student_id = ?");
            psStu.setInt(1, sid);
            ResultSet rsStu = psStu.executeQuery();
            if (!rsStu.next()) { System.out.println("Student not found."); return; }

            // Check room capacity
            String check = "SELECT capacity, allocated FROM rooms WHERE room_id=?";
            PreparedStatement psCheck = con.prepareStatement(check);
            psCheck.setInt(1, rid);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                int cap = rs.getInt("capacity");
                int alloc = rs.getInt("allocated");

                // Check if student already allocated
                PreparedStatement psDup = con.prepareStatement("SELECT * FROM allocations WHERE student_id = ?");
                psDup.setInt(1, sid);
                ResultSet rsDup = psDup.executeQuery();
                if (rsDup.next()) {
                    System.out.println("Student already has an allocation.");
                    return;
                }

                if (alloc < cap) {
                    String insert = "INSERT INTO allocations(student_id, room_id) VALUES (?, ?)";
                    PreparedStatement ps = con.prepareStatement(insert);
                    ps.setInt(1, sid);
                    ps.setInt(2, rid);
                    ps.executeUpdate();

                    String update = "UPDATE rooms SET allocated = allocated + 1 WHERE room_id=?";
                    PreparedStatement psUpdate = con.prepareStatement(update);
                    psUpdate.setInt(1, rid);
                    psUpdate.executeUpdate();

                    System.out.println("Room Allocated Successfully!");
                } else {
                    System.out.println("Room is Already Full!");
                }
            } else {
                System.out.println("Room Not Found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showAvailableRooms() {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) { System.out.println("Cannot connect to DB"); return; }
            String query = "SELECT * FROM rooms WHERE allocated < capacity";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println("\nAvailable Rooms:");
            while (rs.next()) {
                System.out.println("Room ID: " + rs.getInt("room_id") +
                        " | Room No: " + rs.getString("room_number") +
                        " | Capacity: " + rs.getInt("capacity") +
                        " | Allocated: " + rs.getInt("allocated"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showAllStudents() {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) { System.out.println("Cannot connect to DB"); return; }
            String query = "SELECT * FROM students";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println("\nStudents:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("student_id") +
                        " | Name: " + rs.getString("name") +
                        " | Branch: " + rs.getString("branch"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showAllocations() {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) { System.out.println("Cannot connect to DB"); return; }
            String query = "SELECT a.allocation_id, s.student_id, s.name, r.room_id, r.room_number FROM allocations a " +
                    "JOIN students s ON a.student_id = s.student_id " +
                    "JOIN rooms r ON a.room_id = r.room_id";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println("\nAllocations:");
            while (rs.next()) {
                System.out.println("Alloc ID: " + rs.getInt("allocation_id") +
                        " | Student ID: " + rs.getInt("student_id") +
                        " | Name: " + rs.getString("name") +
                        " | Room ID: " + rs.getInt("room_id") +
                        " | Room No: " + rs.getString("room_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
