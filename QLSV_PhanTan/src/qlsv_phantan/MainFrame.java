package qlsv_phantan;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 * Form chính - Tra cứu thông tin sinh viên
 */
public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null); // Căn giữa màn hình
    }
    
    /**
     * Chức năng 1: Tìm Khoa của sinh viên theo MSSV
     * Logic: MSSV -> MaLop (từ SINHVIEN) -> MaKhoa (từ LOP) -> TenKhoa (từ KHOA)
     */
    private void timKhoaSinhVien(String mssv) {
        if (mssv.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã sinh viên!");
            return;
        }
        
        String sql = "SELECT K.TenKhoa " +
                     "FROM SINHVIEN SV " +
                     "INNER JOIN LOP L ON SV.MaLop = L.MaLop " +
                     "INNER JOIN KHOA K ON L.MaKhoa = K.MaKhoa " +
                     "WHERE SV.MSSV = ?";
        
        try (Connection conn = DriverManager.getConnection(ConnectionManager.CurrentConnectionString);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, mssv.trim());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String tenKhoa = rs.getString("TenKhoa");
                lblKetQuaKhoa.setText(tenKhoa);
            } else {
                lblKetQuaKhoa.setText("Không tìm thấy");
                JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên có MSSV: " + mssv);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Chức năng 2: Xem điểm các môn học của sinh viên
     * Logic: Lấy danh sách môn học từ bảng DANGKY và MONHOC
     */
    private void xemDiemSinhVien(String mssv) {
        if (mssv.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã sinh viên!");
            return;
        }
        
        String sql = "SELECT MH.TenMon, DK.Diem1, DK.Diem2, DK.Diem3 " +
                     "FROM DANGKY DK " +
                     "INNER JOIN MONHOC MH ON DK.MsMon = MH.MsMon " +
                     "WHERE DK.MSSV = ?";
        
        try (Connection conn = DriverManager.getConnection(ConnectionManager.CurrentConnectionString);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, mssv.trim());
            ResultSet rs = pstmt.executeQuery();
            
            DefaultTableModel model = new DefaultTableModel();
            String[] columnNames = {"Tên Môn", "Điểm 1", "Điểm 2", "Điểm 3"};
            model.setColumnIdentifiers(columnNames);
            
            int rowCount = 0;
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("TenMon"),
                    rs.getObject("Diem1") != null ? rs.getDouble("Diem1") : "",
                    rs.getObject("Diem2") != null ? rs.getDouble("Diem2") : "",
                    rs.getObject("Diem3") != null ? rs.getDouble("Diem3") : ""
                });
                rowCount++;
            }
            
            tblDiem.setModel(model);
            
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Sinh viên chưa đăng ký môn học nào!");
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMSSV = new javax.swing.JTextField();
        btnTraCuu = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblKetQuaKhoa = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDiem = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hệ thống Quản lý Sinh viên");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HỆ THỐNG TRA CỨU SINH VIÊN");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel2.setText("Nhập Mã Sinh Viên:");

        txtMSSV.setFont(new java.awt.Font("Segoe UI", 0, 14));

        btnTraCuu.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnTraCuu.setText("Tra cứu");
        btnTraCuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraCuuActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel3.setText("Tên Khoa:");

        lblKetQuaKhoa.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblKetQuaKhoa.setForeground(new java.awt.Color(0, 0, 255));
        lblKetQuaKhoa.setText("...");
        lblKetQuaKhoa.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel4.setText("Danh sách điểm các môn học:");

        tblDiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Tên Môn", "Điểm 1", "Điểm 2", "Điểm 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDiem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMSSV, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTraCuu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblKetQuaKhoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMSSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTraCuu))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblKetQuaKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTraCuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraCuuActionPerformed
        String mssv = txtMSSV.getText();
        timKhoaSinhVien(mssv);
        xemDiemSinhVien(mssv);
    }//GEN-LAST:event_btnTraCuuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTraCuu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblKetQuaKhoa;
    private javax.swing.JTable tblDiem;
    private javax.swing.JTextField txtMSSV;
    // End of variables declaration//GEN-END:variables
}
