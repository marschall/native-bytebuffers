package com.github.marschall.nativebytebuffers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.ByteBuffer;
import java.util.Locale;

import org.junit.jupiter.api.Test;

class MmanTests {

  @Test
  void getpagesize() {
    int pagesize = Math.toIntExact(Mman.getpagesize());
    assertTrue(pagesize > 0);
  }

  @Test
  void mmapSuccess() {
    int pagesize = Math.toIntExact(Mman.getpagesize());
    ByteBuffer buffer = Mman.mmap(pagesize);
    assertNotNull(buffer);
    try {
      assertEquals(pagesize, buffer.capacity());
      assertEquals(0, buffer.position());
      assertEquals(pagesize, buffer.limit());
    } finally {
      Mman.munmap(buffer);
    }
  }

  @Test
  void mmapSuccessMacOs() {
    assumeTrue(isMacOs());
    int pagesize = Math.toIntExact(Mman.getpagesize());
    int flags = MmapFlagsMacOs.MAP_SHARED | MmapFlagsMacOs.MAP_ANONYMOUS;
    ByteBuffer buffer = Mman.mmap(pagesize, flags);
    assertNotNull(buffer);
    try {
      assertEquals(pagesize, buffer.capacity());
    } finally {
      Mman.munmap(buffer);
    }
  }

  @Test
  void mmapSuccessLinux() {
    assumeTrue(isLinux());
    int pagesize = Math.toIntExact(Mman.getpagesize());
    int flags = MmapFlags.MAP_SHARED | MmapFlags.MAP_ANONYMOUS;
    ByteBuffer buffer = Mman.mmap(pagesize, flags);
    assertNotNull(buffer);
    try {
      assertEquals(pagesize, buffer.capacity());
    } finally {
      Mman.munmap(buffer);
    }
  }

  @Test
  void writeAndReadContents() {
    int pagesize = Math.toIntExact(Mman.getpagesize());
    ByteBuffer buffer = Mman.mmap(pagesize);
    assertNotNull(buffer);

    try {
      byte[] written = newByteArrayWithContents(pagesize);
      buffer.put(written);
      buffer.flip();

      byte[] read = new byte[pagesize];
      buffer.get(read);
      assertArrayEquals(written, read);
    } finally {
      Mman.munmap(buffer);
    }
  }

  private static byte[] newByteArrayWithContents(int size) {
    byte[] array = new byte[size];
    for (int i = 0; i < array.length; i++) {
      array[i] = (byte) i;
    }
    return array;
  }

  private static boolean isMacOs() {
    return System.getProperty("os.name").toLowerCase(Locale.US).contains("mac");
  }

  private static boolean isLinux() {
    return System.getProperty("os.name").toLowerCase(Locale.US).contains("linux");
  }

}
