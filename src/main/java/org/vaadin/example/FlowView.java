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
        colorPicker.addValueChangeListener(e -> {
            Notification.show("Value now: " + e.getValue().toCssColor());
        });
        add(colorPicker);


        colorPicker2.addValueChangeListener(e -> {
            Notification.show("Value now: " + e.getValue().toCssColor());
        });
        add(colorPicker2);

        var p = new ColorValueDisplay();
        add(p);
        add(new HorizontalLayout(
                        new Button("Show value", e -> {
                            p.setColor(colorPicker.getValue());
                            p.setColor(colorPicker2.getValue());
                        }),
                        new Button("Make green", e -> {
                            colorPicker.setValue(new RgbaColor(0,255,0, 0.8));
                            colorPicker2.setValue(new RgbaColor(0,255,0, 0.8));
                        })
                )
        );
    }

}
