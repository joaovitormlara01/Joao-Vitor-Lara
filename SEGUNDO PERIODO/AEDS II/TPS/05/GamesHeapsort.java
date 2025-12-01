import java.io.*;
import java.util.*;
import java.text.*;

public class GamesHeapsort {
    int id;
    String name, date;
    int owners;
    float price;
    String[] langs, pubs, devs, cats, genres, tags;
    int meta, ach;
    float user;

    static long comp = 0, mov = 0;

    // main
    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8")); // força UTF-8

        long inicio = System.currentTimeMillis();
        GamesHeapsort[] base = carregar("/tmp/games.csv");

        Scanner sc = new Scanner(System.in);
        GamesHeapsort[] vet = new GamesHeapsort[500];
        int n = 0;
        String s = sc.nextLine();

        // lê ids
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
        sc.close();

        // ordena
        heapsort(vet, n);

        long fim = System.currentTimeMillis();
        long tempo = fim - inicio;

        // imprime
        for (int i = 0; i < n; i++)
            vet[i].printar();

        // log
        criarLog("885156_heapsort.txt", comp, mov, tempo);
    }

    // heapsort
    public static void heapsort(GamesHeapsort[] v, int n) {
        // constroi heap max
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(v, n, i);

        // extrai um por um
        for (int i = n - 1; i > 0; i--) {
            trocar(v, 0, i);
            mov++;
            heapify(v, i, 0);
        }
    }

    // ajusta o heap
    public static void heapify(GamesHeapsort[] v, int n, int i) {
        int maior = i;
        int esq = 2 * i + 1;
        int dir = 2 * i + 2;

        // filho esq
        if (esq < n) {
            comp++;
            if (v[esq].owners > v[maior].owners ||
                    (v[esq].owners == v[maior].owners && v[esq].id > v[maior].id))
                maior = esq;
        }

        // filho dir
        if (dir < n) {
            comp++;
            if (v[dir].owners > v[maior].owners ||
                    (v[dir].owners == v[maior].owners && v[dir].id > v[maior].id))
                maior = dir;
        }

        // troca
        if (maior != i) {
            trocar(v, i, maior);
            mov++;
            heapify(v, n, maior);
        }
    }

    // troca elementos
    public static void trocar(GamesHeapsort[] v, int a, int b) {
        GamesHeapsort tmp = v[a];
        v[a] = v[b];
        v[b] = tmp;
    }

    // lê csv
    public static GamesHeapsort[] carregar(String arq) {
        GamesHeapsort[] vet = new GamesHeapsort[20000];
        try {
            Scanner sc = new Scanner(new FileReader(arq));
            sc.nextLine();
            int i = 0;
            while (sc.hasNextLine()) {
                String[] c = sc.nextLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                GamesHeapsort g = new GamesHeapsort();
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

    // imprime
    void printar() {
        System.out.print("=> " + id + " ## " + name + " ## " + date + " ## " + owners + " ## " + price);
        System.out.print(" ## " + limpaLista(langs) + " ## " + meta + " ## " + user);
        System.out.print(" ## " + ach + " ## " + limpaLista(pubs) + " ## " + limpaLista(devs));
        System.out.print(" ## " + limpaLista(cats) + " ## " + limpaLista(genres) + " ## " + limpaLista(tags));
        System.out.println(" ##");
    }

    // remove aspas internas mas mantém colchetes
    static String limpaLista(String[] v) {
        if (v == null || v.length == 0)
            return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < v.length; i++) {
            String item = v[i].replace("\"", "").replace("'", "").trim();
            sb.append(item);
            if (i < v.length - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
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

    static void criarLog(String nome, long c, long m, long t) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nome))) {
            pw.println("885156\t" + c + "\t" + m + "\t" + t);
        } catch (IOException e) {
            System.out.println("erro log");
        }
    }
}
