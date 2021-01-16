package impl;

import core.FastStringReplacer;
import core.FastStringReplacerBuilder;

public class Main {
    public static void main(String[] args) {
        String testString = "Test[\n[]eh\"]";
        FastStringReplacer fastStringReplacer = new FastStringReplacerBuilder()
                .replace("\"", "'")
                .replace("[]", "{}")
                .replace("zf", "xc")
                .delete("Te")
                .delete("s")
                .build();

        System.out.print(fastStringReplacer.execute(testString));

    }
}
