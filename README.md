# QLSV_PhanTan - Hệ Thống Quản Lý Sinh Viên Phân Tán

Một ứng dụng Java Swing quản lý thông tin sinh viên, lớp học, môn học và điểm số. Xây dựng dựa trên kiến trúc phân tán.

## 📋 Tính Năng Chính

- ✅ Quản lý sinh viên
- ✅ Quản lý lớp học và khoa
- ✅ Quản lý môn học
- ✅ Quản lý đăng ký môn học và điểm số
- ✅ Giao diện người dùng thân thiện (Java Swing)

## 🗄️ Database Schema

### ⭐ **[DB.sql](./DB.sql)** - CÓ THỂ XỬ LÝ TẬT CẢ CÁC BẢNG DỮ LIỆU

File cơ sở dữ liệu SQL Server chứa:
- **4 bảng chính**: KHOA, LOP, SINHVIEN, MONHOC, DANGKY
- **Dữ liệu mẫu** đầy đủ để test ứng dụng
- **Mối quan hệ** khóa ngoại giữa các bảng

**Chạy file này trên SQL Server để tạo database mới!**

```
KHOA (Khoa)
├── MaKhoa (PK)
└── TenKhoa

LOP (Lớp)
├── MaLop (PK)
├── TenLop
└── MaKhoa (FK)

SINHVIEN (Sinh Viên)
├── MSSV (PK)
├── HoTen
├── GioiTinh
├── NgaySinh
├── HocBong
└── MaLop (FK)

MONHOC (Môn Học)
├── MsMon (PK)
└── TenMon

DANGKY (Đăng Ký)
├── MSSV (FK, PK)
├── MsMon (FK, PK)
├── Diem1
├── Diem2
└── Diem3
```

## 🛠️ Công Nghệ Sử Dụng

- **Ngôn ngữ**: Java
- **Giao diện**: Java Swing
- **Cơ sở dữ liệu**: SQL Server
- **Build tool**: Ant (NetBeans)

## 📁 Cấu Trúc Dự Án

```
QLSV_PhanTan/
├── src/
│   └── qlsv_phantan/
│       ├── QLSV_PhanTan.java (Main class)
│       ├── LoginFrame.java
│       ├── MainFrame.java
│       ├── ConnectionManager.java
│       └── TestConnection.java
├── DB.sql ⭐ (Database initialization)
├── build.xml (Ant build file)
└── nbproject/ (NetBeans project configuration)
```

## 🚀 Cách Chạy Ứng Dụng

### 1. Tạo Database
```sql
-- Mở SQL Server Management Studio
-- Chạy tệp DB.sql để tạo database và dữ liệu mẫu
```

### 2. Cập nhật Connection String
Sửa thông tin kết nối trong `ConnectionManager.java`:
```java
String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLySinhVien;";
String user = "sa"; // Username của bạn
String password = ""; // Password của bạn
```

### 3. Compile & Run
**Với NetBeans:**
- Mở project trong NetBeans IDE
- Nhấn F6 (Run Project)

**Hoặc dùng Ant:**
```bash
ant run
```

## 📝 Thông Tin Đăng Nhập

Hiện tại ứng dụng có form đăng nhập. Bạn có thể sửa đổi logic xác thực trong `LoginFrame.java`.

## 👨‍💻 Tác Giả

MingDuc1905

## 📄 Giấy Phép

MIT License

---

**⚠️ Lưu ý**: Đảm bảo rằng SQL Server đã được cài đặt và đang chạy trước khi khởi động ứng dụng!
