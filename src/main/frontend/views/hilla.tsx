import { Button } from "@vaadin/react-components/Button.js";
import { TextField } from "@vaadin/react-components/TextField.js";
import { HelloEndpoint } from "Frontend/generated/endpoints";
import { useState } from "react";
import {VerticalLayout} from "@vaadin/react-components/VerticalLayout.js";

/**
 * Hilla view that allows access only for users with a role 'USER'
 */
export default function Hilla() {
  const [name, setName] = useState("");
  const [notifications, setNotifications] = useState([] as string[]);

  return (
    <>
        <VerticalLayout theme="padding spacing">
            <h3>Hilla User View</h3>
            <TextField
                label="Your name"
                onValueChanged={(e) => {
                    setName(e.detail.value);
                }}
            />
            <Button
                id={'say-hello'}
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
