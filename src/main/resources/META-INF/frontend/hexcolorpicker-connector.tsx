import React, { Component, useState } from 'react';
import { createRoot } from 'react-dom/client';
import { HexColorPicker } from "react-colorful";

window.hexcolorpickerConnectorInit = (element, initialValue)  => {
    const root = createRoot(element);

    // to use useState, need a React component
    const Wrapper = () => {
        const [color, setColor] = useState(initialValue);

        const onChange = (hex) => {
            // maintain the state as using useState
            setColor(hex)
            // ... and fire a custom event that is listened by the
            // actual Java component. Could also just use
            // element.$server.myClientCallableMethod, but debouncing
            // is supported when using events
            const event = new Event("color-change");
            event.hex = hex;
            element.dispatchEvent(event);
        };
        element._c = {};
        // this is called by the server if value changed in the JVM side
        // triggers re-render as using useState
        element._c.setValue = newValue => setColor(newValue);

        return <HexColorPicker color={color} onChange={onChange} />;
    };
    root.render(<Wrapper/>);

}
