#define _GNU_SOURCE

#include <stdio.h>
#include <fcntl.h>

int main(void)
{
    printf("F_ADD_SEALS: %d\n", F_ADD_SEALS);
    printf("F_GET_SEALS: %d\n", F_GET_SEALS);

    printf("F_SEAL_SEAL: %d\n", F_SEAL_SEAL);
    printf("F_SEAL_SHRINK: %d\n", F_SEAL_SHRINK);
    printf("F_SEAL_GROW: %d\n", F_SEAL_GROW);
    printf("F_SEAL_WRITE: %d\n", F_SEAL_WRITE);
    printf("F_SEAL_FUTURE_WRITE: %d\n", F_SEAL_FUTURE_WRITE);

    return 0;
}
