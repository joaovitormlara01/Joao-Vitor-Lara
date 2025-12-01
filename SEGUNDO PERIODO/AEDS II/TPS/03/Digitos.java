import java.util.Scanner;

public class Digitos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, "UTF-8");
        while (sc.hasNextInt()) {
            int num = sc.nextInt();
            int resultado = Soma(num);
            System.out.println(resultado);
        }
        sc.close();
    }

    public static int Soma(int n) {
        if (n == 0) {
            return 0;
        }
        return (n % 10) + Soma(n / 10);

    }
}
