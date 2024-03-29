Native ByteBuffers [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.marschall/native-bytebuffers/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.marschall/native-bytebuffers) [![Javadocs](https://www.javadoc.io/badge/com.github.marschall/native-bytebuffers.svg)](https://www.javadoc.io/doc/com.github.marschall/native-bytebuffers)
==================

ByteBuffers that are allocated and released directly with:

* `malloc` and `free`
* `calloc` and `free`
* `aglined_alloc` and `free`
* `memfd_secret` and `close`
* `mmap` and `munmap`
  * HugeTLB page support on Linux
  * superpage support on macOS

Usage
-----

```xml
<dependency>
    <groupId>com.github.marschall</groupId>
    <artifactId>native-bytebuffers</artifactId>
    <version>0.4.2</version>
</dependency>
```

```java
ByteBuffer buffer = Stdlib.malloc(size);

try {
  // do things with the buffer
} finally {
  Stdlib.free(buffer);
}
```

```java
ByteBuffer buffer = Mman.mmap(length);

try {
  // do things with the buffer
} finally {
  Mman.munmap(buffer);
}
```

For best startup performance it is recommended to extract the `.so` from the JAR and add it to a folder present in the `LD_LIBRARY_PATH` environment variable or the `java.library.path` system property. Otherwise this library will extract the `.so` to a temporary folder the first time it is called.

This library has been tested on Linux AMD64, macOS x86-64 as well as  macOS AArch64.

It should be possible to port it other Unix like *BSD.

Why would you want this?
------------------------

You want a direct (off-heap) `java.nio.ByteBuffer` but you:

* don't want to go though the slow allocation path of the JVM for `java.nio.ByteBuffer#allocateDirect` that ends up calling `java.lang.System#gc()` and `java.lang.Thread#sleep` in a loop
* don't want to rely on the slow release mechanism of the JVM for `java.nio.ByteBuffer` that relies on garbage collection
* don't want to use internals like `sun.misc.Unsafe`
* don't want to rely on libffi

This project instead uses official [JNI ByteBuffer APIs](https://docs.oracle.com/en/java/javase/11/docs/specs/jni/functions.html#nio-support) and is therefore portable across JVMs.


Why would you not want this?
----------------------------

This obviously brings all the issues of manual memory management to Java like:

* memory leaks by not calling `free`/`munmap`
* double free
* use after free

These issues can crash the JVM.

As this project uses JNI a native library is required. We ship one for Linux x86-64 but for every other platform you have to build it yourself.

Links
-----

- [nosecmem](https://github.com/JonathonReinhart/nosecmem)
- [Using Linux's memfd_secret syscall from the JVM with JEP-419](https://blog.arkey.fr/2022/05/16/linux_memfd_secret_with_jep-419/)
