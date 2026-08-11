package ducc.annh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bai01 extends JFrame {
    private JTextField txtName;
    private JButton btnGreeting;
    private JLabel lblResult;

    public Bai01() {
        setTitle("Bài 1 - Chào người dùng");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        txtName = new JTextField();
        btnGreeting = new JButton("Chào");
        lblResult = new JLabel("", SwingConstants.CENTER);

        JPanel panelInput = new JPanel(new BorderLayout(10, 10));
        panelInput.add(new JLabel("Nhập tên:"), BorderLayout.WEST);
        panelInput.add(txtName, BorderLayout.CENTER);

        add(panelInput);
        add(btnGreeting);
        add(lblResult);

        btnGreeting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText().trim();
                if (name.isEmpty()) {
                    lblResult.setText("Vui lòng nhập tên!");
                } else {
                    lblResult.setText("Xin chào, " + name + "!");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bai01 app = new Bai01();
            app.setVisible(true);
        });
    }
}
