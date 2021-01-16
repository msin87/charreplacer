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

        final int lastPatternIndex = patterns.size() - 1;
        char[] resultCharArray = new char[inputString.length()+10];
        char[] inputArray = inputString.toCharArray();
        int resultArrayIndex=0;
        int resultLengthLimit = resultCharArray.length-2;
        int tempOldValueCharIndex=0;
        int tempStringCharIndex=0;
        STRING_LOOP:
        for (int stringCharIndex = 0; stringCharIndex < inputArray.length; stringCharIndex++) {
            boolean isFullMatch = true;
            for (int patternIndex = 0; patternIndex < patterns.size(); patternIndex++) {
                Replacement replacement = patterns.get(patternIndex);
                char[] oldValueCharArray = replacement.oldValue.toCharArray();
                for (int oldValueCharIndex = 0; oldValueCharIndex < oldValueCharArray.length; oldValueCharIndex++) {
                    char oldValueChar = oldValueCharArray[oldValueCharIndex];
                    if (oldValueChar != inputArray[stringCharIndex]) {
                        if (patternIndex == lastPatternIndex) {
                            resultCharArray[resultArrayIndex] = inputArray[stringCharIndex];
                            resultArrayIndex++;
                        }
                        break;
                    } else {
                        tempOldValueCharIndex = oldValueCharIndex;
                        tempStringCharIndex = stringCharIndex;
                        while (isFullMatch) {
                            tempOldValueCharIndex++;
                            tempStringCharIndex++;
                            if (tempStringCharIndex == inputArray.length || tempOldValueCharIndex == oldValueCharArray.length)
                                break;
                            if (oldValueCharArray[tempOldValueCharIndex] != inputArray[tempStringCharIndex]) {
                                isFullMatch = false;
                            }
                        }
                        if (isFullMatch) {
                            char[] newValueArray = replacement.newValue.toCharArray();
                            for (int newValueCharIndex=0; newValueCharIndex<newValueArray.length; newValueCharIndex++){
                                resultCharArray[resultArrayIndex] = newValueArray[newValueCharIndex];
                                resultArrayIndex++;
                            }
                            if (resultArrayIndex==resultLengthLimit){
                                resultCharArray = expandArray(resultCharArray,resultCharArray.length+2);
                                resultLengthLimit = resultCharArray.length - 2;
                            }
                            stringCharIndex += replacement.oldValue.length() - 1;
                            continue STRING_LOOP;
                        }
                    }
                }
            }
        }
        return new String(resultCharArray).trim();
    }
}
