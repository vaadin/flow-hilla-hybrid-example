import React, { Component, useState } from 'react';
import {createRoot, Root} from 'react-dom/client';
import {RgbaColor, RgbaColorPicker} from "react-colorful";

const rootMap = new WeakMap<Element, Root>();

(window as any).rgbacolorpickerConnectorInit = (element : Element, initialValue : RgbaColor)  => {
    const root = createRoot(element);

    rootMap.set(element, root);

    // to use useState, need a React component
    const Wrapper = () => {
        const [color, setColor] = useState(initialValue);

        const onChange = (rgba : RgbaColor) => {
            // maintain the state as using useState
            setColor(rgba)
            // ... and fire a custom event that is listened by the
            // actual Java component. Could also just use
            // element.$server.myClientCallableMethod, but debouncing
            // is supported when using events
            const event = new CustomEvent<RgbaColor>("color-change", {detail : rgba});
            element.dispatchEvent(event);
        };
        // @ts-ignore
        element._c = {setValue : setColor};

        return <RgbaColorPicker color={color} onChange={onChange} />;
    };
    root.render(<Wrapper/>);

}

(window as any).rgbacolorpickerConnectorUnmount = (element:Element) => {
    rootMap.get(element)?.unmount();
    rootMap.delete(element);
}
