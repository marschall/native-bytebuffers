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

import jdk.incubator.foreign.MemorySegment;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class SmallAllocationBenchmarks {

  @Param({"128", "1024", "4096"})
  public int allocationSize;

  @Benchmark
  public ByteBuffer allocateDirect() {
    return ByteBuffer.allocateDirect(this.allocationSize);
  }

  @Benchmark
  public void malloc(Blackhole blackhole) {
    ByteBuffer buffer = Stdlib.malloc(this.allocationSize);
    blackhole.consume(buffer);
    Stdlib.free(buffer);
  }

  @Benchmark
  public void calloc(Blackhole blackhole) {
    ByteBuffer buffer = Stdlib.calloc(this.allocationSize);
    blackhole.consume(buffer);
    Stdlib.free(buffer);
  }

  @Benchmark
  public void allocateNative(Blackhole blackhole) {
    try (MemorySegment segment = MemorySegment.allocateNative(this.allocationSize)) {
      blackhole.consume(segment);
    }
  }

}
