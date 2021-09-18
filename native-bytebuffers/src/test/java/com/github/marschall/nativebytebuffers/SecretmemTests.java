package com.github.marschall.nativebytebuffers;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class SecretmemTests {

  @Test
  @Disabled
  void memfd_create() throws IOException {
    int pagesize = Math.toIntExact(Mman.getpagesize());
    int fd = Secretmem.memfd_secret(0); // TODO flags
    try {
      ByteBuffer buffer = Mman.mmap(fd, pagesize); // FIXME
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
