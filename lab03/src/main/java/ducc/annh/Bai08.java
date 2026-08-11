package ducc.annh;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class Bai08 {
	private JFrame frame;
	private JTable table;
	private StudentTableModel model;

	public Bai08() {
		initUI();
	}

	private void initUI() {
		frame = new JFrame("Quản lý sinh viên - Bai08");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		model = new StudentTableModel();
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scroll = new JScrollPane(table);

		JPanel toolbar = new JPanel();
		JButton btnAdd = new JButton("Thêm");
		JButton btnDetail = new JButton("Chi tiết");
		JButton btnExit = new JButton("Thoát");
		toolbar.add(btnAdd); toolbar.add(btnDetail); toolbar.add(btnExit);

		frame.getContentPane().setLayout(new BorderLayout(8,8));
		frame.add(toolbar, BorderLayout.NORTH);
		frame.add(scroll, BorderLayout.CENTER);

		btnAdd.addActionListener(e -> openAddDialog());
		btnDetail.addActionListener(e -> openDetailForSelected());
		btnExit.addActionListener(e -> frame.dispose());

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					openDetailForSelected();
				}
			}
		});

		frame.setSize(700, 420);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void openAddDialog() {
		AddStudentDialog dlg = new AddStudentDialog(frame);
		dlg.setVisible(true);
		if (dlg.isConfirmed()) {
			Student s = dlg.getStudent();
			if (model.containsId(s.getId())) {
				JOptionPane.showMessageDialog(frame, "Mã SV đã tồn tại. Không thể thêm trùng mã.");
				return;
			}
			model.addStudent(s);
		}
	}

	private void openDetailForSelected() {
		int sel = table.getSelectedRow();
		if (sel < 0) { JOptionPane.showMessageDialog(frame, "Chọn sinh viên để xem chi tiết"); return; }
		Student s = model.getStudentAt(sel);
		StudentDetailDialog dlg = new StudentDetailDialog(frame, s);
		dlg.setVisible(true);
		if (dlg.isDeleted()) {
			model.removeStudent(sel);
		} else if (dlg.isSaved()) {
			Student updated = dlg.getStudent();
			// If ID changed, ensure uniqueness (allow if same record)
			int found = model.indexOfId(updated.getId());
			if (found >= 0 && found != sel) {
				JOptionPane.showMessageDialog(frame, "Mã SV đã tồn tại. Không thể đổi sang mã này.");
				return;
			}
			model.updateStudent(sel, updated);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Bai08::new);
	}
}

