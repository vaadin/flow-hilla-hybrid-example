
package org.vaadin.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import com.vaadin.uitest.common.BasePlayWrightIT;

public class FlowViewIT extends BasePlayWrightIT {

    @Override
    public String getUrl() {
        return "http://localhost:8080/flow";
    }

    @BeforeEach
    public void setupTest() throws Exception {
        super.setupTest();
        // Login steps
//        fill(page.locator("vaadin-text-field"), "user");
//        fill(page.locator("vaadin-password-field"), "user");
//        click(page.locator("vaadin-button"));
//        page.waitForURL(getUrl());
    }


    @Test
    public void clickingButtonShowsNotification() throws Exception {
        assertFalse(page.locator("p").count() > 0);
        page.getByLabel("Your name").click();
        click(page.locator("vaadin-button").first());
        assertEquals(1, page.locator("p").count());
    }

    @Test
    public void clickingButtonTwiceShowsTwoNotifications() {
        assertFalse(page.locator("p").count() > 0);
        page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Say hello")).click();
        page.locator("p").nth(0).waitFor();
        page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Say hello")).click();
        page.locator("p").nth(1).waitFor();
        assertEquals(2, page.locator("p").count());
    }

    @Test
    public void testClickButtonShowsHelloAnonymousUserNotificationWhenUserNameIsEmpty() {
        click(page.locator("vaadin-button").first());
        assertTrue(page.locator("p").textContent().contains("Hello anonymous user"));
    }

    @Test
    public void testClickButtonShowsHelloUserNotificationWhenUserIsNotEmpty() {
        fill(page.locator("vaadin-text-field").first(), "Vaadiner");
        click(page.locator("vaadin-button").first());
        assertTrue(page.locator("p").textContent().contains("Hello Vaadiner"));
    }

    @Test
    public void userEntersNameAndClicksButton() throws Exception {
        // Given the user is on the page HelloView
        page.waitForSelector("vaadin-text-field");
        page.waitForSelector("vaadin-button");

        // And the user has entered 'Jane Smith' in the text field with label 'Your
        // name'
        fill(page.locator("vaadin-text-field").first(), "Jane Smith");

        // When the user clicks on the button with label 'Say hello'
        click(page.locator("vaadin-button").first());

        // Then a paragraph with text 'Hello, Jane Smith' should appear
        page.waitForSelector("p");
        assertTrue(page.locator("p").textContent().contains("Jane Smith"));
    }
}
