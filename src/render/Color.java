package render;

public class Color {
    public static int fromARGB(byte a, byte r, byte g, byte b) {
        int color = 0;
        color += ((int) a) << 24;
        color += ((int) r) << 16;
        color += ((int) g) << 8;
        color += (int) b;
        return color;
    }
}
