import java.util.Scanner;

public class Bai01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;

        while (true) {
            System.out.print("Nhap n (so nguyen duong): ");
            n = scanner.nextInt();

            if (n > 0) {
                break;
            }

            System.out.println("So khong hop le. Vui long nhap so nguyen duong.");
        }

        int limit = (n % 2 == 0) ? n : n - 1;
        int sum = 0;

        for (int i = 2; i <= limit; i += 2) {
            sum += i;
        }

        System.out.println("Tong la: " + sum);
        scanner.close();
    }
}
