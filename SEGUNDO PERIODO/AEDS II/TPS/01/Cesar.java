import java.util.Scanner;

public class Cesar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, "UTF-8");

        while (sc.hasNextLine()) {
            String word = sc.nextLine();
            String nova = ""; // string vazia para acumular o resultado

            // condição de parada
            if (word.equalsIgnoreCase("FIM")) {
                break;
            }

            // percorre cada caractere da string
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i); // pega o caractere atual

                if (c >= 32 && c <= 126) {

                    int novoASCII = 32 + ((c - 32 + 3) % 95);
                    nova += (char) novoASCII;
                } else {
                    nova += c;
                }
            }

            System.out.println(nova);
        }

        sc.close();
    }
}
