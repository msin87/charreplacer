package core;

import java.util.Collections;
import java.util.List;

public class FastStringReplacer {
    private final List<String> oldSequenceList;
    private final List<String> newSequenceList;
    private final List<String> deleteSequenceList;
    private int inputIndex = 0;
    private int outputIndex = 0;

    public FastStringReplacer(List<String> oldSequenceList, List<String> newSequenceList, List<String> deleteSequenceList) {
        this.oldSequenceList = oldSequenceList == null ? Collections.<String>emptyList() : oldSequenceList;
        this.newSequenceList = newSequenceList == null ? Collections.<String>emptyList() : newSequenceList;
        this.deleteSequenceList = deleteSequenceList == null ? Collections.<String>emptyList() : deleteSequenceList;
        if ((this.oldSequenceList.size() != this.newSequenceList.size()))
            throw new IllegalStateException("FastStringReplacer: sequence lists or pattern lists are not the same size");
    }

    private void replacer(char[] targetCharArray, char[] resultCharArray) {
        OLD_SEQ_LOOP:
        for (int oldSeqIndex = 0; oldSeqIndex < oldSequenceList.size(); oldSeqIndex++) {
            String oldSequence = oldSequenceList.get(oldSeqIndex);
            if (oldSequence.length() > 1) {
                if (inputIndex < targetCharArray.length - (oldSequence.length() - 1)) {
                    char[] oldSeqCharArray = oldSequence.toCharArray();
                    boolean skipLoop = true;
                    for (int oldSeqCharIndex = 0; oldSeqCharIndex < oldSeqCharArray.length; oldSeqCharIndex++) {
                        skipLoop &= targetCharArray[inputIndex + oldSeqCharIndex] != oldSeqCharArray[oldSeqCharIndex];
                    }
                    if (!skipLoop) {
                        char[] newSeqCharArray = newSequenceList.get(oldSeqIndex).toCharArray();
                        System.arraycopy(newSeqCharArray, 0, resultCharArray, outputIndex, newSeqCharArray.length);
                        inputIndex += newSeqCharArray.length - 1;
                        outputIndex += newSeqCharArray.length - 1;
                    }
                    else {
                        for (int oldSeqCharIndex = 0; oldSeqCharIndex < oldSeqCharArray.length; oldSeqCharIndex++){
                            resultCharArray[outputIndex] = targetCharArray[inputIndex];
                            inputIndex++;
                            outputIndex++;
                        }
                    }
                }
            } else {
                if (targetCharArray[inputIndex] == oldSequence.toCharArray()[0]) {
                    resultCharArray[outputIndex] = newSequenceList.get(oldSeqIndex).toCharArray()[0];
                    break;
                }
            }
        }
    }

    private void deleter(char[] targetCharArray, char[] resultCharArray) {
        DEL_SEQ_LOOP:
        for (int delSeqIndex = 0; delSeqIndex < deleteSequenceList.size(); delSeqIndex++) {
            String delSequence = deleteSequenceList.get(delSeqIndex);
            if (delSequence.length() > 1) {
                if (inputIndex < targetCharArray.length - (delSequence.length() - 1)) {
                    char[] delSeqCharArray = delSequence.toCharArray();
                    for (int delSeqCharIndex = 0; delSeqCharIndex < delSeqCharArray.length; delSeqCharIndex++) {
                        if (targetCharArray[inputIndex + delSeqCharIndex] != delSeqCharArray[delSeqCharIndex])
                            continue DEL_SEQ_LOOP;
                    }
                    inputIndex += delSeqCharArray.length;
                }
            } else {
                if (targetCharArray[inputIndex] == delSequence.toCharArray()[0])
                    inputIndex++;
            }
        }
    }

    public String execute(String targetString) {
        char[] targetCharArray = targetString.toCharArray();
        char[] resultCharArray = new char[targetCharArray.length];
        inputIndex = 0;
        outputIndex = 0;
        for (inputIndex = 0, outputIndex = 0; inputIndex < targetCharArray.length; inputIndex++, outputIndex++) {
            deleter(targetCharArray, resultCharArray);
            replacer(targetCharArray, resultCharArray);
        }
        return new String(resultCharArray).trim();
    }
}
