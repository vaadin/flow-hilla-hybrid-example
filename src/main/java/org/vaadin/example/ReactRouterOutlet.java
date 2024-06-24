package org.vaadin.example;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.react.ReactAdapterComponent;
import com.vaadin.flow.router.Route;

@Tag("react-router-outlet")
@JsModule("./ReactRouterOutlet.tsx")
public class ReactRouterOutlet extends ReactAdapterComponent {
}
