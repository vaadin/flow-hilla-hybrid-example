package org.vaadin.example.vaadin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import org.vaadin.example.react.ReactAdapterComponent;

import java.util.List;

@JsModule("./react-grid.tsx")
@Tag("react-grid")
public class ReactGrid<T> extends ReactAdapterComponent {
    public ReactGrid(List<T> items) {
        addClassNames("block", "w-full");
        setItems(items);
    }

    public List<T> getItems() {
        return getState("items", new TypeReference<List<T>>() {
        });
    }

    public void setItems(List<T> items) {
        setState("items", items);
    }
}
