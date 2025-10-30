package com.centroidfinder;

import org.bytedeco.javacv.*;
// import org.bytedeco.opencv.opencv_core.Mat;

public class JavaCVTest {
    public static void main(String[] args) {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("sampleInput/testVideo.mp4")) {
            grabber.start();
            System.out.println("✅ JavaCV loaded successfully!");
            System.out.println("Video format: " + grabber.getFormat());
            System.out.println("Frame count: " + grabber.getLengthInFrames());
            System.out.println("Frame rate: " + grabber.getFrameRate());

            Frame frame = grabber.grabImage();
            if (frame != null) {
                System.out.println("Successfully grabbed first frame!");
            }

            grabber.stop();
            System.out.println("✅ FFmpeg and JavaCV test completed successfully!");
        } catch (Exception e) {
            System.err.println("❌ JavaCV test failed!");
            e.printStackTrace();
        }
    }
}