package com.centroidfinder;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.util.Arrays;
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
        assertEquals(BLACK, result.getRGB(0, 0) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(0, 1) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(1, 0) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(1, 1) & 0xFFFFFF);
    }

    @Test
    void testToBufferedImageAllBlack() {
        int[][] image = {
                { 0, 0, 0 },
                { 0, 0, 0 }
        };

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        BufferedImage result = dIB.toBufferedImage(image);

        assertEquals(2, result.getHeight());
        assertEquals(3, result.getWidth());
        assertEquals(BLACK, result.getRGB(0, 0) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(1, 0) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(2, 0) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(0, 1) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(1, 1) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(2, 1) & 0xFFFFFF);
    }

    @Test
    void testToBufferedImageAllWhite() {
        int[][] image = {
                { 1, 1, 1 },
                { 1, 1, 1 }
        };

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        BufferedImage result = dIB.toBufferedImage(image);

        assertEquals(2, result.getHeight());
        assertEquals(3, result.getWidth());
        assertEquals(WHITE, result.getRGB(0, 0) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(1, 0) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(2, 0) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(0, 1) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(1, 1) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(2, 1) & 0xFFFFFF);
    }

    @Test
    void testToBufferedImageMixed() {
        int[][] image = {
                { 1, 0, 0 },
                { 1, 0, 1 },
        };

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), 0, 0);

        BufferedImage result = dIB.toBufferedImage(image);

        assertEquals(2, result.getHeight());
        assertEquals(3, result.getWidth());
        assertEquals(WHITE, result.getRGB(0, 0) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(1, 0) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(2, 0) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(0, 1) & 0xFFFFFF);
        assertEquals(BLACK, result.getRGB(1, 1) & 0xFFFFFF);
        assertEquals(WHITE, result.getRGB(2, 1) & 0xFFFFFF);

    }

    @Test
    void testToBufferedImageNullRow() {
        int[][] image = {
                { 1, 0, 0, 1 },
                null,
                { 1, 0, 1, 0 }

        };

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
    void testToBinaryArrayAllWhiteExactTargetColor() {
        BufferedImage img = new BufferedImage(3, 2, BufferedImage.TYPE_INT_RGB);

        int color = 0x008080;
        int width = img.getWidth();
        int height = img.getHeight();

        img.setRGB(0, 0, color);
        img.setRGB(1, 0, color);
        img.setRGB(2, 0, color);
        img.setRGB(0, 1, color);
        img.setRGB(1, 1, color);
        img.setRGB(2, 1, color);

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), color, 1);

        int[][] binarizedImage = dIB.toBinaryArray(img);

        System.out.println(Arrays.deepToString(binarizedImage));

        assertEquals(width, binarizedImage[0].length);
        assertEquals(height, binarizedImage.length);
        assertEquals(0, binarizedImage[0][0]);
        assertEquals(0, binarizedImage[0][1]);
        assertEquals(0, binarizedImage[0][2]);
        assertEquals(0, binarizedImage[1][0]);
        assertEquals(0, binarizedImage[1][1]);
        assertEquals(0, binarizedImage[1][2]);
    }

    @Test
    void testToBinaryArrayAllWhiteWithinThreshold() {
        BufferedImage img = new BufferedImage(3, 2, BufferedImage.TYPE_INT_RGB);

        int color = 0x048080;
        int targetColor = 0x008080;
        int width = img.getWidth();
        int height = img.getHeight();

        img.setRGB(0, 0, color);
        img.setRGB(1, 0, color);
        img.setRGB(2, 0, color);
        img.setRGB(0, 1, color);
        img.setRGB(1, 1, color);
        img.setRGB(2, 1, color);

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), targetColor, 5);

        int[][] binarizedImage = dIB.toBinaryArray(img);

        System.out.println(Arrays.deepToString(binarizedImage));

        assertEquals(width, binarizedImage[0].length);
        assertEquals(height, binarizedImage.length);
        assertEquals(0, binarizedImage[0][0]);
        assertEquals(0, binarizedImage[0][1]);
        assertEquals(0, binarizedImage[0][2]);
        assertEquals(0, binarizedImage[1][0]);
        assertEquals(0, binarizedImage[1][1]);
        assertEquals(0, binarizedImage[1][2]);
    }

    @Test
    void testToBinaryArrayAllBlackOutsideThreshold() {
        BufferedImage img = new BufferedImage(3, 2, BufferedImage.TYPE_INT_RGB);

        int color = 0x108080;
        int targetColor = 0x008080;
        int width = img.getWidth();
        int height = img.getHeight();

        img.setRGB(0, 0, color);
        img.setRGB(1, 0, color);
        img.setRGB(2, 0, color);
        img.setRGB(0, 1, color);
        img.setRGB(1, 1, color);
        img.setRGB(2, 1, color);

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), targetColor, 5);

        int[][] binarizedImage = dIB.toBinaryArray(img);

        System.out.println(Arrays.deepToString(binarizedImage));

        assertEquals(width, binarizedImage[0].length);
        assertEquals(height, binarizedImage.length);
        assertEquals(1, binarizedImage[0][0]);
        assertEquals(1, binarizedImage[0][1]);
        assertEquals(1, binarizedImage[0][2]);
        assertEquals(1, binarizedImage[1][0]);
        assertEquals(1, binarizedImage[1][1]);
        assertEquals(1, binarizedImage[1][2]);
    }

    @Test
    void testToBinaryArrayMixed() {
        BufferedImage img = new BufferedImage(3, 2, BufferedImage.TYPE_INT_RGB);

        int color1 = 0x048080;
        int color2 = 0x108080;
        int targetColor = 0x008080;
        int width = img.getWidth();
        int height = img.getHeight();

        img.setRGB(0, 0, color1);
        img.setRGB(1, 0, color1);
        img.setRGB(2, 0, color2);
        img.setRGB(0, 1, color2);
        img.setRGB(1, 1, color1);
        img.setRGB(2, 1, color2);

        DistanceImageBinarizer dIB = new DistanceImageBinarizer(new EuclideanColorDistance(), targetColor, 5);

        int[][] binarizedImage = dIB.toBinaryArray(img);

        System.out.println(Arrays.deepToString(binarizedImage));

        assertEquals(width, binarizedImage[0].length);
        assertEquals(height, binarizedImage.length);
        assertEquals(0, binarizedImage[0][0]);
        assertEquals(0, binarizedImage[0][1]);
        assertEquals(1, binarizedImage[0][2]);
        assertEquals(1, binarizedImage[1][0]);
        assertEquals(0, binarizedImage[1][1]);
        assertEquals(1, binarizedImage[1][2]);
    }
}