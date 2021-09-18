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

  public static void ftruncate(int fildes, long length) throws IOException {
    int ret = ftruncate0(fildes, length);
    if (ret == -1) {
      // should not happen, should be handeled in JNI
      throw new IOException("could not truncate file");
    }
  }

  private static native int ftruncate0(int fildes, long length);

  public static void close(int fildes) throws IOException {
    int ret = close0(fildes);
    if (ret == -1) {
      // should not happen, should be handeled in JNI
      throw new IOException("could not close file");
    }
  }

  private static native int close0(int fildes);

}
