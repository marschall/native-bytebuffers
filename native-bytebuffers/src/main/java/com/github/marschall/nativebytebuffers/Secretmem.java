package com.github.marschall.nativebytebuffers;

import java.io.IOException;

// linux/secretmem.h
public final class Secretmem {

  static {
    LibraryLoader.assertInitialized();
  }

  private Secretmem() {
    throw new AssertionError("not instantiable");
  }

  public static int memfd_secret(long flags) throws IOException {
    int fd = memfd_secret0(flags);
    if (fd == -1) {
      // should not happen, should be handeled in JNI
      throw new IOException("could not create secret memory file descriptor");
    }
    return fd;
  }

  private static native int memfd_secret0(long flags) throws IOException;

}
