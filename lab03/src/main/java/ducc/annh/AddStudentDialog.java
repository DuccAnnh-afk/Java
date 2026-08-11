package ducc.annh;

import javax.swing.*;
import java.awt.*;

public class AddStudentDialog extends JDialog {
    private JTextField txtId = new JTextField(20);
    private JTextField txtName = new JTextField(20);
    private JTextField txtAge = new JTextField(5);
    private JTextField txtMajor = new JTextField(20);
    private boolean confirmed = false;

    public AddStudentDialog(Frame owner) {
        super(owner, "Thêm sinh viên", true);
        init();
    }

    private void init() {
        JPanel form = new JPanel(new GridLayout(4,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        form.add(new JLabel("Mã SV:")); form.add(txtId);
        form.add(new JLabel("Họ tên:")); form.add(txtName);
        form.add(new JLabel("Tuổi:")); form.add(txtAge);
        form.add(new JLabel("Ngành:")); form.add(txtMajor);

        JButton ok = new JButton("Thêm");
        JButton cancel = new JButton("Hủy");
        ok.addActionListener(e -> {
            if (validateInput()) { confirmed = true; setVisible(false); }
        });
        cancel.addActionListener(e -> { confirmed = false; setVisible(false); });

        JPanel buttons = new JPanel(); buttons.add(ok); buttons.add(cancel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
    }

    private boolean validateInput() {
        if (txtId.getText().trim().isEmpty() || txtName.getText().trim().isEmpty() || txtAge.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã, Họ tên và Tuổi");
            return false;
        }
        try { Integer.parseInt(txtAge.getText().trim()); } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Tuổi phải là số"); return false; }
        return true;
    }

    public boolean isConfirmed() { return confirmed; }

    public Student getStudent() {
        return new Student(txtId.getText().trim(), txtName.getText().trim(), Integer.parseInt(txtAge.getText().trim()), txtMajor.getText().trim());
    }
}
