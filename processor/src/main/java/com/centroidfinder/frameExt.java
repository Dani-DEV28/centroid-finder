package com.centroidfinder;
import java.util.Arrays;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class frameExt {
    public static void processVideo(String videoPath, String hexTargetColor, int threshold, String outputPath) {

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
            Java2DFrameConverter converter = new Java2DFrameConverter()){
            grabber.start(); // open the video file
            int frameNumber = 0;

            Frame frame; // to hold each frame grabbed from the video

            int frameRate = (int) Math.round(grabber.getFrameRate()); //read the video file metadata to retrieve FPS, while Math rounds to nearest integer
            int totalFrames = grabber.getLengthInFrames(); // get the total number of frames in the video to determine when to stop

            String format = grabber.getFormat();
            boolean isImage = format != null && format.startsWith("image");

            String[] imageArgs = { hexTargetColor, String.valueOf(threshold), outputPath }; //setting up for the arguement
            

            if(isImage){ // images
                frame = grabber.grabImage();
                if(frame != null){
                    frameExt.callingProcessor(converter, frame, imageArgs);
                    System.out.println("✅ Processed single image file");
                }
                return;
            }

            while ((frame = grabber.grabImage()) != null  && frameNumber < totalFrames) {
                if (frameNumber % frameRate == 0) { // roughly 1 frame per second (if 30 FPS)
                    frameExt.callingProcessor(converter, frame, imageArgs);
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
    
    public static void callingProcessor(Java2DFrameConverter converter, Frame currentImg, String[] imageArgs){
        BufferedImage image = converter.convert(currentImg);
        // ✅ Call ImageSummaryApp on this saved frame
        System.out.println("About to pass imageArgs: " + Arrays.toString(imageArgs));
        ImageSummaryApp.main(image, imageArgs);
    }
}