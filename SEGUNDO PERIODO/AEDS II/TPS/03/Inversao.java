import java.util.Scanner;

public class Inversao {

    // Função recursiva para inverter a palavra
    public static String reverter(String texto, int pos) {
        if (pos < 0) {
            return "";
        }
        return texto.charAt(pos) + reverter(texto, pos - 1);
    }

    // Função principal de chamada
    public static String reverter(String texto) {
        return reverter(texto, texto.length() - 1);
    }

    // Verificação manual se a palavra é "FIM"
    public static boolean ehFIM(String palavra) {
        if (palavra.length() != 3)
            return false;
        return (palavra.charAt(0) == 'F' &&
                palavra.charAt(1) == 'I' &&
                palavra.charAt(2) == 'M');
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        String palavra = entrada.nextLine();

        // continua até encontrar "FIM"
        while (!ehFIM(palavra)) {
            System.out.println(reverter(palavra));
            palavra = entrada.nextLine();
        }

        entrada.close();
    }
}
