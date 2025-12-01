import java.util.Scanner;

public class Invertida {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, "UTF-8");

        while (sc.hasNextLine()) {
            String word = sc.nextLine();

            // se a palavra for "FIM", encerramos o programa
            if (word.charAt(0) == 'F' && word.charAt(1) == 'I' && word.charAt(2) == 'M') {
                break;
            }

            String invertida = "";
            int tamanho = word.length();

            // percorre a string de trás para frente
            for (int i = tamanho - 1; i >= 0; i--) {
                invertida += word.charAt(i); // pega o caractere na posição i e concatena
            }

            System.out.println(invertida);
        }

        sc.close();
    }
}
