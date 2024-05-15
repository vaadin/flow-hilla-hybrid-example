package org.vaadin.example;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

import java.util.Arrays;

@JsModule("./components/grid-layout/GridLayout.tsx")
@Tag("grid-layout")
public class GridLayout extends ReactAdapterComponent {

    public GridLayout(Component... components) {
        add(components);
    }

    public void add(Component ...components) {
        Arrays.stream(components).forEach(this::add);
    }

    public void add(Component components) {
        getContentElement("content").appendChild(components.getElement());
    }
}
