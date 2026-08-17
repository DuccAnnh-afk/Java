package ducc.annh;

import javax.swing.*;

public class Bai02 extends JFrame {
    private JButton btnLoadData;
    private JProgressBar progressBar;
    private JLabel lblStatus;
    
    public Bai02() {
        setTitle("Mô phỏng tải dữ liệu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
        setVisible(true);
    }
    
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        // JLabel for title
        JLabel lblTitle = new JLabel("Tải dữ liệu:");
        lblTitle.setBounds(20, 20, 100, 25);
        panel.add(lblTitle);
        
        // JButton "Tải dữ liệu"
        btnLoadData = new JButton("Tải dữ liệu");
        btnLoadData.setBounds(20, 50, 120, 30);
        btnLoadData.addActionListener(e -> startLoading());
        panel.add(btnLoadData);
        
        // JProgressBar
        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(160, 50, 300, 30);
        progressBar.setStringPainted(true);
        panel.add(progressBar);
        
        // JLabel for status
        lblStatus = new JLabel("Sẵn sàng");
        lblStatus.setBounds(20, 100, 440, 25);
        panel.add(lblStatus);
        
        add(panel);
    }
    
    private void startLoading() {
        btnLoadData.setEnabled(false);
        lblStatus.setText("Đang tải dữ liệu...");
        progressBar.setValue(0);
        
        // Sử dụng SwingWorker để mô phỏng tải dữ liệu trong nền
        DataLoadingWorker worker = new DataLoadingWorker();
        worker.execute();
    }
    
    // SwingWorker class để mô phỏng tải dữ liệu
    private class DataLoadingWorker extends SwingWorker<Void, Integer> {
        @Override
        protected Void doInBackground() throws Exception {
            // Tải dữ liệu trong 10 giây (1000ms * 10)
            long startTime = System.currentTimeMillis();
            long duration = 10000; // 10 giây
            
            while (System.currentTimeMillis() - startTime < duration) {
                long elapsed = System.currentTimeMillis() - startTime;
                int progress = (int) ((elapsed * 100) / duration);
                progress = Math.min(progress, 100);
                
                publish(progress);
                
                Thread.sleep(100); // Cập nhật mỗi 100ms
            }
            
            publish(100); // Đảm bảo hoàn thành 100%
            return null;
        }
        
        @Override
        protected void process(java.util.List<Integer> chunks) {
            // Cập nhật tiến độ cho từng giá trị được publish
            for (Integer progress : chunks) {
                progressBar.setValue(progress);
            }
        }
        
        @Override
        protected void done() {
            lblStatus.setText("Tải dữ liệu hoàn thành!");
            btnLoadData.setEnabled(true);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai02());
    }
}
