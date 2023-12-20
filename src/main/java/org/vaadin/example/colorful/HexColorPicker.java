package org.vaadin.example.colorful;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

//@NpmPackage(value = "react", version = "18.2.0")
//@NpmPackage(value = "react-dom", version = "18.2.0")
@NpmPackage(value = "react-colorful", version = "5.6.1")
@JsModule("./hexcolorpicker-connector.tsx")
@Tag("hex-color-picker") // The root element could be div, but why not give it a more descriptive name, even if it isn't an actual web component...
public class HexColorPicker extends AbstractField<HexColorPicker, RgbaColor> {

    public HexColorPicker() {
        super(RgbaColor.fromWebHex("#ff0d00"));
        // call init method from hexcolorpicker-connector.tsx
        // that renders the React component to this element
        getElement().executeJs("window.hexcolorpickerConnectorInit($0, $1)", getElement(), getValue().toString());
        // start listening events that push data from the event listener
        getElement().addEventListener("color-change", e -> {
                    var newValue = e.getEventData().getString("event.hex");
                    setModelValue(RgbaColor.fromWebHex(newValue), true);
                })
                .addEventData("event.hex")
                .debounce(1000); // limit events sent to server, if e.g. the poing in the "colormap" is being dragged
    }

    @Override
    protected void setPresentationValue(RgbaColor newPresentationValue) {
        getElement().executeJs(("this._c.setValue($0)"), newPresentationValue.toWebHex());
    }

    public void setValue(String value) {
        super.setValue(RgbaColor.fromWebHex(value));
    }

}
