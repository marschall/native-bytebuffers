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
import com.github.marschall.nativebytebuffers.MmapFlags;

import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class LargeAllocationBenchmarks {

  @Param({"2097152", "16777216"})
  public int allocationSize;

  @Benchmark
  public ByteBuffer allocateDirect() {
    return ByteBuffer.allocateDirect(this.allocationSize);
  }

  @Benchmark
  public void mmap(Blackhole blackhole) {
    int flags = MmapFlags.MAP_SHARED | MmapFlags.MAP_ANONYMOUS | MmapFlags.MAP_HUGETLB | MmapFlags.MAP_HUGE_2MB;
    ByteBuffer buffer = Mman.mmap(this.allocationSize, flags);
    blackhole.consume(buffer);
    Mman.munmap(buffer);
  }

  @Benchmark
  public void allocateNative(Blackhole blackhole) {
    try (ResourceScope scope = ResourceScope.newConfinedScope()) {
      MemorySegment segment = MemorySegment.allocateNative(this.allocationSize, scope);
      blackhole.consume(segment);
    }
  }

}
