package com.github.marschall.nativebytebuffers;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

class SecretmemTests {

  @Test
  void memfd_create() throws IOException {
    int pagesize = Math.toIntExact(Mman.getpagesize());
    int fd;
    try {
      fd = Secretmem.memfd_secret(0);
    } catch (UnsupportedOperationException e) {
      // is allowed to happen
      return;
    }
    try {
      Unistd.ftruncate(fd, pagesize);
      int seals = Seals.F_SEAL_SEAL | Seals.F_SEAL_SHRINK | Seals.F_SEAL_GROW;
      Fcntl.fcntl(fd, FcntlCommands.F_ADD_SEALS, seals);
      int mmapFlags = MmapFlags.MAP_SHARED | MmapFlags.MAP_ANONYMOUS;
      ByteBuffer buffer = Mman.mmap(pagesize, mmapFlags, fd);
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
