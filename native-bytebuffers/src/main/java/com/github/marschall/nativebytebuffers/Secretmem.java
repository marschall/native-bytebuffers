package com.github.marschall.nativebytebuffers;

// linux/secretmem.h
public final class Secretmem {

  static {
    LibraryLoader.assertInitialized();
  }

  private Secretmem() {
    throw new AssertionError("not instantiable");
  }
  
  public static int memfd_secret(long flags) {
    return memfd_secret0(flags);
  }

  private static native int memfd_secret0(long flags);

}
