package com.serenitydojo.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

public class PlaywrightLocatorsTest {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;

    Page page;

    @BeforeAll
    static void setUpBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
                        .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"))
        );
    }

    @BeforeEach
    void setUp() {
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @AfterEach
    void closeContext() {
        browserContext.close();
    }

    @AfterAll
    static void tearDown() {
        browser.close();
        playwright.close();
    }

    @DisplayName("Locating elements using CSS")
    @Nested
    class LocatingElementsUsingCSS {

        @BeforeEach
        void openContactPage() {
            page.navigate("https://practicesoftwaretesting.com/contact");
        }

        @Test
        void locateTheFirstNameFieldByID() {
            page.locator("#first_name").fill("Obi-Wan");
            PlaywrightAssertions.assertThat(page.locator("#first_name")).hasValue("Obi-Wan");
        }

        @Test
        void locateTheSendButtonByCssClass() {
            page.locator("#first_name").fill("Obi-Wan");
            page.locator(".btnSubmit").click();
            List<String> alertMessages = page.locator(".alert").allTextContents();
            Assertions.assertFalse(alertMessages.isEmpty());
        }

        @Test
        void locateTheSendButtonByAttribute()  {
            page.locator("input[placeholder='Your last name *']").fill("Kenobi");
            PlaywrightAssertions.assertThat(page.locator("#last_name")).hasValue("Kenobi");
        }

    }

}
