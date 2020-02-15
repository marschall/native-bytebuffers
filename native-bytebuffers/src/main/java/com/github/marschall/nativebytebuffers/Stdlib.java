package com.github.marschall.nativebytebuffers;

import java.nio.ByteBuffer;
import java.util.Objects;

public class Stdlib {

  static {
    LibraryLoader.assertInitialized();
  }

  public static ByteBuffer malloc(int size) {
    if (size <= 0) {
      throw new IllegalArgumentException("non postive size: " + size);
    }
    ByteBuffer buffer = malloc0(size);
    if (buffer == null) {
      throw new AllocationFailedException("malloc failed");
    }
    return buffer;
  }

  private static native ByteBuffer malloc0(int size);

  public static void free(ByteBuffer buffer) {
    Objects.requireNonNull(buffer, "buffer");
    if (buffer.hasArray()) {
      throw new IllegalArgumentException("only direct buffers can be fee()ed");
    }
    free0(buffer);
  }

  private static native void free0(ByteBuffer buffer);

}
