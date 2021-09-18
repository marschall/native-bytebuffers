package com.github.marschall.nativebytebuffers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class HugeTlbTests {

  @Test
  void getHugeGid() throws IOException {
    assumeTrue(OperatingSystemAssumptions.isLinux());

    int hugeGid = HugeTlb.getHugeGid();
    assertThat("hugeGid", hugeGid, greaterThan(0));
  }

}
