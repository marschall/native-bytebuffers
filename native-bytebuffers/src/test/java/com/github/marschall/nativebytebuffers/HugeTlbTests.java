package com.github.marschall.nativebytebuffers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class HugeTlbTests {

  @Test
  void getHugeGid() throws IOException {
    assumeTrue(OperatingSystemAssumptions.isLinux());
    
    int hugeGid = HugeTlb.getHugeGid();
    assertTrue(hugeGid > 0);
  }

}
