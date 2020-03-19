package com.github.marschall.nativebytebuffers;

/**
 * Values that may be passed as the flags argument to {@link Mman#mmap(int, int)}
 * on a Windows system.
 * 
 * @see <a href="https://docs.microsoft.com/en-us/windows/win32/api/winbase/nf-winbase-createfilemappinga">CreateFileMappingA function</a>
 */
public final class MmapFlagsWindows {

  private MmapFlagsWindows() {
    throw new AssertionError("not instantiable");
  }

  /**
   * Specifies that when a view of the file is mapped into a process address
   * space, the entire range of pages is committed rather than reserved. The
   * system must have enough committable pages to hold the entire mapping.
   * Otherwise, {@link Mman#mmap(int, int)} fails.
   * <p>
   * {@link #SEC_COMMIT} cannot be combined with {@link #SEC_RESERVE}.
   * <p>
   * If no attribute is specified, SEC_COMMIT is assumed.
   */
  public static final int SEC_COMMIT = 0x8000000;

  /**
   * Enables large pages to be used for file mapping objects.
   * <p>
   * The maximum size of the file mapping object must be a multiple of the minimum
   * size of a large page returned by the GetLargePageMinimum function. If it is
   * not, {@link Mman#mmap(int, int)} fails. When mapping a view of a file mapping
   * object created with {@link #SEC_LARGE_PAGES}, the base address and view size
   * must also be multiples of the minimum large page size.
   * <p>
   * {@link #SEC_LARGE_PAGES} requires the SeLockMemoryPrivilege privilege to be
   * enabled in the caller's token.
   * <p>
   * If {@link #SEC_LARGE_PAGES} is specified, {@link #SEC_COMMIT} must also be
   * specified.
   * <p>
   * <dl>
   * <dd>Windows Server 2003:</dd>
   * <dt>This value is not supported until Windows Server 2003 with SP1.</dt>
   * <dd>Windows XP:</dd>
   * <dt>This value is not supported.</dt>
   * </dl>
   */
  public static final int SEC_LARGE_PAGES = 0x80000000;

  /**
   * Sets all pages to be non-cachable.
   * <p>
   * Applications should not use this attribute except when explicitly required
   * for a device. Using the interlocked functions with memory that is mapped with
   * {@link #SEC_NOCACHE} can result in an EXCEPTION_ILLEGAL_INSTRUCTION
   * exception.
   * <p>
   * {@link #SEC_NOCACHE} requires either the {@link #SEC_RESERVE} or
   * {@link #SEC_COMMIT} attribute to be set.
   */
  public static final int SEC_NOCACHE = 0x10000000;

  /**
   * Specifies that when a view of the file is mapped into a process address
   * space, the entire range of pages is reserved for later use by the process
   * rather than committed.
   * <p>
   * Reserved pages can be committed in subsequent calls to the VirtualAlloc
   * function. After the pages are committed, they cannot be freed or decommitted
   * with the VirtualFree function.
   * <p>
   * {@link #SEC_RESERVE} cannot be combined with {@link #SEC_COMMIT}.
   */
  public static final int SEC_RESERVE = 0x4000000;

  /**
   * Sets all pages to be write-combined.
   * <p>
   * Applications should not use this attribute except when explicitly required
   * for a device. Using the interlocked functions with memory that is mapped with
   * {@link #SEC_WRITECOMBINE} can result in an EXCEPTION_ILLEGAL_INSTRUCTION
   * exception.
   * <p>
   * {@link #SEC_WRITECOMBINE} requires either the {@link #SEC_RESERVE} or
   * {@link #SEC_COMMIT} attribute to be set.
   * <dl>
   * <dd>Windows Server 2003 and Windows XP:</dd>
   * <dt>This flag is not supported until Windows Vista.</dt>
   * </dl>
   */
  public static final int SEC_WRITECOMBINE = 0x40000000;

}
