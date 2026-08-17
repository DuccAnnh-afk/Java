package ducc.annh;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class Bai10 extends JFrame {

    private JTextField txtMaSP;
    private JTextField txtTenSP;
    private JTextField txtDonGia;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLuu;
    private JButton btnDoc;

    private JTable table;
    private DefaultTableModel model;

    public Bai10() {

        setTitle("Quản lý sản phẩm bằng file CSV");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        txtMaSP = new JTextField();
        txtTenSP = new JTextField();
        txtDonGia = new JTextField();

        JPanel panelInput = new JPanel(new GridLayout(3, 2, 5, 5));

        panelInput.add(new JLabel("Mã sản phẩm:"));
        panelInput.add(txtMaSP);

        panelInput.add(new JLabel("Tên sản phẩm:"));
        panelInput.add(txtTenSP);

        panelInput.add(new JLabel("Đơn giá:"));
        panelInput.add(txtDonGia);


        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu file CSV");
        btnDoc = new JButton("Đọc file CSV");

        JPanel panelButton = new JPanel(new FlowLayout());

        panelButton.add(btnThem);
        panelButton.add(btnSua);
        panelButton.add(btnXoa);
        panelButton.add(btnLuu);
        panelButton.add(btnDoc);


        String[] columns = {
            "Mã SP",
            "Tên SP",
            "Đơn giá"
        };

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);


        JPanel panelTop = new JPanel(new BorderLayout());

        panelTop.add(panelInput, BorderLayout.CENTER);
        panelTop.add(panelButton, BorderLayout.SOUTH);

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


        btnThem.addActionListener(e -> themSanPham());

        btnSua.addActionListener(e -> suaSanPham());

        btnXoa.addActionListener(e -> xoaSanPham());

        btnLuu.addActionListener(e -> luuFile());

        btnDoc.addActionListener(e -> docFile());

        // Khi chọn một dòng trong JTable
        table.getSelectionModel().addListSelectionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {

                txtMaSP.setText(
                        model.getValueAt(row, 0).toString()
                );

                txtTenSP.setText(
                        model.getValueAt(row, 1).toString()
                );

                txtDonGia.setText(
                        model.getValueAt(row, 2).toString()
                );
            }
        });
    }


    private void themSanPham() {

        String maSP = txtMaSP.getText().trim();
        String tenSP = txtTenSP.getText().trim();
        String donGia = txtDonGia.getText().trim();

        if (maSP.isEmpty()
                || tenSP.isEmpty()
                || donGia.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ thông tin!"
            );

            return;
        }

        try {

            Double.parseDouble(donGia);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Đơn giá phải là số!"
            );

            return;
        }

        model.addRow(new Object[]{
            maSP,
            tenSP,
            donGia
        });

        xoaTrang();

        JOptionPane.showMessageDialog(
                this,
                "Thêm sản phẩm thành công!"
        );
    }


    private void suaSanPham() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn sản phẩm cần sửa!"
            );

            return;
        }

        String maSP = txtMaSP.getText().trim();
        String tenSP = txtTenSP.getText().trim();
        String donGia = txtDonGia.getText().trim();

        if (maSP.isEmpty()
                || tenSP.isEmpty()
                || donGia.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ thông tin!"
            );

            return;
        }

        try {

            Double.parseDouble(donGia);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Đơn giá phải là số!"
            );

            return;
        }

        model.setValueAt(maSP, row, 0);
        model.setValueAt(tenSP, row, 1);
        model.setValueAt(donGia, row, 2);

        xoaTrang();

        JOptionPane.showMessageDialog(
                this,
                "Sửa sản phẩm thành công!"
        );
    }


    private void xoaSanPham() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn sản phẩm cần xóa!"
            );

            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa sản phẩm này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            model.removeRow(row);

            xoaTrang();

            JOptionPane.showMessageDialog(
                    this,
                    "Xóa sản phẩm thành công!"
            );
        }
    }


    private void luuFile() {

        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showSaveDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();

        // Tự thêm đuôi .csv nếu người dùng không nhập
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new File(file.getAbsolutePath() + ".csv");
        }

        File fileCuoiCung = file;

        btnLuu.setEnabled(false);
        btnDoc.setEnabled(false);

        SwingWorker<Void, Void> worker =
                new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {

                try (PrintWriter writer =
                        new PrintWriter(
                                new FileWriter(fileCuoiCung))) {

                    // Ghi dòng tiêu đề
                    writer.println("MaSP,TenSP,DonGia");

                    // Ghi từng sản phẩm
                    for (int i = 0; i < model.getRowCount(); i++) {

                        String maSP =
                                model.getValueAt(i, 0).toString();

                        String tenSP =
                                model.getValueAt(i, 1).toString();

                        String donGia =
                                model.getValueAt(i, 2).toString();

                        writer.println(
                                maSP + ","
                                + tenSP + ","
                                + donGia
                        );

                        // Giả lập quá trình lưu
                        Thread.sleep(100);
                    }
                }

                return null;
            }

            @Override
            protected void done() {

                try {

                    get();

                    JOptionPane.showMessageDialog(
                            Bai10.this,
                            "Lưu file thành công!"
                    );

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(
                            Bai10.this,
                            "Lỗi khi lưu file: "
                            + e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );

                } finally {

                    btnLuu.setEnabled(true);
                    btnDoc.setEnabled(true);
                }
            }
        };

        worker.execute();
    }


    private void docFile() {

        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showOpenDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();

        btnLuu.setEnabled(false);
        btnDoc.setEnabled(false);

        // Xóa dữ liệu cũ
        model.setRowCount(0);

        SwingWorker<Void, Object[]> worker =
                new SwingWorker<Void, Object[]>() {

            @Override
            protected Void doInBackground() throws Exception {

                try (BufferedReader reader =
                        new BufferedReader(
                                new FileReader(file))) {

                    // Bỏ qua dòng tiêu đề
                    String line = reader.readLine();

                    while ((line = reader.readLine()) != null) {

                        if (line.trim().isEmpty()) {
                            continue;
                        }

                        String[] data = line.split(",");

                        if (data.length >= 3) {

                            String maSP = data[0].trim();
                            String tenSP = data[1].trim();
                            String donGia = data[2].trim();

                            publish(new Object[]{
                                maSP,
                                tenSP,
                                donGia
                            });
                        }

                        // Giả lập file lớn
                        Thread.sleep(100);
                    }
                }

                return null;
            }

            @Override
            protected void process(
                    List<Object[]> chunks) {

                for (Object[] product : chunks) {

                    model.addRow(product);
                }
            }

            @Override
            protected void done() {

                try {

                    get();

                    JOptionPane.showMessageDialog(
                            Bai10.this,
                            "Đọc file thành công!"
                    );

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(
                            Bai10.this,
                            "Lỗi khi đọc file: "
                            + e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );

                } finally {

                    btnLuu.setEnabled(true);
                    btnDoc.setEnabled(true);
                }
            }
        };

        worker.execute();
    }



    private void xoaTrang() {

        txtMaSP.setText("");
        txtTenSP.setText("");
        txtDonGia.setText("");

        table.clearSelection();
    }


    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            new Bai10().setVisible(true);

        });
    }
}