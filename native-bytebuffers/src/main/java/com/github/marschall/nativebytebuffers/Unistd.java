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
   *
   * @param fildes
   * @param length
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
   *
   * @param fildes
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
