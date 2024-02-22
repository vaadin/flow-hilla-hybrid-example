import { jsx as _jsx, jsxs as _jsxs, Fragment as _Fragment } from "react/jsx-runtime";
import { Button } from "@vaadin/react-components/Button.js";
import { TextField } from "@vaadin/react-components/TextField.js";
import { HelloEndpoint } from "Frontend/generated/endpoints";
import { useState } from "react";
import { VerticalLayout } from "@vaadin/react-components/VerticalLayout.js";
export default function HillaView() {
    const [name, setName] = useState("");
    const [notifications, setNotifications] = useState([]);
    return (_jsx(_Fragment, { children: _jsxs(VerticalLayout, { theme: "padding spacing", children: [_jsx("h3", { children: "Hilla View" }), _jsx(TextField, { label: "Your name", onValueChanged: (e) => {
                        setName(e.detail.value);
                    } }), _jsx(Button, { onClick: async () => {
                        const serverResponse = await HelloEndpoint.sayHello(name);
                        setNotifications(notifications.concat(serverResponse));
                    }, children: " Say hello " }), notifications.map((notification, index) => (_jsx("p", { children: notification }, index)))] }) }));
}
//# sourceMappingURL=HillaView.js.map