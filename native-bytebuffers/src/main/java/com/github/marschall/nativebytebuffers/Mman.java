package com.github.marschall.nativebytebuffers;

import java.nio.ByteBuffer;
import java.util.Objects;

public final class Mman {

  static {
    LibraryLoader.assertInitialized();
  }

  private Mman() {
    throw new AssertionError("not instantiable");
  }

  public static ByteBuffer mmap(int length, int flags) {
    if (length <= 0) {
      throw new IllegalArgumentException("non postive length: " + length);
    }
    return mmap0(length, flags);
  }

  private static native ByteBuffer mmap0(int length, int flags);

  public static void munmap(ByteBuffer buffer) {
    Objects.requireNonNull(buffer, "buffer");
    if (!buffer.isDirect()) {
      throw new IllegalArgumentException("only direct buffers can be munmap()ed");
    }
    munmap0(buffer, buffer.capacity());
  }

  private static native void munmap0(ByteBuffer buffer, int length);

  public static int getpagesize() {
    return getpagesize0();
  }

  private static native int getpagesize0();

}
