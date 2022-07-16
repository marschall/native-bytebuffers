package com.github.marschall.nativebytebuffers;

import static com.github.marschall.nativebytebuffers.Unistd.getpagesize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class FcntlTests {

  @Test
  void seals() throws IOException {
    assumeTrue(OperatingSystemAssumptions.isLinux());
    int pagesize = Math.toIntExact(getpagesize());
    int flags = MemfdCreateFlags.MFD_ALLOW_SEALING;
    int fd = Mman.memfd_create(this.getClass().getName(), flags);
    try {
      Unistd.ftruncate(fd, pagesize);
      int seals = Seals.F_SEAL_GROW | Seals.F_SEAL_SHRINK | Seals.F_SEAL_SEAL;
      Fcntl.fcntl(fd, FcntlCommands.F_ADD_SEALS, seals);
      assertEquals(seals, Fcntl.fcntl(fd, FcntlCommands.F_GET_SEALS));
    } finally {
      Unistd.close(fd);
    }
  }

}
