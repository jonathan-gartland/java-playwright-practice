import com.microsoft.playwright.*;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NewTestCase {

    @Test
    public void testCase1() {
        System.out.println("in test case 1 of NewTestngClass");
        String name = "joeJumpinJolly";
        assert name.equals("joeJumpinJolly");
    }

    @Test
    public void testPlaywright() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setIsMobile(true)
                    .setHasTouch(true)
                    .setLocale("en-US")
                    .setGeolocation(41.889938, 12.492507)
                    .setPermissions(List.of("geolocation")));
            context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
            Page page = context.newPage();
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
            // page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("colosseum-pixel2.png")));
        }
        catch (Exception e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }
}  