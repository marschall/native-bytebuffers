package com.github.marschall.nativebytebuffers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MmapFlagsMacOsTests {

  @Test
  void constantValues() {
    assertEquals(0, MmapFlagsMacOs.VM_FLAGS_SUPERPAGE_NONE);
    assertEquals(65536, MmapFlagsMacOs.VM_FLAGS_SUPERPAGE_SIZE_ANY);
    assertEquals(131072, MmapFlagsMacOs.VM_FLAGS_SUPERPAGE_SIZE_2MB);
  }

}
