import {createRoot, Root} from "react-dom/client";
import React, {createElement, Dispatch, ReactNode, useReducer} from "react";
import {AbstractModel, DetachedModelConstructor, Value} from "@hilla/form";

function replaceStateReducer<T>(_: T, state: T) {
  return state;
}

export abstract class ReactAdapterElement<T = unknown> extends HTMLElement {
  #root: Root | undefined = undefined;
  #state: T;
  #dispatchState: (state: T) => void = () => {};
  readonly #Wrapper: () => ReactNode;
  #unmountComplete = Promise.resolve();

  protected abstract initializeState(): T;

  constructor() {
    super();
    this.#state = this.initializeState();
    this.#Wrapper = this.#renderWrapper.bind(this);
    this.setState = this.setState.bind(this);
  }

  public async connectedCallback() {
    await this.#unmountComplete;
    this.#root = createRoot(this);
    this.#root.render(createElement(this.#Wrapper));
  }

  public async disconnectedCallback() {
    this.#unmountComplete = Promise.resolve();
    await this.#unmountComplete;
    this.#root?.unmount();
    this.#root = undefined;
  }

  protected get state(): T {
    return this.#state;
  }

  protected set state(state: T) {
    this.#updateState(state, true);
  }

  protected setState(state: T): void {
    this.#updateState(state);
  }

  protected abstract render(): ReactNode;

  #updateState(state: T, skipEvent?: boolean): void {
    if (state === this.#state) {
      return;
    }

    this.#state = state;
    if (!skipEvent) {
      this.dispatchEvent(new CustomEvent('state-changed', {detail: state}));
    }
    this.#dispatchState(state);
  }

  #renderWrapper(): ReactNode {
    const [state, dispatch] = useReducer(replaceStateReducer<T>, this.#state);
    this.#state = state;
    this.#dispatchState = dispatch;
    return this.render();
  }
}

export class ReactAdapterStub<M extends AbstractModel> {
  public tagName: string;
  public modelClass: DetachedModelConstructor<M>;

  constructor(tagName: string, modelClass: DetachedModelConstructor<M>) {
    this.tagName = tagName;
    this.modelClass = modelClass;
  }

  public define(renderer: React.ComponentType<{state: Value<M>, setState: Dispatch<Value<M>>}>) {
    const {tagName, modelClass} = this;

    class ReactAdapterAutoElement extends ReactAdapterElement<Value<M>> {
      static get modelClass() {
        return modelClass;
      }

      protected initializeState(): Value<M> {
        return (modelClass as unknown as {createEmptyValue(): Value<M>}).createEmptyValue();
      }

      protected render(): React.ReactNode {
        const {state, setState} = this;
        return createElement(renderer, {state, setState});
      }
    }

    customElements.define(tagName, ReactAdapterAutoElement);

    return ReactAdapterAutoElement;
  }
}
