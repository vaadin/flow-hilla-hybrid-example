import React, {
  type ReactNode,
} from 'react';
import {RgbaColorPicker} from "react-colorful";
import {
  ReactAdapterElement, ReactAdapterStub
} from "Frontend/ReactAdapterElement";
import RgbaColor
  from "Frontend/generated/org/vaadin/example/colorful/RgbaColor";
import RgbaColorModel
  from "Frontend/generated/org/vaadin/example/colorful/RgbaColorModel";

class RgbaColorPickerElement extends ReactAdapterElement<RgbaColor | undefined> {
  protected initializeState(): RgbaColor | undefined {
    return undefined;
  }

  protected override render(): ReactNode {
    return <RgbaColorPicker color={this.state} onChange={this.setState} />;
  }
}

customElements.define("rgba-color-picker", RgbaColorPickerElement);

// --- But wait, there's more!

// Generated stub:

const rgbaColorPickerStub = new ReactAdapterStub("rgba-color-picker-2", RgbaColorModel);

// User's code:

rgbaColorPickerStub.define(
  ({state, setState}) => <RgbaColorPicker color={state} onChange={setState} />);
