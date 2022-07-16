package com.github.marschall.nativebytebuffers;

import java.io.IOException;

/**
 * Provides access to file management using {@code unistd.h}.
 */
public final class Unistd {

  static {
    LibraryLoader.assertInitialized();
  }

  private Unistd() {
    throw new AssertionError("not instantiable");
  }

  /**
   * Returns the number of bytes in a memory page,
   * where "page" is a fixed-length block, the unit for memory allocation and
   * file mapping performed by {@link Mman#mmap(int, int)}.
   *
   * @return the page size, {@code long} because in theory we could have pages larger than 2 GiB
   * @see <a href="http://man7.org/linux/man-pages/man2/getpagesize.2.html">getpagesize(2)</a>
   */
  public static long getpagesize() {
    return getpagesize0();
  }

  private static native long getpagesize0();

  /**
   * Truncate a file to a specified length.
   *
   * @param fildes file descriptor referring to the underlying open file
   * @param length the size to truncate to
   * @throws IOException
   *           if {@code ftruncate()} fails
   * @see <a href="https://man7.org/linux/man-pages/man3/ftruncate.3p.html">ftruncate(3p)</a>
   */
  public static void ftruncate(int fildes, long length) throws IOException {
    int ret = ftruncate0(fildes, length);
    if (ret == -1) {
      // should not happen, should be handeled in JNI
      throw new IOException("could not truncate file");
    }
  }

  private static native int ftruncate0(int fildes, long length);

  /**
   * Close a file descriptor.
   *
   * @param fildes file descriptor referring to the underlying open file
   * @throws IOException
   *           if {@code close()} fails
   * @see <a href="https://man7.org/linux/man-pages/man2/close.2.html">close(2)</a>
   */
  public static void close(int fildes) throws IOException {
    int ret = close0(fildes);
    if (ret == -1) {
      // should not happen, should be handeled in JNI
      throw new IOException("could not close file");
    }
  }

  private static native int close0(int fildes);

}
