import { Button } from "@hilla/react-components/Button.js";
import { TextField } from "@hilla/react-components/TextField.js";
import { HelloEndpoint } from "Frontend/generated/endpoints.js";
import { useState } from "react";
import {VerticalLayout} from "@hilla/react-components/VerticalLayout.js";

export default function HillaView() {
  const [name, setName] = useState("");
  const [notifications, setNotifications] = useState([] as string[]);


  return (
    <>
        <VerticalLayout className={'centered-content'}>
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
