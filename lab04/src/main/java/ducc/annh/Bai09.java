package ducc.annh;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class Bai09 extends JFrame {

    private JButton btnTaiSanPham;

    private JTable table;

    private DefaultTableModel model;

    private JProgressBar progressBar;

    private JLabel lblTrangThai;

    public Bai09() {

        setTitle("Mô phỏng tải danh sách sản phẩm");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        btnTaiSanPham = new JButton("Tải sản phẩm");

        lblTrangThai = new JLabel("Trạng thái: Chưa tải dữ liệu");

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);


        String[] columns = {
            "Mã SP",
            "Tên SP",
            "Đơn giá"
        };

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);


        javax.swing.JPanel panelTop = new javax.swing.JPanel();

        panelTop.setLayout(new FlowLayout());

        panelTop.add(btnTaiSanPham);
        panelTop.add(lblTrangThai);


        javax.swing.JPanel panelBottom = new javax.swing.JPanel();

        panelBottom.setLayout(new BorderLayout());

        panelBottom.add(progressBar, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);


        btnTaiSanPham.addActionListener(e -> taiSanPham());
    }


    private void taiSanPham() {

        // Xóa dữ liệu cũ
        model.setRowCount(0);

        progressBar.setValue(0);

        lblTrangThai.setText(
                "Trạng thái: Đang tải..."
        );

        // Khóa nút trong khi tải
        btnTaiSanPham.setEnabled(false);


        SwingWorker<Void, Object[]> worker =
                new SwingWorker<Void, Object[]>() {

            @Override
            protected Void doInBackground()
                    throws Exception {

                // Dữ liệu sản phẩm giả lập
                Object[][] sanPham = {

                    {"SP01", "Bàn phím", 250000},

                    {"SP02", "Chuột", 150000},

                    {"SP03", "Màn hình", 2500000}

                };

                // Giả lập quá trình tải
                for (int i = 0; i < sanPham.length; i++) {

                    // Giả lập thời gian tải dữ liệu
                    Thread.sleep(1000);

                    // Gửi sản phẩm về process()
                    publish(sanPham[i]);

                    // Cập nhật progress
                    int progress =
                            (int) (((i + 1)
                            / (double) sanPham.length) * 100);

                    setProgress(progress);
                }

                return null;
            }


            @Override
            protected void process(
                    java.util.List<Object[]> chunks) {

                for (Object[] product : chunks) {

                    model.addRow(product);
                }
            }

            @Override
            protected void done() {

                progressBar.setValue(100);

                lblTrangThai.setText(
                        "Trạng thái: Tải thành công!"
                );

                btnTaiSanPham.setEnabled(true);
            }
        };


        worker.addPropertyChangeListener(evt -> {

            if ("progress".equals(
                    evt.getPropertyName())) {

                int progress =
                        (Integer) evt.getNewValue();

                progressBar.setValue(progress);
            }
        });

        // Bắt đầu tải
        worker.execute();
    }


    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            new Bai09().setVisible(true);

        });
    }
}