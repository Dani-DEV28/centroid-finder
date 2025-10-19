import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

public class DistanceImageBinarizerTest {

    private static final int WHITE = 0xFFFFFF;
    private static final int BLACK = 0x000000;

    private final DistanceImageBinarizer binarizer = new DistanceImageBinarizer((a, b) -> 0, 0x000000, 0);

    @Test
    public void testValidBinaryImageConversion() {
        int[][] binaryImage = {
                { 0, 1 },
                { 1, 0 }
        };

        BufferedImage result = binarizer.toBufferedImage(binaryImage);

        assertEquals(2, result.getWidth());
        assertEquals(2, result.getHeight());

        assertEquals(WHITE, result.getRGB(0, 0) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(1, 0) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(0, 1) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(1, 1) & 0xFFFFFF);
    }

    @Test
    void testToBufferedImageAllBlack() {
        int[][] image = {
                { 0, 0, 0 },
                { 0, 0, 0 }
        };

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        BufferedImage result = dIB.toBufferedImage(image);

        assertEquals(3, result.getHeight());
        assertEquals(2, result.getWidth());
        assertEquals(WHITE, result.getRGB(0, 0) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(0, 1) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(0, 2) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(1, 0) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(1, 1) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(1, 2) & 0xFFFFFF);

    }

    @Test
    void testToBufferedImageAllWhite() {
        int[][] image = {
                { 1, 1, 1 },
                { 1, 1, 1 }
        };

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        BufferedImage result = dIB.toBufferedImage(image);

        assertEquals(3, result.getHeight());
        assertEquals(2, result.getWidth());
        assertEquals(BLACK, result.getRGB(0, 0) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(0, 1) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(0, 2) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(1, 0) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(1, 1) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(1, 2) & 0xFFFFFF);

    }

    @Test
    void testToBufferedImageMixed() {
        int[][] image = {
                { 1, 0, 0 },
                { 1, 0, 1 },
        };

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        BufferedImage result = dIB.toBufferedImage(image);

        assertEquals(3, result.getHeight());
        assertEquals(2, result.getWidth());
        assertEquals(BLACK, result.getRGB(0, 0) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(0, 1) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(0,2) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(1, 0) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(1, 1) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(1, 2) & 0xFFFFFF);

    }

    @Test
    void testToBufferedImageNullRow() {
        int[][] image = {
                { 1, 0, 0, 1 },
                null,
                { 1, 0, 1, 0 }

        };

        // BufferedImage img = new BufferedImage(image.length, image[0].length, BufferedImage.TYPE_INT_RGB);

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        assertThrows(NullPointerException.class, () -> {
            dIB.toBufferedImage(image);
        });
    }

    @Test
    void testToBufferedImageIlegalData() {
        int[][] image = {
                { 1, 0, 0, 1 },
                { 1, 0, 3, 0 }
        };

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        assertThrows(IllegalArgumentException.class, () -> {
            dIB.toBufferedImage(image);
        });
    }

    @Test
    void testToBufferedImageJaggedRows() {
        int[][] image = {
                { 1, 0, 0, 1 },
                { 1, 0, 0 }

        };

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        assertThrows(IllegalArgumentException.class, () -> {
            dIB.toBufferedImage(image);
        });
    }

    // @Test
    // void testToBufferedImageSquareImage() {
    // int[][] image = {
    // { 1, 0 },
    // { 1, 0 }

    // };

    // BufferedImage img = new BufferedImage(image.length, image[0].length,
    // BufferedImage.TYPE_INT_RGB);

    // DistanceImageBinarizer dIB = new DistanceImageBinarizer(new
    // EuclideanColorDistance(), 0, 0);

    // assertThrows(IllegalArgumentException.class, () -> {
    // dIB.toBufferedImage(image);
    // });
    // }

    // @Test
    // void testToBinaryArray() {
    // BufferedImage img = new BufferedImage(4, 3, BufferedImage.TYPE_INT_RGB);

    // int width = img.getWidth();
    // int height = img.getHeight();
    // }
}