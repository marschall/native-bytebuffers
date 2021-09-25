package com.github.marschall.nativebytebuffers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;
/**
 * Provides access to memory management using {@code sys/mman.h}.
 */
public final class Mman {

  private static final Charset NATIVE_CHARSET;

  static {
    LibraryLoader.assertInitialized();
    // JEP 400
    String nativeCharsetName = System.getProperty("native.encoding", Charset.defaultCharset().name());
    NATIVE_CHARSET = Charset.forName(nativeCharsetName);
  }

  private Mman() {
    throw new AssertionError("not instantiable");
  }

  /**
   * Calls {@code mmap()} to create an anonymous shared virtual memory region
   * and creates a {@code ByteBuffer} around it.
   * <p>
   * Protection will be {@code PROT_READ | PROT_WRITE}.
   *
   * @param length
   *          the length of the mapping, must be greater than 0
   * @return a new ByteBuffer around the newly {@code mmap()}-ed area,
   *         never {@code null},
   *         must be released with {@link #munmap(ByteBuffer)}
   * @throws IllegalArgumentException
   *           if {@code length} is not positive
   * @throws AllocationFailedException
   *           if {@code mmap()} fails
   * @see Mman#mmap(int, int)
   * @see Mman#munmap(ByteBuffer)
   * @see <a href="http://man7.org/linux/man-pages/man2/mmap.2.html">mmap(2)</a>
   */
  public static ByteBuffer mmap(int length) {

    if (length <= 0) {
      throw new IllegalArgumentException("non postive length: " + length);
    }
    ByteBuffer buffer = mmap0(length);

    if (buffer == null) {
      throw new AllocationFailedException("mmap failed");
    }
    return buffer;
  }

  /**
   * Calls {@code mmap()} to create a virtual memory region and creates a
   * {@code ByteBuffer} around it.
   * <p>
   * Protection will be {@code PROT_READ | PROT_WRITE}.
   *
   * @param length
   *          the length of the mapping, must be greater than 0
   * @param flags
   *          ORed arguments from {@link MmapFlags} that control various aspects
   *          of the mapping
   * @return a new ByteBuffer around the newly {@code mmap()}-ed area,
   *         never {@code null},
   *         must be released with {@link #munmap(ByteBuffer)}
   * @throws IllegalArgumentException
   *           if {@code length} is not positive
   * @throws AllocationFailedException
   *           if {@code mmap()} fails
   * @see Mman#munmap(ByteBuffer)
   * @see <a href="http://man7.org/linux/man-pages/man2/mmap.2.html">mmap(2)</a>
   */
  public static ByteBuffer mmap(int length, int flags) {
    if (length <= 0) {
      throw new IllegalArgumentException("non postive length: " + length);
    }
    ByteBuffer buffer = mmap1(length, flags);

    if (buffer == null) {
      throw new AllocationFailedException("mmap failed");
    }
    return buffer;
  }
  public static ByteBuffer mmap(int length, int flags, int fd) {
    if (length <= 0) {
      throw new IllegalArgumentException("non postive length: " + length);
    }
    ByteBuffer buffer = mmap2(length, flags, fd);

    if (buffer == null) {
      throw new AllocationFailedException("mmap failed");
    }
    return buffer;
  }

  private static native ByteBuffer mmap0(int length);

  private static native ByteBuffer mmap1(int length, int flags);

  private static native ByteBuffer mmap2(int length, int flags, int fd);

  /**
   * Calls {@code munmap()} on the contents of the given {@link ByteBuffer}.
   * <p>
   * This method may crash the JVM the buffer passed has not been created by
   * {@link #mmap(int, int)}, {@link #mmap(int)} or has already been passed to
   * this method.
   *
   * @param buffer
   *          the buffer to free, not {@code null}, must have been created with
   *          {@link #mmap(int, int)} or {@link #mmap(int)}, must not be
   *          accessed afterwards, must not be passed to this method again
   * @throws NullPointerException
   *           if {@code buffer} is {@code null}
   * @throws IllegalArgumentException
   *           if {@code buffer} is not a direct buffer
   * @throws ReleaseFailedException
   *           if unmapping fails
   * @see Mman#munmap(ByteBuffer)
   * @see <a href="http://man7.org/linux/man-pages/man2/mmap.2.html">mmap(2)</a>
   */
  public static void munmap(ByteBuffer buffer) {
    Objects.requireNonNull(buffer, "buffer");
    if (!buffer.isDirect()) {
      throw new IllegalArgumentException("only direct buffers can be munmap()ed");
    }
    munmap0(buffer);
  }

  private static native void munmap0(ByteBuffer buffer);

  /**
   * Calls {@code memfd_create()} to create an anonymous file and returns a file
   * descriptor that refers to it.
   *
   * @param name
   *          the name supplied in name is used as a filename and will be
   *          displayed as the target of the corresponding symbolic link in the
   *          directory {@code /proc/self/fd/}. The displayed name is always
   *          prefixed with memfd: and serves only for debugging purposes. Names
   *          do not affect the behavior of the file descriptor, and as such
   *          multiple files can have the same name without any side effects.
   *          Must not be longer than 249 in the native encoding
   * @param flags
   *          bitwise ORed in flags to change the behavior of
   *          {@code memfd_create()}
   *
   * @return new file descriptor that can be used to refer to the file. This
   *         file descriptor is opened for both reading and writing.
   * @throws IOException
   *           if {@code memfd_create()} fails
   * @see MemfdCreateFlags
   * @see <a href="https://man7.org/linux/man-pages/man2/memfd_create.2.html">memfd_create(2)<a>
   */
  public static int memfd_create(String name, int flags) throws IOException {
    byte[] nameInBytes = name.getBytes(NATIVE_CHARSET);
    int nameLength = nameInBytes.length;
    if (nameLength > 249) {
      throw new IllegalArgumentException("name too long");
    }
    int fd = memfd_create0(nameInBytes, nameLength, flags);
    if (fd == -1) {
      // should not happen, should be handeled in JNI
      throw new IOException("could not create memfd: " + name);
    }
    return fd;
  }

  private static native int memfd_create0(byte[] name, int nameLength, int flags) throws IOException;

  /**
   * Returns the number of bytes in a memory page,
   * where "page" is a fixed-length block, the unit for memory allocation and
   * file mapping performed by {@link Mman#mmap(int, int)}.
   *
   * @return the page size, {@code long} because in theory we could have pages larger than 2 GiB
   * @see <a href="http://man7.org/linux/man-pages/man2/getpagesize.2.html">getpagesize(2)</a>
   */
  public static long getpagesize() {
    return getpagesize0();
  }

  private static native long getpagesize0();

}
