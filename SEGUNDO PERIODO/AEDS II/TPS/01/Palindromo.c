#include <stdio.h>
#include <string.h>
#include <stdbool.h>

// função recursiva para verificar se é palíndromo
bool Palindromo(int i, int f, char w[])
{
    if (i >= f)
    {
        return true; // condição de parada: se os índices se cruzaram, é palíndromo
    }
    if (w[i] != w[f])
    {
        return false; // se encontrou um caractere diferente, não é palíndromo
    }
    // chamada recursiva avançando o início e recuando o fim
    return Palindromo(i + 1, f - 1, w);
}

int main(void)
{
    char word[1001];

    // lê linhas até o EOF com fgets
    while (fgets(word, sizeof(word), stdin))
    {
        // remove \n (ou \r\n) do final da string
        word[strcspn(word, "\r\n")] = '\0';

        // condição de parada: se a palavra for "FIM", encerra o programa
        if (strcmp(word, "FIM") == 0)
        {
            break;
        }

        int inicio = 0, fim = (int)strlen(word) - 1; // declara um início e um fim para a comparação

        bool ok = Palindromo(inicio, fim, word);

        if (ok)
            printf("SIM\n");
        else
            printf("NAO\n");
    }

    return 0;
}
