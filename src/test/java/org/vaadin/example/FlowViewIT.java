package org.vaadin.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.ParagraphElement;
import com.vaadin.flow.component.login.testbench.LoginOverlayElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.BrowserTest;
import com.vaadin.testbench.BrowserTestBase;

public class FlowViewIT extends BrowserTestBase {

    private static final String SAY_HELLO_BUTTON_ID = "say-hello";

    /**
     * If running on CI, get the host name from environment variable HOSTNAME
     *
     * @return the host name
     */
    private static String getDeploymentHostname() {
        String hostname = System.getenv("HOSTNAME");
        if (hostname != null && !hostname.isEmpty()) {
            return hostname;
        }
        return "localhost";
    }

    protected String getPath() {
        return "/flow";
    }

    @BeforeEach
    public void open() {
        getDriver().get("http://" + getDeploymentHostname() + ":8080" + getPath());
        login();
    }

    @BrowserTest
    public void clickingButtonShowsNotification() throws Exception {
        Assertions.assertFalse($(ParagraphElement.class).exists());
        waitForElementPresent(By.id(SAY_HELLO_BUTTON_ID));
        $(ButtonElement.class).id(SAY_HELLO_BUTTON_ID).click();
        $(ParagraphElement.class).waitForFirst();
        Assertions.assertTrue($(ParagraphElement.class).exists());
    }

    @BrowserTest
    public void clickingButtonTwiceShowsTwoNotifications() {
        Assertions.assertFalse($(ParagraphElement.class).exists());
        waitForElementPresent(By.id(SAY_HELLO_BUTTON_ID));
        ButtonElement button = $(ButtonElement.class).id(SAY_HELLO_BUTTON_ID);
        button.click();
        $(ParagraphElement.class).waitForFirst();
        button.click();
        Assertions.assertEquals(2, $(ParagraphElement.class).all().size());
    }

    @BrowserTest
    public void testClickButtonShowsHelloAnonymousUserNotificationWhenUserNameIsEmpty() {
        waitForElementPresent(By.id(SAY_HELLO_BUTTON_ID));
        ButtonElement button = $(ButtonElement.class).id(SAY_HELLO_BUTTON_ID);
        button.click();
        ParagraphElement msg = $(ParagraphElement.class).waitForFirst();
        Assertions.assertEquals("Hello anonymous user", msg.getText());
    }

    @BrowserTest
    public void testClickButtonShowsHelloUserNotificationWhenUserIsNotEmpty() {
        waitForElementPresent(By.id(SAY_HELLO_BUTTON_ID));
        TextFieldElement textField = $(TextFieldElement.class).waitForFirst();
        textField.setValue("Vaadiner");
        ButtonElement button = $(ButtonElement.class).id(SAY_HELLO_BUTTON_ID);
        button.click();
        ParagraphElement msg = $(ParagraphElement.class).waitForFirst();
        Assertions.assertEquals("Hello Vaadiner", msg.getText());
    }

    @BrowserTest
    public void testEnterShowsHelloUserNotificationWhenUserIsNotEmpty() {
        waitForElementPresent(By.id(SAY_HELLO_BUTTON_ID));
        TextFieldElement textField = $(TextFieldElement.class).waitForFirst();
        textField.setValue("Vaadiner");
        ButtonElement button = $(ButtonElement.class).id(SAY_HELLO_BUTTON_ID);
        button.click();
        ParagraphElement msg = $(ParagraphElement.class).waitForFirst();
        Assertions.assertEquals("Hello Vaadiner", msg.getText());
    }

    protected void login() {
        login("admin", "admin");
    }

    protected void login(String user, String password) {
        LoginOverlayElement loginOverlay = $(LoginOverlayElement.class)
                .waitForFirst();
        loginOverlay.getUsernameField().setValue(user);
        loginOverlay.getPasswordField().setValue(password);
        loginOverlay.submit();
    }
}
