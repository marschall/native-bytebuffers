package com.github.marschall.nativebytebuffers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

final class LibraryLoader {

  static final boolean INITIALIZED;

  static {
    boolean initialized = false;
    String version = getVersion();
    String libraryName = "native-bytebuffers-" + version;
    try {
      Runtime.getRuntime().loadLibrary(libraryName);
      initialized = true;
    } catch (UnsatisfiedLinkError e) {
      // the library is not in the library path
      // extract it from the JAR and load it from there
      String fileName = System.mapLibraryName(libraryName);
      Path extracted = extractLibrary(fileName);
      try {
        System.load(extracted.toAbsolutePath().toString());
        initialized = true;
      } finally {
        // under Linux can be deleted after loading
        try {
          Files.delete(extracted);
        } catch (IOException e1) {
          throw new AssertionError("could not delete temp file: " + fileName, e);
        }
      }
    }
    INITIALIZED = initialized;
  }

  private static String getVersion() {
    String fileName = "native-bytebuffers.version";
    try (InputStream stream = LibraryLoader.class.getClassLoader().getResourceAsStream(fileName)) {
      if (stream == null) {
        throw new AssertionError("could not load resource: " + fileName);
      }
      ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
      byte[] buffer = new byte[16];
      int read = 0;
      do {
        if (read > 0) {
          bos.write(buffer, 0, read);
        }
        read = stream.read(buffer);
      } while (read != -1);
      return new String(bos.toByteArray(), StandardCharsets.US_ASCII);
    } catch (IOException e) {
      throw new AssertionError("could not load file: " + fileName, e);
    }
  }

  private static Path extractLibrary(String fileName) {
    Path tempFile;
    try {
      tempFile = Files.createTempFile("native-bytebuffers-", ".so");
    } catch (IOException e) {
      throw new AssertionError("could not create temp file", e);
    }
    try (OutputStream output = Files.newOutputStream(tempFile);
         InputStream input = LibraryLoader.class.getClassLoader().getResourceAsStream(fileName)) {
      if (input == null) {
        throw new AssertionError("could not load resource: " + fileName);
      }
      byte[] buffer = new byte[8192];
      int read = 0;
      do {
        if (read > 0) {
          output.write(buffer, 0, read);
        }
        read = input.read(buffer);
      } while (read != -1);
    } catch (IOException e) {
      throw new AssertionError("could copy to temp file: " + fileName, e);
    }
    return tempFile;
  }

  static void assertInitialized() {
    if (!INITIALIZED) {
      throw new IllegalStateException("not initialized");
    }
  }

  private LibraryLoader() {
    throw new AssertionError("not instantiable");
  }

}
