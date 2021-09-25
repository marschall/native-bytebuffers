package com.github.marschall.nativebytebuffers;

public final class Seals {

  private Seals() {
    throw new AssertionError("not instantiable");
  }

  /**
   * If this seal is set, any further call to {@link Fcntl#fcntl(int, int, int)}
   * with {@link #F_ADD_SEALS} fails with the error EPERM. Therefore, this seal
   * prevents any modifications to the set of seals itself. If the initial set
   * of seals of a file includes {@link #F_SEAL_SEAL}, then this effectively
   * causes the set of seals to be constant and locked.
   */
  public static final int F_SEAL_SEAL = 1;

  /**
   * If this seal is set, the file in question cannot be reduced in size. This
   * affects open(2) with the O_TRUNC flag as well as truncate(2) and
   * ftruncate(2). Those calls fail with EPERM if you try to shrink the file in
   * question. Increasing the file size is still possible.
   */
  public static final int F_SEAL_SHRINK = 2;

  /**
   * If this seal is set, the size of the file in question cannot be increased.
   * This affects write(2) beyond the end of the file, truncate(2),
   * ftruncate(2), and fallocate(2). These calls fail with EPERM if you use them
   * to increase the file size. If you keep the size or shrink it, those calls
   * still work as expected.
   */
  public static final int F_SEAL_GROW = 4;

  /**
   * If this seal is set, you cannot modify the contents of the file. Note that
   * shrinking or growing the size of the file is still possible and allowed.
   * Thus, this seal is normally used in combination with one of the other
   * seals. This seal affects write(2) and fallocate(2) (only in combination
   * with the FALLOC_FL_PUNCH_HOLE flag). Those calls fail with EPERM if this
   * seal is set. Furthermore, trying to create new shared, writable
   * memory-mappings via {@link Mman#mmap(int, int, int)} will also fail with
   * EPERM.
   * <p>
   * Using the {@link #F_ADD_SEALS} operation to set the {@link #F_SEAL_WRITE}
   * seal fails with EBUSY if any writable, shared mapping exists. Such mappings
   * must be unmapped before you can add this seal. Furthermore, if there are
   * any asynchronous I/O operations (io_submit(2)) pending on the file, all
   * outstanding writes will be discarded.
   */
  public static final int F_SEAL_WRITE = 8;

  /**
   * The effect of this seal is similar to {@link #F_SEAL_WRITE}, but the
   * contents of the file can still be modified via shared writable mappings
   * that were created prior to the seal being set. Any attempt to create a new
   * writable mapping on the file via {@link Mman#mmap(int, int, int)} will fail
   * with EPERM. Likewise, an attempt to write to the file via write(2) will
   * fail with EPERM.
   * <p>
   * Using this seal, one process can create a memory buffer that it can
   * continue to modify while sharing that buffer on a "read-only" basis with
   * other processes.
   */
  public static final int F_SEAL_FUTURE_WRITE = 16;

}
