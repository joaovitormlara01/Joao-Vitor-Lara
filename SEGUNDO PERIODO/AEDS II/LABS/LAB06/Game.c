#include <stdio.h>
#include <string.h>

#define MAX_STR 100 // tamanho maximo da string
#define MAX_LIST 10 // quantidade maximo de itens na lista

// estrutura struct

typedef struct
{
    int id;
    char name[MAX_STR];
    char releaseDate[11];
    int estimatedOwners;
    float price;
    char supportedLanguages[MAX_LIST][MAX_STR]; // até 10 idiomas
    int langCount;
    int metacriticScore;
    float userScore;
    int achievements;
    char publishers[MAX_LIST][MAX_STR];
    int pubCount;
    char developers[MAX_LIST][MAX_STR];
    int devCount;
    char categories[MAX_LIST][MAX_STR];
    int catCount;
    char genres[MAX_LIST][MAX_STR];
    int genCount;
    char tags[MAX_LIST][MAX_STR];
    int tagCount;
} Game;