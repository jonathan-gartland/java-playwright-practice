import com.microsoft.playwright.*;
import org.testng.annotations.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NewTestCase {

    Playwright playwright;
    Browser browser;

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
    }

    @AfterMethod
    void closeContext() {
        context.close();
    }

    @Test
    public void testCase1() {
        System.out.println("in test case 1 of NewTestCase");
        assert 1 == 1;
    }

    @Test
    public void testPlaywright() {
        context = browser.newContext(new Browser.NewContextOptions().setIsMobile(true)
                .setHasTouch(true).setLocale("en-US").setGeolocation(41.889938, 12.492507)
                .setPermissions(List.of("geolocation")));
            context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
            page = context.newPage();
            page.navigate("https://www.openstreetmap.org/");
            Locator closePopup = page.locator("#sidebar > div.welcome.p-3 > div:nth-child(1) > div > button");
            closePopup.click();
            Locator userDiaryLink = page.locator("body > header > nav.secondary > ul > li:nth-child(2) > a");
            userDiaryLink.click();
            Locator titleH1 = page.locator("#content > div.content-heading.bg-body-secondary.border-bottom.border-secondary-subtle > div > div > div > h1");
            assertThat(titleH1).containsText("Users' Diaries");
            assertThat(page).hasTitle("Users' Diaries | OpenStreetMap");
            assertThat(page).hasURL("https://www.openstreetmap.org/diary");
            context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("trace.zip")));
        }
}  