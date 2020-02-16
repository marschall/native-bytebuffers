package com.github.marschall.nativebytebuffers;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * Provides access to memory management using {@code stdlib.h}.
 */
public final class Stdlib {

  static {
    LibraryLoader.assertInitialized();
  }

  private Stdlib() {
    throw new AssertionError("not instantiable");
  }

  /**
   * Calls {@code malloc()} and wraps the result in a {@link ByteBuffer}
   *
   * @param size
   *          the number of bytes to allocate, must be positive, {@code int}
   *          because {@link ByteBuffer} only supports {@code int} indices
   * @return the new buffer,
   *         never {@code null},
   *         the content is uninitialized as per {@code malloc()} contract,
   *         must be released with {@link #free(ByteBuffer)}
   * @throws AllocationFailedException
   *           if {@code malloc} returns {@code NULL}
   * @throws IllegalArgumentException
   *           if {@code size} is not positive
   * @see #free(ByteBuffer)
   * @see <a href="http://man7.org/linux/man-pages/man3/malloc.3.html">MALLOC(3)</a>
   */
  public static ByteBuffer malloc(int size) {
    if (size <= 0) {
      throw new IllegalArgumentException("non postive size: " + size);
    }
    ByteBuffer buffer = malloc0(size);
    if (buffer == null) {
      throw new AllocationFailedException("malloc failed");
    }
    return buffer;
  }

  private static native ByteBuffer malloc0(int size);

  /**
   * Calls {@code free()} on the contents of the given {@link ByteBuffer}.
   * <p>
   * This method may crash the JVM the buffer passed has not been created by
   * {@link #malloc(int)} or has already been passed to this method.
   *
   * @param buffer
   *          the buffer to free, not {@code null}, must have been created with
   *          {@link #malloc(int)}, must not be accessed afterwards, must not be
   *          passed to this method again
   * @throws NullPointerException
   *           if {@code buffer} is {@code null}
   * @throws IllegalArgumentException
   *           if {@code buffer} is not a direct buffer
   * @see <a href="http://man7.org/linux/man-pages/man3/malloc.3.html">MALLOC(3)</a>
   */
  public static void free(ByteBuffer buffer) {
    Objects.requireNonNull(buffer, "buffer");
    if (!buffer.isDirect()) {
      throw new IllegalArgumentException("only direct buffers can be fee()ed");
    }
    free0(buffer);
  }

  private static native void free0(ByteBuffer buffer);

}
