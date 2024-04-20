import com.microsoft.playwright.*;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.util.Arrays.asList;

public class NewTestngClass {

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
//      BrowserContext context = browser.newContext(new Browser.NewContextOptions()
//        .setUserAgent("Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3765.0 Mobile Safari/537.36")
//        .setViewportSize(411, 731)
//        .setDeviceScaleFactor(2.625)
//        .setIsMobile(true)
//        .setHasTouch(true)
//        .setLocale("en-US")
//        .setGeolocation(41.889938, 12.492507)
//        .setPermissions(asList("geolocation")));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    //.setUserAgent("Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3765.0 Mobile Safari/537.36")
                    //.setDeviceScaleFactor(2.625)
                    //.setViewportSize(411, 731)
                    .setIsMobile(true)
                    .setHasTouch(true)
                    .setLocale("en-US")
                    .setGeolocation(41.889938, 12.492507)
                    .setPermissions(asList("geolocation")));
            Page page = context.newPage();
            page.navigate("https://www.openstreetmap.org/");
            Locator closePopup = page.locator("#sidebar > div.welcome.p-3 > div:nth-child(1) > div > button");
            closePopup.click();
            Locator userDiaryLink = page.locator("body > header > nav.secondary > ul > li:nth-child(2) > a");
            userDiaryLink.click();
            Locator titleH1 = page.locator("#content > div.content-heading.bg-body-secondary.border-bottom.border-secondary-subtle > div > div > div > h1");
            assertThat(titleH1).containsText("Users' Diaries");
            assertThat(page).hasTitle("Users' Diaries | OpenStreetMaps");
            // assertThat(page.url()).isEqualTo("https://www.openstreetmap.org/diary");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("colosseum-pixel2.png")));
        }
    }

}  