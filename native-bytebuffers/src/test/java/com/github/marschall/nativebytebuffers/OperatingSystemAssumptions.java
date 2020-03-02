package com.github.marschall.nativebytebuffers;

import java.util.Locale;

final class OperatingSystemAssumptions {

  private OperatingSystemAssumptions() {
    throw new AssertionError("not instantiable");
  }

  static boolean isMacOs() {
    return System.getProperty("os.name").toLowerCase(Locale.US).contains("mac");
  }

  static boolean isLinux() {
    return System.getProperty("os.name").toLowerCase(Locale.US).contains("linux");
  }

}
