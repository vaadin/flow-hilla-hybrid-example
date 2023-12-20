package org.vaadin.example.colorful;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.shared.Registration;

import elemental.json.JsonObject;

@NpmPackage(value = "react-colorful", version = "5.6.1")
@JsModule("./rgbacolorpicker-connector.tsx")
@Tag("rgba-color-picker") // The root element could be div, but why not give it a more descriptive name, even if it isn't an actual web component...
public class RgbaColorPicker extends AbstractField<RgbaColorPicker, RgbaColor> {

    public RgbaColorPicker() {
        super(new RgbaColor(255,0,0, 0.5));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        getElement().executeJs("window.rgbacolorpickerConnectorInit($0, %s)".formatted(getValue()), getElement());

        getElement().addEventListener("color-change", e -> {
                    JsonObject json = e.getEventData().getObject("event.detail");
                    var newValue = new RgbaColor(
                            (int) json.getNumber("r"),
                            (int) json.getNumber("g"),
                            (int) json.getNumber("b"),
                            json.getNumber("a")
                    );

                    setModelValue(newValue,true);
                })
                .addEventData("event.detail")
                .debounce(1000);

    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);

        getElement().executeJs("window.rgbacolorpickerConnectorUnmount($0)", getElement());
    }

    @Override
    protected void setPresentationValue(RgbaColor newPresentationValue) {
        getElement().executeJs(("this._c.setValue(%s)").formatted(getValue()));
    }
}
