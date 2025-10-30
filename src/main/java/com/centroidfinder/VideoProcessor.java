package com.centroidfinder;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class VideoProcessor {
    public static void main(String[] args) {
        String videoPath = "sampleInput/testVideo.mp4"; // path to your input video
        String outputDir = "sampleOutput/frames/"; // directory to save extracted frames

        String hexTargetColor = args[1];
        int threshold = 0; //related to try catch line 41-46
        try {
            threshold = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Threshold must be an integer.");
            return;
        }

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
        Java2DFrameConverter converter = new Java2DFrameConverter();

        try {
            grabber.start(); // open the video file
            int frameNumber = 0;

            // Make sure the output folder exists
            new File(outputDir).mkdirs();

            Frame frame;
            while ((frame = grabber.grabImage()) != null) {
                if (frameNumber % grabber.getFrameRate() == 0) { // roughly 1 frame per second (if 30 FPS)
                    BufferedImage image = converter.convert(frame);
                    String fileName = String.format("%sframe_%05d.png", outputDir, frameNumber);
                    ImageIO.write(image, "png", new File(fileName));
                    //Call ImageSummaryApp("java ImageSummaryApp ${fileName} <hex_target_color> <threshold>")
                    // System.out.println("Saved: " + fileName + "FPS = " + grabber.getFrameRate());
                }
                frameNumber++;
            }

            grabber.stop();
            System.out.println("Frame extraction completed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}