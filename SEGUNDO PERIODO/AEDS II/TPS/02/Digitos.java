import java.util.Scanner;

public class Digitos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // enquanto houver inteiros na entrada
        while (sc.hasNextInt()) {
            int numero = sc.nextInt();
            if (numero == 0)
                break;

            int resultado = Soma(numero);
            System.out.println(resultado);
        }

        sc.close();
    }

    // método que soma os dígitos de um número positivo
    public static int Soma(int num) {
        int soma = 0;
        while (num != 0) {
            soma += num % 10; // pega o último dígito
            num /= 10; // remove o último dígito
        }
        return soma;
    }
}
