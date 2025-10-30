
---

## Class Overview

### **VideoProcessor**
- **Purpose:** Main entry point of the program.
- **Responsibilities:**
  - Incorporates **JavaCV** to read frames from the input `.mp4` video.
  - Sends each frame to the `FrameAnalyzer`.
  - Collects results and coordinates timing for CSV output.

### **FrameAnalyzer**
- **Purpose:** Handles frame-by-frame processing.
- **Responsibilities:**
  - Iterates recursively through frames located in `./sampleOutput/frames/*.png`.
  - Passes each frame to the `CentroidFinder` for analysis.
  - Manages time intervals between frames for timestamp logging.

### **CentroidFinder** - Postponed
- **Purpose:** Core logic to identify the largest centroid in a frame.
- **Responsibilities:**
  - Processes each image frame.
  - Finds the **largest object near the center** of the image.
  - Returns `(x, y)` coordinates of the centroid or `(-1, -1)` if none is found.

### **CSVWriter** - Postponed
- **Purpose:** Handles output of results.
- **Responsibilities:**
  - Writes timestamped centroid data into a `.csv` file.
  - Ensures the format:
    ```
    time_seconds, x_coordinate, y_coordinate
    ```
  - Handles missing data gracefully by writing `(-1, -1)` when no centroid is found.

---

## Wave 3: Packaging

Use **Maven Assembly Plugin** to package the project into a runnable `.jar` file.

### **Maven Plugin Configuration**

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-assembly-plugin</artifactId>
  <version>3.3.0</version>
  <configuration>
    <archive>
      <manifest>
        <mainClass>your.package.VideoProcessor</mainClass>
      </manifest>
    </archive>
    <descriptorRefs>
      <descriptorRef>jar-with-dependencies</descriptorRef>
    </descriptorRefs>
  </configuration>
  <executions>
    <execution>
      <id>make-assembly</id>
      <phase>package</phase>
      <goals>
        <goal>single</goal>
      </goals>
    </execution>
  </executions>
</plugin>
