package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=WeeFeeFoodHub;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "admin";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("SUCCESS: Connected to WeeFeeFoodHub Database!");

        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: JDBC Driver not found. Did you add the JAR to your libraries?");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("ERROR: Database connection failed. Check your SQL server status, port, or credentials.");
            e.printStackTrace();
        }
        return conn;
    }
}
