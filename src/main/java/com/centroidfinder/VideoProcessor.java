package com.centroidfinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VideoProcessor {
    public static void main(String[] args) {
        // if (args.length < 3) {
        //     System.out.println("Usage: java VideoProcessor <videoPath> <hex_target_color> <threshold>");
        //     return;
        // } // can be ignore, but just checking is parameter are present

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

        checkCreateDir(Paths.get(outputDir));

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
        checkCreateDir(Paths.get(outputDir));

        String outputCsv = "sampleOutput/masterGroup.csv";

        extractCSV extractor = new extractCSV(outputCsv);
        extractor.extractFromDirectory(csvDirectory);

        System.out.println("CSV extraction complete!");

        System.out.println("Frame extraction completed!");
    }

    public static void checkCreateDir(Path dirPath){
        try {
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath); // creates parent dirs too
                System.out.println("Directory created: " + dirPath);
            } else {
                System.out.println("Directory already exists: " + dirPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}