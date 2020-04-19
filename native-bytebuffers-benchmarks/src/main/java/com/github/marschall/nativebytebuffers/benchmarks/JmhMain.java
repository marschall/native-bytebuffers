package com.github.marschall.nativebytebuffers.benchmarks;

import static org.openjdk.jmh.results.format.ResultFormatType.TEXT;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class JmhMain {

  /**
   * Main method, runs the benchmarks.
   *
   * @param args first element contains the file name
   * @throws RunnerException  if something goes wrong during execution
   */
  public static void main(String[] args) throws RunnerException {
    int threads = args.length > 0 ? Integer.parseInt(args[0]) : 1;
    Options options = new OptionsBuilder()
            .include(JmhMain.class.getPackage().getName() + ".*")
            .jvmArgs("-Xmx4g", "-XX:MaxDirectMemorySize=512M", "--add-modules", "jdk.incubator.foreign")
            .warmupIterations(5)
            .warmupTime(TimeValue.seconds(1L))
            .measurementIterations(5)
            .measurementTime(TimeValue.seconds(1L))
            .forks(3)
            .resultFormat(TEXT)
            .threads(threads)
            .output("results-threads-" + threads + ".txt")
            .build();
    new Runner(options).run();
  }

}
