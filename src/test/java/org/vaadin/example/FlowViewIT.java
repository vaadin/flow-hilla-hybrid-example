
package org.vaadin.example;

import com.vaadin.uitest.common.BasePlayWrightIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlowViewIT extends BasePlayWrightIT {

    @Override
    public String getUrl() {
        return "http://localhost:8080/flow";
    }

    // Scenario: Initial state of FlowView
    @Test
    public void initialStateOfFlowView() throws Exception {
        // Given the user is on the page FlowView
        page.waitForSelector("vaadin-app-layout");

        // Then the user should see an app layout with tag name 'vaadin-app-layout'
        Assertions.assertTrue(page.locator("vaadin-app-layout").count() > 0);

        // And a button with role 'button' and label 'Say hello'
        Assertions.assertTrue(page.locator("button[role='button'][aria-label='Say hello']").count() > 0);

        // And a text field with role 'textbox' and label 'Your name'
        Assertions.assertTrue(page.locator("input[role='textbox'][aria-label='Your name']").count() > 0);
    }

    // Scenario: User clicks on the 'Say hello' button
    @Test
    public void userClicksOnTheSayHelloButton() throws Exception {
        // Given the user is on the page FlowView
        page.waitForSelector("vaadin-app-layout");

        // And the user has entered 'Jane Smith' in the text field with role 'textbox'
        // and label 'Your name'
        fill(page.locator("input[role='textbox'][aria-label='Your name']"), "Jane Smith");

        // When the user clicks on the button with role 'button' and label 'Say hello'
        click(page.locator("button[role='button'][aria-label='Say hello']"));

        // Then a notification with tag name 'vaadin-notification' and text 'Hello, Jane
        // Smith' should appear
        page.waitForSelector("vaadin-notification");
        Assertions.assertTrue(page.locator("vaadin-notification").innerText().contains("Hello, Jane Smith"));
    }

    // Additional scenarios would be implemented here following the same pattern.
    // For example:
    // @Test
    // @Disabled
    // public void additionalScenario() throws Exception {
    // // Given ...
    // // When ...
    // // Then ...
    // }
}
