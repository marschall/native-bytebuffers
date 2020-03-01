package com.github.marschall.nativebytebuffers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

final class HugeTlb {

  private static final String HUGETLB_SHM_GROUP = "/proc/sys/vm/hugetlb_shm_group";

  private HugeTlb() {
    throw new AssertionError("not instantiable");
  }

  static int getHugeGid() throws IOException {
    Path path = Paths.get(HUGETLB_SHM_GROUP);
    if (!Files.exists(path)) {
      throw new IllegalStateException(HUGETLB_SHM_GROUP + " is not present");
    }
    if (!Files.isReadable(path)) {
      throw new IllegalStateException(HUGETLB_SHM_GROUP + " is not readable");
    }
//    if (Files.size(path) == 0) {
//      throw new IllegalStateException(HUGETLB_SHM_GROUP + " is empty");
//    }
    return parseFileAsInt(path, 4);
  }

  static void parseMeminfo() throws IOException {
    Path path = Paths.get("/proc/meminfo");
    try (InputStream stream = Files.newInputStream(path);
         Reader reader = new InputStreamReader(stream, StandardCharsets.US_ASCII);
         BufferedReader bufferedReader = new BufferedReader(reader, 32)) {

      String line = bufferedReader.readLine();
      while (line != null) {
        if (line.startsWith("AnonHugePages:")) {
        } else if (line.startsWith("ShmemHugePages:")) {
        } else if (line.startsWith("HugePages_Total:")) {
        } else if (line.startsWith("HugePages_Free:")) {
        } else if (line.startsWith("HugePages_Rsvd:")) {
        } else if (line.startsWith("HugePages_Surp:")) {
        } else if (line.startsWith("Hugepagesize:")) {
        } else if (line.startsWith("Hugetlb:")) {

        } else {

        }
        line = bufferedReader.readLine();
      }
    }
  }

  static void parsePageSize(int pageSize) throws IOException {
    Path path = Paths.get("/sys/kernel/mm/hugepages/hugepages-" + (pageSize / 1024) + "kB/");
    int free_hugepages = parseFileAsInt(path.resolve("free_hugepages"), 32);
    int nr_hugepages = parseFileAsInt(path.resolve("nr_hugepages"), 32);
    int nr_hugepages_mempolicy = parseFileAsInt(path.resolve("nr_hugepages_mempolicy"), 32);
    int nr_overcommit_hugepages = parseFileAsInt(path.resolve("nr_overcommit_hugepages"), 32);
    int resv_hugepages = parseFileAsInt(path.resolve("resv_hugepages"), 32);
    int surplus_hugepages = parseFileAsInt(path.resolve("surplus_hugepages"), 32);
  }

  static void getShmmax() {
    Paths.get("/proc/sys/kernel/shmmax");
  }


  private static int parseFileAsInt(Path path, int bufferSize) throws IOException {

    try (InputStream stream = Files.newInputStream(path);
         Reader reader = new InputStreamReader(stream, StandardCharsets.US_ASCII);
         BufferedReader bufferedReader = new BufferedReader(reader, bufferSize)) {
      // TODO check for null
      String line = bufferedReader.readLine();
      return Integer.parseInt(line);
    }
  }

}
