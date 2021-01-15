package core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FastStringReplacerBuilder {
    private final List<FastStringReplacer.Replacement> patterns = new ArrayList<FastStringReplacer.Replacement>();
    public FastStringReplacerBuilder replace(String oldSequence, String newSequence) {
        FastStringReplacer.Replacement replacement = new FastStringReplacer.Replacement();
        replacement.oldValue = oldSequence;
        replacement.newValue = newSequence;
        patterns.add(replacement);
        return this;
    }


    public FastStringReplacerBuilder delete(String deleteSequence) {
        FastStringReplacer.Replacement replacement = new FastStringReplacer.Replacement();
        replacement.oldValue = deleteSequence;
        replacement.newValue = "";
        patterns.add(replacement);
        return this;
    }

    public FastStringReplacer build() {
        return new FastStringReplacer(patterns);
    }

}
