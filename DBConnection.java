import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hostel_db?allowPublicKeyRetrieval=true&useSSL=false",
                "root",
                "password"
            );
        } catch (Exception e) {
            System.out.println("DB connection failed: " + e.getMessage());
        }
        return con;
    }
}

