import {ReactAdapterElement, RenderHooks} from "Frontend/ReactAdapter.js";
import React from "react";

export default class GridLayoutElement extends ReactAdapterElement {
  protected render(hooks: RenderHooks): React.ReactElement | null {
    const content = this.useContent('content');
    return <>{content}</>;
  }
}

customElements.define('grid-layout', GridLayoutElement);
