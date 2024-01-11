import React, {
  type ReactNode,
} from 'react';
import {
  ReactAdapterElement
} from "Frontend/ReactAdapterElement";
import {Button} from "@hilla/react-components/Button.js";

class ReactButtonElement extends ReactAdapterElement {
  protected override render(): ReactNode {
    const [text] = this.useState<string>("text");
    const dispatchCustomClick = this.useCustomEvent("custom-click");
    return <Button onClick={dispatchCustomClick}>{text}</Button>;
  }
}

customElements.define("react-button", ReactButtonElement);
