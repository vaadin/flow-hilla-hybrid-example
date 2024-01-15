import React, {
  type ReactNode,
} from 'react';
import {RgbaColor, RgbaColorPicker} from "react-colorful";
import {
  ReactAdapterElement
} from "Frontend/ReactAdapterElement";

class RgbaColorPickerElement extends ReactAdapterElement {
  protected override render(): ReactNode {
    const [color, setColor] =
      this.useState<RgbaColor>("color");

    return <RgbaColorPicker
      color={color}
      onChange={setColor}
    />;
  }
}

customElements.define(
  "rgba-color-picker",
  RgbaColorPickerElement
);
