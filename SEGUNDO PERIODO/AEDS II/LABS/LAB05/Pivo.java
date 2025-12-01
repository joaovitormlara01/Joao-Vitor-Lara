import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Pivo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, "UTF-8");

        System.out.print("Digite o tamanho do vetor: ");
        int tam = sc.nextInt();
        if (tam < 0) {
            System.out.println("Tamanho inválido.");
            sc.close();
            return;
        }

        int[] vetor = new int[tam];
        if (tam > 0) {
            System.out.println("Digite os elementos do seu vetor:");
            for (int i = 0; i < tam; i++) {
                vetor[i] = sc.nextInt();
            }
        }

        // MENU DE OPÇÕES
        System.out.println("\nQual tipo de pivô você deseja usar?");
        System.out.println("1 - Primeiro elemento");
        System.out.println("2 - Último elemento");
        System.out.println("3 - Pivô aleatório");
        System.out.println("4 - Mediana de três (início, meio, fim)");
        System.out.print("Sua opção: ");
        int x = sc.nextInt();
        sc.close();

        Pivo sorter = new Pivo();
        long t0 = System.nanoTime();

        switch (x) {
            case 1:
                sorter.quicksortFirstPivo(0, tam - 1, vetor);
                break;
            case 2:
                sorter.quicksortLastPivo(0, tam - 1, vetor);
                break;
            case 3:
                sorter.quicksortRandomPivo(0, tam - 1, vetor);
                break;
            case 4:
                sorter.quicksortMedianOfThree(0, tam - 1, vetor);
                break;
            default:
                System.out.println("Opção inválida. Nada foi ordenado.");
                return;
        }

        long t1 = System.nanoTime();

        // imprime resultado
        System.out.println("\nVetor ordenado:");
        for (int i = 0; i < tam; i++) {
            System.out.print(vetor[i]);
            if (i < tam - 1)
                System.out.print(" ");
        }
        System.out.printf("%nTempo: %.3f ms%n", (t1 - t0) / 1_000_000.0);
    }

    // Pivô = primeiro elemento
    public void quicksortFirstPivo(int esq, int dir, int[] array) {
        if (esq >= dir)
            return;

        int i = esq, j = dir;
        int pivo = array[esq];

        while (i <= j) {
            while (array[i] < pivo)
                i++;
            while (array[j] > pivo)
                j--;
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }

        if (esq < j)
            quicksortFirstPivo(esq, j, array);
        if (i < dir)
            quicksortFirstPivo(i, dir, array);
    }

    // Pivô = último elemento
    public void quicksortLastPivo(int esq, int dir, int[] array) {
        if (esq >= dir)
            return;

        int i = esq, j = dir;
        int pivo = array[dir];

        while (i <= j) {
            while (array[i] < pivo)
                i++;
            while (array[j] > pivo)
                j--;
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }

        if (esq < j)
            quicksortLastPivo(esq, j, array);
        if (i < dir)
            quicksortLastPivo(i, dir, array);
    }

    // Pivô aleatório (valor do índice sorteado)
    public void quicksortRandomPivo(int esq, int dir, int[] array) {
        if (esq >= dir)
            return;

        int i = esq, j = dir;
        int randIdx = ThreadLocalRandom.current().nextInt(esq, dir + 1);
        int pivo = array[randIdx];

        while (i <= j) {
            while (array[i] < pivo)
                i++;
            while (array[j] > pivo)
                j--;
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }

        if (esq < j)
            quicksortRandomPivo(esq, j, array);
        if (i < dir)
            quicksortRandomPivo(i, dir, array);
    }

    // Mediana de três (início, meio, fim) como pivô (pelo valor)
    public void quicksortMedianOfThree(int esq, int dir, int[] array) {
        if (esq >= dir)
            return;

        int i = esq, j = dir;
        int mid = esq + ((dir - esq) >>> 1);
        int pivo = array[medianOfThreeIndex(array, esq, mid, dir)];

        while (i <= j) {
            while (array[i] < pivo)
                i++;
            while (array[j] > pivo)
                j--;
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }

        if (esq < j)
            quicksortMedianOfThree(esq, j, array);
        if (i < dir)
            quicksortMedianOfThree(i, dir, array);
    }

    private int medianOfThreeIndex(int[] a, int i, int j, int k) {
        // retorna o índice cujo valor é a mediana entre a[i], a[j], a[k]
        if (a[i] <= a[j]) {
            if (a[j] <= a[k])
                return j;
            else if (a[i] <= a[k])
                return k;
            else
                return i;
        } else {
            if (a[i] <= a[k])
                return i;
            else if (a[j] <= a[k])
                return k;
            else
                return j;
        }
    }

    private void swap(int[] array, int i, int j) {
        if (i == j)
            return;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
