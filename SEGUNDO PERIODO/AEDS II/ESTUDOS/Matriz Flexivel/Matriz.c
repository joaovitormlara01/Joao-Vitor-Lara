#include <stdio.h>
#include <stdlib.h>

// Estrutura da célula
typedef struct Celula
{
    int elemento;
    struct Celula *esq, *dir, *sup, *inf;
} Celula;

// Estrutura da matriz
typedef struct
{
    Celula *inicio;
    int linhas, colunas;
} Matriz;

// Cria uma nova célula
Celula *novaCelula(int x)
{
    Celula *tmp = (Celula *)malloc(sizeof(Celula));
    tmp->elemento = x;
    tmp->esq = tmp->dir = tmp->sup = tmp->inf = NULL;
    return tmp;
}

// Cria e encadeia a matriz
Matriz *novaMatriz(int linhas, int colunas)
{
    Matriz *m = (Matriz *)malloc(sizeof(Matriz));
    m->linhas = linhas;
    m->colunas = colunas;

    m->inicio = novaCelula(0);
    Celula *i = m->inicio;

    // primeira linha
    for (int j = 1; j < colunas; j++)
    {
        i->dir = novaCelula(0);
        i->dir->esq = i;
        i = i->dir;
    }

    // demais linhas
    Celula *linha = m->inicio;
    for (int l = 1; l < linhas; l++)
    {
        Celula *nova = novaCelula(0);
        linha->inf = nova;
        nova->sup = linha;

        Celula *atual = nova;
        Celula *acima = linha->dir;

        for (int j = 1; j < colunas; j++)
        {
            atual->dir = novaCelula(0);
            atual->dir->esq = atual;
            atual->dir->sup = acima;
            acima->inf = atual->dir;

            atual = atual->dir;
            acima = acima->dir;
        }

        linha = linha->inf;
    }

    return m;
}

// Mostra os elementos da matriz
void mostrar(Matriz *m)
{
    Celula *i = m->inicio;
    for (int l = 0; l < m->linhas; l++)
    {
        Celula *j = i;
        for (int c = 0; c < m->colunas; c++)
        {
            printf("%d ", j->elemento);
            j = j->dir;
        }
        printf("\n");
        i = i->inf;
    }
}

// Programa principal
int main()
{
    Matriz *m = novaMatriz(2, 2);
    mostrar(m);
    return 0;
}
