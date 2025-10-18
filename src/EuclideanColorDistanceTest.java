import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EuclideanColorDistanceTest {
    private final EuclideanColorDistance colorDistance = new EuclideanColorDistance();

    @Test
    void testSameColorDistanceIsZero() {
        int red = 0xFF0000;
        assertEquals(0.0, colorDistance.distance(red, red), "Distance should be zero for identical colors");
    }

    @Test
    void testRedAndBlueDistance() {
        int red = 0xFF0000;
        int blue = 0x0000FF;

        double expected = Math.sqrt(Math.pow(255 - 0, 2) + Math.pow(0 - 0, 2) + Math.pow(0 - 255, 2));
        assertEquals(expected, colorDistance.distance(red, blue), 0.0001, "Red-Blue distance mismatch");
    }

    @Test
    void testRedAndGreenDistance() {
        int red = 0xFF0000;
        int green = 0x00FF00;

        double expected = Math.sqrt(Math.pow(255 - 0, 2) + Math.pow(0 - 255, 2) + Math.pow(0 - 0, 2));
        assertEquals(expected, colorDistance.distance(red, green), 0.0001, "Red-Green distance mismatch");
    }

    @Test
    void testBlackAndWhiteDistance() {
        int black = 0x000000;
        int white = 0xFFFFFF;

        double expected = Math.sqrt(3 * Math.pow(255, 2)); // 255 diff per channel
        assertEquals(expected, colorDistance.distance(black, white), 0.0001, "Black-White distance mismatch");
    }

    @Test
    void testDifferentMixedColors() {
        int colorA = 0x123456; // R=18, G=52, B=86
        int colorB = 0x654321; // R=101, G=67, B=33

        double expected = Math.sqrt(
            Math.pow(18 - 101, 2) +
            Math.pow(52 - 67, 2) +
            Math.pow(86 - 33, 2)
        );

        assertEquals(expected, colorDistance.distance(colorA, colorB), 0.0001, "Mixed color distance mismatch");
    }

    @Test
    void exceedBitHexValueNegative(){
        int exceed = 0xFF000000;

        assertThrows(IllegalArgumentException.class, () -> {
            colorDistance.distance(exceed, exceed);
        });
    }

    @Test
    void exceedBitHexValuePositive(){
        int exceed = 0x7FFFFFFF;

        assertThrows(IllegalArgumentException.class, () -> {
            colorDistance.distance(exceed, exceed);
        });
    }
}