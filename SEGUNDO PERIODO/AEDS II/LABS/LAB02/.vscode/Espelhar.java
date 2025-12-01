import java.util.Scanner;

public class Espelhar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            int x = sc.nextInt();
            if (!sc.hasNextInt()) break;
            int y = sc.nextInt();

            int inicio, fim;
            if (x < y) { inicio = x; fim = y; }
            else       { inicio = y; fim = x; }

            String seq = "";
            for (int i = inicio; i <= fim; i++) {
                seq += i;
            }

            System.out.print(seq);
            for (int i = seq.length() - 1; i >= 0; i--) {
                System.out.print(seq.charAt(i));
            }
            System.out.println();
        }

        sc.close();
    }
}
