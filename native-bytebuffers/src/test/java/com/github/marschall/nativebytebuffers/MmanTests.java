package com.github.marschall.nativebytebuffers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MmanTests {

  @Test
  void getpagesize() {
    int pagesize = Mman.getpagesize();
    assertTrue(pagesize > 0);
    System.out.println(pagesize);
  }

}
