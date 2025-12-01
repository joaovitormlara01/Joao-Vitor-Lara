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

public class Fila {
    private Celula primeiro;
    private Celula ultimo;

    Fila() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    void inserir(int x) {
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;

    }

    void mostrar() {
        for (Celula i = primeiro; i != null; i = i.prox) {
            System.out.println(("" + i.elemento));
        }
    }

    void remover() {
        if (primeiro == ultimo)
            System.out.println("erro");

        Celula tmp = primeiro.prox;
        primeiro.prox = tmp.prox;

        if (tmp == ultimo) {
            ultimo = primeiro;

        }
        tmp.prox = null;
        tmp = null;
    }

    void Maior() {
        int maior = 0;
        for (Celula i = primeiro; i != null; i = i.prox) {
            if (i.elemento > maior) {
                maior = i.elemento;
            }
        }
        System.out.println("maior numero da fila é : " + maior);
    }

    void inverter() {

    }

    void terceiro() {
        System.out.println("terceiro elemento da fila é : " + primeiro.prox.prox.prox.elemento);

    }

    public static void main(String[] args) {
        Fila f = new Fila();
        System.out.println("fila criada");
        f.inserir(1);
        f.inserir(2);
        f.inserir(3);
        f.inserir(4);
        f.inserir(5);
        f.inserir(6);
        f.remover();
        f.Maior();
        f.terceiro();
        System.out.println("lista completa");
        f.mostrar();
    }
}
