package org.vaadin.example;

import jakarta.annotation.security.RolesAllowed;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H3;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Flow view that is available only for users with role 'ADMIN'
 */
@RolesAllowed("USER")
@Menu(title = "Flow admin", icon = "vaadin:tools")
@Route("flow")
@PageTitle("Flow Admin page")
public class FlowView extends VerticalLayout {

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        Notification.show("OnDetach");
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        Notification.show("OnAttach");
    }

    public FlowView(GreetService service) {

        // Use TextField for standard text input
        TextField textField = new TextField("Your name");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello", e -> {
            add(new Paragraph(service.greet(textField.getValue())));
        });
        button.setId("say-hello");

        add(new H3("Flow Admin View"), textField, button);

        add(new PickerComponent());
    }

}
