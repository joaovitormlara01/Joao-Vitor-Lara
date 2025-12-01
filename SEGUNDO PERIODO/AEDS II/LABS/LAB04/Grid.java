import java.util.Scanner;

public class Grid {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, "UTF-8");
        while (sc.hasNextInt()) {
            int size = sc.nextInt();
            int[] largada = Largada(size);
            int[] chegada = Chegada(size);
            int x = Ultrapassagem(size, largada, chegada);
            System.out.println(x);
        }

    }

    public static int[] Largada(int size) {
        Scanner sc = new Scanner(System.in, "UTF-8");
        int larg[] = new int[size];
        for (int i = 0; i < size - 1; i++) {
            larg[i] = sc.nextInt();
        }
        return larg;
    }

    public static int[] Chegada(int size) {
        Scanner sc = new Scanner(System.in, "UTF-8");
        int cheg[] = new int[size];
        for (int i = 0; i < size - 1; i++) {
            cheg[i] = sc.nextInt();
        }
        return cheg;
    }

    public static int Ultrapassagem(int size, int[] largada, int[] chegada) {
        int count = 1;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (largada[i] == chegada[j]) {
                    if (j > i) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}