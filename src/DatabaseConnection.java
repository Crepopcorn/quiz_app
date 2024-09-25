import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/quiz_app";  // Update DB name if needed
    private static final String USER = "root";  // DB user
    private static final String PASSWORD = "Mypwd_3016";  // DB password

    // Get connection to DB
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Handle driver not found
            System.out.println("Driver not found: " + e.getMessage());
        }

        // Return DB connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

