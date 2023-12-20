import React, { Component, useState } from 'react';
import { createRoot } from 'react-dom/client';
import { RgbaColorPicker } from "react-colorful";

window.rgbacolorpickerConnectorInit = (element, initialValue)  => {
    const root = createRoot(element);

    // to use useState, need a React component
    const Wrapper = () => {
        const [color, setColor] = useState(initialValue);

        const onChange = (rgba) => {
            // maintain the state as using useState
            setColor(rgba)
            // ... and fire a custom event that is listened by the
            // actual Java component. Could also just use
            // element.$server.myClientCallableMethod, but debouncing
            // is supported when using events
            const event = new Event("color-change");
            event.rgba = rgba;
            element.dispatchEvent(event);
        };
        element._c = {};
        // this is called by the server if value changed in the JVM side
        // triggers re-render as using useState
        element._c.setValue = newValue => setColor(newValue);

        return <RgbaColorPicker color={color} onChange={onChange} />;
    };
    root.render(<Wrapper/>);

}
