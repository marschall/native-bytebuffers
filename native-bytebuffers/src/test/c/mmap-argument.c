#define _GNU_SOURCE

#include <stdio.h>
#include <sys/mman.h>

int main(void)
{
    printf("PROT_EXEC: %d\n", PROT_EXEC);
    printf("PROT_READ: %d\n", PROT_READ);
    printf("PROT_WRITE: %d\n", PROT_WRITE);
    printf("PROT_NONE: %d\n", PROT_NONE);

    printf("MAP_SHARED: %d\n", MAP_SHARED);
    printf("MAP_SHARED_VALIDATE: %d\n", MAP_SHARED_VALIDATE);
    printf("MAP_PRIVATE: %d\n", MAP_PRIVATE);
    printf("MAP_32BIT: %d\n", MAP_32BIT);
    printf("MAP_ANONYMOUS: %d\n", MAP_ANONYMOUS);
    printf("MAP_GROWSDOWN: %d\n", MAP_GROWSDOWN);
    printf("MAP_HUGETLB: %d\n", MAP_HUGETLB);
    printf("MAP_HUGE_SHIFT: %d\n", MAP_HUGE_SHIFT);
    // printf("MAP_HUGE_2MB: %d\n", MAP_HUGE_2MB);
    // printf("MAP_HUGE_1GB: %d\n", MAP_HUGE_1GB);
    printf("MAP_LOCKED: %d\n", MAP_LOCKED);
    printf("MAP_NONBLOCK: %d\n", MAP_NONBLOCK);
    printf("MAP_NORESERVE: %d\n", MAP_NORESERVE);
    printf("MAP_POPULATE: %d\n", MAP_POPULATE);
    printf("MAP_STACK: %d\n", MAP_STACK);
    // printf("MAP_UNINITIALIZED: %d\n", MAP_UNINITIALIZED);

    return 0;
}
