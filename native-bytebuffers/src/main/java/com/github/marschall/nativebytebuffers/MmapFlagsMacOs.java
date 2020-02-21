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

}
