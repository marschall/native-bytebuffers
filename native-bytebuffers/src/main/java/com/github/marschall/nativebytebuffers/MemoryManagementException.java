package com.github.marschall.nativebytebuffers;

/**
 * Abstract base class for memory management exceptions.
 */
public abstract class MemoryManagementException extends RuntimeException {

  MemoryManagementException() {
    super();
  }

  MemoryManagementException(String message) {
    super(message);
  }

}
