package org.vaadin.example.colorful;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

import elemental.json.JsonObject;

//@NpmPackage(value = "react", version = "18.2.0")
//@NpmPackage(value = "react-dom", version = "18.2.0")
@NpmPackage(value = "react-colorful", version = "5.6.1")
@JsModule("./rgbacolorpicker-connector.tsx")
@Tag("rgba-color-picker") // The root element could be div, but why not give it a more descriptive name, even if it isn't an actual web component...
public class RgbaColorPicker extends AbstractField<RgbaColorPicker, RgbaColor> {

    public RgbaColorPicker() {
        super(new RgbaColor(255,0,0, 0.5));
        // call init method from rgbacolorpicker-connector.tsx
        // that renders the React component to this element
        getElement().executeJs("window.rgbacolorpickerConnectorInit($0, %s)".formatted(getValue()), getElement());
        // start listening events that push data from the event listener
        getElement().addEventListener("color-change", e -> {
                    JsonObject json = e.getEventData().getObject("event.rgba");
                    var newValue = new RgbaColor(
                            (int) json.getNumber("r"),
                            (int) json.getNumber("g"),
                            (int) json.getNumber("b"),
                            json.getNumber("a")
                    );

                    setModelValue(newValue,true);
                })
                .addEventData("event.rgba")
                .debounce(1000); // limit events sent to server, if e.g. the poing in the "colormap" is being dragged
    }


    @Override
    protected void setPresentationValue(RgbaColor newPresentationValue) {
        getElement().executeJs(("this._c.setValue(%s)").formatted(getValue()));
    }
}
