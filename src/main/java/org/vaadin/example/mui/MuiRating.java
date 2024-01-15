package org.vaadin.example.mui;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import org.vaadin.example.react.ReactAdapterComponent;

import java.util.function.DoubleConsumer;

@NpmPackage(value = "@emotion/react", version = "11.11.3")
@NpmPackage(value = "@emotion/styled", version = "11.11.0")
@NpmPackage(value = "@mui/material", version = "5.15.4")
@JsModule("./mui-rating.tsx")
@Tag("mui-rating")
public class MuiRating extends ReactAdapterComponent {
    public MuiRating() {
        setRating(-1);
    }

    public double getRating() {
        return getState("rating", Double.class);
    }

    public void setRating(double rating) {
        setState("rating", rating);
    }

    public void addChangeListener(DoubleConsumer onChange) {
        addStateChangeListener("rating", Double.class, onChange::accept);
    }
}
