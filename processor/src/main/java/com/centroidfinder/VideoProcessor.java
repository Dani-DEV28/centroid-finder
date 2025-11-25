package com.centroidfinder;

import java.io.File;
import java.io.IOException;

public class VideoProcessor {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java VideoProcessor <videoPath> <hex_target_color> <threshold> [outputPath]");
            return;
        } // can be ignore, but just checking is parameter are present

        String videoPath = args[0];
        String hexTargetColor = args.length > 1 && !args[1].isEmpty() ? args[1] : "FFA200"; // default to orange if not provided
        
        int threshold = 164; // default
        if (args.length > 2) {
            try {
                int parsed = Integer.parseInt(args[2]);
                threshold = parsed > 0 ? parsed : threshold;
            } catch (NumberFormatException e) {
                System.err.println("Threshold must be an integer. Using default: " + threshold);
            }
        }

        // String videoPath = "./processor/sampleInput/ensantina.mp4"; // path to your input video

        // String hexTargetColor = "FFA200";
        // int threshold = 164; 

        String outputPath = args.length > 3 ? args[3] : "processor/sampleOutput/CSV/groups_master.csv";
        File csvFile = new File(outputPath);

        new File(csvFile.getParent()).mkdirs();

        if (csvFile.exists() && !csvFile.delete()) {
            System.err.println("Failed to delete existing file: " + outputPath);
        }

        try {
            if (csvFile.createNewFile()) {
                System.out.println("Created new file: " + outputPath);
            } else {
                System.out.println("File already exists: " + outputPath);
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            return;
        }
        
        // String filePathCSV = outputDirCSV + "groups_master.csv";

        // Delete the file if it exists, then recreate a fresh one
        // File csvFile = new File(filePathCSV);
        // if (csvFile.exists()) {
        //     if (csvFile.delete()) {
        //         System.out.println("Old groups_master.csv deleted.");
        //     } else {
        //         System.err.println("Failed to delete existing groups_master.csv.");
        //     }
        // }

        // // Create a new, empty file
        // try {
        //     if (csvFile.createNewFile()) {
        //         System.out.println("New groups_master.csv created.");
        //     } else {
        //         System.out.println("groups_master.csv already exists or couldn't be created.");
        //     }
        // } catch (IOException e) {
        //     System.err.println("Error creating groups_master.csv: " + e.getMessage());
        // }

        // frameExt.processVideo(videoPath, hexTargetColor, threshold);

        // System.out.println("Frame extraction completed!");

        try {
            frameExt.processVideo(videoPath, hexTargetColor, threshold);
            System.out.println("Frame extraction completed for video: " + videoPath);
            System.out.println("Output CSV: " + outputPath);
        } catch (Exception e) {
            System.err.println("Error processing video: " + e.getMessage());
            e.printStackTrace();
        }
    }
}