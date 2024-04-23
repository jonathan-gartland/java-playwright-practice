import com.microsoft.playwright.*;
import org.testng.annotations.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class MpnLookupTestCase {
    // Shared between all tests in this class.
    Playwright playwright;
    Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @BeforeClass
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterClass
    void closeBrowser() {
        playwright.close();
    }

    @BeforeMethod
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
        page.navigate("http://localhost:3000");
    }

    @AfterMethod
    void closeContext() {
        context.close();
    }

    @Test
    public void MpnLookupPageTitle() {
        Locator titleLabel = page.getByTestId("mpn-lookup-title");
        assertThat(titleLabel).containsText("MPN Lookup");
    }

    @Test
    public void AboutSection() {
        Locator qtInfoLink = page.getByTestId("qtlink");
        qtInfoLink.click();
        page.waitForURL("**/quanti-tray-system/", new Page.WaitForURLOptions().setTimeout(5000));
        Locator qtInfoHeader = page.locator("text=Take the guesswork out of bacterial counts");
        assertThat(qtInfoHeader).isVisible();
        page.goBack(new Page.GoBackOptions().setTimeout(5000));
        assertThat(qtInfoLink).isVisible();
    }

    @Test
    public void MpnGeneratorPageLinks() {
        Locator mpnGeneratorLink = page.getByTestId("mpngen");
        mpnGeneratorLink.click();
        page.waitForURL("**/mpn-generator/", new Page.WaitForURLOptions().setTimeout(5000));
        Locator mpnGeneratorHeader = page.locator("text=MPN Generator MPN Generator Software");
        assertThat(mpnGeneratorHeader).isVisible();
        page.goBack(new Page.GoBackOptions().setTimeout(5000));
        assertTrue(mpnGeneratorLink.isVisible());
    }

    @Test
    public void TestMpnSpa() {
        Locator inputWellsPos = page.locator("#qtinput");
        assertTrue(inputWellsPos.isVisible());
        int posWellCount = Integer.parseInt(inputWellsPos.inputValue());
        assertEquals(posWellCount, 0);
        String qtMpnVal = page.getByTestId("qt-mpn-val").innerText();
        Dictionary<Integer, Double> mpnValues = new Hashtable<Integer, Double>();
        mpnValues.put(51, 146.1);
        mpnValues.put(1, 0.3);
        mpnValues.put(18, 14.1);
        mpnValues.put(33, 37.5);

        Enumeration<Integer> keys = mpnValues.keys();
        while (keys.hasMoreElements()) {
            Integer key = keys.nextElement();
            inputWellsPos.fill(Integer.toString(key));
            Double newMpnVal = Double.valueOf(page.getByTestId("qt-mpn-val").innerText().split(":")[1]);
            assertEquals(newMpnVal, mpnValues.get(key));
        }
        inputWellsPos.fill("");
        String emptyMessage = page.getByTestId("empty-value-label").innerText();
        assertEquals(emptyMessage, "Enter valid value please!");


    }
}
