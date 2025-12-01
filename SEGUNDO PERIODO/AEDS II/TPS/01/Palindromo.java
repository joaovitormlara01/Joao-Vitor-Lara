import java.util.Scanner;

public class Palindromo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, "UTF-8");

        while (sc.hasNextLine()) {
            String word = sc.nextLine();

            // condição de parada: se a palavra for "FIM", o programa encerra
            if (word.equalsIgnoreCase("FIM")) {
                break;
            }

            int i = 0, j = word.length() - 1; // declara um inicio e um final para a comparação

            while (i < j && word.charAt(i) == word.charAt(j)) {
                // condição : se o i < j e as letras da string de acordo com a posição i e j são
                // iguais
                // o while irá continuar funcionando ate q pare;
                i++;
                j--;
            }

            if (i < j)
                System.out.println("NAO"); // quer dizer que saiu do while antes de i ser >= a j;
            else
                System.out.println("SIM"); // while continuo ate q o i ficou com o valor de >= a j;
        }

        sc.close();
    }
}
