import java.util.Random;
import java.util.Scanner;

public class AlteracaoAleatoria {

    // método que recebe uma palavra e o gerador de números aleatórios
    public static String sorteia(String palavra, Random aleatorio) {
        int i = 0; // variável de controle do while
        int tamanho = palavra.length(); // tamanho da string recebida
        String resposta = ""; // string vazia que vamos preencher e retornar

        char letra1 = (char) ('a' + (Math.abs(aleatorio.nextInt()) % 26)); // sorteamos a primeira letra aleatória entre
                                                                           // 'a' e 'z'
        char letra2 = (char) ('a' + (Math.abs(aleatorio.nextInt()) % 26)); // sorteamos a segunda letra aleatória entre
                                                                           // 'a' e 'z'

        // percorremos a string original até o fim
        while (i < tamanho) {
            char letraAtual = palavra.charAt(i); // pegamos a letra da posição atual

            // se a letra atual for igual à primeira sorteada, trocamos pela segunda
            if (letraAtual == letra1) {
                letraAtual = letra2;
            }

            // acrescentamos a letra (original ou trocada) na nova string resposta
            resposta += letraAtual;
            i++; // avançamos para a próxima posição da string
        }

        System.out.println(resposta); // imprimimos a string alterada
        return resposta; // retornamos a string alterada
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in); // entrada de dados do usuário
        Random aleatorio = new Random(); // criamos o gerador aleatório
        aleatorio.setSeed(4); // fixamos a semente para ter sempre os mesmos resultados

        String palavra = entrada.nextLine(); // recebemos a primeira palavra digitada

        // condição de parada: só encerra quando a palavra digitada for "FIM"
        while (!(palavra.length() >= 3 && palavra.charAt(0) == 'F'
                && palavra.charAt(1) == 'I' && palavra.charAt(2) == 'M')) {

            // chamamos o método sorteia passando a palavra e o gerador
            sorteia(palavra, aleatorio);

            // pedimos uma nova palavra e repetimos o processo
            palavra = entrada.nextLine();
        }

        entrada.close(); // fechamos o scanner
    }
}
