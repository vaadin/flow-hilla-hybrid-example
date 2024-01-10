import React, {useEffect, useRef, useState} from "react";
import {VerticalLayout} from "@hilla/react-components/VerticalLayout.js";

function MyLoginView() {
    return React.createElement("my-flow-component");
}

export default function HillaView() {

    useEffect(() => {
        const script = document.createElement('script');
        script.src = '/web-component/my-flow-component.js';
        document.head.appendChild(script);

        return () => {};
    }, []);

    return (
        <>
            <VerticalLayout className={'centered-content'}>
                <h3>Hilla View</h3>
                <MyLoginView/>
            </VerticalLayout>
        </>
    );
}
