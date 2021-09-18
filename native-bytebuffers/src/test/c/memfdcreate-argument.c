#define _GNU_SOURCE

#include <stdio.h>
#include <sys/mman.h>
#include <linux/memfd.h>

int main(void)
{
    printf("MFD_CLOEXEC: %d\n", MFD_CLOEXEC);
    printf("MFD_ALLOW_SEALING: %d\n", MFD_ALLOW_SEALING);
    printf("MFD_HUGETLB: %d\n", MFD_HUGETLB);
    printf("MFD_HUGE_64KB: %d\n", MFD_HUGE_64KB);
    printf("MFD_HUGE_512KB: %d\n", MFD_HUGE_512KB);
    printf("MFD_HUGE_1MB: %d\n", MFD_HUGE_1MB);
    printf("MFD_HUGE_2MB: %d\n", MFD_HUGE_2MB);
    printf("MFD_HUGE_8MB: %d\n", MFD_HUGE_8MB);
    printf("MFD_HUGE_16MB: %d\n", MFD_HUGE_16MB);
    printf("MFD_HUGE_32MB: %d\n", MFD_HUGE_32MB);
    printf("MFD_HUGE_256MB: %d\n", MFD_HUGE_256MB);
    printf("MFD_HUGE_512MB: %d\n", MFD_HUGE_512MB);
    printf("MFD_HUGE_1GB: %d\n", MFD_HUGE_1GB);
    printf("MFD_HUGE_2GB: %d\n", MFD_HUGE_2GB);
    printf("MFD_HUGE_16GB: %d\n", MFD_HUGE_16GB);

    return 0;
}
