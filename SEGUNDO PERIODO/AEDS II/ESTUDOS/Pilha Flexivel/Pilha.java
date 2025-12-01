class No {
    int elemento;
    No prox;

    No(int elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

public class Pilha {
    private No topo;

    // Construtor
    Pilha() {
        topo = null;
    }

    // Inserir elemento no topo
    public void inserir(int x) {
        No novo = new No(x);
        novo.prox = topo;
        topo = novo;
    }

    // Remover elemento
    public int remover() {
        if (topo == null) {
            System.out.println("Erro: pilha vazia!");
            System.exit(1);
        }
        int elemento = topo.elemento;
        No tmp = topo;
        topo = topo.prox;
        tmp.prox = null;
        tmp = null;
        return elemento;
    }

    // Imprimir reverso
    private void imprimirReverso(No i) {
        if (i != null) {
            imprimirReverso(i.prox);
            System.out.print(i.elemento + " ");
        }
    }

    // Mostrar pilha
    public void mostrar() {
        imprimirReverso(topo);
        System.out.println();
    }

    public static void main(String[] args) {
        Pilha p = new Pilha();

        p.inserir(3);
        p.inserir(5);
        p.inserir(7);

        System.out.print("Pilha antes da remoção: ");
        p.mostrar();

        int removido = p.remover();
        System.out.println("Elemento removido: " + removido);

        System.out.print("Pilha depois da remoção: ");
        p.mostrar();
    }
}
