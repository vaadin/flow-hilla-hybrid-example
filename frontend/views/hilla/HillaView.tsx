import { VerticalLayout } from "@vaadin/react-components/VerticalLayout";
import { loadComponentScript } from "Frontend/generated/flow/Flow";
import React from "react";

function MyLoginView() {
    loadComponentScript("my-flow-component");
    return React.createElement("my-flow-component", {
        userlbl: 'User Label as attribute',
        pwdlbl:  'Password is written in this box:'
    })
}

export default function HillaView() {
    return (
        <>
            <VerticalLayout className={'centered-content'}>
                <h3>Hilla View</h3>
                <MyLoginView/>
            </VerticalLayout>
        </>
    );
}
