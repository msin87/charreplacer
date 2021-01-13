package core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FastStringReplacerBuilder {
    private final List<String> oldSequenceList = new ArrayList<String>(2);
    private final List<String> newSequenceList = new ArrayList<String>(2);
    private final List<String> deleteSequenceList = new ArrayList<String>(2);

    public FastStringReplacerBuilder replace(String oldSequence, String newSequence) {
        oldSequenceList.add(oldSequence);
        newSequenceList.add(newSequence);
        return this;
    }


    public FastStringReplacerBuilder delete(String deleteSequence) {
        deleteSequenceList.add(deleteSequence);
        return this;
    }

    public FastStringReplacer build() {
        return new FastStringReplacer(oldSequenceList, newSequenceList, deleteSequenceList);
    }

}
