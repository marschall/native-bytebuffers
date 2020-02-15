package com.github.marschall.nativebytebuffers;

public final class AllocationFailedException extends RuntimeException {

  AllocationFailedException() {
    super();
  }

  AllocationFailedException(String message) {
    super(message);
  }

}
