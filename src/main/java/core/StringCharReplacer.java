package core;

public class StringCharReplacer {
    private StringCharReplacer(){}
    public static String convertComponentInputToTemplateValue(String componentInputContent) {
        char[] testCharArray = componentInputContent.toCharArray();
        char[] result = new char[testCharArray.length];
        for (int i = 0; i < testCharArray.length; i++) {
            switch (testCharArray[i]) {
                case '"':
                    result[i] = '\'';
                    break;
                case '\n':
                    break;
                case '[':
                    if (i < testCharArray.length - 1 && testCharArray[i + 1] == ']') {
                        result[i] = '{';
                        result[i + 1] = '}';
                        i++;
                    } else {
                        result[i] = testCharArray[i];
                    }
                    break;
                default:
                    result[i] = testCharArray[i];
            }
        }
        return new String(result).trim();
    }
}
