package ducc.annh;

import javax.swing.*;
import java.awt.*;

public class StudentDetailDialog extends JDialog {
    private JTextField txtId = new JTextField(20);
    private JTextField txtName = new JTextField(20);
    private JTextField txtAge = new JTextField(5);
    private JTextField txtMajor = new JTextField(20);
    private boolean saved = false;
    private boolean deleted = false;

    public StudentDetailDialog(Frame owner, Student s) {
        super(owner, "Chi tiết sinh viên", true);
        init(s);
    }

    private void init(Student s) {
        txtId.setText(s.getId()); txtName.setText(s.getName()); txtAge.setText(String.valueOf(s.getAge())); txtMajor.setText(s.getMajor());

        JPanel form = new JPanel(new GridLayout(4,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        form.add(new JLabel("Mã SV:")); form.add(txtId);
        form.add(new JLabel("Họ tên:")); form.add(txtName);
        form.add(new JLabel("Tuổi:")); form.add(txtAge);
        form.add(new JLabel("Ngành:")); form.add(txtMajor);

        JButton btnSave = new JButton("Lưu");
        JButton btnDelete = new JButton("Xóa");
        JButton btnCancel = new JButton("Đóng");

        btnSave.addActionListener(e -> {
            if (validateInput()) { saved = true; setVisible(false); }
        });
        btnDelete.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) { deleted = true; setVisible(false); }
        });
        btnCancel.addActionListener(e -> { setVisible(false); });

        JPanel buttons = new JPanel(); buttons.add(btnSave); buttons.add(btnDelete); buttons.add(btnCancel);

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

    public boolean isSaved() { return saved; }
    public boolean isDeleted() { return deleted; }

    public Student getStudent() {
        return new Student(txtId.getText().trim(), txtName.getText().trim(), Integer.parseInt(txtAge.getText().trim()), txtMajor.getText().trim());
    }
}
