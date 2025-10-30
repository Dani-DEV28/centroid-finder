package com.centroidfinder;

import java.io.*;
import java.nio.file.*;

public class extractCSV {

    private final Path outputFile;

    public extractCSV(String outputFilePath) {
        this.outputFile = Paths.get(outputFilePath);
        // Ensure parent directory exists
        try {
            Files.createDirectories(this.outputFile.getParent());
        } catch (IOException e) {
            System.err.println("Error creating output folder: " + e.getMessage());
        }
    }

    public void extractFromDirectory(String dirPath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile.toFile(), true))) {
            Files.list(Paths.get(dirPath))
                .filter(path -> path.toString().endsWith(".csv"))
                .forEach(path -> processFile(path, writer));
        } catch (IOException e) {
            System.err.println("Error reading directory: " + e.getMessage());
        }
    }

    private void processFile(Path filePath, PrintWriter writer) {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    writer.printf("%s,%s,%s%n", filePath.getFileName(), parts[1].trim(), parts[2].trim());
                } else {
                    System.out.printf("%s → Not enough columns%n", filePath.getFileName());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file " + filePath + ": " + e.getMessage());
        }
    }
}