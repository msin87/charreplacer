import core.FastStringReplacer;
import core.FastStringReplacerBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class FastStringReplacerTest {
    //String testString = "Test\n[]eTezf[]\"as\"[]d[]zx.\"Test2\"[sdeTesdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]Test\n[]asd.\"Test2\"[sdsdg].\"\".[\"s\"]";
    String testString = "Test\n[]eh";

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
