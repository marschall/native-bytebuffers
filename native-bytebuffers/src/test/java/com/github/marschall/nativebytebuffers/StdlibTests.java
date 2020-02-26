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

      ByteBufferAssertions.assertReadableAndWritable(buffer);
    } finally {
      Stdlib.free(buffer);
    }

  }

  @Test
  void mallocNegative() {
    assertThrows(IllegalArgumentException.class, () -> Stdlib.malloc(-512));
  }

  @Test
  void mallocZero() {
    assertThrows(IllegalArgumentException.class, () -> Stdlib.malloc(0));
  }

  @Test
  void mallocWriteAndReadContents() {
    int size = 16;
    ByteBuffer buffer = Stdlib.malloc(size);
    assertNotNull(buffer);
    try {
      assertEquals(size, buffer.capacity());

      ByteBufferAssertions.assertReadableAndWritable(buffer);
    } finally {
      Stdlib.free(buffer);
    }
  }

  @Test
  void callocSuccess() {
    int size = 512;
    ByteBuffer buffer = Stdlib.calloc(size);
    assertNotNull(buffer);

    try {
      assertEquals(size, buffer.capacity());
      assertEquals(0, buffer.position());
      assertEquals(size, buffer.limit());
      for (int i = 0; i < size; i++) {
        assertEquals(0, buffer.get());
      }

      buffer.clear();
      assertEquals(size, buffer.capacity());

      ByteBufferAssertions.assertReadableAndWritable(buffer);
    } finally {
      Stdlib.free(buffer);
    }

  }

  @Test
  void aligned_allocSuccess() {
    int alignment = 1024;
    int size = alignment * 8;
    ByteBuffer buffer = Stdlib.aligned_alloc(alignment, size);
    assertNotNull(buffer);

    try {
      assertEquals(size, buffer.capacity());

      ByteBufferAssertions.assertReadableAndWritable(buffer);
    } finally {
      Stdlib.free(buffer);
    }
  }

}
