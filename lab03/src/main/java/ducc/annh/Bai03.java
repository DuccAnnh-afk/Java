package ducc.annh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bai03 extends JFrame {
    private JTextField txtA;
    private JTextField txtB;
    private JButton btnSolve;
    private JLabel lblResult;

    public Bai03() {
        setTitle("Bài 3 - Giải phương trình bậc nhất");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        txtA = new JTextField();
        txtB = new JTextField();
        btnSolve = new JButton("Giải");
        lblResult = new JLabel("", SwingConstants.CENTER);

        JPanel panelA = new JPanel(new BorderLayout(10, 10));
        panelA.add(new JLabel("a:"), BorderLayout.WEST);
        panelA.add(txtA, BorderLayout.CENTER);

        JPanel panelB = new JPanel(new BorderLayout(10, 10));
        panelB.add(new JLabel("b:"), BorderLayout.WEST);
        panelB.add(txtB, BorderLayout.CENTER);

        add(panelA);
        add(panelB);
        add(btnSolve);
        add(lblResult);

        btnSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double a = Double.parseDouble(txtA.getText().trim());
                    double b = Double.parseDouble(txtB.getText().trim());

                    if (a == 0) {
                        if (b == 0) {
                            lblResult.setText("Phương trình có vô số nghiệm");
                        } else {
                            lblResult.setText("Phương trình vô nghiệm");
                        }
                    } else {
                        double x = -b / a;
                        lblResult.setText("Nghiệm x = " + x);
                    }
                } catch (NumberFormatException ex) {
                    lblResult.setText("Vui lòng nhập đúng số!");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bai03 app = new Bai03();
            app.setVisible(true);
        });
    }
}
