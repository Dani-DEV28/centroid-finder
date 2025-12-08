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
        String hexTargetColor = args.length > 1 && !args[1].isEmpty() ? args[1] : "303e2f"; // default to orange if not provided
        
        int threshold = 60; // default
        if (args.length > 2) { // check for the presence of threshold argument, and parse it to integer
            try {
                int parsed = Integer.parseInt(args[2]);
                threshold = parsed > 0 ? parsed : threshold;
            } catch (NumberFormatException e) {
                System.err.println("Threshold must be an integer. Using default: " + threshold);
            }
        }

        String outputPath = args.length > 3 ? args[3] : "processor/sampleOutput/CSV/groups_master.csv"; // default output path, or use provided one
        File csvFile = new File(outputPath);

        new File(csvFile.getParent()).mkdirs();

        if (csvFile.exists() && !csvFile.delete()) { // to ensure that old file is deleted before creating a new one
            System.err.println("Failed to delete existing file: " + outputPath);
        }

        try { //Secondary Check when creating CSV File
            if (csvFile.createNewFile()) {
                System.out.println("Created new file: " + outputPath);
            } else {
                System.out.println("File already exists: " + outputPath);
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            return;
        }

        try {
            frameExt.processVideo(videoPath, hexTargetColor, threshold, outputPath);
            System.out.println("Frame extraction completed for video: " + videoPath);
            System.out.println("Output CSV: " + outputPath);
        } catch (Exception e) {
            System.err.println("Error processing video: " + e.getMessage());
            e.printStackTrace();
        }
    }
}