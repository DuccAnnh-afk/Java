package ducc.annh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bai04 extends JFrame {
    private JTextField txtA;
    private JTextField txtB;
    private JTextField txtC;
    private JButton btnCheck;
    private JLabel lblResult;

    public Bai04() {
        setTitle("Bài 4 - Kiểm tra và phân loại tam giác");
        setSize(420, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        txtA = new JTextField();
        txtB = new JTextField();
        txtC = new JTextField();
        btnCheck = new JButton("Kiểm tra");
        lblResult = new JLabel("", SwingConstants.CENTER);

        JPanel panelA = new JPanel(new BorderLayout(10, 10));
        panelA.add(new JLabel("Cạnh a:"), BorderLayout.WEST);
        panelA.add(txtA, BorderLayout.CENTER);

        JPanel panelB = new JPanel(new BorderLayout(10, 10));
        panelB.add(new JLabel("Cạnh b:"), BorderLayout.WEST);
        panelB.add(txtB, BorderLayout.CENTER);

        JPanel panelC = new JPanel(new BorderLayout(10, 10));
        panelC.add(new JLabel("Cạnh c:"), BorderLayout.WEST);
        panelC.add(txtC, BorderLayout.CENTER);

        add(panelA);
        add(panelB);
        add(panelC);
        add(btnCheck);
        add(lblResult);

        btnCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double a = Double.parseDouble(txtA.getText().trim());
                    double b = Double.parseDouble(txtB.getText().trim());
                    double c = Double.parseDouble(txtC.getText().trim());

                    if (a <= 0 || b <= 0 || c <= 0) {
                        lblResult.setText("Độ dài cạnh phải > 0");
                    } else if (a + b <= c || a + c <= b || b + c <= a) {
                        lblResult.setText("Ba đoạn thẳng này không tạo thành tam giác");
                    } else if (a == b && b == c) {
                        lblResult.setText("Đây là tam giác đều");
                    } else if (a == b || a == c || b == c) {
                        lblResult.setText("Đây là tam giác cân");
                    } else {
                        lblResult.setText("Đây là tam giác thường");
                    }
                } catch (NumberFormatException ex) {
                    lblResult.setText("Vui lòng nhập đúng số!");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bai04 app = new Bai04();
            app.setVisible(true);
        });
    }
}
