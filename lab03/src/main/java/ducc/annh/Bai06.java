package ducc.annh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Bai06 extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cboRole;
    private JCheckBox chkRemember;
    private JCheckBox chkShowPassword;
    private JLabel lblMessage;

    public Bai06() {
        setTitle("Bài 6 - Form đăng nhập cơ bản");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 280);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        JPanel userPanel = new JPanel(new BorderLayout(10, 10));
        userPanel.add(new JLabel("Tên đăng nhập:"), BorderLayout.WEST);
        txtUsername = new JTextField();
        userPanel.add(txtUsername, BorderLayout.CENTER);

        JPanel passPanel = new JPanel(new BorderLayout(10, 10));
        passPanel.add(new JLabel("Mật khẩu:"), BorderLayout.WEST);
        txtPassword = new JPasswordField();
        passPanel.add(txtPassword, BorderLayout.CENTER);

        JPanel rolePanel = new JPanel(new BorderLayout(10, 10));
        rolePanel.add(new JLabel("Vai trò:"), BorderLayout.WEST);
        cboRole = new JComboBox<>(new String[]{"Người dùng", "Quản trị", "Khách"});
        rolePanel.add(cboRole, BorderLayout.CENTER);

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        chkRemember = new JCheckBox("Ghi nhớ đăng nhập");
        chkShowPassword = new JCheckBox("Hiện mật khẩu");
        optionsPanel.add(chkRemember);
        optionsPanel.add(chkShowPassword);

        JButton btnLogin = new JButton("Đăng nhập");
        lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setForeground(Color.BLUE);

        formPanel.add(userPanel);
        formPanel.add(passPanel);
        formPanel.add(rolePanel);
        formPanel.add(optionsPanel);
        formPanel.add(btnLogin);

        add(formPanel, BorderLayout.CENTER);
        add(lblMessage, BorderLayout.SOUTH);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        chkShowPassword.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (chkShowPassword.isSelected()) {
                    txtPassword.setEchoChar((char) 0);
                } else {
                    txtPassword.setEchoChar('*');
                }
            }
        });
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String role = (String) cboRole.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            return;
        }

        boolean valid = false;
        if ("admin".equals(username) && "123456".equals(password) && "Quản trị".equals(role)) {
            valid = true;
        } else if ("user".equals(username) && "123".equals(password) && "Người dùng".equals(role)) {
            valid = true;
        } else if ("guest".equals(username) && "guest".equals(password) && "Khách".equals(role)) {
            valid = true;
        }

        if (valid) {
            lblMessage.setForeground(new Color(0, 128, 0));
            lblMessage.setText("Đăng nhập thành công! " + (chkRemember.isSelected() ? "Đã ghi nhớ tài khoản." : ""));
        } else {
            lblMessage.setForeground(Color.RED);
            lblMessage.setText("Tên đăng nhập, mật khẩu hoặc vai trò không đúng.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bai06 app = new Bai06();
            app.setVisible(true);
        });
    }
}
