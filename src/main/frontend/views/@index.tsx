import { VerticalLayout } from "@vaadin/react-components";
import type { ViewConfig } from "@vaadin/hilla-file-router/types.js";
import {reactElement} from "Frontend/generated/flow/Flow";

export const config: ViewConfig = {
  menu: {
    title: "Public page",
    icon: 'vaadin:group'
  }
};

type MyProperties = {
  greeting: string
}

function MyFlowComponent(props: MyProperties) {
  return reactElement("my-flow-component", { greeting: props.greeting },
    () => {
      document.getElementById("loading")?.remove();
    }, (err: any) => { console.error(err); });
}

export default function Public() {
    return (
      <VerticalLayout theme="padding">
        <p>This is a Hilla view</p>
        <div id={"loading"}>Loading...</div>
        <MyFlowComponent greeting={'Hello from Flow component in Hilla!'}/>
      </VerticalLayout>
    );
}
