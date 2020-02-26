package com.github.marschall.nativebytebuffers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

final class ByteBufferAssertions {

  ByteBufferAssertions() {
    throw new AssertionError("not instantiable");
  }

  static void assertReadableAndWritable(ByteBuffer buffer) {
    assertEquals(0, buffer.position());

    int capacity = buffer.capacity();
    assertEquals(capacity, buffer.limit());

    for (int i = 0; i < capacity; i++) {
      buffer.put((byte) i);
    }
    buffer.flip();

    for (int i = 0; i < capacity; i++) {
      assertEquals((byte) i, buffer.get(i));
    }
  }


}
