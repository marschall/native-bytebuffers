package com.github.marschall.nativebytebuffers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.nio.ByteBuffer;

import org.junit.Test;

public class StdlibTest {

  @Test
  public void mallocSuccess() {
    int size = 512;
    ByteBuffer buffer = Stdlib.malloc(size);
    assertNotNull(buffer);

    try {
      assertEquals(size, buffer.capacity());
      assertEquals(0, buffer.position());
      assertEquals(size, buffer.limit());
    } finally {
      Stdlib.free(buffer);
    }

  }

  @Test
  public void mallocNegative() {
    assertThrows(IllegalArgumentException.class, () -> Stdlib.malloc(-512));
  }

  @Test
  public void mallocZero() {
    assertThrows(IllegalArgumentException.class, () -> Stdlib.malloc(0));
  }

  @Test
  public void mallocWriteAndReadContents() {
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

  @Test
  public void callocSuccess() {
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
    } finally {
      Stdlib.free(buffer);
    }

  }

  @Test
  public void aligned_allocSuccess() {
    int size = 4096;
    ByteBuffer buffer = Stdlib.aligned_alloc(size, size);
    assertNotNull(buffer);

    try {
      assertEquals(size, buffer.capacity());
      assertEquals(0, buffer.position());
      assertEquals(size, buffer.limit());
    } finally {
      Stdlib.free(buffer);
    }

  }

}
