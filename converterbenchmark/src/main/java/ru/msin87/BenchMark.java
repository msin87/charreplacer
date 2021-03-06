package ru.msin87;

import core.FastStringReplacer;
import core.FastStringReplacerBuilder;
import core.StringCharReplacer;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
public class BenchMark {

    String testString = "Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]";
    FastStringReplacer fastStringReplacer;
    @Setup
    public void doSetup(){
        fastStringReplacer = new FastStringReplacerBuilder()
                .replace("\"", "'")
                .delete("\n")
                .replace("[]", "{}")
                .build();
    }
    @Benchmark
    public String replaceChain() {
        return this.testString.replace('"', '\'').replace("\n", "").replace("[]", "{}");
    }

    @Benchmark
    public String replaceAllChain() {
        return this.testString.replaceAll("\"", "'").replaceAll("\n", "").replace("[]", "{}");
    }

    @Benchmark
    public String forLoopReplace() {
        return StringCharReplacer.forLoopReplace(testString);
    }

    @Benchmark
    public String fastStringReplacer(){
        return fastStringReplacer.execute(testString);
    }
}
