package ezycommerce.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {

    
    private static final String URL      = "jdbc:mysql://localhost:3306/ezycommerce";
    private static final String USER     = "root";
    private static final String PASSWORD = "1234";

    
    private static Connection connection = null;

    
    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connected to database!");
            }
        } catch (Exception e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
        }
        return connection;
    }

    
    public static void testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                System.out.println("✅ Database connection is working!");
            } else {
                System.out.println("❌ Database connection failed!");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    
    public static void showAllProducts() {
        try {
            Connection conn = getConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery("SELECT * FROM products");

            System.out.println("\n--- Products from Database ---");
            while (rs.next()) {
                System.out.println(
                    "ID: "       + rs.getInt("id")       +
                    " | Name: "  + rs.getString("name")  +
                    " | Price: $" + rs.getDouble("price") +
                    " | Stock: " + rs.getInt("stock")
                );
            }
        } catch (Exception e) {
            System.out.println("❌ Error fetching products: " + e.getMessage());
        }
    }

    
    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
                System.out.println("✅ Connection closed!");
            }
        } catch (Exception e) {
            System.out.println("❌ Error closing connection: " + e.getMessage());
        }
    }

    
    public static void main(String[] args) {
        testConnection();
        showAllProducts();
        closeConnection();
    }
}