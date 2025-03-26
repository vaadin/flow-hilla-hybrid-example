
package org.vaadin.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class FlowViewIT  {

    private static final String BASE_URL = "http://localhost:8080/flow";
    Page page;

    @BeforeEach
    public void setupTest() throws Exception {
        page = Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)).newContext().newPage();
        page.setDefaultTimeout(30000);
        page.navigate("http://localhost:8080/flow");
        page.locator("vaadin-login-form vaadin-text-field input").fill("admin");
        page.locator("vaadin-login-form vaadin-password-field input").fill("admin");
        page.locator("vaadin-login-form vaadin-button").first().click();
        page.waitForURL(BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        page.close();
    }

    @Test
    public void clickingButtonShowsNotification() throws Exception {
        page.getByLabel("Your name").click();
        page.locator("vaadin-vertical-layout vaadin-button").first().click();
        page.waitForSelector("vaadin-vertical-layout p");
        page.locator("vaadin-vertical-layout p").nth(0).waitFor();
        assertEquals(1, page.locator("vaadin-vertical-layout p").count());
    }

    @Test
    public void clickingButtonTwiceShowsTwoNotifications() {
        page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Say hello")).click();
        page.locator("vaadin-vertical-layout p").nth(0).waitFor();
        page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Say hello")).click();
        page.locator("vaadin-vertical-layout p").nth(1).waitFor();
        assertEquals(2, page.locator("vaadin-vertical-layout p").count());
    }

    @Test
    public void testClickButtonShowsHelloAnonymousUserNotificationWhenUserNameIsEmpty() {
        page.locator("vaadin-vertical-layout vaadin-button").first().click();;
        assertTrue(page.locator("vaadin-vertical-layout p").textContent().contains("Hello anonymous user"));
    }

    @Test
    public void testClickButtonShowsHelloUserNotificationWhenUserIsNotEmpty() {
        page.waitForSelector("vaadin-vertical-layout vaadin-text-field");
        page.locator("vaadin-vertical-layout vaadin-text-field input").first().fill("Vaadiner");
        page.locator("vaadin-vertical-layout vaadin-button").first().click();
        assertTrue(page.locator("vaadin-vertical-layout p").textContent().contains("Hello Vaadiner"));
    }

    @Test
    public void userEntersNameAndClicksButton() throws Exception {
        // Given the user is on the page HelloView
        page.waitForSelector("vaadin-vertical-layout vaadin-text-field");
        page.waitForSelector("vaadin-vertical-layout vaadin-button");

        // And the user has entered 'Jane Smith' in the text field with label 'Your
        // name'
        page.locator("vaadin-vertical-layout vaadin-text-field input").first().fill("Jane Smith");

        // When the user clicks on the button with label 'Say hello'
        page.locator("vaadin-vertical-layout vaadin-button").first().click();

        // Then a paragraph with text 'Hello, Jane Smith' should appear
        page.waitForSelector("vaadin-vertical-layout p");
        assertTrue(page.locator("vaadin-vertical-layout p").textContent().contains("Jane Smith"));
    }
}
