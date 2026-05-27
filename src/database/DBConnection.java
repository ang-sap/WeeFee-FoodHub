package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database URL
    // localhost = your own computer/server
    // 1433 = default SQL Server port
    // WeeFeeFoodHub = database name
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=WeeFeeFoodHub;encrypt=true;trustServerCertificate=true;";

    // SQL Server username
    private static final String USER = "sa";

    // SQL Server password
    private static final String PASSWORD = "admin";

    // Method used to connect the Java program to the database
    public static Connection getConnection() {

        // Connection object that will store the database connection
        Connection conn = null;

        try {

            // Loads the SQL Server JDBC Driver
            // This allows Java to communicate with SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Creates the connection using the URL, username, and password
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Message shown if connection is successful
            System.out.println("SUCCESS: Connected to WeeFeeFoodHub Database!");

        } catch (ClassNotFoundException e) {

            // Runs if the JDBC Driver is missing
            System.out.println("ERROR: JDBC Driver not found. Did you add the JAR to your libraries?");
            e.printStackTrace();

        } catch (SQLException e) {

            // Runs if database connection fails
            // Possible reasons:
            // - Wrong username/password
            // - SQL Server is not running
            // - Wrong port or database name
            System.out.println("ERROR: Database connection failed. Check your SQL server status, port, or credentials.");
            e.printStackTrace();
        }

        // Returns the database connection
        return conn;
    }
}
