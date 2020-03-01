package com.github.marschall.nativebytebuffers;

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

      ByteBufferAssertions.assertReadableAndWritable(buffer);
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

      ByteBufferAssertions.assertReadableAndWritable(buffer);
    } finally {
      Mman.munmap(buffer);
    }
  }

  @Test
  void mmapSuccessMacOsSuperpage() {
    assumeTrue(isMacOs());
    int size = 2 * 1024 * 1024;
    int flags = MmapFlagsMacOs.MAP_SHARED | MmapFlagsMacOs.MAP_ANONYMOUS | MmapFlagsMacOs.VM_FLAGS_SUPERPAGE_SIZE_2MB;
    ByteBuffer buffer = Mman.mmap(size, flags);
    assertNotNull(buffer);
    try {
      assertEquals(size, buffer.capacity());

      ByteBufferAssertions.assertReadableAndWritable(buffer);
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

      ByteBufferAssertions.assertReadableAndWritable(buffer);
    } finally {
      Mman.munmap(buffer);
    }
  }

  @Test
  void mmapSuccessLinuxHugeTlb() {
    assumeTrue(isLinux());
    int size = 2 * 1024 * 1024;
    int flags = MmapFlags.MAP_SHARED | MmapFlags.MAP_ANONYMOUS | MmapFlags.MAP_HUGETLB | MmapFlags.MAP_HUGE_2MB;
//    int size = 1 * 1024 * 1024 * 1024;
//    int flags = MmapFlags.MAP_SHARED | MmapFlags.MAP_ANONYMOUS | MmapFlags.MAP_HUGETLB | MmapFlags.MAP_HUGE_1GB;
    ByteBuffer buffer = Mman.mmap(size, flags);
    assertNotNull(buffer);
    try {
      assertEquals(size, buffer.capacity());

      ByteBufferAssertions.assertReadableAndWritable(buffer);
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
      ByteBufferAssertions.assertReadableAndWritable(buffer);
    } finally {
      Mman.munmap(buffer);
    }
  }

  private static boolean isMacOs() {
    return System.getProperty("os.name").toLowerCase(Locale.US).contains("mac");
  }

  private static boolean isLinux() {
    return System.getProperty("os.name").toLowerCase(Locale.US).contains("linux");
  }

}
