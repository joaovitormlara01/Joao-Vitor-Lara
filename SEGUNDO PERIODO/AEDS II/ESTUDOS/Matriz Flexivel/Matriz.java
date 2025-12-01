class Celula {
    public int elemento;
    public Celula esq, dir, sup, inf;

    // Construtor padrão
    Celula() {
        this(0);
    }

    // Construtor com valor
    Celula(int elemento) {
        this.elemento = elemento;
        this.esq = this.dir = this.sup = this.inf = null;
    }
}

public class Matriz {

    private Celula inicio;
    private int linhas, colunas;

    // Construtor
    Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        construirMatriz();
    }

    // Cria a estrutura encadeada
    private void construirMatriz() {
        inicio = new Celula();
        Celula i = inicio;

        // Primeira linha
        for (int j = 1; j < colunas; j++) {
            i.dir = new Celula();
            i.dir.esq = i;
            i = i.dir;
        }

        // Demais linhas
        Celula linha = inicio;
        for (int l = 1; l < linhas; l++) {
            Celula nova = new Celula();
            linha.inf = nova;
            nova.sup = linha;

            Celula atual = nova;
            Celula acima = linha.dir;

            for (int j = 1; j < colunas; j++) {
                atual.dir = new Celula();
                atual.dir.esq = atual;
                atual.dir.sup = acima;
                acima.inf = atual.dir;

                atual = atual.dir;
                acima = acima.dir;
            }

            linha = linha.inf;
        }
    }

    // Mostra a matriz
    public void mostrar() {
        Celula i = inicio;
        for (int l = 0; l < linhas; l++) {
            Celula j = i;
            for (int c = 0; c < colunas; c++) {
                System.out.print(j.elemento + " ");
                j = j.dir;
            }
            System.out.println();
            i = i.inf;
        }
    }

    // Main
    public static void main(String[] args) {
        Matriz m = new Matriz(2, 2); // cria matriz 2x2
        m.mostrar(); // exibe a matriz
    }
}
