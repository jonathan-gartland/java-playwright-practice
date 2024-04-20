import com.microsoft.playwright.*;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.util.Arrays.asList;

public class WdioTestCase {
    @Test
    public void testCase1() {
        System.out.println("in test case 1 of WdioTestngClass");
        String name = "joeJumpinJolly";
        assert name.equals("joeJumpinJolly") == true;
        assert 1 == 1;
    }
}
