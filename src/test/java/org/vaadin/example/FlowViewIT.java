
package org.vaadin.example;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import com.vaadin.uitest.common.BasePlayWrightIT;

public class FlowViewIT extends BasePlayWrightIT {

    private static final String BASE_URL = "http://localhost:8080/flow";

    @Override
    public String getUrl() {
        return BASE_URL;
    }

    static String regex = BASE_URL + "(\\?continue)?$";
    static Pattern pattern = Pattern.compile(regex);

    @BeforeEach
    public void setupTest() throws Exception {
        super.setupTest();
        // Login steps
        fill(page.locator("vaadin-login-form vaadin-text-field"), "admin");
        fill(page.locator("vaadin-login-form vaadin-password-field"), "admin");
        click(page.locator("vaadin-login-form vaadin-button"));
        page.waitForURL(pattern);
        page.waitForSelector("vaadin-button");
    }


    @Test
    public void clickingButtonShowsNotification() throws Exception {
        assertFalse(page.locator("vaadin-vertical-layout p").count() > 0);
        page.getByLabel("Your name").click();
        click(page.locator("vaadin-vertical-layout vaadin-button").first());
        assertEquals(1, page.locator("p").count());
    }

    @Test
    public void clickingButtonTwiceShowsTwoNotifications() {
        assertFalse(page.locator("vaadin-vertical-layout p").count() > 0);
        page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Say hello")).click();
        page.locator("vaadin-vertical-layout p").nth(0).waitFor();
        page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Say hello")).click();
        page.locator("vaadin-vertical-layout p").nth(1).waitFor();
        assertEquals(2, page.locator("vaadin-vertical-layout p").count());
    }

    @Test
    public void testClickButtonShowsHelloAnonymousUserNotificationWhenUserNameIsEmpty() {
        click(page.locator("vaadin-vertical-layout vaadin-button").first());
        assertTrue(page.locator("vaadin-vertical-layout p").textContent().contains("Hello anonymous user"));
    }

    @Test
    public void testClickButtonShowsHelloUserNotificationWhenUserIsNotEmpty() {
        page.waitForSelector("vaadin-vertical-layout vaadin-text-field");
        fill(page.locator("vaadin-vertical-layout vaadin-text-field").first(), "Vaadiner");
        click(page.locator("vaadin-vertical-layout vaadin-button").first());
        assertTrue(page.locator("vaadin-vertical-layout p").textContent().contains("Hello Vaadiner"));
    }

    @Test
    public void userEntersNameAndClicksButton() throws Exception {
        // Given the user is on the page HelloView
        page.waitForSelector("vaadin-vertical-layout vaadin-text-field");
        page.waitForSelector("vaadin-vertical-layout vaadin-button");

        // And the user has entered 'Jane Smith' in the text field with label 'Your
        // name'
        fill(page.locator("vaadin-vertical-layout vaadin-text-field").first(), "Jane Smith");

        // When the user clicks on the button with label 'Say hello'
        click(page.locator("vaadin-vertical-layout vaadin-button").first());

        // Then a paragraph with text 'Hello, Jane Smith' should appear
        page.waitForSelector("vaadin-vertical-layout p");
        assertTrue(page.locator("vaadin-vertical-layout p").textContent().contains("Jane Smith"));
    }
}
