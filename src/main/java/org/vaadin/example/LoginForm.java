package org.vaadin.example;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializableRunnable;

public class LoginForm extends Div {
    private TextField userName = new TextField();
    private PasswordField password =
            new PasswordField();
    private Div errorMsg = new Div();
    private String userLabel;
    private String pwdLabel;
    private FormLayout layout = new FormLayout();
    private List<SerializableRunnable> loginListeners =
            new CopyOnWriteArrayList<>();

    public LoginForm() {
        updateForm();

        add(layout);

        Button login = new Button("Login",
                event -> login());
        add(login, errorMsg);
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
        System.out.println("Login");
        fireLoginEvent();
    }

    private void fireLoginEvent() {
        loginListeners.forEach(
                SerializableRunnable::run);
    }
}
