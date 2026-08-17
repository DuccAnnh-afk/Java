package ducc.annh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Bai03 extends JFrame {
    private JTextField txtN;
    private JLabel lblResult;
    private JProgressBar progressBar;
    private JButton btnCalculate;

    public Bai03() {
        setTitle("Tính tổng các số nguyên tố nhỏ hơn N");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create components
        JLabel lblN = new JLabel("Nhập N:");
        txtN = new JTextField(15);
        btnCalculate = new JButton("Tính");
        lblResult = new JLabel("Kết quả: ");
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        // Layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblN, gbc);

        gbc.gridx = 1;
        panel.add(txtN, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(btnCalculate, gbc);

        gbc.gridy = 2;
        panel.add(new JLabel("Tiến độ:"), gbc);

        gbc.gridy = 3;
        panel.add(progressBar, gbc);

        gbc.gridy = 4;
        panel.add(lblResult, gbc);

        add(panel);

        // Event handler
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePrimes();
            }
        });

        setVisible(true);
    }

    private void calculatePrimes() {
        try {
            int n = Integer.parseInt(txtN.getText());
            if (n <= 2) {
                lblResult.setText("Kết quả: 0");
                progressBar.setValue(0);
                return;
            }

            btnCalculate.setEnabled(false);
            progressBar.setValue(0);
            lblResult.setText("Kết quả: Đang tính...");

            PrimeCalculator calculator = new PrimeCalculator(n);
            calculator.execute();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập một số nguyên hợp lệ!");
            btnCalculate.setEnabled(true);
        }
    }

    private class PrimeCalculator extends SwingWorker<Long, Integer> {
        private int n;

        public PrimeCalculator(int n) {
            this.n = n;
        }

        @Override
        protected Long doInBackground() throws Exception {
            long sum = 0;
            int count = 0;
            int total = n - 2;

            for (int i = 2; i < n; i++) {
                if (isPrime(i)) {
                    sum += i;
                }
                count++;
                int progress = (int) ((count * 100.0) / total);
                publish(progress);
            }
            return sum;
        }

        @Override
        protected void process(List<Integer> chunks) {
            if (!chunks.isEmpty()) {
                progressBar.setValue(chunks.get(chunks.size() - 1));
            }
        }

        @Override
        protected void done() {
            try {
                long result = get();
                lblResult.setText("Kết quả: " + result);
                progressBar.setValue(100);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(Bai03.this, "Lỗi: " + e.getMessage());
            } finally {
                btnCalculate.setEnabled(true);
            }
        }

        private boolean isPrime(int num) {
            if (num < 2) return false;
            if (num == 2) return true;
            if (num % 2 == 0) return false;
            for (int i = 3; i * i <= num; i += 2) {
                if (num % i == 0) return false;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Bai03();
            }
        });
    }
}
