package org.vaadin.example.vaadin;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.dom.DomEvent;
import elemental.json.JsonObject;
import org.vaadin.example.react.ReactAdapterComponent;

import java.util.List;

@JsModule("./react-lazy-grid.tsx")
@Tag("react-lazy-grid")
public class ReactLazyGrid<T> extends ReactAdapterComponent {
    private List<T> items;

    public ReactLazyGrid(List<T> items) {
        setItems(items);
        getElement().addEventListener("data-request", this::handleDataRequest)
                .addEventData("event.detail.requestId")
                .addEventData("event.detail.page")
                .addEventData("event.detail.pageSize");
        addClassNames("block", "w-full");
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
        setState("size", items.size());
        getElement().callJsFunction("refreshData");
    }

    private void handleDataRequest(DomEvent event) {
        JsonObject eventData = event.getEventData();
        int requestId =  (int) eventData.getNumber("event.detail.requestId");
        int page = (int) eventData.getNumber("event.detail.page");
        int pageSize = (int) eventData.getNumber("event.detail.pageSize");
        int offset = page * pageSize;
        List<T> slice = items.subList(offset, offset + pageSize);
        getElement().callJsFunction("dataResponseCallback", requestId, writeAsJson(slice));
    }
}
