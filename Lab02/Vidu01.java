import java.util.Scanner;

public class Vidu01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhap ma sinh vien: ");
        String maSV = scanner.nextLine();

        System.out.print("Nhap ho ten: ");
        String hoTen = scanner.nextLine();

        double diemChuyenCan = nhapDiem(scanner, "diem chuyen can");
        double diemGiuaKy = nhapDiem(scanner, "diem giua ky");
        double diemCuoiKy = nhapDiem(scanner, "diem cuoi ky");

        double diemTongKet = diemChuyenCan * 0.1 + diemGiuaKy * 0.3 + diemCuoiKy * 0.6;
        String xepLoai = xepLoai(diemTongKet);

        System.out.println("\nThong tin sinh vien:");
        System.out.printf("%-12s %-25s %-10s %-10s\n", "Ma SV", "Ho ten", "Tong ket", "Xep loai");
        System.out.printf("%-12s %-25s %-10.2f %-10s\n", maSV, hoTen, diemTongKet, xepLoai);

        scanner.close();
    }

    private static double nhapDiem(Scanner scanner, String tenDiem) {
        double diem;
        while (true) {
            System.out.print("Nhap " + tenDiem + " (0-10): ");
            diem = scanner.nextDouble();
            scanner.nextLine();

            if (diem >= 0 && diem <= 10) {
                return diem;
            }

            System.out.println("Loi: diem khong hop le. Vui long nhap lai.");
        }
    }

    private static String xepLoai(double diemTongKet) {
        if (diemTongKet >= 8.5) {
            return "A";
        } else if (diemTongKet >= 7.0) {
            return "B";
        } else if (diemTongKet >= 5.5) {
            return "C";
        } else if (diemTongKet >= 4.0) {
            return "D";
        } else {
            return "F";
        }
    }
}
