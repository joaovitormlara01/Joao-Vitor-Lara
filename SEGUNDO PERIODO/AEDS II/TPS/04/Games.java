import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.*;

class Games {

    // ====== MAIN ======
    public static void main(String[] args) {
        Games[] base = carregarBanco(); // lê /tmp/games.csv
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();

        while (!"FIM".equals(s)) {
            int alvo = Integer.parseInt(s);
            // busca simples
            for (int i = 0; i < base.length; i++) {
                if (base[i] != null && base[i].getId() == alvo) {
                    base[i].printar();
                }
            }
            s = in.nextLine();
        }
        in.close();
    }

    // ====== I/O DO CSV ======
    public static Games[] carregarBanco() {
        Games vet[] = new Games[19000]; // espaço folgado
        Scanner rd = null;

        try {
            rd = new Scanner(new FileReader("/tmp/games.csv"));
            rd.nextLine(); // pula cabeçalho

            for (int i = 0; rd.hasNextLine(); i++) {
                String linha = rd.nextLine();
                Games g = new Games();
                try {
                    g.ingestir(linha);
                    vet[i] = g;
                } catch (Exception e) {
                    System.err.println("Erro na linha " + (i + 2) + ": " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Falha: " + e.getMessage());
        } finally {
            if (rd != null)
                rd.close();
        }
        return vet;
    }

    // ====== SAÍDA ======
    public void printar() {
        // mantém exatamente o formato esperado
        System.out.print(
                "=> " + this.gid + " ## " + this.titulo + " ## " + this.dtLanc + " ## " + this.ownersEst +
                        " ## " + this.custo + " ## " + Arrays.toString(this.langs) + " ## " + this.meta +
                        " ## " + this.usuario + " ## " + this.cheevs + " ## " + Arrays.toString(this.pubs) + " ## " +
                        Arrays.toString(this.devs) + " ## " + Arrays.toString(this.cats) + " ## " +
                        Arrays.toString(this.kinds) + " ## " + Arrays.toString(this.labels) + " ## ");
        System.out.println();
    }

    // ====== PARSE DE LINHA ======
    public void ingestir(String linhaBruta) {
        // normaliza separador fora de aspas
        StringBuilder sb = new StringBuilder();
        boolean aspas = false;

        for (int i = 0; i < linhaBruta.length(); i++) {
            char c = linhaBruta.charAt(i);

            if (c == '"')
                aspas = !aspas;

            if (!aspas) {
                if (c == ',')
                    sb.append(';');
                else if (c != '\"')
                    sb.append(c);
            } else {
                if (c != '"' && c != '[' && c != ']')
                    sb.append(c);
            }
        }

        String linha = sb.toString();
        String[] col = linha.split(";");

        try {
            setId(Integer.parseInt(col[0]));
            setName(col[1]);
            setReleaseDate(convData(col[2]));
            setEstimatedOwners(convOwners(col[3]));
            setPrice(convPreco(col[4]));

            setSupportedLanguages(convLista(col[5]));
            setMetacriticScore(convScore(col[6]));
            setUserScore(convUserScore(col[7]));
            setAchievements(convCheevs(col[8]));

            // condicionais só pra evitar AIOOB
            setPublishers(col.length > 9 ? convLista(col[9]) : new String[0]);
            setDevelopers(col.length > 10 ? convLista(col[10]) : new String[0]);
            setCategories(col.length > 11 ? convLista(col[11]) : new String[0]);
            setGenres(col.length > 12 ? convLista(col[12]) : new String[0]);
            setTags(col.length > 13 ? convLista(col[13]) : new String[0]);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Erro ao processar: " + linha);
        }
    }

    // ====== CONVERSORES ======
    private String convData(String raw) {
        // data padrão
        if (raw == null || raw.trim().isEmpty())
            return "01/01/1970";
        try {
            SimpleDateFormat in = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");
            Date d = in.parse(raw);
            return out.format(d);
        } catch (ParseException e) {
            return "01/01/1970";
        }
    }

    private int convOwners(String raw) {
        if (raw == null || raw.trim().isEmpty())
            return 0;
        String nums = raw.replaceAll("[^0-9]", "");
        return nums.isEmpty() ? 0 : Integer.parseInt(nums);
    }

    private float convPreco(String raw) {
        if (raw == null || raw.trim().isEmpty() || raw.equalsIgnoreCase("Free to Play"))
            return 0.0f;
        try {
            return Float.parseFloat(raw);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    private int convScore(String raw) {
        if (raw == null || raw.trim().isEmpty())
            return -1;
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private float convUserScore(String raw) {
        if (raw == null || raw.trim().isEmpty() || raw.equalsIgnoreCase("tbd"))
            return -1.0f;
        try {
            return Float.parseFloat(raw);
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }

    private int convCheevs(String raw) {
        if (raw == null || raw.trim().isEmpty())
            return 0;
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String[] convLista(String raw) {
        if (raw == null || raw.trim().isEmpty() || raw.equals("[]"))
            return new String[0];
        String conteudo = raw.replace("[", "").replace("]", "").replace("'", "").trim();
        if (conteudo.isEmpty())
            return new String[0];
        return conteudo.split("\\s*,\\s*");
    }

    // ====== CLONE ======
    public Games clone() {
        Games k = new Games();
        k.gid = this.gid;
        k.titulo = this.titulo;
        k.dtLanc = this.dtLanc;
        k.ownersEst = this.ownersEst;
        k.custo = this.custo;
        k.langs = Arrays.copyOf(this.langs, this.langs.length);
        k.meta = this.meta;
        k.usuario = this.usuario;
        k.cheevs = this.cheevs;
        k.pubs = Arrays.copyOf(this.pubs, this.pubs.length);
        k.devs = Arrays.copyOf(this.devs, this.devs.length);
        k.cats = Arrays.copyOf(this.cats, this.cats.length);
        k.kinds = Arrays.copyOf(this.kinds, this.kinds.length);
        k.labels = Arrays.copyOf(this.labels, this.labels.length);
        return k;
    }

    // ====== ATRIBUTOS (renomeados) ======
    private int gid; // id
    private String titulo; // name
    private String dtLanc; // releaseDate
    private int ownersEst; // estimatedOwners
    private float custo; // price
    private String[] langs; // supportedLanguages
    private int meta; // metacriticScore
    private float usuario; // userScore
    private int cheevs; // achievements
    private String[] pubs; // publishers
    private String[] devs; // developers
    private String[] cats; // categories
    private String[] kinds; // genres
    private String[] labels; // tags

    // ====== CONSTRUTORES ======
    public Games() {
        // vazio ok
        this.gid = 0;
        this.titulo = "";
        this.dtLanc = "";
        this.ownersEst = 0;
        this.custo = 0.0f;
        this.langs = new String[0];
        this.meta = -1;
        this.usuario = -1.0f;
        this.cheevs = 0;
        this.pubs = new String[0];
        this.devs = new String[0];
        this.cats = new String[0];
        this.kinds = new String[0];
        this.labels = new String[0];
    }

    public Games(int id, String name, String releasedate, int estimatedOwners, float price, String[] supportedLanguages,
            int metacriticScore, float userScore, int achievements, String[] publishers, String[] developers,
            String[] categories, String[] genres, String[] tags) {
        // mapeia pro novo nome
        this.gid = id;
        this.titulo = name;
        this.dtLanc = releasedate;
        this.ownersEst = estimatedOwners;
        this.custo = price;
        this.langs = supportedLanguages;
        this.meta = metacriticScore;
        this.usuario = userScore;
        this.cheevs = achievements;
        this.pubs = publishers;
        this.devs = developers;
        this.cats = categories;
        this.kinds = genres;
        this.labels = tags;
    }

    // ====== GET/SET ======
    public void setId(int id) {
        this.gid = id;
    }

    public int getId() {
        return gid;
    }

    public void setName(String name) {
        this.titulo = name;
    }

    public String getName() {
        return titulo;
    }

    public void setReleaseDate(String releasedate) {
        this.dtLanc = releasedate;
    }

    public String getReleaseDate() {
        return dtLanc;
    }

    public void setEstimatedOwners(int estimatedOwners) {
        this.ownersEst = estimatedOwners;
    }

    public int getEstimatedOwners() {
        return ownersEst;
    }

    public void setPrice(float price) {
        this.custo = price;
    }

    public float getPrice() {
        return custo;
    }

    public void setSupportedLanguages(String[] supportedLanguages) {
        this.langs = supportedLanguages;
    }

    public String[] getSupportedLanguages() {
        return langs;
    }

    public void setMetacriticScore(int metacriticScore) {
        this.meta = metacriticScore;
    }

    public int getMetacriticScore() {
        return meta;
    }

    public void setUserScore(float userScore) {
        this.usuario = userScore;
    }

    public float getUserScore() {
        return usuario;
    }

    public void setAchievements(int achievements) {
        this.cheevs = achievements;
    }

    public int getAchievements() {
        return cheevs;
    }

    public void setPublishers(String[] publishers) {
        this.pubs = publishers;
    }

    public String[] getPublishers() {
        return pubs;
    }

    public void setDevelopers(String[] developers) {
        this.devs = developers;
    }

    public String[] getDevelopers() {
        return devs;
    }

    public void setCategories(String[] categories) {
        this.cats = categories;
    }

    public String[] getCategories() {
        return cats;
    }

    public void setGenres(String[] genres) {
        this.kinds = genres;
    }

    public String[] getGenres() {
        return kinds;
    }

    public void setTags(String[] tags) {
        this.labels = tags;
    }

    public String[] getTags() {
        return labels;
    }
}
