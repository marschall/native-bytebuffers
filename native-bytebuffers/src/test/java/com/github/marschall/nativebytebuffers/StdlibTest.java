package com.github.marschall.nativebytebuffers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

class StdlibTest {

  @Test
  void mallocSuccess() {
    ByteBuffer buffer = Stdlib.malloc(512);
    assertNotNull(buffer);
    Stdlib.free(buffer);
  }

  @Test
  void mallocNegative() {
    assertThrows(IllegalArgumentException.class, () -> Stdlib.malloc(-512));
  }

  @Test
  void mallocZeror() {
    assertThrows(IllegalArgumentException.class, () -> Stdlib.malloc(0));
  }

}
