class Celula {
    int elemento;
    Celula prox;

    Celula() {
        this(0);
    }

    Celula(int elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

public class Lista {
    private Celula primeiro;
    private Celula ultimo;

    Lista() {
        primeiro = new Celula(); // célula cabeça
        ultimo = primeiro;
    }

    void inserirInicio(int x) {
        Celula tmp = new Celula(x);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        }
        tmp = null;
    }

    void inserirFim(int x) {
        Celula i = new Celula(x);
        ultimo.prox = i;
        ultimo = i;
    }

    void inserir(int x, int pos) {
        int tamanho = tamanho();
        pos = pos - 1;
        if (pos < 0 || pos > tamanho) {
            System.out.println("erro");
        } else if (pos == 0) {
            inserirInicio(x);
        } else if (pos == tamanho) {
            inserirFim(x);
        } else {
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox)
                ;
            Celula tmp = new Celula(x);
            tmp.prox = i.prox;
            i.prox = tmp;
            tmp = i = null;
        }
    }

    void removerInicio() {
        Celula i = primeiro.prox;
        primeiro.prox = i.prox;
        i.prox = null;
        i = null;

    }

    void removerFim() {
        if (primeiro == ultimo) {
            System.out.println("erro");
        }
        Celula i;
        for (i = primeiro; i.prox != ultimo; i = i.prox)
            ;
        ultimo = i;
        ultimo.prox = null;

    }

    void remover(int pos) {
        int tamanho = tamanho();
        pos = pos - 1;
        if (pos < 0 || pos > tamanho) {
            System.out.println("erro");
        } else if (pos == 0) {
            removerInicio();
        } else if (pos == tamanho) {
            removerFim();
        } else {
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox)
                ;
            Celula tmp = i.prox;
            i.prox = tmp.prox;
            tmp.prox = null;
            i = tmp = null;
        }
    }

    int tamanho() {
        int t = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            t++;
        }
        return t;
    }

    void mostrar() {
        System.out.println("Printando Lista...");
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            System.out.println(("[" + i.elemento + "]"));
        }
    }

    public static void main(String[] args) {
        Lista l = new Lista();
        System.out.println("Lista flexível criada com sucesso!");
        l.inserirInicio(1);
        l.inserirInicio(2);
        l.inserirInicio(3);
        l.inserirInicio(4);
        l.inserirFim(7);
        l.inserirFim(7);
        l.inserir(10, 4);
        l.remover(4);
        l.removerInicio();
        l.removerInicio();
        l.removerFim();
        l.mostrar();
    }
}