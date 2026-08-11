package ducc.annh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bai05 extends JFrame {
    private JTextField txtN;
    private JButton btnShow;
    private JTextArea txtArea;
    private JScrollPane scrollPane;

    public Bai05() {
        setTitle("Bài 5 - Hiển thị dãy Fibonacci");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(new JLabel("Nhập n:"), BorderLayout.WEST);
        txtN = new JTextField();
        topPanel.add(txtN, BorderLayout.CENTER);

        btnShow = new JButton("Hiển thị");
        topPanel.add(btnShow, BorderLayout.EAST);

        txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        scrollPane = new JScrollPane(txtArea);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(txtN.getText().trim());
                    if (n <= 0) {
                        txtArea.setText("Vui lòng nhập n > 0");
                        return;
                    }

                    StringBuilder result = new StringBuilder();
                    int a = 0, b = 1;
                    for (int i = 0; i < n; i++) {
                        if (i > 0) {
                            result.append(", ");
                        }
                        result.append(a);
                        int next = a + b;
                        a = b;
                        b = next;
                    }

                    txtArea.setText(result.toString());
                } catch (NumberFormatException ex) {
                    txtArea.setText("Vui lòng nhập đúng số nguyên!");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bai05 app = new Bai05();
            app.setVisible(true);
        });
    }
}
