import React, {
  type ReactNode,
} from 'react';
import {RgbaColorPicker} from "react-colorful";
import {ReactAdapterElement} from "Frontend/ReactAdapterElement";
import RgbaColor
  from "Frontend/generated/org/vaadin/example/colorful/RgbaColor";

class RgbaColorPickerElement extends ReactAdapterElement<RgbaColor | undefined> {
  protected initializeState(): RgbaColor | undefined {
    return undefined;
  }

  protected override render(): ReactNode {
    return <RgbaColorPicker color={this.state} onChange={this.setState} />;
  }
}

customElements.define("rgba-color-picker", RgbaColorPickerElement);
