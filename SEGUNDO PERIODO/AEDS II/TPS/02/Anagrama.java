import java.util.Scanner;

public class Anagrama {
    // A função verifica se chegou ao fim das execuções
    public static boolean fim(String x) {
        if (x.length() != 3)
            return false;
        return (x.charAt(0) == 'F' && x.charAt(1) == 'I' && x.charAt(2) == 'M');
    }

    // Função que transforma tudo em maiúsculo manualmente
    public static String maiuscula(String word) {
        String r = "";
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char) (c - 32);
            }
            r += c;
        }
        return r;
    }

    // Função para ordenar a string usando um algoritmo de ordenação manual (Bubble
    // Sort)
    public static String Sort(String word) {
        char[] arr = new char[word.length()];

        // Copia os caracteres da string para o array
        for (int i = 0; i < word.length(); i++) {
            arr[i] = word.charAt(i);
        }

        // Ordena o array
        for (int rep = 0; rep < arr.length - 1; rep++) {
            for (int i = 0; i < arr.length - (rep + 1); i++) {
                if (arr[i] > arr[i + 1]) {
                    char temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
        }

        // Constrói a nova string a partir do array ordenado
        String resultado = "";
        for (int i = 0; i < arr.length; i++) {
            resultado += arr[i];
        }
        return resultado;
    }

    // Função que verifica se as palavras são anagramas (comparando as strings
    // ordenadas)
    public static boolean Anagrama(String x, String y) {
        if (x.length() != y.length()) {
            return false; // tamanhos diferentes -> não pode ser anagrama
        }
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) != y.charAt(i)) {
                return false;
            }
        }
        return true; // passou por todas as comparações
    }

    // Mesmo que não seja usada, mantida para preservar a assinatura do 1º código
    public static void swap(int x, int y) {
        int temp = x;
        x = y;
        y = temp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, "UTF-8");
        String word = sc.next();

        while (!fim(word)) {
            // Ignora o separador -
            sc.next();
            String anag = sc.next();

            word = maiuscula(word);
            anag = maiuscula(anag);

            word = Sort(word);
            anag = Sort(anag);

            if (Anagrama(word, anag)) {
                System.out.println("SIM");
            } else {
                System.out.println("N\u00C3O");
            }

            if (sc.hasNext()) {
                word = sc.next();
            } else {
                break;
            }
        }
        sc.close();
    }
}
