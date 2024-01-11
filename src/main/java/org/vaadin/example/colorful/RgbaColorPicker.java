package org.vaadin.example.colorful;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import org.vaadin.example.react.ReactAdapterComponent;

import java.util.function.Consumer;

@NpmPackage(value = "react-colorful", version = "5.6.1")
@JsModule("./rgba-color-picker.tsx")
@Tag("rgba-color-picker")
public class RgbaColorPicker extends ReactAdapterComponent {

    public RgbaColorPicker() {
        setColor(new RgbaColor(255, 0, 0, 0.5));
    }

    public RgbaColor getColor() {
        return getState("color", RgbaColor.class);
    }

    public void setColor(RgbaColor color) {
        setState("color", color);
    }

    public void addColorChangeListener(Consumer<RgbaColor> listener) {
        addStateChangeListener("color", RgbaColor.class, listener);
    }
}
