package org.vaadin.example.vaadin;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import org.vaadin.example.react.ReactAdapterComponent;

@JsModule("./react-button.tsx")
@Tag("react-button")
public class ReactButton extends ReactAdapterComponent {
    public ReactButton(String text, Runnable action) {
        setText(text);
        addClickListener(action);
    }

    public String getText() {
        // NOTE: Flow throws for "textContent" property
        return getState("text", String.class);
    }

    public void setText(String text) {
        setState("text", text);
    }

    public void addClickListener(Runnable action) {
        getElement().addEventListener("custom-click", (event -> {
            action.run();
        }));
    }
}
