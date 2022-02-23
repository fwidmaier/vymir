package render;

/**
 * A class to easily encode ARGB (or RGB) information to a singe integer.
 */
public class Color {
    /**
     * Method to encode ARGB information into an integer.
     * For each color channel, 8 bits are reserved in the integer - resulting in the 32 bits.
     * the first 8 bits are reserved for the alpha channel.
     * The second byte is reserved for red... etc. pp.
     * @param a the alpha channel
     * @param r the red channel
     * @param g the green channel
     * @param b the blue channel
     * @return the encoded color
     */
    public static int fromARGB(byte a, byte r, byte g, byte b) {
        int color = 0;
        color += ((int) a) << 24;
        color += ((int) r) << 16;
        color += ((int) g) << 8;
        color += (int) b;
        return color;
    }

    /**
     * Method to encode RGB information to a singe integer.
     * @param r the red channel
     * @param g the green channel
     * @param b the blue channel
     * @return the encoded color
     */
    public static int fromRGB(byte r, byte g, byte b) {
        return fromARGB((byte) 255, r, g, b);
    }
}
