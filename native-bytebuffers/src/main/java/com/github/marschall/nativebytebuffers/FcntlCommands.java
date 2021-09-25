package com.github.marschall.nativebytebuffers;

public final class FcntlCommands {

  private FcntlCommands() {
    throw new AssertionError("not instantiable");
  }

  /**
   * Add the seals given in the bit-mask argument arg to the set of seals of the
   * inode referred to by the file descriptor fd. Seals cannot be removed again.
   * Once this call succeeds, the seals are enforced by the kernel immediately.
   * If the current set of seals includes {@link Seals#F_SEAL_SEAL} (see below),
   * then this call will be rejected with EPERM. Adding a seal that is already
   * set is a no-op, in case {@link Seals#F_SEAL_SEAL} is not set already. In
   * order to place a seal, the file descriptor fd must be writable.
   *
   * @see Fcntl#fcntl(int, int, int)
   */
  public static final int F_ADD_SEALS = 1033;

  /**
   * Return (as the function result) the current set of seals of the inode
   * referred to by fd. If no seals are set, 0 is returned. If the file does not
   * support sealing, -1 is returned and errno is set to EINVAL.
   *
   * @see Fcntl#fcntl(int, int, int)
   */
  public static final int F_GET_SEALS = 1034;

}
