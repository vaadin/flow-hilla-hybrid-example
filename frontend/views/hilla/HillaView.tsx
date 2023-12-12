import { Button } from "@hilla/react-components/Button.js";
import { Notification } from "@hilla/react-components/Notification.js";
import { TextField } from "@hilla/react-components/TextField.js";
import { HelloEndpoint } from "Frontend/generated/endpoints.js";
import { useState } from "react";
import {VerticalLayout} from "@hilla/react-components/VerticalLayout.js";

export default function HillaView() {
  const [name, setName] = useState("");

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
                    Notification.show(serverResponse);
                }}
            > Say hello </Button>
        </VerticalLayout>
    </>
  );
}
