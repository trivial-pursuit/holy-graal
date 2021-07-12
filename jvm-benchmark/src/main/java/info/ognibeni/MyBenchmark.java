package info.ognibeni;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Borrowed from <a href="https://github.com/chrisseaton/graalvm-ten-things/blob/master/TopTen.java">Java-Benchmarking</a>
 */
public class MyBenchmark {

    @Warmup(iterations = 3, time = 5)
    @Measurement(iterations = 5, time = 10)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Benchmark
    public void topTwenty(Blackhole blackhole) {
        Arrays.stream(new String[]{"info/ognibeni/20xbible.txt"})
            .flatMap(this::fileLines)
            .flatMap(line -> Arrays.stream(line.split("\\b")))
            .map(word -> word.replaceAll("[^a-zA-Z]", ""))
            .filter(word -> word.length() > 0)
            .map(String::toLowerCase)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream()
            .sorted((a, b) -> -a.getValue().compareTo(b.getValue()))
            .limit(20)
            .forEach(e -> blackhole.consume(String.format("%s = %d%n", e.getKey(), e.getValue())));
//          .forEach(e -> System.out.println(String.format("%s = %d", e.getKey(), e.getValue())));
    }

    private Stream<String> fileLines(String path) {
        try {
            return
                new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)))).lines();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
