package com.github.marschall.nativebytebuffers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UnistdTests {

  @Test
  void getpagesize() {
    int pagesize = Math.toIntExact(Unistd.getpagesize());
    assertTrue(pagesize > 0);
  }

}
