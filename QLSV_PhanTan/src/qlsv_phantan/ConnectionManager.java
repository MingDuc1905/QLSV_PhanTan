/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlsv_phantan;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author ASUS
 */
public class ConnectionManager {
    // Tên máy chủ (host) và tên các instance thực tế trên SSMS
    public static final String SERVER_HOST = "MINGDUC"; // Host chung
    public static final String INSTANCE_ROOT = "SEVER01";   // Server gốc: MINGDUC\SEVER01
    public static final String INSTANCE_CNTT = "SEVER02";   // Server CNTT: MINGDUC\SEVER02
    public static final String INSTANCE_KINHTE = "SEVER03"; // Server Kinh Tế: MINGDUC\SEVER03
    public static final String INSTANCE_SUPHAP = "SEVER04"; // Server Sư phạm: MINGDUC\SEVER04

    // Optional: cổng tĩnh cho từng instance (đặt -1 nếu dùng tên instance + SQL Browser)
    public static final int PORT_ROOT = 1435;   // cổng cho SEVER01 (Server gốc - QuanLySinhVien)
    public static final int PORT_CNTT = 1436;   // cổng tĩnh cho SEVER02 (CNTT)
    public static final int PORT_KINHTE = 1433; // cổng tĩnh cho SEVER03 (Kinh Tế)
    public static final int PORT_SUPHAP = 1437; // cổng cho SEVER04 (Sư phạm)

    // Tên Database - Tất cả server đều dùng chung database QuanLySinhVien
    public static final String DATABASE_NAME = "QuanLySinhVien";
    
    // Chuỗi kết nối mẫu (sử dụng tài khoản sa, mật khẩu Tien123* cho đơn giản)
    private static final String CONN_STR_TEMPLATE = 
        "jdbc:sqlserver://%s;instanceName=%s;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true;";
    // Dạng theo cổng tĩnh
    private static final String CONN_STR_WITH_PORT_TEMPLATE = 
        "jdbc:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true;";

    public static String CurrentConnectionString = "";
    public static String CurrentInstance = ""; // Lưu: SEVER01/SEVER02/SEVER03

    // Phương thức tạo chuỗi kết nối
    public static String createConnectionString(String instance, String dbName, String user, String pass) {
        // instance phải là tên instance thuần: SEVER01/SEVER02/SEVER03
        return String.format(CONN_STR_TEMPLATE, SERVER_HOST, instance, dbName, user, pass);
    }

    // Tạo chuỗi kết nối: ưu tiên dùng cổng tĩnh nếu có cấu hình, ngược lại dùng tên instance
    public static String createConnectionStringPreferPort(String instance, String dbName, String user, String pass) {
        int port = -1;
        if (INSTANCE_CNTT.equals(instance)) port = PORT_CNTT;
        else if (INSTANCE_KINHTE.equals(instance)) port = PORT_KINHTE;
        else if (INSTANCE_ROOT.equals(instance)) port = PORT_ROOT;
        else if (INSTANCE_SUPHAP.equals(instance)) port = PORT_SUPHAP;

        if (port > 0) {
            return String.format(CONN_STR_WITH_PORT_TEMPLATE, SERVER_HOST, port, dbName, user, pass);
        }
        return createConnectionString(instance, dbName, user, pass);
    }
}
