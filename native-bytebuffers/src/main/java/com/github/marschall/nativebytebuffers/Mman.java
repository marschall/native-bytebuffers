package com.github.marschall.nativebytebuffers;

import java.nio.ByteBuffer;
import java.util.Objects;
/**
 * Provides access to memory management using {@code sys/mman.h}.
 */
public final class Mman {

  static {
    LibraryLoader.assertInitialized();
  }

  private Mman() {
    throw new AssertionError("not instantiable");
  }

  /**
   * Calls {@code mmap()} to create an anonymous shared virtual memory region
   * and creates a {@code ByteBuffer} around it.
   * <p>
   * Protection will be {@code PROT_READ | PROT_WRITE}.
   *
   * @param length
   *          the length of the mapping, must be greater than 0
   * @return a new ByteBuffer around the newly {@code mmap()}-ed area,
   *         never {@code null},
   *         must be released with {@link #munmap(ByteBuffer)}
   * @throws IllegalArgumentException
   *           if {@code length} is not positive
   * @throws AllocationFailedException
   *           if {@code mmap()} fails
   * @see Mman#mmap(int, int)
   * @see Mman#munmap(ByteBuffer)
   * @see <a href="MMAP(2)">http://man7.org/linux/man-pages/man2/mmap.2.html</a>
   */
  public static ByteBuffer mmap(int length) {

    if (length <= 0) {
      throw new IllegalArgumentException("non postive length: " + length);
    }
    ByteBuffer buffer = mmap0(length);

    if (buffer == null) {
      throw new AllocationFailedException("mmap failed");
    }
    return buffer;
  }

  private static native ByteBuffer mmap0(int length);

  /**
   * Calls {@code mmap()} to create a virtual memory region and creates a
   * {@code ByteBuffer} around it.
   * <p>
   * Protection will be {@code PROT_READ | PROT_WRITE}.
   *
   * @param length
   *          the length of the mapping, must be greater than 0
   * @param flags
   *          ORed arguments from {@link MmapFlags} that control various aspects
   *          of the mapping
   * @return a new ByteBuffer around the newly {@code mmap()}-ed area,
   *         never {@code null},
   *         must be released with {@link #munmap(ByteBuffer)}
   * @throws IllegalArgumentException
   *           if {@code length} is not positive
   * @throws AllocationFailedException
   *           if {@code mmap()} fails
   * @see Mman#munmap(ByteBuffer)
   * @see <a href="MMAP(2)">http://man7.org/linux/man-pages/man2/mmap.2.html</a>
   */
  public static ByteBuffer mmap(int length, int flags) {
    if (length <= 0) {
      throw new IllegalArgumentException("non postive length: " + length);
    }
    ByteBuffer buffer = mmap1(length, flags);

    if (buffer == null) {
      throw new AllocationFailedException("mmap failed");
    }
    return buffer;
  }

  private static native ByteBuffer mmap1(int length, int flags);

  /**
   * Calls {@code munmap()} on the contents of the given {@link ByteBuffer}.
   * <p>
   * This method may crash the JVM the buffer passed has not been created by
   * {@link #mmap(int, int)}, {@link #mmap(int)} or has already been passed to
   * this method.
   *
   * @param buffer
   *          the buffer to free, not {@code null}, must have been created with
   *          {@link #mmap(int, int)} or {@link #mmap(int)}, must not be
   *          accessed afterwards, must not be passed to this method again
   * @throws NullPointerException
   *           if {@code buffer} is {@code null}
   * @throws IllegalArgumentException
   *           if {@code buffer} is not a direct buffer
   * @throws ReleaseFailedException
   *           if unmapping fails
   * @see Mman#munmap(ByteBuffer)
   * @see <a href="MMAP(2)">http://man7.org/linux/man-pages/man2/mmap.2.html</a>
   */
  public static void munmap(ByteBuffer buffer) {
    Objects.requireNonNull(buffer, "buffer");
    if (!buffer.isDirect()) {
      throw new IllegalArgumentException("only direct buffers can be munmap()ed");
    }
    munmap0(buffer);
  }

  private static native void munmap0(ByteBuffer buffer);

  /**
   * Returns the number of bytes in a memory page,
   * where "page" is a fixed-length block, the unit for memory allocation and
   * file mapping performed by {@link Mman#mmap(int, int)}.
   *
   * @return the page size, {@code long} because in theory we could have pages larger than 2 GiB
   * @see <a href="GETPAGESIZE(2)">http://man7.org/linux/man-pages/man2/getpagesize.2.html</a>
   */
  public static long getpagesize() {
    return getpagesize0();
  }

  private static native long getpagesize0();

  /**
   * Gets the value of the {@code MAP_ANONYMOUS} flag. This is different
   * on macOS and Linux.
   *
   * @return the value of the {@code MAP_ANONYMOUS} flag
   */
  static int getMapAnonymous() {
    return getMapAnonymous0();
  }

  private static native int getMapAnonymous0();

}
