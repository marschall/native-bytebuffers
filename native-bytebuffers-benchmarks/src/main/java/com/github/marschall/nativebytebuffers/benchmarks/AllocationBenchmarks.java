package com.github.marschall.nativebytebuffers.benchmarks;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import com.github.marschall.nativebytebuffers.Stdlib;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class AllocationBenchmarks {

  @Param({"1024", "8192", "1048576"})
  public int allocationSize;

  @Benchmark
  public ByteBuffer jvm() {
    return ByteBuffer.allocateDirect(this.allocationSize);
  }

  @Benchmark
  public void malloc(Blackhole blackhole) {
    ByteBuffer buffer = Stdlib.malloc(this.allocationSize);
    blackhole.consume(buffer);
    Stdlib.free(buffer);
  }

}
