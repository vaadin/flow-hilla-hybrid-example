import React, {
  type ReactNode,
} from 'react';
import {
  ReactAdapterElement
} from "Frontend/ReactAdapterElement";
import {Grid} from "@hilla/react-components/Grid.js";
import {GridColumn} from "@hilla/react-components/GridColumn.js";

export class ReactGridElement<TItem> extends ReactAdapterElement {
  protected override render(): ReactNode {
    const [items] = this.useState<TItem[]>("items");
    return <Grid<TItem> items={items}>
      <GridColumn header="Name" path="name" />
      <GridColumn header="Email" path="email" />
    </Grid>;
  }
}

customElements.define("react-grid", ReactGridElement);
