package com.github.marschall.nativebytebuffers;

import java.io.IOException;

/**
 * Provides access to <a href="https://lwn.net/Articles/865256/">memfd_secret</a>.
 * <p>
 * Requires the <code>secretmem.enable=1</code> kernel parameter.
 */
public final class Secretmem {

  static {
    LibraryLoader.assertInitialized();
  }

  private Secretmem() {
    throw new AssertionError("not instantiable");
  }

  public static int memfd_secret(int flags) throws IOException {
    int fd = memfd_secret0(flags);
    if (fd == -1) {
      // should not happen, should be handeled in JNI
      throw new IOException("could not create secret memory file descriptor");
    }
    return fd;
  }

  private static native int memfd_secret0(int flags) throws IOException;

}
