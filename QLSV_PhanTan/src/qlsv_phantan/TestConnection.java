package qlsv_phantan;

import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        // Test nhiều kết nối khác nhau
        String[][] tests = {
            {"MINGDUC\\SEVER01", "QuanLySinhVien", "sa", "123456"},
            {"MINGDUC\\SEVER01", "master", "sa", "123456"},
            {"localhost\\SEVER01", "QuanLySinhVien", "sa", "123456"},
            {"127.0.0.1", "QuanLySinhVien", "sa", "123456"}
        };
        
        for (String[] test : tests) {
            String server = test[0];
            String db = test[1];
            String user = test[2];
            String pass = test[3];
            
            System.out.println("\n========================================");
            System.out.println("Testing: " + server + " | DB: " + db);
            System.out.println("========================================");
            
            // Test với instance name
            if (server.contains("\\")) {
                String[] parts = server.split("\\\\");
                String connStr = String.format(
                    "jdbc:sqlserver://%s;instanceName=%s;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true;",
                    parts[0], parts[1], db, user, pass
                );
                testConnection(connStr);
            } else {
                // Test với IP
                String connStr = String.format(
                    "jdbc:sqlserver://%s;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true;",
                    server, db, user, pass
                );
                testConnection(connStr);
            }
            
            // Test với port 1433
            String connStrPort = String.format(
                "jdbc:sqlserver://%s:1433;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true;",
                server.split("\\\\")[0], db, user, pass
            );
            System.out.println("\nTest với port 1433:");
            testConnection(connStrPort);
        }
    }
    
    private static void testConnection(String connStr) {
        System.out.println("Connection String: " + maskPassword(connStr));
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(connStr);
            System.out.println("✅ KẾT NỐI THÀNH CÔNG!");
            System.out.println("Database: " + conn.getCatalog());
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Lỗi: Không tìm thấy JDBC Driver");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kết nối: " + e.getMessage());
        }
    }
    
    private static String maskPassword(String connStr) {
        return connStr.replaceAll("password=[^;]+", "password=****");
    }
}
