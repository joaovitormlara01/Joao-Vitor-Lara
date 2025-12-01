class No {
    int elemento;
    No esq;
    No dir;

    No(int elemento) {
        this(elemento, null, null);
    }

    No(int elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

public class ArvoreBinaria {
    private No raiz;
    private int n;

    public ArvoreBinaria() {
        raiz = null;
        n = 0;
    }

    void inserir(int x) {
        raiz = inserir(x, raiz);
    }

    private No inserir(int x, No i) {
        if (i == null) {
            i = new No(x);
            n++;
        } else if (x < i.elemento) {
            i.esq = inserir(x, i.esq);
        } else if (x > i.elemento) {
            i.dir = inserir(x, i.dir);
        } else {
            System.out.println("ERRO");
        }
        return i;
    }

    boolean pesquisar(int x) {
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(int x, No i) {
        boolean resp;
        if (i == null) {
            return false;
        } else if (x == i.elemento) {
            return true;
        } else if (x < i.elemento) {
            resp = pesquisar(x, i.esq);
        } else {
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    void caminharCentral() {
        caminharCentral(raiz);
    }

    private void caminharCentral(No i) {
        if (i != null) {
            caminharCentral(i.esq);
            System.out.println(i.elemento);
            caminharCentral(i.dir);
        }
    }

    void caminharPre() {
        caminharPre(raiz);
    }

    private void caminharPre(No i) {
        if (i != null) {
            System.out.println(i.elemento);
            caminharPre(i.esq);
            caminharPre(i.dir);
        }
    }

    void caminharPos() {
        caminharPos(raiz);
    }

    private void caminharPos(No i) {
        if (i != null) {
            caminharPos(i.esq);
            caminharPos(i.dir);
            System.out.println(i.elemento);
        }
    }

    int numPares() {
        return numPares(raiz);
    }

    private int numPares(No i) {
        if (i == null)
            return 0;

        int count = 0;

        if (i.elemento % 2 == 0)
            count++;

        count += numPares(i.esq);
        count += numPares(i.dir);

        return count;
    }

    private int soma = 0;

    int somar() {
        return somar(raiz);
    }

    private int somar(No i) {
        if (i == null) {
            return 0;
        } else {
            return i.elemento + somar(i.esq) + somar(i.dir);
        }
    }

    int getAltura() {
        return getAltura(raiz, 0);
    }

    private int getAltura(No i, int altura) {
        if (i == null) {
            altura--;
        } else {
            int alturaEsq = getAltura(i.esq, altura + 1);
            int alturaDir = getAltura(i.dir, altura + 1);
            if (alturaEsq >= alturaDir) {
                altura = alturaEsq;
            } else {
                altura = alturaDir;
            }
        }
        return altura;
    }

    int[] treeSort() {
        int[] array = new int[n];
        n = 0;
        treeSort(raiz, array);
        return array;
    }

    private void treeSort(No i, int[] array) {
        if (i != null) {
            treeSort(i.esq, array);
            array[n] = i.elemento;
            n++;
            treeSort(i.dir, array);
        }
    }

    public static void main(String[] args) {
        ArvoreBinaria a = new ArvoreBinaria();

        a.inserir(7);
        a.inserir(3);
        a.inserir(9);
        a.inserir(1);
        a.inserir(5);
        a.inserir(8);
        a.inserir(2);

        int s = a.somar();
        System.out.println("Soma:" + s);

        System.out.println("Altura: " + a.getAltura());
        System.out.println("Qtd de Numeros Pares:" + a.numPares());

        int[] ordenado = a.treeSort();
        System.out.println("TreeSort:");
        for (int x : ordenado) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
}
