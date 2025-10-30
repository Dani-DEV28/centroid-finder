package com.centroidfinder;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BinarizingImageGroupFinderTest {

    static class MockBinarizer implements ImageBinarizer {
        boolean toBinaryArrayCalled = false;
        BufferedImage receivedImage;
        int[][] returnValue = { { 1, 0 }, { 0, 1 } };

        @Override
        public int[][] toBinaryArray(BufferedImage image) {
            toBinaryArrayCalled = true;
            receivedImage = image;
            return returnValue;
        }

        @Override
        public BufferedImage toBufferedImage(int[][] image) {
            fail("toBufferedImage() should not be called in this context");
            return null;
        }
    }

    static class MockGroupFinder implements BinaryGroupFinder {
        boolean findConnectedGroupsCalled = false;
        int[][] receivedArray;
        List<Group> returnValue = List.of(new Group(3, new Coordinate(2, 2)));

        @Override
        public List<Group> findConnectedGroups(int[][] image) {
            findConnectedGroupsCalled = true;
            receivedArray = image;
            return returnValue;
        }
    }

    @Test
    void findConnectedGroups_DelegatesToDependencies() {
        MockBinarizer mockBinarizer = new MockBinarizer();
        MockGroupFinder mockGroupFinder = new MockGroupFinder();

        BinarizingImageGroupFinder finder = new BinarizingImageGroupFinder(mockBinarizer, mockGroupFinder);

        BufferedImage img = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        List<Group> result = finder.findConnectedGroups(img);

        assertTrue(mockBinarizer.toBinaryArrayCalled, "Expected binarizer to be called");
        assertSame(mockBinarizer.receivedImage, img, "Expected binarizer to receive the same image");
        assertTrue(mockGroupFinder.findConnectedGroupsCalled, "Expected group finder to be called");
        assertTrue(Arrays.equals(mockBinarizer.returnValue, mockGroupFinder.receivedArray),
                "Expected group finder to receive the binary array from binarizer");
        assertEquals(mockGroupFinder.returnValue, result, "Expected returned groups to match the mock output");
    }

    @Test
    void constructorRejectsNullArguments() {
        assertThrows(NullPointerException.class,
                () -> new BinarizingImageGroupFinder(null, new MockGroupFinder()));
        assertThrows(NullPointerException.class,
                () -> new BinarizingImageGroupFinder(new MockBinarizer(), null));
    }

    @Test
    void testBinarizingImageGroupFinderTestNullImageBinarizer() {
        ImageBinarizer binarizer = null;
        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();

        assertThrows(NullPointerException.class, () -> {
            new BinarizingImageGroupFinder(binarizer, groupFinder);
        });
    }

    @Test
    void testBinarizingImageGroupFinderTestNullGroupFinder() {
        ImageBinarizer binarizer = new DistanceImageBinarizer(null, 0, 0);
        BinaryGroupFinder groupFinder = null;

        assertThrows(NullPointerException.class, () -> {
            new BinarizingImageGroupFinder(binarizer, groupFinder);
        });
    }
}
