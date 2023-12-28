package org.vaadin.example.colorful;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.dom.DomListenerRegistration;
import com.vaadin.flow.internal.JsonCodec;
import dev.hilla.EndpointExposed;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import dev.hilla.parser.jackson.JacksonObjectMapperFactory;
import elemental.json.Json;
import elemental.json.JsonValue;
import org.springframework.beans.factory.annotation.Autowired;

@EndpointExposed
public abstract class ReactAdapterComponent<C extends AbstractField<C, S>, P, S> extends AbstractField<C, S> {
    private static ObjectMapper MAPPER = new JacksonObjectMapperFactory.Json().build();

    private static int DEFAULT_DEBOUNCE_TIMEOUT = 1000;

    private P props;

    private DomListenerRegistration stateListenerRegistration;

    public ReactAdapterComponent(@Nonnull @Autowired(required = false) P props, @Nullable @Autowired(required = false) S initialState) {
        super(initialState);
        setProps(props);
        setPresentationValue(initialState);
        this.stateListenerRegistration = getElement().addPropertyChangeListener("state", "state-changed", (event -> {
            JsonValue newStateJson = JsonCodec.encodeWithoutTypeInfo(event.getValue());
            S newState = readFromJson(newStateJson, (Class<? extends S>) initialState.getClass());
            setModelValue(newState, event.isUserOriginated());
        })).throttle(DEFAULT_DEBOUNCE_TIMEOUT);
    }

    protected DomListenerRegistration getStateListenerRegistration() {
        return stateListenerRegistration;
    }

    @Nonnull
    public P getProps() {
        return props;
    }

    public void setProps(P props) {
        this.props = props;
        getElement().setPropertyJson("props", writeAsJson(props));
    }

    public S getState() {
        return getValue();
    }

    public void setState(S state) {
        setValue(state);
    }

    protected void setPresentationValue(S presentationValue) {
        getElement().setPropertyJson("state", writeAsJson(presentationValue));
    }

    private static JsonValue writeAsJson(Object object) {
        try {
            return Json.instance().parse(MAPPER.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
    }

    private static <T> T readFromJson(JsonValue json, Class<T> toClass) {
        try {
            return MAPPER.readValue(json.toJson(), toClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting from JSON", e);
        }
    }
}
