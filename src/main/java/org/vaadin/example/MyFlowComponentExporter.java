package org.vaadin.example;

import com.vaadin.flow.component.WebComponentExporter;
import com.vaadin.flow.component.webcomponent.WebComponent;

public class MyFlowComponentExporter
        extends WebComponentExporter<MyFlowComponent> {

    public MyFlowComponentExporter() {
        super("my-flow-component");
    }

    @Override
    protected void configureInstance(WebComponent<MyFlowComponent> webComponent,
                                     MyFlowComponent component) {
    }
}
