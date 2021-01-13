import core.StringCharReplacer;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConverterTest {
    @Test
    public void convertComponentInputToTemplateValueTest(){
        String testString = "Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]";
        String resultString = StringCharReplacer.convertComponentInputToTemplateValue(testString);
        assertFalse(resultString.contains("\n"));
        assertFalse(resultString.contains("\""));
        assertFalse(resultString.contains("[]"));
        assertTrue(resultString.contains("'"));
        assertTrue(resultString.contains("{}"));
    }
}
