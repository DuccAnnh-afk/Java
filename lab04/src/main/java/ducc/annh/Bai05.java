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
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class Bai05 extends JFrame {

    private JButton btnChonFile;
    private JButton btnDemDong;
    private JLabel lblFile;
    private JLabel lblKetQua;
    private JProgressBar progressBar;

    private File fileDaChon;

    public Bai05() {
        setTitle("Đếm số dòng trong file");
        setSize(600, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tạo các thành phần
        btnChonFile = new JButton("Chọn file");
        btnDemDong = new JButton("Đếm dòng");

        lblFile = new JLabel("Chưa chọn file");
        lblKetQua = new JLabel("Số dòng: 0");

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        // Panel chứa nút
        javax.swing.JPanel panelButton = new javax.swing.JPanel();
        panelButton.setLayout(new FlowLayout());
        panelButton.add(btnChonFile);
        panelButton.add(btnDemDong);

        // Panel thông tin
        javax.swing.JPanel panelInfo = new javax.swing.JPanel();
        panelInfo.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelInfo.add(lblFile);
        panelInfo.add(lblKetQua);

        // Thêm vào giao diện
        add(panelButton, BorderLayout.NORTH);
        add(panelInfo, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);

        // Sự kiện chọn file
        btnChonFile.addActionListener(e -> chonFile());

        // Sự kiện đếm dòng
        btnDemDong.addActionListener(e -> demDong());
    }

    private void chonFile() {
        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            fileDaChon = fileChooser.getSelectedFile();

            lblFile.setText("File: " + fileDaChon.getAbsolutePath());
            lblKetQua.setText("Số dòng: 0");
            progressBar.setValue(0);
        }
    }

    private void demDong() {

        if (fileDaChon == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn file trước!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Không cho bấm nút trong lúc đang đọc file
        btnChonFile.setEnabled(false);
        btnDemDong.setEnabled(false);

        progressBar.setValue(0);
        lblKetQua.setText("Đang đếm...");

        SwingWorker<Integer, Integer> worker = new SwingWorker<Integer, Integer>() {

            @Override
            protected Integer doInBackground() throws Exception {

                int soDong = 0;

                // Đếm tổng số dòng trước để tính %
                int tongDong = demTongDong(fileDaChon);

                try (BufferedReader br = new BufferedReader(
                        new FileReader(fileDaChon))) {

                    String line;

                    while ((line = br.readLine()) != null) {

                        soDong++;

                        // Tính tiến trình
                        if (tongDong > 0) {
                            int percent = (soDong * 100) / tongDong;
                            setProgress(percent);
                        }

                    }
                }

                return soDong;
            }

            @Override
            protected void done() {
                try {
                    int ketQua = get();

                    lblKetQua.setText("Số dòng: " + ketQua);
                    progressBar.setValue(100);

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(
                            Bai05.this,
                            "Lỗi khi đọc file: " + e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );

                } finally {
                    btnChonFile.setEnabled(true);
                    btnDemDong.setEnabled(true);
                }
            }
        };

        // Đồng bộ tiến trình SwingWorker với JProgressBar
        worker.addPropertyChangeListener(evt -> {

            if ("progress".equals(evt.getPropertyName())) {
                int progress = (Integer) evt.getNewValue();
                progressBar.setValue(progress);
            }
        });

        worker.execute();
    }

    // Hàm đếm tổng số dòng để tính phần trăm
    private int demTongDong(File file) throws IOException {

        int tongDong = 0;

        try (BufferedReader br = new BufferedReader(
                new FileReader(file))) {

            while (br.readLine() != null) {
                tongDong++;
            }
        }

        return tongDong;
    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
            new Bai05().setVisible(true);
        });
    }
}