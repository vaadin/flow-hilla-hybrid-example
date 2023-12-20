package org.vaadin.example.colorful;

// { r: 200, g: 150, b: 35, a: 0.5 }
public record RgbaColor(int r, int g, int b, double a) {

    @Override
    public String toString() {
        return "{ r: %s, g: %s, b: %s, a: %s }".formatted(r, g, b, a);
    }

    public String toCssColor() {
        return "rgba(%s,%s,%s,%s)".formatted(r, g, b, a);
    }

    static RgbaColor fromWebHex(String hex) {
        int r = Integer.valueOf(hex.substring(1, 3), 16);
        int g = Integer.valueOf(hex.substring(3, 5), 16);
        int b = Integer.valueOf(hex.substring(5, 7), 16);
        return new RgbaColor(r,g,b,1);
    }

    public String toWebHex() {
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
