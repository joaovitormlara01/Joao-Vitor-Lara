
class CelulaDupla {
    int elemento;
    CelulaDupla ant, prox;

    CelulaDupla() {
        this(0);
    }

    CelulaDupla(int elemento) {
        this.elemento = elemento;
        this.ant = this.prox = null;
    }
}

public class ListaDupla {
    private CelulaDupla primeiro;
    private CelulaDupla ultimo;

    ListaDupla() {
        primeiro = new CelulaDupla();
        ultimo = primeiro;
    }

    void inserirInicio(int x) {
        CelulaDupla tmp = new CelulaDupla(x);
        tmp.ant = primeiro;
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        } else {
            tmp.prox.ant = tmp;
        }
        tmp = null;
    }

    void inserirFim(int x) {
        ultimo.prox = new CelulaDupla(x);
        ultimo.prox.ant = ultimo;
        ultimo = ultimo.prox;
    }

    public void inserir(int x, int pos) {
        int tamanho = tamanho();
        pos = pos - 1;
        if (pos < 0 || pos > tamanho) {
            System.out.println("erro");
        } else if (pos == 0) {
            inserirInicio(x);
        } else if (pos == tamanho) {
            inserirFim(x);
        } else {
            CelulaDupla i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox)
                ;
            CelulaDupla tmp = new CelulaDupla(x);
            tmp.ant = i;
            tmp.prox = i.prox;
            tmp.ant.prox = tmp.prox.ant = tmp;
            tmp = i = null;
        }
    }

    int tamanho() {
        int t = 0;
        for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
            t++;
        }
        return t;
    }

    void removerInicio() {
        if (primeiro == ultimo) {
            System.out.println("erro");
        }
        CelulaDupla tmp = primeiro;
        primeiro = primeiro.prox;
        tmp.prox = primeiro.ant = null;
        tmp = null;
    }

    void removerFim() {
        if (primeiro == ultimo)
            System.out.println("erro");
        ultimo = ultimo.ant;
        ultimo.prox.ant = null;
        ultimo.prox = null;
    }

    public void remover(int pos) {
        int tamanho = tamanho();
        if (primeiro == ultimo) {
            System.out.println("erro");
        } else if (pos < 0 || pos >= tamanho) {
            System.out.println("erro");
        } else if (pos == 0) {
            removerInicio();
        } else if (pos == tamanho - 1) {
            removerFim();
        } else {
            CelulaDupla i = primeiro.prox;
            for (int j = 0; j < pos; j++, i = i.prox)
                ;
            i.ant.prox = i.prox;
            i.prox.ant = i.ant;
            i.prox = i.ant = null;
            i = null;
        }
    }

    void mostrar() {
        System.out.println(" Printando Lista :");
        for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
            System.out.println("[" + i.elemento + "]");
        }
    }

    void mostrarInverso() {
        System.out.println("Mostrando Inverso");
        for (CelulaDupla i = ultimo; i.ant != null; i = i.ant) {
            System.out.println("[" + i.elemento + "]");
        }
    }

    void quickSort(CelulaDupla esq, CelulaDupla dir) {
        if (dir != null && esq != dir && esq != dir.prox) {
            int pivo = dir.elemento;
            CelulaDupla i = esq.ant;

            for (CelulaDupla j = esq; j != dir; j = j.prox) {
                if (j.elemento <= pivo) {
                    if (i == null)
                        i = esq;
                    else
                        i = i.prox;

                    int temp = i.elemento;
                    i.elemento = j.elemento;
                    j.elemento = temp;
                }
            }

            if (i == null)
                i = esq;
            else
                i = i.prox;

            int temp = i.elemento;
            i.elemento = dir.elemento;
            dir.elemento = temp;

            quickSort(esq, i.ant);
            quickSort(i.prox, dir);
        }
    }

    public static void main(String[] args) {
        ListaDupla l = new ListaDupla();
        System.out.println("Lista dupla flexível criada com sucesso!");
        l.inserirInicio(1);
        l.inserirInicio(1);
        l.inserirInicio(1);
        l.inserirInicio(2);
        l.inserirFim(2);
        l.mostrar();
        l.remover(4);
        l.removerFim();
        l.removerInicio();
        l.mostrar();
        l.inserirFim(4);
        l.inserirInicio(3);
        l.inserir(2, 3);
        l.mostrar();
        l.mostrarInverso();
        l.quickSort(l.primeiro.prox, l.ultimo);
        l.mostrar();
    }
}
