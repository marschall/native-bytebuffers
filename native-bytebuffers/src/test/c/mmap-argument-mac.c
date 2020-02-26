#define _GNU_SOURCE

#include <stdio.h>
#include <sys/mman.h>
#include <mach/vm_statistics.h>

int main(void)
{
    printf("PROT_EXEC: %d\n", PROT_EXEC);
    printf("PROT_READ: %d\n", PROT_READ);
    printf("PROT_WRITE: %d\n", PROT_WRITE);
    printf("PROT_NONE: %d\n", PROT_NONE);

    printf("MAP_SHARED: %d\n", MAP_SHARED);
    printf("MAP_PRIVATE: %d\n", MAP_PRIVATE);
    printf("MAP_ANONYMOUS: %d\n", MAP_ANONYMOUS);
    printf("MAP_HASSEMAPHORE: %d\n", MAP_HASSEMAPHORE);
    
    printf("VM_FLAGS_SUPERPAGE_SHIFT: %d\n", VM_FLAGS_SUPERPAGE_SHIFT);
    printf("SUPERPAGE_NONE: %d\n", SUPERPAGE_NONE);
    printf("SUPERPAGE_SIZE_ANY: %d\n", SUPERPAGE_SIZE_ANY);
    printf("VM_FLAGS_SUPERPAGE_NONE: %d\n", VM_FLAGS_SUPERPAGE_NONE);
    printf("VM_FLAGS_SUPERPAGE_SIZE_ANY: %d\n", VM_FLAGS_SUPERPAGE_SIZE_ANY);
    printf("VM_FLAGS_SUPERPAGE_SIZE_2MB: %d\n", VM_FLAGS_SUPERPAGE_SIZE_2MB);

    return 0;
}
