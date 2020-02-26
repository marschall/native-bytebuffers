package com.github.marschall.nativebytebuffers;

/**
 * Values that may be passed as the flags argument to {@link Mman#mmap(int, int)}
 * on a macOS system.
 */
public final class MmapFlagsMacOs {

  private MmapFlagsMacOs() {
    throw new AssertionError("not instantiable");
  }
  /**
   * Modifications are shared.
   */
  public static final int MAP_SHARED = 1;

  /**
   * Map anonymous memory not associated with any specific file.
   */
  public static final int MAP_ANONYMOUS = 4096;

  /**
   * Notify the kernel that the region may contain semaphores and that special
   * handling may be necessary.
   */
  public static final int MAP_HASSEMAPHORE = 512;
  
  public static final int VM_FLAGS_SUPERPAGE_SHIFT = 16;
  public static final int SUPERPAGE_NONE = 0;
  public static final int SUPERPAGE_SIZE_ANY = 1;
  public static final int SUPERPAGE_SIZE_2MB = 2;
  
  public static final int VM_FLAGS_SUPERPAGE_NONE = SUPERPAGE_NONE << VM_FLAGS_SUPERPAGE_SHIFT;
  public static final int VM_FLAGS_SUPERPAGE_SIZE_ANY = SUPERPAGE_SIZE_ANY << VM_FLAGS_SUPERPAGE_SHIFT;
  public static final int VM_FLAGS_SUPERPAGE_SIZE_2MB = SUPERPAGE_SIZE_2MB<<VM_FLAGS_SUPERPAGE_SHIFT;

}
