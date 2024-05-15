package org.vaadin.example;

import com.vaadin.flow.server.auth.AnonymousAllowed;

import com.vaadin.flow.component.html.H3;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@AnonymousAllowed
@Menu(title = "Flow admin", icon = "vaadin:tools")
@Route("flow")
public class FlowView extends VerticalLayout {

    public FlowView(GreetService service) {

        GridLayout gridLayout = new GridLayout();
        add(gridLayout);

        // Use TextField for standard text input
        TextField textField = new TextField("Your name");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello", e -> {
            gridLayout.add(new Paragraph(service.greet(textField.getValue())));
        });
        button.setId("say-hello");

        gridLayout.add(new H3("Flow Admin View"), textField, button);
    }

}
