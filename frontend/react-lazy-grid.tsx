import React, {
  type ReactNode, useCallback, useRef, Ref, RefObject,
} from 'react';
import {
  ReactAdapterElement
} from "Frontend/ReactAdapterElement";
import {
  Grid,
  GridDataProvider, GridDataProviderCallback,
  GridDataProviderParams,
  GridElement
} from "@hilla/react-components/Grid.js";
import {GridColumn} from "@hilla/react-components/GridColumn.js";

export class ReactLazyGridElement<TItem> extends ReactAdapterElement {
  #requestId: number = 0;
  #requestPool = new Map<number, Parameters<GridDataProvider<TItem>>>();
  #dispatchDataRequest = this.useCustomEvent<GridDataProviderParams<TItem> & {
    requestId: number
  }>(
    "data-request"
    , {
      detail: {
        requestId: 0,
        page: 0,
        pageSize: 0,
        filters: [],
        sortOrders: [],
      },
    },
  );

  #gridRef?: RefObject<GridElement>;
  #requestItemsBound = this.requestItems.bind(this);

  protected override render(): ReactNode {
    this.#gridRef = useRef<GridElement>(null);
    const [size] = this.useState<number>("size");
    return <Grid dataProvider={this.#requestItemsBound} size={size}>
      <GridColumn header="Name" path="name"/>
      <GridColumn header="Email" path="email"/>
    </Grid>;
  }

  protected requestItems(params: GridDataProviderParams<TItem>, callback: GridDataProviderCallback<TItem>) {
    this.#requestId++;
    this.#requestPool.set(this.#requestId, [params, callback]);
    this.#dispatchDataRequest({requestId: this.#requestId, ...params});
  }

  protected dataResponseCallback(requestId: number, items: TItem[]) {
    if (!this.#requestPool.has(requestId)) {
      return;
    }

    const [_, callback] = this.#requestPool.get(requestId)!;
    this.#requestPool.delete(requestId);
    callback(items);
  }

  protected refreshData() {
    this.#gridRef?.current?.clearCache();
  }
}

customElements.define("react-lazy-grid", ReactLazyGridElement);
