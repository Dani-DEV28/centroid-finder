package com.centroidfinder;
import java.util.Arrays;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import java.awt.image.BufferedImage;

public class frameExt {
    public static void main(String videoPath, String outputDir, String hexTargetColor, int threshold){

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
        Java2DFrameConverter converter = new Java2DFrameConverter();

        try{
            grabber.start(); // open the video file
            int frameNumber = 0;

            Frame frame;

            int frameRate = (int) Math.round(grabber.getFrameRate());
            int totalFrames = grabber.getLengthInFrames();

            while ((frame = grabber.grabImage()) != null  && frameNumber < totalFrames) {
                if (frameNumber % frameRate == 0) { // roughly 1 frame per second (if 30 FPS)
                    BufferedImage image = converter.convert(frame);
                    // String fileName = String.format("%sframe_%05d.png", outputDir, frameNumber);
                    // ImageIO.write(image, "png", new File(fileName));

                    // ✅ Call ImageSummaryApp on this saved frame
                    String[] imageArgs = { hexTargetColor, String.valueOf(threshold) };
                    System.out.println("About to pass imageArgs: " + Arrays.toString(imageArgs));
                    ImageSummaryApp.main(image, imageArgs);
                }
                frameNumber++;
            }

            grabber.stop();

            // String csvDirectory = "./sampleOutput/CSV/";
            // String outputCsv = "sampleOutput/masterGroup.csv";

            // extractCSV extractor = new extractCSV(outputCsv);
            // extractor.extractFromDirectory(csvDirectory);

            // System.out.println("CSV extraction complete!");

            // System.out.println("Frame extraction completed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}