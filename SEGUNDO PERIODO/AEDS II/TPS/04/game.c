#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define BUF_LIN 4096
#define BUF_CAMPO 512

// ---- Estrutura----
typedef struct
{
    int cod;
    char *nomeJ;
    char *dataFmt;
    int donosEst;
    float precoV;
    char **idiomas;
    int nIdiomas;
    int meta;
    float notaUsr;
    int feitos;
    char **pubs;
    int nPubs;
    char **devs;
    int nDevs;
    char **cats;
    int nCats;
    char **gens;
    int nGens;
    char **tags;
    int nTags;
} GameRec;

// ====== Protótipos ======
// alto nível
void parseLinha(GameRec *g, char *linha);
void mostrarJogo(GameRec *g);
void limparJogo(GameRec *g);
void printArr(char **v, int n);

// utilidades
char *puxarCampo(char *linha, int *pos);
char **splitar(char *s, char delim, int *n);
char *aparar(const char *s);
bool ehBlank(char c);
char *fmtData(char *raw);

// =================== MAIN ===================
int main(void)
{
    const char *path = "/tmp/games.csv";
    FILE *arq = fopen(path, "r");
    if (!arq)
    {
        perror("Erro ao abrir o arquivo");
        return 1;
    }

    // conta linhas úteis
    char buf[BUF_LIN];
    int total = 0;
    fgets(buf, BUF_LIN, arq); // cabeçalho
    while (fgets(buf, BUF_LIN, arq))
        total++;
    fclose(arq);

    GameRec *base = (GameRec *)malloc(sizeof(GameRec) * total);
    if (!base)
    {
        printf("Falha na alocação.\n");
        return 1;
    }

    // preenche
    arq = fopen(path, "r");
    fgets(buf, BUF_LIN, arq); // pula cabeçalho
    int i = 0;
    while (fgets(buf, BUF_LIN, arq))
    {
        parseLinha(&base[i], buf);
        i++;
    }
    fclose(arq);

    // stdin: IDs até FIM
    char in[BUF_CAMPO];
    while (fgets(in, BUF_CAMPO, stdin))
    {
        in[strcspn(in, "\n")] = '\0';
        if (strcmp(in, "FIM") == 0)
            break;

        int alvo = atoi(in);
        for (int k = 0; k < total; k++)
        {
            if (base[k].cod == alvo)
            {
                mostrarJogo(&base[k]);
                break;
            }
        }
    }

    for (int k = 0; k < total; k++)
        limparJogo(&base[k]);
    free(base);
    return 0;
}

// =================== EXIBIÇÃO ===================
void printArr(char **v, int n)
{
    printf("[");
    for (int i = 0; i < n; i++)
    {
        printf("%s", v[i]);
        if (i < n - 1)
            printf(", ");
    }
    printf("]");
}

void mostrarJogo(GameRec *g)
{
    // garante 2 dígitos no dia
    char d[12];
    strcpy(d, g->dataFmt);
    if (d[1] == '/')
    { // 1/05/2010 -> 01/05/2010
        memmove(d + 1, d, strlen(d) + 1);
        d[0] = '0';
    }

    printf("=> %d ## %s ## %s ## %d ## %.2f ## ",
           g->cod, g->nomeJ, d, g->donosEst, g->precoV);

    printArr(g->idiomas, g->nIdiomas);

    // imprime direto
    printf(" ## %d ## %.1f ## %d ## ",
           g->meta, g->notaUsr, g->feitos);

    printArr(g->pubs, g->nPubs);
    printf(" ## ");
    printArr(g->devs, g->nDevs);
    printf(" ## ");
    printArr(g->cats, g->nCats);
    printf(" ## ");
    printArr(g->gens, g->nGens);
    printf(" ## ");
    printArr(g->tags, g->nTags);
    printf(" ##\n");
}

// =================== PARSE ===================
void parseLinha(GameRec *g, char *linha)
{
    int pos = 0;

    char *cId = puxarCampo(linha, &pos);
    g->cod = atoi(cId);
    free(cId);
    g->nomeJ = puxarCampo(linha, &pos);
    g->dataFmt = fmtData(puxarCampo(linha, &pos));

    char *cOwn = puxarCampo(linha, &pos);
    g->donosEst = atoi(cOwn);
    free(cOwn);

    char *cPreco = puxarCampo(linha, &pos);
    g->precoV = (cPreco[0] == '\0' || strcmp(cPreco, "Free to Play") == 0) ? 0.0f : atof(cPreco);
    free(cPreco);

    // idiomas: tira colchetes/aspas simples
    char *cLangs = puxarCampo(linha, &pos);
    if (cLangs[0] == '[')
        memmove(cLangs, cLangs + 1, strlen(cLangs));
    cLangs[strcspn(cLangs, "]")] = '\0';
    for (int i = 0; cLangs[i]; i++)
        if (cLangs[i] == '\'')
            cLangs[i] = ' ';
    g->idiomas = splitar(cLangs, ',', &g->nIdiomas);
    free(cLangs);

    char *cMeta = puxarCampo(linha, &pos);
    g->meta = (cMeta[0] == '\0') ? 0 : atoi(cMeta); // vazio -> 0
    free(cMeta);

    char *cUsr = puxarCampo(linha, &pos);
    if (cUsr[0] == '\0' || strcmp(cUsr, "tbd") == 0)
        g->notaUsr = 0.0f; // vazio/tbd -> 0.0
    else
        g->notaUsr = atof(cUsr);
    free(cUsr);

    char *cAch = puxarCampo(linha, &pos);
    g->feitos = atoi(cAch);
    free(cAch);

    char *cP = puxarCampo(linha, &pos);
    g->pubs = splitar(cP, ',', &g->nPubs);
    free(cP);
    char *cD = puxarCampo(linha, &pos);
    g->devs = splitar(cD, ',', &g->nDevs);
    free(cD);
    char *cC = puxarCampo(linha, &pos);
    g->cats = splitar(cC, ',', &g->nCats);
    free(cC);
    char *cG = puxarCampo(linha, &pos);
    g->gens = splitar(cG, ',', &g->nGens);
    free(cG);
    char *cT = puxarCampo(linha, &pos);
    g->tags = splitar(cT, ',', &g->nTags);
    free(cT);
}

// =================== LIMPEZA ===================
void limparJogo(GameRec *g)
{
    free(g->nomeJ);
    free(g->dataFmt);

    for (int i = 0; i < g->nIdiomas; i++)
        free(g->idiomas[i]);
    free(g->idiomas);
    for (int i = 0; i < g->nPubs; i++)
        free(g->pubs[i]);
    free(g->pubs);
    for (int i = 0; i < g->nDevs; i++)
        free(g->devs[i]);
    free(g->devs);
    for (int i = 0; i < g->nCats; i++)
        free(g->cats[i]);
    free(g->cats);
    for (int i = 0; i < g->nGens; i++)
        free(g->gens[i]);
    free(g->gens);
    for (int i = 0; i < g->nTags; i++)
        free(g->tags[i]);
    free(g->tags);
}

// =================== UTILS ===================
char *puxarCampo(char *linha, int *pos)
{
    char *out = (char *)malloc(BUF_CAMPO);
    int i = 0;
    bool asp = false;

    if (linha[*pos] == '"')
    {
        asp = true;
        (*pos)++;
    }
    while (linha[*pos] != '\0' && (asp || linha[*pos] != ','))
    {
        if (asp && linha[*pos] == '"')
        {
            (*pos)++;
            break;
        }
        out[i++] = linha[(*pos)++];
    }
    if (linha[*pos] == ',')
        (*pos)++;
    out[i] = '\0';
    return out;
}

char **splitar(char *s, char delim, int *n)
{
    int cnt = 1;
    for (int i = 0; s[i] != '\0'; i++)
        if (s[i] == delim)
            cnt++;
    *n = cnt;

    char **v = (char **)malloc(sizeof(char *) * cnt);
    int ini = 0, idx = 0;

    for (int i = 0;; i++)
    {
        if (s[i] == delim || s[i] == '\0')
        {
            int tam = i - ini;
            char tmp[BUF_CAMPO];
            if (tam >= BUF_CAMPO)
                tam = BUF_CAMPO - 1;
            strncpy(tmp, s + ini, tam);
            tmp[tam] = '\0';

            char *aparada = aparar(tmp);
            v[idx] = (char *)malloc(strlen(aparada) + 1);
            strcpy(v[idx], aparada);
            free(aparada);

            idx++;
            ini = (s[i] == '\0') ? i : i + 1;
        }
        if (s[i] == '\0')
            break;
    }
    return v;
}

char *aparar(const char *s)
{
    while (ehBlank(*s))
        s++;
    char *cp = (char *)malloc(strlen(s) + 1);
    strcpy(cp, s);
    char *fim = cp + strlen(cp) - 1;
    while (fim >= cp && ehBlank(*fim))
    {
        *fim = '\0';
        fim--;
    }
    return cp;
}

bool ehBlank(char c)
{
    return c == ' ' || c == '\f' || c == '\n' || c == '\r' || c == '\t' || c == '\v';
}

char *fmtData(char *raw)
{
    // "Mmm dd, yyyy" -> "dd/MM/yyyy"
    char *out = (char *)malloc(12);
    char dia[3] = "01", mes[4] = {0}, ano[5] = "0000";
    sscanf(raw, "%3s %[^,], %4s", mes, dia, ano);

    const char *m = "01";
    if (!strcmp(mes, "Feb"))
        m = "02";
    else if (!strcmp(mes, "Mar"))
        m = "03";
    else if (!strcmp(mes, "Apr"))
        m = "04";
    else if (!strcmp(mes, "May"))
        m = "05";
    else if (!strcmp(mes, "Jun"))
        m = "06";
    else if (!strcmp(mes, "Jul"))
        m = "07";
    else if (!strcmp(mes, "Aug"))
        m = "08";
    else if (!strcmp(mes, "Sep"))
        m = "09";
    else if (!strcmp(mes, "Oct"))
        m = "10";
    else if (!strcmp(mes, "Nov"))
        m = "11";
    else if (!strcmp(mes, "Dec"))
        m = "12";

    sprintf(out, "%s/%s/%s", dia, m, ano);
    free(raw);
    return out;
}
