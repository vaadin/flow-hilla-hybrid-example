import {createRoot, Root} from "react-dom/client";
import React, {createElement, Dispatch, ReactNode, useReducer} from "react";

function replaceReducer<T>(_: T, newValue: T): T {
  return newValue;
}

function throwNotInitializedYet(): never {
  throw new TypeError('Not inititialized yet');
}

const emptyAction: Dispatch<unknown> = () => {};

export abstract class ReactAdapterElement<P = unknown, S = unknown> extends HTMLElement {
  #root: Root | undefined = undefined;
  #rootRendered: boolean = false;

  #props: P | undefined = undefined;
  #propsInitialized: boolean = false;
  #dispatchProps: Dispatch<P> = emptyAction;

  #state: S | undefined = undefined;
  #stateInitialized: boolean = false;
  #dispatchState: Dispatch<S> = emptyAction;

  readonly #Wrapper: () => ReactNode;
  #unmountComplete = Promise.resolve();

  constructor() {
    super();
    this.#Wrapper = this.#renderWrapper.bind(this);
    this.setState = this.setState.bind(this);
  }

  public async connectedCallback() {
    await this.#unmountComplete;
    this.#root = createRoot(this);
    this.#maybeRenderRoot();
  }

  public async disconnectedCallback() {
    this.#unmountComplete = Promise.resolve();
    await this.#unmountComplete;
    this.#root?.unmount();
    this.#root = undefined;
    this.#rootRendered = false;
  }

  protected get props(): P {
    return this.#props!;
  }

  protected set props(props: P) {
    this.#updateProps(props);
    this.#maybeRenderRoot();
  }

  protected get state(): S {
    return this.#state!;
  }

  protected set state(state: S) {
    this.#updateState(state, true);
    this.#maybeRenderRoot();
  }

  protected setState(state: S): void {
    this.#updateState(state);
  }

  protected abstract render(): ReactNode;

  #maybeRenderRoot() {
    if (this.#rootRendered || !this.#propsInitialized || !this.#stateInitialized || !this.#root) {
      return;
    }

    this.#root.render(createElement(this.#Wrapper));
    this.#rootRendered = true;
  }

  #updateProps(props: P): void {
    const oldProps = this.#props;
    this.#props = props;
    if (!this.#propsInitialized) {
      this.#propsInitialized = true;
    }

    if (props === oldProps) {
      return;
    }
    this.#dispatchProps(props);
  }

  #updateState(state: S, skipEvent?: boolean): void {
    const oldState = this.state;
    this.#state = state;
    if (!this.#stateInitialized) {
      this.#stateInitialized = true;
    }

    if (state === oldState) {
      return;
    }

    if (!skipEvent) {
      this.dispatchEvent(new CustomEvent('state-changed', {detail: state}));
    }
    this.#dispatchState(state);
  }

  #renderWrapper(): ReactNode {
    const [props, dispatchProps] = useReducer(replaceReducer<P>, this.#props!);
    this.#props = this.props;
    this.#dispatchProps = dispatchProps;
    const [state, dispatchState] = useReducer(replaceReducer<S>, this.#state!);
    this.#state = state;
    this.#dispatchState = dispatchState;
    return this.render();
  }
}
