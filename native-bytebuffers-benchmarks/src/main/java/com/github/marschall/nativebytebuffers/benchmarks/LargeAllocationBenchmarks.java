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

import com.github.marschall.nativebytebuffers.Mman;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class LargeAllocationBenchmarks {

  @Param({"4096", "1048576", "8388608"})
  public int allocationSize;

  @Benchmark
  public ByteBuffer allocateDirect() {
    return ByteBuffer.allocateDirect(this.allocationSize);
  }

  @Benchmark
  public void mmap(Blackhole blackhole) {
    ByteBuffer buffer = Mman.mmap(this.allocationSize);
    blackhole.consume(buffer);
    Mman.munmap(buffer);
  }

}
