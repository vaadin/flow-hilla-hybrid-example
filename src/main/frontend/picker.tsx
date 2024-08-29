import {type ReactElement} from 'react';
import { VerticalLayout } from "@vaadin/react-components";
import {ReactAdapterElement, type RenderHooks} from "Frontend/generated/flow/ReactAdapter";

class PickerElement extends ReactAdapterElement {
  protected override render(hooks: RenderHooks): ReactElement | null {
    return <VerticalLayout><span>Hello!</span></VerticalLayout>;
  }
}

customElements.define(
  "my-picker",
  PickerElement
);
