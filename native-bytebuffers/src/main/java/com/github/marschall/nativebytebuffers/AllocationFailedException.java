package com.github.marschall.nativebytebuffers;

/**
 * Signals a memory allocation has failed.
 */
public final class AllocationFailedException extends MemoryManagementException {

  AllocationFailedException() {
    super();
  }

  AllocationFailedException(String message) {
    super(message);
  }

}
