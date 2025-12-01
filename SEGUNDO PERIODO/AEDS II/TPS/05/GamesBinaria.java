import java.io.*;
import java.util.*;
import java.text.*;

public class GamesBinaria {
    int id;
    String name, date;
    int owners;
    float price;
    String[] langs, pubs, devs, cats, genres, tags;
    int meta, ach;
    float user;

    static long comp = 0;

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8")); // força UTF-8

        long inicio = System.currentTimeMillis();
        GamesBinaria[] base = carregar("/tmp/games.csv");

        Scanner sc = new Scanner(System.in);
        GamesBinaria[] vet = new GamesBinaria[500];
        int n = 0;
        String s = sc.nextLine();

        // lê ids para inserir
        while (!s.equals("FIM")) {
            int alvo = Integer.parseInt(s);
            for (int i = 0; i < base.length; i++) {
                if (base[i] != null && base[i].id == alvo) {
                    vet[n++] = base[i];
                    break;
                }
            }
            s = sc.nextLine();
        }

        // ordena por nome
        ordenarPorNome(vet, n);

        // pesquisas
        String busca = sc.nextLine();
        while (!busca.equals("FIM")) {
            boolean achou = buscaBinaria(vet, n, busca);
            System.out.println(achou ? " SIM" : " NAO");
            busca = sc.nextLine();
        }
        sc.close();

        long fim = System.currentTimeMillis();
        long tempo = fim - inicio;

        // log
        criarLog("885156_binaria.txt", tempo, comp);
    }

    // ordena alfabeticamente (insertion sort)
    public static void ordenarPorNome(GamesBinaria[] v, int n) {
        for (int i = 1; i < n; i++) {
            GamesBinaria tmp = v[i];
            int j = i - 1;
            while (j >= 0 && comparar(v[j].name, tmp.name) > 0) {
                comp++;
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = tmp;
        }
    }

    // compara duas strings sem usar compareTo
    public static int comparar(String a, String b) {
        int tam = Math.min(a.length(), b.length());
        for (int i = 0; i < tam; i++) {
            char ca = a.charAt(i);
            char cb = b.charAt(i);
            if (ca != cb)
                return ca - cb;
        }
        return a.length() - b.length();
    }

    // busca binária por nome
    public static boolean buscaBinaria(GamesBinaria[] v, int n, String chave) {
        int esq = 0, dir = n - 1;
        while (esq <= dir) {
            int meio = (esq + dir) / 2;
            comp++;
            int cmp = comparar(v[meio].name, chave);
            if (cmp == 0)
                return true;
            else if (cmp < 0)
                esq = meio + 1;
            else
                dir = meio - 1;
        }
        return false;
    }

    // lê csv
    public static GamesBinaria[] carregar(String arq) {
        GamesBinaria[] vet = new GamesBinaria[20000];
        try {
            Scanner sc = new Scanner(new FileReader(arq));
            sc.nextLine();
            int i = 0;
            while (sc.hasNextLine()) {
                String[] c = sc.nextLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                GamesBinaria g = new GamesBinaria();
                g.id = parseInt(c, 0);
                g.name = limpa(c, 1);
                g.date = data(limpa(c, 2));
                g.owners = limpaInt(limpa(c, 3));
                g.price = limpaFloat(limpa(c, 4));
                g.langs = lista(limpa(c, 5));
                g.meta = parseInt(c, 6);
                g.user = limpaFloat(limpa(c, 7));
                g.ach = parseInt(c, 8);
                g.pubs = lista(getSafe(c, 9));
                g.devs = lista(getSafe(c, 10));
                g.cats = lista(getSafe(c, 11));
                g.genres = lista(getSafe(c, 12));
                g.tags = lista(getSafe(c, 13));
                vet[i++] = g;
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }
        return vet;
    }

    // funções simples
    static String limpa(String[] v, int i) {
        return (i < v.length) ? v[i].replace("\"", "") : "";
    }

    static String getSafe(String[] v, int i) {
        return (i < v.length) ? v[i] : "";
    }

    static int parseInt(String[] v, int i) {
        try {
            return Integer.parseInt(v[i].replaceAll("[^0-9-]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    static int limpaInt(String s) {
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    static float limpaFloat(String s) {
        try {
            if (s == null)
                return 0f;
            s = s.replace("Free to Play", "0").trim();
            if (s.length() == 0)
                return 0f;
            return Float.parseFloat(s);
        } catch (Exception e) {
            return 0f;
        }
    }

    static String[] lista(String s) {
        if (s == null || s.isEmpty())
            return new String[0];
        s = s.replace("[", "").replace("]", "").trim();
        if (s.isEmpty())
            return new String[0];
        return s.split("\\s*,\\s*");
    }

    static String data(String raw) {
        if (raw == null || raw.isEmpty())
            return "01/01/1970";
        try {
            Date d = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).parse(raw);
            return new SimpleDateFormat("dd/MM/yyyy").format(d);
        } catch (Exception e) {
            return "01/01/1970";
        }
    }

    static void criarLog(String nome, long tempo, long c) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nome))) {
            pw.println("885156\t" + tempo + "\t" + c);
        } catch (IOException e) {
            System.out.println("erro log");
        }
    }
}
