import React, {
  type ReactNode,
} from 'react';
import {RgbaColorPicker} from "react-colorful";
import {
  ReactAdapterElement
} from "Frontend/ReactAdapterElement";
import RgbaColor
  from "Frontend/generated/org/vaadin/example/colorful/RgbaColor";
import RgbaColorPickerHTMLAttributes
  from "Frontend/generated/org/vaadin/example/colorful/RgbaColorPickerHTMLAttributes";

class RgbaColorPickerElement extends ReactAdapterElement<RgbaColorPickerHTMLAttributes, RgbaColor | undefined> {
  protected override render(): ReactNode {
    return <RgbaColorPicker
      color={this.state}
      onChange={this.setState}
      {...this.props}
    />;
  }
}

customElements.define("rgba-color-picker", RgbaColorPickerElement);
