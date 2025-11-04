package com.centroidfinder;

import java.io.File;
import java.io.IOException;

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

        String videoPath = "./processor/sampleInput/ensantina.mp4"; // path to your input video
        String outputDir = "sampleOutput/frames/"; // directory to save extracted frames

        String hexTargetColor = "FFA200";
        int threshold = 164; 

        // FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
        // Java2DFrameConverter converter = new Java2DFrameConverter();

        String outputDirCSV = "processor/sampleOutput/CSV/";
        new File(outputDirCSV).mkdirs();

        // int dotIndex = file.getName().lastIndexOf('.');
        // String noExt = file.getName().substring(0, dotIndex);
        
        String filePathCSV = outputDirCSV + "groups_master.csv";

        // Delete the file if it exists, then recreate a fresh one
        File csvFile = new File(filePathCSV);
        if (csvFile.exists()) {
            if (csvFile.delete()) {
                System.out.println("Old groups_master.csv deleted.");
            } else {
                System.err.println("Failed to delete existing groups_master.csv.");
            }
        }

        // Create a new, empty file
        try {
            if (csvFile.createNewFile()) {
                System.out.println("New groups_master.csv created.");
            } else {
                System.out.println("groups_master.csv already exists or couldn't be created.");
            }
        } catch (IOException e) {
            System.err.println("Error creating groups_master.csv: " + e.getMessage());
        }

        frameExt.main(videoPath, outputDir, hexTargetColor, threshold);

        // String csvDirectory = "./sampleOutput/CSV/";
        // String outputCsv = "sampleOutput/masterGroup.csv";

        // extractCSV extractor = new extractCSV(outputCsv);
        // extractor.extractFromDirectory(csvDirectory);

        // System.out.println("CSV extraction complete!");

        System.out.println("Frame extraction completed!");
    }
}