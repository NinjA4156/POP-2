package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {

    // JDBC URL for MySQL database
    private static final String JDBC_URL ="jdbc:mysql://localhost:3306/ServiceManagement";
    // Database credentials
    private static final String USERNAME="root";
    private static final String PASSWORD = "41561011p";

    // Method to establish a connection to the MySQL database
    public static Connection getConnection() throws SQLException {
        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found!", e);
        }
        // Establish the connection
        return DriverManager.getConnection(JDBC_URL, USERNAME,PASSWORD);
    }

    // Test the connection
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to MySQL database!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
//--module-path "C:\Softwares\javafx-sdk-22\lib" --add-modules javafx.controls,javafx.fxml