#include <stdio.h>
#include <string.h>

int main(void)
{
    char esq[101], dir[101], final[203];

    // lê pares até EOF.
    while (scanf("%100s %100s", esq, dir) == 2)
    {
        int e = 0, d = 0, f = 0;

        // intercala enquanto ambas têm caracteres
        while (esq[e] != '\0' && dir[d] != '\0')
        {
            final[f++] = esq[e++];
            final[f++] = dir[d++];
        }

        // copia o resto da esquerda (se sobrar)
        while (esq[e] != '\0')
        {
            final[f++] = esq[e++];
        }

        // copia o resto da direita (se sobrar)
        while (dir[d] != '\0')
        {
            final[f++] = dir[d++];
        }

        final[f] = '\0';
        printf("%s\n", final);
    }

    return 0;
}
