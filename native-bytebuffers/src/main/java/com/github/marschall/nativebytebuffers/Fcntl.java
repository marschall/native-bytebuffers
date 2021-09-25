package com.github.marschall.nativebytebuffers;

/**
 * Allows to manipulate file descriptors {@code fcntl.h}.
 */
public final class Fcntl {

  static {
    LibraryLoader.assertInitialized();
  }

  private Fcntl() {
    throw new AssertionError("not instantiable");
  }

  /**
   * Performs one of the operations described below on the open file descriptor
   * fd. The operation is determined by cmd.
   * <p>
   * {@code void} version of the method.
   *
   * @param fd file descriptor referring to the underlying open file
   * @param cmd the command to execute
   * @return return code or result
   * @see FcntlCommands
   */
  public static int fcntl(int fd, int cmd) {
    return fcntl0(fd, cmd);
  }

  private static native int fcntl0(int fd, int cmd);

  /**
   * Performs one of the operations described below on the open file descriptor
   * fd. The operation is determined by cmd.
   * <p>
   * {@code int} version of the method.
   *
   * @param fd file descriptor referring to the underlying open file
   * @param cmd the command to execute
   * @param arg argument for cmd
   * @return return code or result
   * @see FcntlCommands
   */
  public static int fcntl(int fd, int cmd, int arg) {
    return fcntl1(fd, cmd, arg);
  }

  private static native int fcntl1(int fd, int cmd, int arg);

}
