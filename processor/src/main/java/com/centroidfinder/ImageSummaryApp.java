package com.centroidfinder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

/**
 * The Image Summary Application.
 * 
 * This application takes three command-line arguments:
 * 1. The path to an input image file (for example, "image.png").
 * 2. A target hex color in the format RRGGBB (for example, "FF0000" for red).
 * 3. An integer threshold for binarization.
 * 
 * The application performs the following steps:
 * 
 * 1. Loads the input image.
 * 2. Parses the target color from the hex string into a 24-bit integer.
 * 3. Binarizes the image by comparing each pixel's Euclidean color distance to
 * the target color.
 * A pixel is marked white (1) if its distance is less than the threshold;
 * otherwise, it is marked black (0).
 * 4. Converts the binary array back to a BufferedImage and writes the binarized
 * image to disk as "binarized.png".
 * 5. Finds connected groups of white pixels in the binary image.
 * Pixels are connected vertically and horizontally (not diagonally).
 * For each group, the size (number of pixels) and the centroid (calculated
 * using integer division) are computed.
 * 6. Writes a CSV file named "groups.csv" containing one row per group in the
 * format "size,x,y".
 * Coordinates follow the convention: (x:0, y:0) is the top-left, with x
 * increasing to the right and y increasing downward.
 * 
 * Usage:
 * java ImageSummaryApp <input_image> <hex_target_color> <threshold>
 */
public class ImageSummaryApp {
    public static void main(BufferedImage image, String hexTargetColor, int threshold, String outputPath) {
        System.out.println("ISA boot");

        // Parse the target color from a hex string (format RRGGBB) into a 24-bit
        // integer (0xRRGGBB)
        int targetColor = 0;
        try {
            targetColor = Integer.parseInt(hexTargetColor, 16);
        } catch (NumberFormatException e) {
            System.err.println("Invalid hex target color. Please provide a color in RRGGBB format.");
            return;
        }

        // Create the DistanceImageBinarizer with a EuclideanColorDistance instance.
        ColorDistanceFinder distanceFinder = new EuclideanColorDistance();
        ImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, targetColor, threshold);

        // Create an ImageGroupFinder using a BinarizingImageGroupFinder with a
        // DFS-based BinaryGroupFinder.
        ImageGroupFinder groupFinder = new BinarizingImageGroupFinder(binarizer, new DfsBinaryGroupFinder());

        // Find connected groups in the input image.
        // The BinarizingImageGroupFinder is expected to internally binarize the image,
        // then locate connected groups of white pixels.
        List<Group> groups = groupFinder.findConnectedGroups(image);

        // Write the groups to the CSV file.
    try {
        // Count how many lines are already in the CSV to get next line index
        long lineCount = 0;
        File csvFile = new File(outputPath);
        if (csvFile.exists()) {
            lineCount = Files.lines(csvFile.toPath()).count();
        }

            try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath, true))) {
                if (!groups.isEmpty()) {
                    Group firstGroup = groups.get(0);

                    // Convert the group to CSV row and split by comma
                    String[] parts = firstGroup.toCsvRow().split(",");

                    if (parts.length >= 3) {
                        long lineNumber = lineCount + 1; // Next line index
                        writer.printf("%d,%s,%s%n", lineNumber, parts[1].trim(), parts[2].trim());
                        System.out.printf("Wrote line %d → %s,%s%n", lineNumber, parts[1].trim(), parts[2].trim());
                    } else {
                        System.out.println("Group row does not have enough columns.");
                    }

                } else {
                    System.out.println("No groups found in this image.");
                }

            }

        } catch (Exception e) {
            System.err.println("Error writing groups_master.csv");
            e.printStackTrace();
        }
    }
}
