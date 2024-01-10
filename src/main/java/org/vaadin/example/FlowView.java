package org.vaadin.example;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("flow")
public class FlowView extends VerticalLayout {
    public FlowView() {
        add("Flow View");
    }
}
