#include <stdio.h>

// função recursiva que soma os dígitos
int somaDigitos(int n)
{
    if (n == 0)
        return 0; // caso base
    return (n % 10) + somaDigitos(n / 10);
}

int main()
{
    int num;

    // enquanto conseguir ler um inteiro
    while (scanf("%d", &num) == 1)
    {
        printf("%d\n", somaDigitos(num));
    }

    return 0;
}
