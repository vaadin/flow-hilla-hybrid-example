package org.vaadin.example.colorful;

import dev.hilla.Nonnull;

public record RgbaColorPickerHTMLAttributes(
        @Nonnull String id,
        @Nonnull String className
) {
    public RgbaColorPickerHTMLAttributes() {
        this("", "");
    }

    public RgbaColorPickerHTMLAttributes id(String id) {
        return new RgbaColorPickerHTMLAttributes(id, className);
    }

    public RgbaColorPickerHTMLAttributes className(String className) {
        return new RgbaColorPickerHTMLAttributes(id, className);
    }
}
