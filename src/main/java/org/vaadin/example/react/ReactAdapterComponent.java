package org.vaadin.example.react;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.dom.DomListenerRegistration;
import com.vaadin.flow.internal.JsonCodec;
import dev.hilla.parser.jackson.JacksonObjectMapperFactory;
import elemental.json.Json;
import elemental.json.JsonValue;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ReactAdapterComponent extends Component {
    private static ObjectMapper MAPPER = new JacksonObjectMapperFactory.Json().build();

    private static int DEFAULT_THROTTLE_TIMEOUT = 1000;

    protected <T> DomListenerRegistration addStateChangeListener(String stateName, Class<T> typeClass, Consumer<T> listener) {
        return addJsonReaderStateChangeListener(stateName, (jsonValue -> readFromJson(jsonValue, typeClass)), listener);
    }

    protected <T> DomListenerRegistration addStateChangeListener(String stateName, TypeReference<T> typeReference, Consumer<T> listener) {
        return addJsonReaderStateChangeListener(stateName, (jsonValue -> readFromJson(jsonValue, typeReference)), listener);
    }

    protected <T> void setState(String stateName, T value) {
        getElement().setPropertyJson(stateName, writeAsJson(value));
    }

    protected <T> T getState(String stateName, Class<T> typeClass) {
        return readFromJson((JsonValue) getElement().getPropertyRaw(stateName), typeClass);
    }

    protected <T> T getState(String stateName, TypeReference<T> typeReference) {
        return readFromJson((JsonValue) getElement().getPropertyRaw(stateName), typeReference);
    }

    private <T> DomListenerRegistration addJsonReaderStateChangeListener(String stateName, Function<JsonValue, T> jsonReader, Consumer<T> listener) {
        return getElement().addPropertyChangeListener(stateName, stateName + "-changed", (event -> {
            JsonValue newStateJson = JsonCodec.encodeWithoutTypeInfo(event.getValue());
            T newState = jsonReader.apply(newStateJson);
            listener.accept(newState);
        })).throttle(DEFAULT_THROTTLE_TIMEOUT);
    }

    protected static JsonValue writeAsJson(Object object) {
        try {
            return Json.instance().parse(MAPPER.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
    }

    private static <T> T readFromJson(JsonValue json, Class<T> typeClass) {
        try {
            return MAPPER.readValue(json.toJson(), typeClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting from JSON", e);
        }
    }

    private static <T> T readFromJson(JsonValue json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json.toJson(), typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting from JSON", e);
        }
    }
}
