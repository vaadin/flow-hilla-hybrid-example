package org.vaadin.example;

import com.vaadin.flow.component.WebComponentExporter;
import com.vaadin.flow.component.webcomponent.WebComponent;

public class MyFlowComponentExporter
        extends WebComponentExporter<MyFlowComponent> {

    public static final String TAG = "my-flow-component";

    public MyFlowComponentExporter() {
        super(TAG);
        addProperty("userlbl", "")
                .onChange(MyFlowComponent::setUserNameLabel);
        addProperty("pwdlbl", "")
                .onChange(MyFlowComponent::setPasswordLabel);
        addProperty("hellomsg", "Hello!")
                .onChange(MyFlowComponent::setHelloMessage);
    }

    @Override
    protected void configureInstance(WebComponent<MyFlowComponent> webComponent,
                                     MyFlowComponent component) {
        component.addLoginListener(() ->
                webComponent.fireEvent("logged-in"));
    }
}
