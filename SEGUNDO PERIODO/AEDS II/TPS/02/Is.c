#include <stdio.h>
#include <stdbool.h>

// Função auxiliar para calcular o tamanho da string
int tamanho(char *str)
{
    int i = 0;
    while (str[i] != '\0')
    {
        i++;
    }
    return i;
}

// Função auxiliar para verificar se string é "FIM"
bool ehFIM(char *str)
{
    return (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0');
}

bool Met1(char *str)
{
    int length = tamanho(str);

    if (length == 0)
        return false;

    for (int i = 0; i < length; i++)
    {
        char c = str[i];

        // transforma em minúscula se for maiúscula
        if (c >= 'A' && c <= 'Z')
            c = c + 32;

        // só pode ser vogal
        if (!(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'))
        {
            return false;
        }
    }
    return true;
}

bool Met2(char *str)
{
    int length = tamanho(str);

    if (length == 0)
        return false;

    for (int i = 0; i < length; i++)
    {
        char c = str[i];

        if (c >= 'A' && c <= 'Z')
            c = c + 32;

        // precisa ser letra
        if (!(c >= 'a' && c <= 'z'))
            return false;

        // não pode ser vogal
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
            return false;
    }
    return true;
}

bool Met3(char *str)
{
    int length = tamanho(str);
    int i = 0;
    bool temDigito = false;

    if (length == 0)
        return false;

    // sinal opcional
    if (str[0] == '+' || str[0] == '-')
        i++;

    for (; i < length; i++)
    {
        char c = str[i];
        if (c >= '0' && c <= '9')
        {
            temDigito = true;
        }
        else
        {
            return false;
        }
    }
    return temDigito;
}

bool Met4(char *str)
{
    int length = tamanho(str);
    int i = 0;
    int separadores = 0;
    bool temDigito = false;

    if (length == 0)
        return false;

    if (str[0] == '+' || str[0] == '-')
        i++;

    for (; i < length; i++)
    {
        char c = str[i];
        if (c >= '0' && c <= '9')
        {
            temDigito = true;
        }
        else if ((c == '.' || c == ',') && separadores == 0)
        {
            separadores = 1;
        }
        else
        {
            return false;
        }
    }

    return (temDigito && separadores == 1);
}

int main()
{
    char str[1024];

    while (1)
    {
        if (fgets(str, sizeof(str), stdin) == NULL)
            break;

        // retirar '\n' se existir
        int len = tamanho(str);
        if (len > 0 && str[len - 1] == '\n')
            str[len - 1] = '\0';

        if (ehFIM(str))
            break;

        printf("%s ", Met1(str) ? "SIM" : "NAO");
        printf("%s ", Met2(str) ? "SIM" : "NAO");
        printf("%s ", Met3(str) ? "SIM" : "NAO");
        printf("%s\n", Met4(str) ? "SIM" : "NAO");
    }

    return 0;
}
