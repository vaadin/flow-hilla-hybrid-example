package org.vaadin.example.colorful;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.internal.JsonUtils;
import com.vaadin.flow.shared.Registration;

import dev.hilla.BrowserCallable;
import dev.hilla.Endpoint;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

@NpmPackage(value = "react-colorful", version = "5.6.1")
@JsModule("./rgba-color-picker.tsx")
@Tag("rgba-color-picker")
public class RgbaColorPicker extends AbstractSinglePropertyField<RgbaColorPicker, RgbaColor> {

    public RgbaColorPicker() {
        super("state",
                new RgbaColor(255,0,0, 0.5),
                JsonObject.class, (jsonObject) -> JsonUtils.readToObject(jsonObject, RgbaColor.class),
                JsonUtils::beanToJson);
    }

}
