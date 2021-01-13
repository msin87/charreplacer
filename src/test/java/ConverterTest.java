import core.StringCharReplacer;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConverterTest {
    String testString = "Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]";
    @Test
    public void convertComponentInputToTemplateValueTest(){
        String resultString = StringCharReplacer.forLoopReplace(testString);
        assertFalse(resultString.contains("\n"));
        assertFalse(resultString.contains("\""));
        assertFalse(resultString.contains("[]"));
        assertTrue(resultString.contains("'"));
        assertTrue(resultString.contains("{}"));
    }

    @Test
    public void compareReplaceAllWithReplace() {
        assertEquals(testString
                        .replaceAll("\"", "'")
                        .replaceAll("\n", "")
                        .replace("[]", "{}"),
                StringCharReplacer.forLoopReplace(testString));
    }
}
