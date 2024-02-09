package org.vaadin.example;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializableRunnable;
import com.vaadin.flow.server.VaadinRequest;

public class MyFlowComponent extends Div {

    private TextField userName = new TextField();
    private PasswordField password =
            new PasswordField();
    private Div errorMsg = new Div();
    private String userLabel;
    private String pwdLabel;
    private FormLayout layout = new FormLayout();
    private List<SerializableRunnable> loginListeners =
            new CopyOnWriteArrayList<>();
    String helloMessage;

    public MyFlowComponent() {
        updateForm();

        add(layout);

        Button login = new Button("Login",
                event -> login());
        login.addClickShortcut(Key.ENTER);
        add(login, errorMsg);
    }

    public void setHelloMessage(String helloMessage) {
        this.helloMessage = helloMessage;

    }

    public void setUserNameLabel(
            String userNameLabelString) {
        userLabel = userNameLabelString;
        updateForm();
    }

    public void setPasswordLabel(String pwd) {
        pwdLabel = pwd;
        updateForm();
    }

    public void updateForm() {
        layout.removeAll();

        layout.addFormItem(userName, userLabel);
        layout.addFormItem(password, pwdLabel);
    }

    public void addLoginListener(
            SerializableRunnable loginListener) {
        loginListeners.add(loginListener);
    }

    private void login() {
        if (userName.getValue().equals("user") &&
        password.getValue().equals("user")) {
            errorMsg.setText("logged in");
            fireLoginEvent();
        } else {
            errorMsg.setText("Authentication failure");
        }
    }

    private void fireLoginEvent() {
        loginListeners.forEach(
                SerializableRunnable::run);
    }
}
