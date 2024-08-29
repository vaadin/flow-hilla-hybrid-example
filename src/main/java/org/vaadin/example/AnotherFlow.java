package org.vaadin.example;

import jakarta.annotation.security.RolesAllowed;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Route("other")
@Menu(title = "Other Flow")
public class AnotherFlow extends Div {

    public AnotherFlow() {
        add(new Paragraph("Other Flow view"));
    }
}
