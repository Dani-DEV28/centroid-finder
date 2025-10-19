import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

public class DistanceImageBinarizerTest {

    private static final int WHITE = 0xFFFFFF;
    private static final int BLACK = 0x000000;

    @Test
    void testToBufferedImageAllBlack() {
        int[][] image = {
                { 0, 0, 0 },
                { 0, 0, 0 }
        };

        BufferedImage img = new BufferedImage(image.length, image[0].length, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < image.length; x++) {
            for (int y = 0; y < image[x].length; y++) {
                if (image[x][y] == 1) {
                    img.setRGB(x, y, 0x000000);
                }
                if (image[x][y] == 0) {
                    img.setRGB(x, y, 0xFFFFFF);
                }
            }
        }
        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        BufferedImage resualt = dIB.toBufferedImage(image);

        assertEquals(img.getRGB(0, 0), resualt.getRGB(0, 0));
        assertEquals(img.getRGB(1, 0), resualt.getRGB(1, 0));
        assertEquals(img.getRGB(2, 0), resualt.getRGB(2, 0));
        assertEquals(img.getRGB(0, 1), resualt.getRGB(0, 0));
        assertEquals(img.getRGB(1, 1), resualt.getRGB(1, 0));
        assertEquals(img.getRGB(2, 1), resualt.getRGB(2, 0));

    }

    @Test
    void testToBufferedImageAllWhite() {
        int[][] image = {
                { 1, 1, 1 },
                { 1, 1, 1 }
        };

        BufferedImage img = new BufferedImage(image.length, image[0].length, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < image.length; x++) {
            for (int y = 0; y < image[x].length; y++) {
                if (image[x][y] == 1) {
                    img.setRGB(x, y, 0x000000);
                }
                if (image[x][y] == 0) {
                    img.setRGB(x, y, 0xFFFFFF);
                }
            }
        }
        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        BufferedImage resualt = dIB.toBufferedImage(image);

        assertEquals(img.getRGB(0, 0), resualt.getRGB(0, 0));
        assertEquals(img.getRGB(1, 0), resualt.getRGB(1, 0));
        assertEquals(img.getRGB(2, 0), resualt.getRGB(2, 0));
        assertEquals(img.getRGB(0, 1), resualt.getRGB(0, 0));
        assertEquals(img.getRGB(1, 1), resualt.getRGB(1, 0));
        assertEquals(img.getRGB(2, 1), resualt.getRGB(2, 0));

    }

    @Test
    void testToBufferedImageMixed() {
        int[][] image = {
                { 1, 0, 0 },
                { 1, 0, 1 },
        };

        BufferedImage img = new BufferedImage(image.length, image[0].length, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < image.length; x++) {
            for (int y = 0; y < image[x].length; y++) {
                if (image[x][y] == 1) {
                    img.setRGB(x, y, 0x000000);
                }
                if (image[x][y] == 0) {
                    img.setRGB(x, y, 0xFFFFFF);
                }
            }
        }
        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        BufferedImage resualt = dIB.toBufferedImage(image);

        assertEquals(img.getRGB(0, 0), resualt.getRGB(0, 0));
        assertEquals(img.getRGB(1, 0), resualt.getRGB(1, 0));
        assertEquals(img.getRGB(2, 0), resualt.getRGB(2, 0));
        assertEquals(img.getRGB(0, 1), resualt.getRGB(0, 0));
        assertEquals(img.getRGB(1, 1), resualt.getRGB(1, 0));
        assertEquals(img.getRGB(2, 1), resualt.getRGB(2, 0));

    }

    @Test
    void testToBufferedImageNullRow() {
        int[][] image = {
                { 1, 0, 0, 1 },
                null,
                { 1, 0, 1, 0 }

        };

        BufferedImage img = new BufferedImage(image.length, image[0].length, BufferedImage.TYPE_INT_RGB);

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

    @Test
    void testToBufferedImageSquareImage() {
        int[][] image = {
                { 1, 0 },
                { 1, 0 }

        };

        BufferedImage img = new BufferedImage(image.length, image[0].length, BufferedImage.TYPE_INT_RGB);

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        assertThrows(IllegalArgumentException.class, () -> {
            dIB.toBufferedImage(image);
        });
    }

    // @Test
    // void testToBinaryArray() {
    // BufferedImage img = new BufferedImage(4, 3, BufferedImage.TYPE_INT_RGB);

    // int width = img.getWidth();
    // int height = img.getHeight();

    // }
}