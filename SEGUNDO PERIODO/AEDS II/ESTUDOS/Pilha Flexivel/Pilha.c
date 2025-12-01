#include <stdio.h>
#include <stdlib.h>

// Estrutura de nó
typedef struct No
{
    int elemento;
    struct No *prox;
} No;

// Estrutura da pilha
typedef struct Pilha
{
    No *topo;
} Pilha;

// Cria uma nova pilha
Pilha *newPilha()
{
    Pilha *tmp = (Pilha *)malloc(sizeof(Pilha));
    tmp->topo = NULL;
    return tmp;
}

// Cria um novo nó
No *newNo(int x)
{
    No *tmp = (No *)malloc(sizeof(No));
    tmp->elemento = x;
    tmp->prox = NULL;
    return tmp;
}

// Insere elemento na pilha
void inserirPilha(int x, Pilha *p)
{
    No *novo = newNo(x);
    novo->prox = p->topo;
    p->topo = novo;
}

// Remove o elemento do topo da pilha
int removerPilha(Pilha *p)
{
    if (p->topo == NULL)
    {
        printf("Erro: pilha vazia!\n");
        exit(1);
    }

    int elemento = p->topo->elemento;
    No *tmp = p->topo;
    p->topo = p->topo->prox;
    tmp->prox = NULL;
    free(tmp);
    tmp = NULL;

    return elemento;
}

// Imprime a pilha de forma reversa (base → topo)
void imprimirReverso(No *topo)
{
    if (topo != NULL)
    {
        imprimirReverso(topo->prox);
        printf("%d ", topo->elemento);
    }
}

// Programa principal
int main()
{
    Pilha *p = newPilha();

    inserirPilha(3, p);
    inserirPilha(5, p);
    inserirPilha(7, p);

    printf("Pilha antes da remoção: ");
    imprimirReverso(p->topo);
    printf("\n");

    int removido = removerPilha(p);
    printf("Elemento removido: %d\n", removido);

    printf("Pilha depois da remoção: ");
    imprimirReverso(p->topo);
    printf("\n");

    return 0;
}
