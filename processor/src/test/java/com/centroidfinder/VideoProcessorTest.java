package com.centroidfinder;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

public class VideoProcessorTest {
    // frameNumber / frameRate;
    @Test
    void test1() {
        BufferedImage img = new BufferedImage(3, 2, BufferedImage.TYPE_INT_RGB);
    }

    @Test
    void emptyArgs() {
        VideoProcessor empty = new VideoProcessor();

        String err4Empty = "Usage: java VideoProcessor <videoPath> <hex_target_color> <threshold> [outputPath]";

        assertEquals(empty, err4Empty);
    }

    @Test
    void nonThresholdInt() {
        String[] argument = {"salamanader.jpg", "FFA200", "Pizza"};
        VideoProcessor incorrectThreshold = new VideoProcessor(argument);
    }
}
