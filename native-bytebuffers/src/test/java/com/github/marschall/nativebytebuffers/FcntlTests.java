package com.github.marschall.nativebytebuffers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class FcntlTests {

  @Test
  void seals() throws IOException {
    assumeTrue(OperatingSystemAssumptions.isLinux());
    int pagesize = Math.toIntExact(Mman.getpagesize());
    int flags = MemfdCreateFlags.MFD_ALLOW_SEALING;
    int fd = Mman.memfd_create(this.getClass().getName(), flags);
    Unistd.ftruncate(fd, pagesize);
    try {
      int seals = Seals.F_SEAL_GROW | Seals.F_SEAL_SHRINK | Seals.F_SEAL_SEAL;
      Fcntl.fcntl(fd, FcntlCommands.F_ADD_SEALS, seals);
      assertEquals(seals, Fcntl.fcntl(fd, FcntlCommands.F_GET_SEALS));
    } finally {
      Unistd.close(fd);
    }
  }

}
