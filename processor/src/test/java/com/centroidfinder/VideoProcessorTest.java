package com.centroidfinder;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

class VideoProcessorTest {

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // --------------------------
    // Helper: inject mock frameExt
    // --------------------------
    // private void injectMockFrameExt() throws Exception {
    //     Field field = VideoProcessor.class.getDeclaredField("frameExt");
    //     field.setAccessible(true);

    //     // Stub with a no-op implementation
    //     field.set(null, new Object() {
    //         public void processVideo(String v, String h, int t, String o) {
    //             // no-op for tests
    //         }
    //     });
    // }

    @Test
    void testMissingArgumentsPrintsUsage() throws Exception {
        VideoProcessor.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Usage: java VideoProcessor"),
            "Should print usage when args are missing");
    }

    @Test
    void testDefaultHexColorWhenMissing(@TempDir Path tempDir) throws Exception {
        // injectMockFrameExt();

        Path csv = tempDir.resolve("out.csv");
        String[] args = {
            "dummy.mp4",  // videoPath
            "",           // empty hex color → default FFA200
            "200",        // threshold
            csv.toString()
        };

        VideoProcessor.main(args);

        String console = outContent.toString();
        assertTrue(console.contains("Created new file"), "CSV file should be created");
        assertTrue(Files.exists(csv), "Output CSV file should exist");
    }

    @Test
    void testThresholdFallsBackToDefaultIfInvalid(@TempDir Path tempDir) throws Exception {
        // injectMockFrameExt();

        Path csv = tempDir.resolve("out.csv");

        String[] args = {
            "video.mp4",
            "00FFAA",
            "notANumber",  // invalid threshold
            csv.toString()
        };

        VideoProcessor.main(args);

        assertTrue(errContent.toString().contains("Threshold must be an integer"),
            "Should warn that threshold is invalid");
        assertTrue(Files.exists(csv), "Should still create output file");
    }

    @Test
    void testThresholdDoesNotAcceptNegative(@TempDir Path tempDir) throws Exception {
        // injectMockFrameExt();

        Path csv = tempDir.resolve("out.csv");

        String[] args = {
            "video.mp4",
            "00FFAA",
            "-50",          // negative → default used
            csv.toString()
        };

        VideoProcessor.main(args);

        // Should not print parse error, but threshold should fallback silently
        assertFalse(errContent.toString().contains("Threshold must be an integer")); 
        assertTrue(Files.exists(csv), "File should still be created");
    }


    // --- Neeed some understanding of project structure to test this, as there no sys.out msg of Default path used ---
    // @Test
    // void testDefaultOutputPathUsedWhenMissing(@TempDir Path tempDir) throws Exception {
    //     // injectMockFrameExt();

    //     // Simulate running in a temp project environment
    //     System.setProperty("user.dir", tempDir.toString());

    //     String[] args = {
    //         "video.mp4",
    //         "00FFAA",
    //         "150"
    //         // missing output → use default processor/sampleOutput/CSV/groups_master.csv
    //     };

    //     VideoProcessor.main(args);

    //     Path expected = tempDir.resolve("processor/sampleOutput/CSV/groups_master.csv");

    //     assertTrue(Files.exists(expected), "Default output path file should be created");
    // }
}
