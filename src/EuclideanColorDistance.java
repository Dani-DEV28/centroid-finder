public class EuclideanColorDistance implements ColorDistanceFinder {
    /**
     * Returns the euclidean color distance between two hex RGB colors.
     * 
     * Each color is represented as a 24-bit integer in the form 0xRRGGBB, where
     * RR is the red component, GG is the green component, and BB is the blue
     * component,
     * each ranging from 0 to 255.
     * 
     * The Euclidean color distance is calculated by treating each color as a point
     * in 3D space (red, green, blue) and applying the Euclidean distance formula:
     * 
     * sqrt((r1 - r2)^2 + (g1 - g2)^2 + (b1 - b2)^2)
     * 
     * This gives a measure of how visually different the two colors are.
     * 
     * @param colorA the first color as a 24-bit hex RGB integer
     * @param colorB the second color as a 24-bit hex RGB integer
     * @return the Euclidean distance between the two colors
     */
    @Override
    public double distance(int colorA, int colorB) {
        int b1 = convert(colorA, 0x0000FF, 0);
        int b2 = convert(colorB, 0x0000FF, 0);

        int g1 = convert(colorA, 0x00FF00, 8);
        int g2 = convert(colorB, 0x00FF00, 8);

        int r1 = convert(colorA, 0xFF0000, 16);
        int r2 = convert(colorB, 0xFF0000, 16);

        double dist = Math.sqrt(Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2));

        return dist;
    }

    private int convert(int color, int mask, int shift) {
        int preShift = color & mask;
        return preShift >> shift;
    }
}
