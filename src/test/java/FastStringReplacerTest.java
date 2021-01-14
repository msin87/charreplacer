import core.FastStringReplacer;
import core.FastStringReplacerBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FastStringReplacerTest {
    //String testString = "Test\n[]eTezf[]\"as\"[]d[]zx.\"Test2\"[sdeTesdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]";
    String testString = "Test\n[]eh\"]";

    public boolean isPatternAtBegin(char[] scanArray, char[] patternArray, int scanOffset) {
        int patternHits = 0;
        boolean startSuccess=false;
            while (scanArray.length >= scanOffset) {
                for (char patternChar:patternArray){
                    if ()
                }
                if (scanArray[scanOffset] == patternArray[0]) {
                    patternHits++;
                    scanOffset++;
                } else {
                    break;
                }
            }
        return patternHits >= patternArray.length;
    }

    public char[] replacePattern(char[] scanArray, char[] patternArray, char[] newSequence) {
        char[] resultArray = new char[scanArray.length];
        for (int scanIndex = 0, resultIndex = 0; scanIndex < scanArray.length; scanIndex++, resultIndex++) {
            if (isPatternAtBegin(scanArray, patternArray, scanIndex)) {
                if (patternArray.length > 1) {
                    System.arraycopy(newSequence, 0, resultArray, resultIndex, newSequence.length);
                    scanIndex += patternArray.length - 1;
                    resultIndex += patternArray.length - 1;
                } else resultArray[resultIndex] = newSequence[0];
            } else {
                resultArray[resultIndex] = scanArray[scanIndex];
            }
        }
        return resultArray;
    }

    public char[] expandArray(char[] sourceArray, int newSize) {
        if (newSize <= sourceArray.length)
            return sourceArray;
        char[] tempArray = new char[newSize];
        System.arraycopy(sourceArray, 0, tempArray, 0, sourceArray.length);
        return tempArray;
    }

    public char[] replaceOrDeletePattern(char[] scanArray, char[] patternArray, char[] newSequence) {
        char[] resultArray = new char[scanArray.length];
        for (int scanIndex = 0, resultIndex = 0; scanIndex < scanArray.length; scanIndex++, resultIndex++) {

            if (isPatternAtBegin(scanArray, patternArray, scanIndex)) {
                if (patternArray.length > 1) {
                    if (newSequence == null) {
                        resultIndex -= patternArray.length - 1;
                    } else {
                        if (resultIndex + newSequence.length >= resultArray.length) {
                            resultArray = expandArray(resultArray, resultArray.length + 10 * newSequence.length);
                        }
                        System.arraycopy(newSequence, 0, resultArray, resultIndex, newSequence.length);
                        resultIndex += newSequence.length - 1;
                        if (resultIndex >= resultArray.length) {
                            expandArray(resultArray, resultArray.length + 10 * newSequence.length);
                        }
                    }
                    scanIndex += patternArray.length - 1;
                } else {
                    if (newSequence == null) {
                        resultIndex--;
                    } else {
                        if (newSequence.length > 1) {
                            if (resultIndex + newSequence.length >= resultArray.length) {
                                resultArray = expandArray(resultArray, resultArray.length + 10 * newSequence.length);
                            }
                            System.arraycopy(newSequence, 0, resultArray, resultIndex, newSequence.length);
                            resultIndex += newSequence.length - 1;
                        } else {
                            resultArray[resultIndex] = newSequence[0];
                        }
                    }
                }
            } else {
                if (resultIndex >= resultArray.length) {
                    resultArray = expandArray(resultArray, resultArray.length + 10);
                }
                resultArray[resultIndex] = scanArray[scanIndex];
            }
        }
        return resultArray;
    }

    public enum ReplacerAction {
        COPY_ORIGINAL,
        COPY_NEW,
        DELETE
    }

    public String replaceOrDeletePatterns(String sourceString, List<String> patternList) {
        char[] scanArray = sourceString.toCharArray();
        char[] resultArray = new char[scanArray.length];
        char[] newSequence = new char[0];
        ReplacerAction replacerAction;
        int patternLength = 0;
        for (int scanIndex = 0, resultIndex = 0; scanIndex < scanArray.length; scanIndex++) {
            replacerAction = ReplacerAction.COPY_ORIGINAL;
            for (int patternIndex = 0; patternIndex < patternList.size() - 1; patternIndex += 2) {
                char[] patternArray = patternList.get(patternIndex).toCharArray();
                patternLength = patternArray.length;
                newSequence = patternList.get(patternIndex + 1) == null ? null : patternList.get(patternIndex + 1).toCharArray();
                if (isPatternAtBegin(scanArray, patternArray, scanIndex)) {
                    if (patternArray.length > 1) {
                        if (newSequence == null) {
                            replacerAction = ReplacerAction.DELETE;
                        } else {
                            replacerAction = ReplacerAction.COPY_NEW;
                        }
                    } else {
                        if (newSequence == null) {
                            replacerAction = ReplacerAction.DELETE;
                        } else {
                            replacerAction = ReplacerAction.COPY_NEW;
                            if (newSequence.length > 1) {
                                resultIndex += newSequence.length - 1;
                            }
                        }
                    }
                    break;
                }
            }
            switch (replacerAction) {

                case COPY_ORIGINAL:
                    if (resultIndex >= resultArray.length) {
                        resultArray = expandArray(resultArray, resultArray.length + 10);
                    }
                    resultArray[resultIndex] = scanArray[scanIndex];
                    resultIndex++;
                    break;
                case DELETE:
                    scanIndex += patternLength - 1;
                    break;
                case COPY_NEW:
                    if (resultIndex + newSequence.length >= resultArray.length) {
                        resultArray = expandArray(resultArray, resultArray.length + 10 * newSequence.length);
                    }
                    if (newSequence.length > 1) {
                        System.arraycopy(newSequence, 0, resultArray, resultIndex, newSequence.length);
                        scanIndex += patternLength - 1;
                    } else {
                        resultArray[resultIndex] = newSequence[0];
                    }
                    resultIndex += newSequence.length;
                    break;
            }
        }
        return new String(resultArray).trim();
    }

    @Test
    public void getPatternIndexTest() {
        String pattern = "st";
        assertFalse(isPatternAtBegin(testString.toCharArray(), pattern.toCharArray(), 0));
    }

    @Test
    public void replacePatternTest() {
        String pattern = "st";
        String newSequence = "kl";
        assertEquals(testString.replace(pattern, newSequence), new String(replacePattern(testString.toCharArray(), pattern.toCharArray(), newSequence.toCharArray())).trim());
    }

    @Test
    public void replaceOrDeletePatternTest() {
        assertEquals(testString.replace("st", "kl"), new String(replaceOrDeletePattern(testString.toCharArray(), "st".toCharArray(), "kl".toCharArray())).trim());
        assertEquals(testString.replace("st", "k"), new String(replaceOrDeletePattern(testString.toCharArray(), "st".toCharArray(), "k".toCharArray())).trim());
        assertEquals(testString.replace("s", "ty"), new String(replaceOrDeletePattern(testString.toCharArray(), "s".toCharArray(), "ty".toCharArray())).trim());
        assertEquals(testString.replace("st", ""), new String(replaceOrDeletePattern(testString.toCharArray(), "st".toCharArray(), null)).trim());
    }

    @Test
    public void replaceOrDeletePatternsTest() {
        List<String> patterns = new ArrayList<String>();
        patterns.add("\"");
        patterns.add("'");
        patterns.add("[]");
        patterns.add("{}");
        patterns.add("zf");
        patterns.add("xc");
        patterns.add("Te");
        patterns.add(null);
        patterns.add("s");
        patterns.add(null);
        String resultString = replaceOrDeletePatterns(testString, patterns);
        assertEquals(testString
                        .replace('"', '\'')
                        .replace("[]", "{}")
                        .replace("zf", "xc")
                        .replaceAll("Te", "")
                        .replaceAll("s", ""),
                resultString);
    }

    @Test
    public void fastStringReplacerTest() {
        FastStringReplacer fastStringReplacer = new FastStringReplacerBuilder()
                .replace("\"", "'")
                .replace("[]", "{}")
                .replace("zf", "xc")
                .delete("Te")
                .delete("s")
                .build();

        String resultString = fastStringReplacer.execute(testString);
        assertEquals(testString
                        .replace('"', '\'')
                        .replace("[]", "{}")
                        .replace("zf", "xc")
                        .replaceAll("Te", "")
                        .replaceAll("s", ""),
                resultString
        );


    }
}
