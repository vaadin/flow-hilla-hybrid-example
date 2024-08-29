package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;

public class MyFlowComponent extends Div {

    private String greetings;

    public MyFlowComponent() {
        add(new Paragraph("This is embedded component"));
        Button button = new Button("Say hello", event ->
                Notification.show(greetings));
        add(button);
    }

    public void setGreetings(String greetings) {
        this.greetings = greetings;
    }
}


