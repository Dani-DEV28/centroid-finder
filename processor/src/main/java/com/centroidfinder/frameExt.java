package com.centroidfinder;
import java.util.Arrays;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class frameExt {
    public static void processVideo(String videoPath, String hexTargetColor, int threshold){

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
            Java2DFrameConverter converter = new Java2DFrameConverter()){
            grabber.start(); // open the video file
            int frameNumber = 0;

            Frame frame;

            int frameRate = (int) Math.round(grabber.getFrameRate());
            int totalFrames = grabber.getLengthInFrames();

            while ((frame = grabber.grabImage()) != null  && frameNumber < totalFrames) {
                if (frameNumber % frameRate == 0) { // roughly 1 frame per second (if 30 FPS)
                    BufferedImage image = converter.convert(frame);

                    // ✅ Call ImageSummaryApp on this saved frame
                    String[] imageArgs = { hexTargetColor, String.valueOf(threshold) };
                    System.out.println("About to pass imageArgs: " + Arrays.toString(imageArgs));
                    ImageSummaryApp.main(image, imageArgs);
                }
                frameNumber++;
            }

            System.out.println("✅ Frame extraction complete.");

        }  catch (IOException e) {
            System.err.println("I/O Error while accessing video: " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }   
}