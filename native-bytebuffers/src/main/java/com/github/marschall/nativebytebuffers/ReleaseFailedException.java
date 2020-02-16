package com.github.marschall.nativebytebuffers;

/**
 * Signals a memory release has failed.
 */
public final class ReleaseFailedException extends MemoryManagementException {

  ReleaseFailedException() {
    super();
  }

  ReleaseFailedException(String message) {
    super(message);
  }

}
