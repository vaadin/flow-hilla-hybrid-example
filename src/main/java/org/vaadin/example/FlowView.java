package org.vaadin.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.colorful.RgbaColor;
import org.vaadin.example.colorful.RgbaColorPicker;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.vaadin.example.mui.MuiRating;
import org.vaadin.example.vaadin.ReactButton;
import org.vaadin.example.vaadin.ReactGrid;
import org.vaadin.example.vaadin.ReactLazyGrid;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route("flow")
@PreserveOnRefresh
public class FlowView extends VerticalLayout {

    public static class ColorValueDisplay  extends Paragraph {
        public ColorValueDisplay() {
            getStyle().setFont("bold 30px sans-serif");
        }

        public void setColor(RgbaColor color) {
            setText(color.toString());
            getStyle().setColor(color.toCssColor());
        }
    }

    public FlowView(@Autowired GreetService service) {
        var colorPicker = new RgbaColorPicker();
        var colorPicker2 = new RgbaColorPicker();
        colorPicker.addColorChangeListener((color) -> {
            Notification.show("Value now: " + color.toCssColor());
        });
        add(colorPicker);


        colorPicker2.addColorChangeListener((color) -> {
            Notification.show("Value now: " + color.toCssColor());
        });
        add(colorPicker2);

        var p = new ColorValueDisplay();
        add(p);
        add(new HorizontalLayout(
                        new Button("Show value", e -> {
                            p.setColor(colorPicker.getColor());
                            p.setColor(colorPicker2.getColor());
                        }),
                        new Button("Make green", e -> {
                            colorPicker.setColor(new RgbaColor(0,255,0, 0.8));
                            colorPicker2.setColor(new RgbaColor(0,255,0, 0.8));
                        })
                )
        );
        add(new HorizontalLayout(
                new ReactButton("Say hi", () -> {
                    Notification.show("Hi custom event");
                }),
                new MuiRating()
        ));
        var list = IntStream.range(0, 300).mapToObj((i) -> new GridItem("User " + i, "u" + i + "@example.com")).toList();
        add(new ReactGrid<>(list));
        add(new ReactLazyGrid<>(list));
    }

    public record GridItem(String name, String email) {
    }
}
