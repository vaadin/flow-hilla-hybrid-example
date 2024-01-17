import React, {useEffect, useRef, useState} from "react";
import {VerticalLayout} from "@hilla/react-components/VerticalLayout.js";

let comp;

function MyLoginView() {
    comp = React.createElement("my-flow-component");
    return comp;
}

export default function HillaView() {

    useEffect(() => {
        const script = document.createElement('script');
        script.src = '/web-component/my-flow-component.js';
        document.head.appendChild(script);

        return () => {
            // const flowElement = document.querySelector('my-flow-component');
            //
            // // Check if the element exists before attempting to remove it
            // if (flowElement) {
            //     // Remove the element
            //     flowElement.parentNode?.removeChild(flowElement);
            // }
            //
            // // @ts-ignore
            // var clients = window.Vaadin?.Flow?.clients;
            // if (clients) {
            //     var exportedWcClient = Object.keys(clients)
            //         .filter((key) => key.startsWith("wc-"));
            //     if (exportedWcClient && exportedWcClient.length > 0) {
            //         var key = exportedWcClient[0];
            //         // @ts-ignore
            //         delete window.Vaadin.Flow.clients[key];
            //     }
            // }
        };
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
