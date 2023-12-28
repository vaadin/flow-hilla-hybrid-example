package org.vaadin.example.colorful;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import dev.hilla.Endpoint;
import jakarta.annotation.security.DenyAll;

@NpmPackage(value = "react-colorful", version = "5.6.1")
@JsModule("./rgba-color-picker.tsx")
@Tag("rgba-color-picker")
@Endpoint
@DenyAll
public class RgbaColorPicker extends ReactAdapterComponent<RgbaColorPicker, RgbaColorPickerHTMLAttributes, RgbaColor> {

    public RgbaColorPicker() {
        super(new RgbaColorPickerHTMLAttributes().className("flow-color-picker"), new RgbaColor(255, 0, 0, 0.5));
    }

}
