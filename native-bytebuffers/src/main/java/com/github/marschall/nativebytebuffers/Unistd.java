package com.github.marschall.nativebytebuffers;

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

  public static void ftruncate(int fildes, long length) {
    ftruncate0(fildes, length);
  }

  private static native void ftruncate0(int fildes, long length);

  public static void close(int fildes) {
    close0(fildes);
  }

  private static native void close0(int fildes);

}
