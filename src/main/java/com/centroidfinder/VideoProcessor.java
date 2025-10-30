package com.centroidfinder;

public class VideoProcessor {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java VideoProcessor <videoPath> <hex_target_color> <threshold>");
            return;
        } // can be ignore, but just checking is parameter are present

        // String videoPath = args[0];
        // String hexTargetColor = args[1];
        // int threshold = 0;
        // try {
        //     threshold = Integer.parseInt(args[2]);
        // } catch (NumberFormatException e) {
        //     System.err.println("Threshold must be an integer.");
        //     return;
        // }

        String videoPath = "sampleInput/ensantina.mp4"; // path to your input video
        String outputDir = "sampleOutput/frames/"; // directory to save extracted frames

        String hexTargetColor = "FFA200";
        int threshold = 164; //related to try catch line 41-46
        // try {
        //     threshold = Integer.parseInt(args[2]);
        // } catch (NumberFormatException e) {
        //     System.err.println("Threshold must be an integer.");
        //     return;
        // }

        // FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
        // Java2DFrameConverter converter = new Java2DFrameConverter();

        frameExt.main(videoPath, outputDir, hexTargetColor, threshold);

        String csvDirectory = "./sampleOutput/CSV/";
        String outputCsv = "sampleOutput/masterGroup.csv";

        extractCSV extractor = new extractCSV(outputCsv);
        extractor.extractFromDirectory(csvDirectory);

        System.out.println("CSV extraction complete!");

        System.out.println("Frame extraction completed!");
    }
}