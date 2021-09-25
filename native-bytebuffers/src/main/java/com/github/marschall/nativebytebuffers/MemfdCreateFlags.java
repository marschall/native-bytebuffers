package com.github.marschall.nativebytebuffers;

public final class MemfdCreateFlags {

  private MemfdCreateFlags() {
    throw new AssertionError("not instantiable");
  }

  /**
   * Set the close-on-exec (FD_CLOEXEC) flag on the new file descriptor. See the
   * description of the O_CLOEXEC flag in open(2) for reasons why this may be
   * useful.
   */
  public static final int MFD_CLOEXEC = 1;

  /**
   * Allow sealing operations on this file. See the discussion of the
   * F_ADD_SEALS and F_GET_SEALS operations in fcntl(2), and also NOTES, below.
   * The initial set of seals is empty. If this flag is not set, the initial set
   * of seals will be F_SEAL_SEAL, meaning that no other seals can be set on the
   * file.
   */
  public static final int MFD_ALLOW_SEALING = 2;


  /**
   * (since Linux 4.14) The anonymous file will be created in the hugetlbfs
   * filesystem using huge pages. See the Linux kernel source file
   * Documentation/admin-guide/mm/hugetlbpage.rst for more information about
   * hugetlbfs. Specifying both MFD_HUGETLB and MFD_ALLOW_SEALING in flags is
   * supported since Linux 4.16.
   */
  public static final int MFD_HUGETLB = 4;

  /**
   * Used in conjunction with MFD_HUGETLB to select alternative hugetlb page
   * sizes (respectively, 2 MB, 1 GB, ...) on systems that support multiple
   * hugetlb page sizes. Definitions for known huge page sizes are included in
   * the header file &lt;linux/memfd.h&gt;.
   * <p>
   * For details on encoding huge page sizes not included in the header file,
   * see the discussion of the similarly named constants in mmap(2).
   */
  public static final int MFD_HUGE_64KB = 1073741824;
  public static final int MFD_HUGE_512KB = 1275068416;
  public static final int MFD_HUGE_1MB = 1342177280;
  public static final int MFD_HUGE_2MB = 1409286144;
  public static final int MFD_HUGE_8MB = 1543503872;
  public static final int MFD_HUGE_16MB = 1610612736;
  public static final int MFD_HUGE_32MB = 1677721600;
  public static final int MFD_HUGE_256MB = 1879048192;
  public static final int MFD_HUGE_512MB = 1946157056;
  public static final int MFD_HUGE_1GB = 2013265920;
  public static final int MFD_HUGE_2GB = 2080374784;
  public static final int MFD_HUGE_16GB = -2013265920;


}
