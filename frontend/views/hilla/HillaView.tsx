import { Button } from "@hilla/react-components/Button.js";
import { Notification } from "@hilla/react-components/Notification.js";
import { TextField } from "@hilla/react-components/TextField.js";
import { HelloEndpoint } from "Frontend/generated/endpoints.js";
import {useEffect, useRef, useState} from "react";
import {VerticalLayout} from "@hilla/react-components/VerticalLayout.js";
//@ts-ignore
import {LoginForm} from "Frontend/generated/flow/web-components/login-form.js";

export default function HillaView() {
  const [name, setName] = useState("");

  //@ts-ignore
  const ref = useRef(this);

    useEffect(() => {
        const el = document.createElement('login-form') as LoginForm;
        el.userlbl = "Your username";
        el.pwdlbl = "Your password";
        const layout = ref.current.querySelectorAll('vaadin-vertical-layout')[0];

        if (layout) {
            layout.appendChild(el);
        }

        return () => {
            // Cleanup when the component unmounts
            if (layout && el.parentNode === layout) {
                layout.removeChild(el);
            }
        };
    }, []);

  return (
    <div ref={ref}>
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
    </div>
  );
}
