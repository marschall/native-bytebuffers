package com.github.marschall.nativebytebuffers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class HugeTlbTests {

  @Test
  void getHugeGid() throws IOException {
    int hugeGid = HugeTlb.getHugeGid();
    assertTrue(hugeGid > 0);
  }

}
