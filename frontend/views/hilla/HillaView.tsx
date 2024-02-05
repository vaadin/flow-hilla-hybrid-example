import { Button } from "@vaadin/react-components/Button.js";
import { TextField } from "@vaadin/react-components/TextField.js";
import { HelloEndpoint } from "Frontend/generated/endpoints.js";
import { useState } from "react";
import {VerticalLayout} from "@vaadin/react-components/VerticalLayout.js";
import { createWebComponent } from "Frontend/generated/flow/Flow";

let comp;

function MyLoginView() {
    return createWebComponent("my-flow-component");
}

export default function HillaView() {
  const [name, setName] = useState("");
  const [notifications, setNotifications] = useState([] as string[]);


  return (
    <>
        <VerticalLayout className={'centered-content'}>
            <MyLoginView/>
            <h3>Hilla View</h3>
            <TextField
                label="Your name"
                onValueChanged={(e) => {
                    setName(e.detail.value);
                }}
            />
            <Button
                onClick={async () => {
                    const serverResponse = await HelloEndpoint.sayHello(name);
                    setNotifications(notifications.concat(serverResponse));
                }}
            > Say hello </Button>
                {notifications.map((notification, index) => (
                    <p key={index}>{notification}</p>
                ))}
        </VerticalLayout>
    </>
  );
}
