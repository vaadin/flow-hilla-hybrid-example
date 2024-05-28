package com.vaadin.uitest.common;

public interface HasTestView {

    default String getUrl() {
        return "http://localhost:8080/";
    }

    default String getView() {
        return "";
    }
}
