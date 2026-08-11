package ducc.annh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bai02 extends JFrame {
    private JTextField txtNum1;
    private JTextField txtNum2;
    private JButton btnSum;
    private JLabel lblResult;

    public Bai02() {
        setTitle("Bài 2 - Tính tổng hai số");
        setSize(380, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        txtNum1 = new JTextField();
        txtNum2 = new JTextField();
        btnSum = new JButton("Tính tổng");
        lblResult = new JLabel("", SwingConstants.CENTER);

        JPanel panelNum1 = new JPanel(new BorderLayout(10, 10));
        panelNum1.add(new JLabel("Số thứ nhất:"), BorderLayout.WEST);
        panelNum1.add(txtNum1, BorderLayout.CENTER);

        JPanel panelNum2 = new JPanel(new BorderLayout(10, 10));
        panelNum2.add(new JLabel("Số thứ hai:"), BorderLayout.WEST);
        panelNum2.add(txtNum2, BorderLayout.CENTER);

        add(panelNum1);
        add(panelNum2);
        add(btnSum);
        add(lblResult);

        btnSum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double num1 = Double.parseDouble(txtNum1.getText().trim());
                    double num2 = Double.parseDouble(txtNum2.getText().trim());
                    double sum = num1 + num2;
                    lblResult.setText("Tổng = " + sum);
                } catch (NumberFormatException ex) {
                    lblResult.setText("Vui lòng nhập đúng số!");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bai02 app = new Bai02();
            app.setVisible(true);
        });
    }
}
