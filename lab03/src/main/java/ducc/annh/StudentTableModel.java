package ducc.annh;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {
    private final String[] columns = {"Mã SV", "Họ tên", "Tuổi", "Ngành"};
    private final List<Student> students = new ArrayList<>();

    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student s = students.get(rowIndex);
        switch (columnIndex) {
            case 0: return s.getId();
            case 1: return s.getName();
            case 2: return s.getAge();
            case 3: return s.getMajor();
            default: return null;
        }
    }

    public void addStudent(Student s) {
        int idx = students.size();
        students.add(s);
        fireTableRowsInserted(idx, idx);
    }

    public void updateStudent(int index, Student s) {
        if (index < 0 || index >= students.size()) return;
        students.set(index, s);
        fireTableRowsUpdated(index, index);
    }

    public void removeStudent(int index) {
        if (index < 0 || index >= students.size()) return;
        students.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public Student getStudentAt(int index) {
        return students.get(index);
    }

    public boolean containsId(String id) {
        if (id == null) return false;
        for (Student s : students) {
            if (id.equals(s.getId())) return true;
        }
        return false;
    }

    public int indexOfId(String id) {
        if (id == null) return -1;
        for (int i = 0; i < students.size(); i++) {
            if (id.equals(students.get(i).getId())) return i;
        }
        return -1;
    }
}
