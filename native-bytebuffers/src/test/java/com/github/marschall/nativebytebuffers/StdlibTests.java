package com.github.marschall.nativebytebuffers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

class StdlibTests {

  @Test
  void mallocSuccess() {
    int size = 512;
    ByteBuffer buffer = Stdlib.malloc(size);
    assertNotNull(buffer);

    try {
      assertEquals(size, buffer.capacity());
    } finally {
      Stdlib.free(buffer);
    }

  }

  @Test
  void mallocNegative() {
    assertThrows(IllegalArgumentException.class, () -> Stdlib.malloc(-512));
  }

  @Test
  void mallocZeror() {
    assertThrows(IllegalArgumentException.class, () -> Stdlib.malloc(0));
  }

  @Test
  void writeAndReadContents() {
    ByteBuffer buffer = Stdlib.malloc(16);
    assertNotNull(buffer);
    try {
      byte[] written = new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0xA, 0xB, 0xC, 0xD, 0xE};
      buffer.put(written);
      buffer.flip();

      byte[] read = new byte[16];
      buffer.get(read);
      assertArrayEquals(written, read);
    } finally {
      Stdlib.free(buffer);
    }
  }

}
