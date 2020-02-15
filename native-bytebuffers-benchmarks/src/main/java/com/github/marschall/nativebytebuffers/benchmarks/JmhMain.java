package com.github.marschall.nativebytebuffers.benchmarks;

import static org.openjdk.jmh.results.format.ResultFormatType.TEXT;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JmhMain {

  /**
   * Main method, runs the benchmarks.
   *
   * @param args first element contains the file name
   * @throws RunnerException  if something goes wrong during execution
   */
  public static void main(String[] args) throws RunnerException {
    Options options = new OptionsBuilder()
            .include(JmhMain.class.getPackage().getName() + ".*")
            .jvmArgs("-Xmx4g", "-XX:MaxDirectMemorySize=512M")
            .warmupIterations(5)
            .measurementIterations(5)
            .forks(3)
            .resultFormat(TEXT)
//            .threads(Integer.parseInt(args[0]))
            .output("results.txt")
            .build();
    new Runner(options).run();
  }

}
