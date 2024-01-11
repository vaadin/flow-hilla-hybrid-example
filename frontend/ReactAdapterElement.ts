import {createRoot, Root} from "react-dom/client";
import React, {createElement, Dispatch, ReactNode, useReducer} from "react";

type FlowStateKeyChangedAction<K extends string, V> = Readonly<{
  type: 'stateKeyChanged',
  key: K,
  value: V,
}>;

type FlowStateReducerAction = FlowStateKeyChangedAction<string, unknown>;

function stateReducer<S extends Readonly<Record<string, unknown>>>(state: S, action: FlowStateReducerAction): S {
  switch (action.type) {
    case "stateKeyChanged":
      const {key, value} = action satisfies FlowStateKeyChangedAction<string, unknown>;
      return {
        ...state,
        key: value
      } as S;
  }
}

type DispatchEvent<T> = T extends undefined
  ? () => boolean
  : { dispatch(value: T): boolean }["dispatch"];

const emptyAction: Dispatch<unknown> = () => {};

type RenderHooks = Readonly<{
  useState<T>(key: string, initialValue?: T): [value: T, setValue: Dispatch<T>];
  useCustomEvent<T = undefined>(type: string, options?: CustomEventInit<T>): DispatchEvent<T>;
}>;

export abstract class ReactAdapterElement extends HTMLElement {
  #root: Root | undefined = undefined;
  #rootRendered: boolean = false;

  #state: Record<string, unknown> = Object.create(null);
  #stateSetters = new Map<string, Dispatch<unknown>>();
  #customEvents = new Map<string, DispatchEvent<unknown>>();
  #dispatchFlowState: Dispatch<FlowStateReducerAction> = emptyAction;
  #renderHooks: RenderHooks;

  readonly #Wrapper: () => ReactNode;
  #unmountComplete = Promise.resolve();

  constructor() {
    super();
    this.#renderHooks = {
      useState: this.useState.bind(this),
      useCustomEvent: this.useCustomEvent.bind(this)
    };
    this.#Wrapper = this.#renderWrapper.bind(this);
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

  protected useState<T>(key: string, initialValue?: T): [value: T, setValue: Dispatch<T>] {
    if (!this.#stateSetters.has(key)) {
      const value = ((this as Record<string, unknown>)[key] as T) ?? initialValue!;
      this.#state[key] = value;
      Object.defineProperty(this, key, {
        enumerable: true,
        get(): T {
          return this.#state[key];
        },
        set(nextValue: T) {
          this.#state[key] = nextValue;
          this.#dispatchFlowState({type: 'stateKeyChanged', key, value});
        }
      });

      const dispatchChangedEvent = this.useCustomEvent<{value: T}>(`${key}-changed`, {detail: {value}});
      const setValue = (value: T) => {
        this.#state[key] = value;
        dispatchChangedEvent({value});
        this.#dispatchFlowState({type: 'stateKeyChanged', key, value});
      };
      this.#stateSetters.set(key, setValue as Dispatch<unknown>);
      return [value, setValue];
    }
    return [this.#state[key] as T, this.#stateSetters.get(key)!];
  }

  protected useCustomEvent<T = undefined>(type: string, options: CustomEventInit<T> = {}): DispatchEvent<T> {
    if (!this.#customEvents.has(type)) {
      const dispatch = ((detail?: T) => {
        const eventInitDict = "detail" in options ? {
          ...options,
          detail
        } : options;
        const event = new CustomEvent(type, eventInitDict);
        return this.dispatchEvent(event);
      }) as DispatchEvent<T>;
      this.#customEvents.set(type, dispatch);
      return dispatch;
    }
    return this.#customEvents.get(type)! as DispatchEvent<T>;
  }

  protected abstract render(hooks: RenderHooks): ReactNode;

  #maybeRenderRoot() {
    if (this.#rootRendered || !this.#root) {
      return;
    }

    this.#root.render(createElement(this.#Wrapper));
    this.#rootRendered = true;
  }

  #renderWrapper(): ReactNode {
    const [state, dispatchFlowState] = useReducer(stateReducer, this.#state);
    this.#state = state;
    this.#dispatchFlowState = dispatchFlowState;
    return this.render(this.#renderHooks);
  }
}
