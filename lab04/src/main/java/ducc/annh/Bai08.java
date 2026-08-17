package ducc.annh;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class Bai08 extends JFrame {

    private JButton btnChonFile;
    private JButton btnDocFile;

    private JLabel lblFile;
    private JLabel lblDiemTB;
    private JLabel lblCaoNhat;

    private JTable table;
    private DefaultTableModel model;

    private File fileDaChon;

    public Bai08() {

        setTitle("Thống kê điểm sinh viên");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        btnChonFile = new JButton("Chọn file");
        btnDocFile = new JButton("Đọc file");

        lblFile = new JLabel("Chưa chọn file");
        lblDiemTB = new JLabel("Điểm trung bình: 0");
        lblCaoNhat = new JLabel("Điểm cao nhất: ");


        String[] columns = {
            "Mã SV",
            "Họ tên",
            "Điểm"
        };

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);


        javax.swing.JPanel panelTop = new javax.swing.JPanel();
        panelTop.setLayout(new FlowLayout());

        panelTop.add(btnChonFile);
        panelTop.add(btnDocFile);


        javax.swing.JPanel panelInfo = new javax.swing.JPanel();
        panelInfo.setLayout(new FlowLayout(FlowLayout.LEFT));

        panelInfo.add(lblFile);
        panelInfo.add(lblDiemTB);
        panelInfo.add(lblCaoNhat);


        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelInfo, BorderLayout.SOUTH);


        btnChonFile.addActionListener(e -> chonFile());

        btnDocFile.addActionListener(e -> docFile());
    }


    private void chonFile() {

        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            fileDaChon = fileChooser.getSelectedFile();

            lblFile.setText(
                    "File: " + fileDaChon.getAbsolutePath()
            );

            // Xóa dữ liệu cũ trong bảng
            model.setRowCount(0);

            lblDiemTB.setText("Điểm trung bình: 0");
            lblCaoNhat.setText("Điểm cao nhất: ");
        }
    }


    private void docFile() {

        // Kiểm tra đã chọn file chưa
        if (fileDaChon == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn file trước!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Xóa dữ liệu cũ
        model.setRowCount(0);

        lblDiemTB.setText("Đang đọc...");
        lblCaoNhat.setText("Đang tìm...");

        // Khóa nút trong khi đọc
        btnChonFile.setEnabled(false);
        btnDocFile.setEnabled(false);


        SwingWorker<ThongKe, Object[]> worker =
                new SwingWorker<ThongKe, Object[]>() {

            @Override
            protected ThongKe doInBackground() throws Exception {

                double tongDiem = 0;
                double diemCaoNhat = -1;

                String maSVCaoNhat = "";
                String hoTenCaoNhat = "";

                int soSinhVien = 0;

                try (BufferedReader br =
                        new BufferedReader(
                                new FileReader(fileDaChon))) {

                    String line;

                    // Đọc dòng đầu tiên
                    // MaSV,HoTen,Diem
                    line = br.readLine();

                    // Đọc từng dòng sinh viên
                    while ((line = br.readLine()) != null) {

                        if (line.trim().isEmpty()) {
                            continue;
                        }

                        // Tách dữ liệu CSV
                        String[] data = line.split(",");

                        if (data.length >= 3) {

                            String maSV = data[0].trim();
                            String hoTen = data[1].trim();
                            double diem =
                                    Double.parseDouble(
                                            data[2].trim()
                                    );

                            soSinhVien++;

                            tongDiem += diem;

                            // Tìm điểm cao nhất
                            if (diem > diemCaoNhat) {

                                diemCaoNhat = diem;
                                maSVCaoNhat = maSV;
                                hoTenCaoNhat = hoTen;
                            }

                            // Gửi dữ liệu về JTable
                            publish(new Object[]{
                                maSV,
                                hoTen,
                                diem
                            });
                        }
                    }
                }

                double diemTB = 0;

                if (soSinhVien > 0) {
                    diemTB = tongDiem / soSinhVien;
                }

                return new ThongKe(
                        diemTB,
                        diemCaoNhat,
                        maSVCaoNhat,
                        hoTenCaoNhat
                );
            }


            @Override
            protected void process(
                    java.util.List<Object[]> chunks) {

                for (Object[] row : chunks) {

                    model.addRow(row);
                }
            }


            @Override
            protected void done() {

                try {

                    ThongKe ketQua = get();

                    lblDiemTB.setText(
                            String.format(
                                    "Điểm trung bình: %.2f",
                                    ketQua.diemTB
                            )
                    );

                    lblCaoNhat.setText(
                            String.format(
                                    "Điểm cao nhất: %.1f - %s (%s)",
                                    ketQua.diemCaoNhat,
                                    ketQua.hoTenCaoNhat,
                                    ketQua.maSVCaoNhat
                            )
                    );

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(
                            Bai08.this,
                            "Lỗi khi đọc file: "
                            + e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );

                } finally {

                    btnChonFile.setEnabled(true);
                    btnDocFile.setEnabled(true);
                }
            }
        };

        // Bắt đầu chạy
        worker.execute();
    }


    private static class ThongKe {

        double diemTB;
        double diemCaoNhat;

        String maSVCaoNhat;
        String hoTenCaoNhat;

        public ThongKe(
                double diemTB,
                double diemCaoNhat,
                String maSVCaoNhat,
                String hoTenCaoNhat) {

            this.diemTB = diemTB;
            this.diemCaoNhat = diemCaoNhat;
            this.maSVCaoNhat = maSVCaoNhat;
            this.hoTenCaoNhat = hoTenCaoNhat;
        }
    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            new Bai08().setVisible(true);

        });
    }
}