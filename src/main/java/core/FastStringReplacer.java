package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastStringReplacer {
    private List<Replacement> patterns = new ArrayList<Replacement>();
    public char[] expandArray(char[] sourceArray, int newSize) {
        if (newSize <= sourceArray.length)
            return sourceArray;
        char[] tempArray = new char[newSize];
        System.arraycopy(sourceArray, 0, tempArray, 0, sourceArray.length);
        return tempArray;
    }
    public FastStringReplacer(List<Replacement> patterns) {
        this.patterns = patterns;
        Collections.sort(this.patterns);

    }
    public static class Replacement implements Comparable<Replacement> {
        public String oldValue;
        public String newValue;

        public int compareTo(Replacement anotherReplacement) {
            return anotherReplacement.oldValue.length() - this.oldValue.length();
        }
    }
    public String execute(String inputString) {

        char[] resultCharArray = new char[inputString.length()+10];
        STRING_LOOP:
        for (int stringCharIndex = 0, resultArrayIndex=0; stringCharIndex < inputString.length(); stringCharIndex++, resultArrayIndex++) {
            boolean isFullMatch = true;
            for (int patternIndex = 0; patternIndex < patterns.size(); patternIndex++) {
                Replacement replacement = patterns.get(patternIndex);
                char[] oldValueCharArray = replacement.oldValue.toCharArray();
                for (int oldValueCharIndex = 0; oldValueCharIndex < oldValueCharArray.length; oldValueCharIndex++) {
                    char oldValueChar = oldValueCharArray[oldValueCharIndex];
                    if (oldValueChar != inputString.charAt(stringCharIndex)) {
                        if (patternIndex == patterns.size() - 1) {

                            resultCharArray[resultArrayIndex] = inputString.charAt(stringCharIndex);
                        }
                        break;
                    } else {
                        int tempOldValueCharIndex = oldValueCharIndex;
                        int tempStringCharIndex = stringCharIndex;
                        while (isFullMatch) {
                            tempOldValueCharIndex++;
                            tempStringCharIndex++;
                            if (tempStringCharIndex == inputString.length() || tempOldValueCharIndex == oldValueCharArray.length)
                                break;
                            if (oldValueCharArray[tempOldValueCharIndex] != inputString.charAt(tempStringCharIndex)) {
                                isFullMatch = false;
                            }
                        }
                        if (isFullMatch) {
                            resultString.append(replacement.newValue);
                            stringCharIndex += replacement.oldValue.length() - 1;
                            continue STRING_LOOP;
                        }
                    }
                }
            }
        }
        return resultString.toString();
    }
}
