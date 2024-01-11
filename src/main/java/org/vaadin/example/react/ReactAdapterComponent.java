package org.vaadin.example.react;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.dom.DomListenerRegistration;
import com.vaadin.flow.internal.JsonCodec;
import dev.hilla.parser.jackson.JacksonObjectMapperFactory;
import elemental.json.Json;
import elemental.json.JsonValue;

import java.util.function.Consumer;

// TODO:
//  - Vaadin Progress Bar
//  - Vaadin Grid
//  - Material UI

public abstract class ReactAdapterComponent extends Component {
    private static ObjectMapper MAPPER = new JacksonObjectMapperFactory.Json().build();

    private static int DEFAULT_THROTTLE_TIMEOUT = 1000;

    protected <T> DomListenerRegistration addStateChangeListener(String stateName, Class<? extends T> typeClass, Consumer<T> listener) {
        return getElement().addPropertyChangeListener(stateName, stateName + "-changed", (event -> {
            JsonValue newStateJson = JsonCodec.encodeWithoutTypeInfo(event.getValue());
            T newState = readFromJson(newStateJson, typeClass);
            listener.accept(newState);
        })).throttle(DEFAULT_THROTTLE_TIMEOUT);
    }

    protected <T> void setState(String stateName, T value) {
        getElement().setPropertyJson(stateName, writeAsJson(value));
    }

    protected <T> T getState(String stateName, Class<? extends T> typeClass) {
        return readFromJson((JsonValue) getElement().getPropertyRaw(stateName), typeClass);
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
