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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class Bai07 extends JFrame {

    private JButton btnChonFile;
    private JButton btnTimKiem;

    private JTextField txtTuKhoa;

    private JLabel lblFile;
    private JLabel lblKetQua;

    private JTextArea txtKetQua;

    private JProgressBar progressBar;

    private File fileDaChon;

    public Bai07() {

        setTitle("Tìm kiếm từ khóa trong file");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnChonFile = new JButton("Chọn file");
        btnTimKiem = new JButton("Tìm kiếm");

        txtTuKhoa = new JTextField(20);

        lblFile = new JLabel("Chưa chọn file");
        lblKetQua = new JLabel("Số dòng tìm thấy: 0");

        txtKetQua = new JTextArea();
        txtKetQua.setEditable(false);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        javax.swing.JPanel panelTop = new javax.swing.JPanel();
        panelTop.setLayout(new FlowLayout());

        panelTop.add(btnChonFile);
        panelTop.add(new JLabel("Từ khóa:"));
        panelTop.add(txtTuKhoa);
        panelTop.add(btnTimKiem);


        javax.swing.JPanel panelInfo = new javax.swing.JPanel();
        panelInfo.setLayout(new FlowLayout(FlowLayout.LEFT));

        panelInfo.add(lblFile);
        panelInfo.add(lblKetQua);


        javax.swing.JPanel panelCenter = new javax.swing.JPanel();
        panelCenter.setLayout(new BorderLayout());

        panelCenter.add(panelInfo, BorderLayout.NORTH);
        panelCenter.add(new JScrollPane(txtKetQua), BorderLayout.CENTER);


        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);


        // Chọn file
        btnChonFile.addActionListener(e -> chonFile());

        // Tìm kiếm
        btnTimKiem.addActionListener(e -> timKiem());
    }


    private void chonFile() {

        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            fileDaChon = fileChooser.getSelectedFile();

            lblFile.setText(
                    "File: " + fileDaChon.getAbsolutePath()
            );

            txtKetQua.setText("");
            lblKetQua.setText("Số dòng tìm thấy: 0");
            progressBar.setValue(0);
        }
    }


    private void timKiem() {

        // Kiểm tra file
        if (fileDaChon == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn file trước!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Lấy từ khóa
        String tuKhoa = txtTuKhoa.getText().trim();

        // Kiểm tra từ khóa
        if (tuKhoa.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập từ khóa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Xóa kết quả cũ
        txtKetQua.setText("");
        lblKetQua.setText("Đang tìm kiếm...");
        progressBar.setValue(0);

        // Khóa nút trong lúc tìm kiếm
        btnChonFile.setEnabled(false);
        btnTimKiem.setEnabled(false);


        SwingWorker<Integer, String> worker =
                new SwingWorker<Integer, String>() {

            @Override
            protected Integer doInBackground() throws Exception {

                int soDongTimThay = 0;

                try (BufferedReader br =
                        new BufferedReader(
                                new FileReader(fileDaChon))) {

                    String line;

                    while ((line = br.readLine()) != null) {

                        // Không phân biệt hoa thường
                        if (line.toLowerCase()
                                .contains(tuKhoa.toLowerCase())) {

                            soDongTimThay++;

                            // Đưa dòng tìm thấy ra JTextArea
                            publish(line);
                        }
                    }
                }

                return soDongTimThay;
            }

            @Override
            protected void process(
                    java.util.List<String> chunks) {

                for (String line : chunks) {

                    txtKetQua.append(line);
                    txtKetQua.append("\n");
                }
            }

            @Override
            protected void done() {

                try {

                    int ketQua = get();

                    lblKetQua.setText(
                            "Số dòng tìm thấy: " + ketQua
                    );

                    progressBar.setValue(100);

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(
                            Bai07.this,
                            "Lỗi khi đọc file: "
                            + e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );

                } finally {

                    btnChonFile.setEnabled(true);
                    btnTimKiem.setEnabled(true);
                }
            }
        };

        worker.execute();
    }


    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            new Bai07().setVisible(true);

        });
    }
}