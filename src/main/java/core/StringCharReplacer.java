package core;

public class StringCharReplacer {
    private StringCharReplacer(){}
    public static String convertComponentInputToTemplateValue(String componentInputContent) {
        char[] testCharArray = componentInputContent.toCharArray();
        char[] result = new char[testCharArray.length];
        for (int inputIndex = 0, outputIndex = 0; inputIndex < testCharArray.length; inputIndex++, outputIndex++) {
            switch (testCharArray[inputIndex]) {
                case '"':
                    result[outputIndex] = '\'';
                    break;
                case '\n':
                    outputIndex--;
                    break;
                case '[':
                    if (inputIndex < testCharArray.length - 1 && testCharArray[inputIndex + 1] == ']') {
                        result[outputIndex] = '{';
                        result[outputIndex + 1] = '}';
                        inputIndex++;
                        outputIndex++;
                    } else {
                        result[outputIndex] = testCharArray[inputIndex];
                    }
                    break;
                default:
                    result[outputIndex] = testCharArray[inputIndex];
            }
        }
        return new String(result).trim();
    }
}
