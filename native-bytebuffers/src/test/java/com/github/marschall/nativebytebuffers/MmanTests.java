package com.github.marschall.nativebytebuffers;

import static com.github.marschall.nativebytebuffers.Unistd.getpagesize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

class MmanTests {


  @Test
  void mmapSuccess() {
    int pagesize = Math.toIntExact(getpagesize());
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
    assumeTrue(OperatingSystemAssumptions.isMacOs());
    int pagesize = Math.toIntExact(getpagesize());
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
    assumeTrue(OperatingSystemAssumptions.isMacOs(), "superpages are only supported on macOS");
    assumeFalse(OperatingSystemAssumptions.isAarch64(), "superpages are not supported on M1");
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
    assumeTrue(OperatingSystemAssumptions.isLinux());
    int pagesize = Math.toIntExact(getpagesize());
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
    assumeTrue(OperatingSystemAssumptions.isLinux());
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
    int pagesize = Math.toIntExact(getpagesize());
    ByteBuffer buffer = Mman.mmap(pagesize);
    assertNotNull(buffer);

    try {
      ByteBufferAssertions.assertReadableAndWritable(buffer);
    } finally {
      Mman.munmap(buffer);
    }
  }

  @Test
  void memfd_createUnsupported() throws IOException {
    assumeFalse(OperatingSystemAssumptions.isLinux());
    assertThrows(UnsupportedOperationException.class, () -> Mman.memfd_create(this.getClass().getName(), 0));
  }

  @Test
  void memfd_createSuccess() throws IOException {
    assumeTrue(OperatingSystemAssumptions.isLinux());
    int pagesize = Math.toIntExact(getpagesize());
    int flags = MmapFlags.MAP_SHARED | MmapFlags.MAP_ANONYMOUS;
    int fd = Mman.memfd_create(this.getClass().getName(), 0);
    try {
      Unistd.ftruncate(fd, pagesize);
      ByteBuffer buffer = Mman.mmap(pagesize, flags, fd);
      try {
        ByteBufferAssertions.assertReadableAndWritable(buffer);
      } finally {
        Mman.munmap(buffer);
      }
    } finally {
      Unistd.close(fd);
    }
  }

  @Test
  void memfd_createSuccessHugetlb() throws IOException {
    assumeTrue(OperatingSystemAssumptions.isLinux());
    int size = 2 * 1024 * 1024;
    int mmapFlags = MmapFlags.MAP_SHARED | MmapFlags.MAP_ANONYMOUS;
    int memfdFlags = MemfdCreateFlags.MFD_HUGETLB | MemfdCreateFlags.MFD_HUGE_2MB;
    int fd = Mman.memfd_create(this.getClass().getName(), memfdFlags);
    try {
      Unistd.ftruncate(fd, size);
      ByteBuffer buffer = Mman.mmap(size, mmapFlags, fd);
      try {
        ByteBufferAssertions.assertReadableAndWritable(buffer);
      } finally {
        Mman.munmap(buffer);
      }
    } finally {
      Unistd.close(fd);
    }
  }

}
