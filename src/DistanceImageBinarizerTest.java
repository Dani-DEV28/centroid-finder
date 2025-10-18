import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

public class DistanceImageBinarizerTest {

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
}