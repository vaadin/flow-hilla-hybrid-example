import { Button, TextField, VerticalLayout } from "@vaadin/react-components";
import { HelloEndpoint } from "Frontend/generated/endpoints";
import { useState } from "react";
import type { ViewConfig } from "@vaadin/hilla-file-router/types.js";

export const config: ViewConfig = {
    loginRequired: true,
    rolesAllowed: ['ROLE_USER'],
    menu: {
        icon: 'vaadin:user'
    }
};

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
