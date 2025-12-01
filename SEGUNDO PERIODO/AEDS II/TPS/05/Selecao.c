#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <time.h>

#define MAX_LINE 2000
#define MAX_LIST 50

typedef struct StringArray
{
    char *itens[MAX_LIST];
    int qtd;
} StringArray;

typedef struct Game
{
    int id;
    char name[256];
    char releaseDate[20];
    int estimatedOwners;
    float price;
    StringArray supportedLanguages;
    int metacriticScore;
    float userScore;
    int achievements;
    StringArray publishers;
    StringArray developers;
    StringArray categories;
    StringArray genres;
    StringArray tags;
} Game;

typedef struct Lista
{
    Game *dados;
    int tam;
    int limite;
} Lista;

// ========================= FUNÇÕES PRINCIPAIS ==============================

// inicia a lista
void iniciarLista(Lista *l, int limite)
{
    l->limite = limite;
    l->tam = 0;
    l->dados = malloc(sizeof(Game) * limite);
}

// adiciona no fim
void inserirFim(Lista *l, Game jogo)
{
    if (l->tam < l->limite)
        l->dados[l->tam++] = jogo;
}

// imprime os dados do jogo
void mostrarGame(Game *g)
{
    // ajusta data
    char *meses[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    char mesStr[4];
    int d, a, m = 1;
    if (sscanf(g->releaseDate, "%3s %d, %d", mesStr, &d, &a) == 3)
    {
        for (int i = 0; i < 12; i++)
        {
            if (strcmp(mesStr, meses[i]) == 0)
            {
                m = i + 1;
                break;
            }
        }
        sprintf(g->releaseDate, "%02d/%02d/%d", d, m, a);
    }
    else
        strcpy(g->releaseDate, "01/01/1970");

    printf("=> %d ## %s ## %s ## %d ## ", g->id, g->name, g->releaseDate, g->estimatedOwners);
    if (g->price == (int)g->price)
        printf("%.1f", g->price);
    else
        printf("%g", g->price);

    printf(" ## [");
    for (int i = 0; i < g->supportedLanguages.qtd; i++)
    {
        if (i > 0)
            printf(", ");
        printf("%s", g->supportedLanguages.itens[i]);
    }

    printf("] ## %d ## %.1f ## %d ## [", g->metacriticScore, g->userScore, g->achievements);
    for (int i = 0; i < g->publishers.qtd; i++)
    {
        if (i > 0)
            printf(", ");
        printf("%s", g->publishers.itens[i]);
    }

    printf("] ## [");
    for (int i = 0; i < g->developers.qtd; i++)
    {
        if (i > 0)
            printf(", ");
        printf("%s", g->developers.itens[i]);
    }

    printf("] ## [");
    for (int i = 0; i < g->categories.qtd; i++)
    {
        if (i > 0)
            printf(", ");
        printf("%s", g->categories.itens[i]);
    }

    printf("] ## [");
    for (int i = 0; i < g->genres.qtd; i++)
    {
        if (i > 0)
            printf(", ");
        printf("%s", g->genres.itens[i]);
    }

    printf("] ## [");
    for (int i = 0; i < g->tags.qtd; i++)
    {
        if (i > 0)
            printf(", ");
        printf("%s", g->tags.itens[i]);
    }
    printf("] ##\n");
}

// le o csv
void lerArquivo(Lista *l, char *nomeArq)
{
    FILE *arq = fopen(nomeArq, "r");
    if (!arq)
    {
        printf("arquivo nao encontrado\n");
        return;
    }
    char linha[MAX_LINE];
    fgets(linha, MAX_LINE, arq); // pula cabeçalho
    while (fgets(linha, MAX_LINE, arq))
    {
        linha[strcspn(linha, "\n")] = 0;
        Game g;
        char aux[MAX_LINE], *campos[20], *tok;
        int cont = 0, dentro = 0, k = 0;
        for (int i = 0; linha[i]; i++)
        {
            if (linha[i] == '"')
                dentro = !dentro;
            else if (linha[i] == ',' && !dentro)
                aux[k++] = ';';
            else
                aux[k++] = linha[i];
        }
        aux[k] = '\0';
        tok = strtok(aux, ";");
        while (tok && cont < 20)
        {
            campos[cont++] = tok;
            tok = strtok(NULL, ";");
        }

        g.id = atoi(campos[0]);
        strcpy(g.name, campos[1]);
        strcpy(g.releaseDate, campos[2]);
        g.estimatedOwners = atoi(campos[3]);
        g.price = atof(campos[4]);
        g.metacriticScore = atoi(campos[6]);
        g.userScore = atof(campos[7]);
        g.achievements = atoi(campos[8]);

        inserirFim(l, g);
    }
    fclose(arq);
}

// compara nome
int cmpNome(const char *a, const char *b)
{
    while (*a && *b)
    {
        if (*a != *b)
            return (*a < *b) ? -1 : 1;
        a++;
        b++;
    }
    if (*a == *b)
        return 0;
    return *a ? 1 : -1;
}

// selection sort
void ordenar(Game *v, int n, long long *cmp, long long *mov)
{
    for (int i = 0; i < n - 1; i++)
    {
        int menor = i;
        for (int j = i + 1; j < n; j++)
        {
            (*cmp)++;
            if (cmpNome(v[j].name, v[menor].name) < 0)
                menor = j;
        }
        if (menor != i)
        {
            Game tmp = v[i];
            v[i] = v[menor];
            v[menor] = tmp;
            (*mov) += 3;
        }
    }
}

// log
void criarLog(const char *mat, double tempo, long long cmp, long long mov)
{
    char nome[64];
    sprintf(nome, "%s_selecao.txt", mat);
    FILE *f = fopen(nome, "w");
    if (!f)
        return;
    fprintf(f, "%s\t%lld\t%lld\t%.3f\n", mat, cmp, mov, tempo);
    fclose(f);
}

// libera memória
void liberar(Lista *l)
{
    free(l->dados);
}

// =========================== MAIN ===============================

int main()
{
    const char *MAT = "885156";
    Lista base;
    iniciarLista(&base, 20000);
    lerArquivo(&base, "/tmp/games.csv");

    Game *selecao = malloc(sizeof(Game) * 5000);
    int nSel = 0;
    char entrada[64];

    while (scanf("%63s", entrada) == 1 && strcmp(entrada, "FIM") != 0)
    {
        int id = atoi(entrada);
        for (int i = 0; i < base.tam; i++)
        {
            if (base.dados[i].id == id)
            {
                selecao[nSel++] = base.dados[i];
                break;
            }
        }
    }

    long long cmp = 0, mov = 0;
    clock_t ini = clock();
    ordenar(selecao, nSel, &cmp, &mov);
    clock_t fim = clock();

    double tempo = 1000.0 * (double)(fim - ini) / CLOCKS_PER_SEC;

    for (int i = 0; i < nSel; i++)
        mostrarGame(&selecao[i]);

    criarLog(MAT, tempo, cmp, mov);

    liberar(&base);
    free(selecao);
    return 0;
}
