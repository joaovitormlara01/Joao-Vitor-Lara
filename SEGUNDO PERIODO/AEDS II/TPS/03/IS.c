#include <stdio.h>
#include <stdlib.h>

// Função para imprimir os resultados
void imprimir(int qtdChars, int qtdVogais, int qtdConsoantes, int qtdSeparadores, int qtdDigitos)
{
    if (qtdChars == qtdVogais)
    {
        printf("SIM NAO NAO NAO\n");
    }
    else if (qtdChars == qtdConsoantes)
    {
        printf("NAO SIM NAO NAO\n");
    }
    else if (qtdDigitos == (qtdChars - 1) && qtdSeparadores == 1)
    {
        printf("NAO NAO NAO SIM\n");
    }
    else if (qtdChars == qtdDigitos)
    {
        printf("NAO NAO SIM SIM\n");
    }
    else
    {
        printf("NAO NAO NAO NAO\n");
    }
}

// Função recursiva para checar cada caractere da palavra
void analisarRecursivo(char texto[], int indice, int qtdChars, int qtdVogais, int qtdConsoantes, int qtdSeparadores, int qtdDigitos)
{
    if (texto[indice] == '\0')
    {
        // fim da string → imprime resultado
        imprimir(qtdChars, qtdVogais, qtdConsoantes, qtdSeparadores, qtdDigitos);
        return;
    }

    qtdChars++;
    if ((texto[indice] >= 'a' && texto[indice] <= 'z') || (texto[indice] >= 'A' && texto[indice] <= 'Z'))
    {
        if (texto[indice] == 'a' || texto[indice] == 'e' || texto[indice] == 'i' || texto[indice] == 'o' || texto[indice] == 'u' ||
            texto[indice] == 'A' || texto[indice] == 'E' || texto[indice] == 'I' || texto[indice] == 'O' || texto[indice] == 'U')
            qtdVogais++;
        else
            qtdConsoantes++;
    }
    else if (texto[indice] >= '0' && texto[indice] <= '9')
    {
        qtdDigitos++;
    }
    else if (texto[indice] == ',' || texto[indice] == '.')
    {
        qtdSeparadores++;
    }

    // chamada recursiva para o próximo caractere
    analisarRecursivo(texto, indice + 1, qtdChars, qtdVogais, qtdConsoantes, qtdSeparadores, qtdDigitos);
}

// Função recursiva para ler as entradas até encontrar "FIM"
void lerDados()
{
    char buffer[400];
    if (fgets(buffer, 400, stdin) == NULL)
        return;

    // remover '\n' do final se existir
    int pos = 0;
    while (buffer[pos] != '\0')
    {
        if (buffer[pos] == '\n')
        {
            buffer[pos] = '\0';
            break;
        }
        pos++;
    }

    if (buffer[0] == 'F' && buffer[1] == 'I' && buffer[2] == 'M' && buffer[3] == '\0')
        return;

    analisarRecursivo(buffer, 0, 0, 0, 0, 0, 0);

    // chamada recursiva para processar a próxima entrada
    lerDados();
}

int main()
{
    lerDados();
    return 0;
}
