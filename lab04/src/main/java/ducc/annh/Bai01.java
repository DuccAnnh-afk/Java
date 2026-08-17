package ducc.annh;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Ứng dụng đồng hồ đếm ngược sử dụng SwingWorker
 * Giao diện không bị treo khi đếm ngược
 */
public class Bai01 extends JFrame {
    private JTextField tfSeconds;
    private JButton btnStart;
    private JLabel lblCountdown;
    private CountdownWorker countdownWorker;

    public Bai01() {
        setTitle("Đồng Hồ Đếm Ngược");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        // Tạo panel chứa các thành phần
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // JLabel nhập số giây
        JLabel lblInput = new JLabel("Nhập số giây:");
        panel.add(lblInput);

        // JTextField nhập số giây
        tfSeconds = new JTextField("10");
        tfSeconds.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(tfSeconds);

        // JButton "Bắt đầu"
        btnStart = new JButton("Bắt Đầu");
        btnStart.setFont(new Font("Arial", Font.BOLD, 14));
        btnStart.addActionListener(e -> startCountdown());
        panel.add(btnStart);

        // JLabel hiển thị thời gian còn lại
        lblCountdown = new JLabel("Thời gian còn lại: 00:00");
        lblCountdown.setFont(new Font("Arial", Font.BOLD, 18));
        lblCountdown.setHorizontalAlignment(SwingConstants.CENTER);
        lblCountdown.setForeground(Color.BLUE);
        panel.add(lblCountdown);

        add(panel);
        setVisible(true);
    }

    /**
     * Bắt đầu đếm ngược
     */
    private void startCountdown() {
        try {
            int seconds = Integer.parseInt(tfSeconds.getText());
            if (seconds <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số giây > 0", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vô hiệu hóa các thành phần trong lúc đếm ngược
            tfSeconds.setEnabled(false);
            btnStart.setEnabled(false);

            // Tạo và khởi động SwingWorker
            countdownWorker = new CountdownWorker(seconds);
            countdownWorker.execute();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập một số nguyên hợp lệ", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * SwingWorker để thực hiện đếm ngược trong nền
     */
    private class CountdownWorker extends SwingWorker<Void, Integer> {
        private int totalSeconds;

        public CountdownWorker(int totalSeconds) {
            this.totalSeconds = totalSeconds;
        }

        @Override
        protected Void doInBackground() throws Exception {
            // Cập nhật ban đầu
            publish(totalSeconds);

            // Đếm ngược
            for (int i = totalSeconds - 1; i >= 0; i--) {
                Thread.sleep(1000); // Chờ 1 giây
                publish(i);
            }

            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            // Nhận giá trị từ doInBackground và cập nhật UI
            for (int remaining : chunks) {
                int minutes = remaining / 60;
                int seconds = remaining % 60;
                lblCountdown.setText(String.format("Thời gian còn lại: %02d:%02d", 
                    minutes, seconds));
            }
        }

        @Override
        protected void done() {
            // Khi đếm ngược hoàn thành
            lblCountdown.setText("Thời gian còn lại: 00:00");
            lblCountdown.setForeground(Color.RED);

            // Bật lại các thành phần
            tfSeconds.setEnabled(true);
            btnStart.setEnabled(true);

            JOptionPane.showMessageDialog(Bai01.this, "Đếm ngược hoàn thành!", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Đặt lại màu
            lblCountdown.setForeground(Color.BLUE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai01());
    }
}
