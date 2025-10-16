import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DfsBinaryGroupFinder implements BinaryGroupFinder {
    /**
     * Finds connected pixel groups of 1s in an integer array representing a binary
     * image.
     * 
     * The input is a non-empty rectangular 2D array containing only 1s and 0s.
     * If the array or any of its subarrays are null, a NullPointerException
     * is thrown. If the array is otherwise invalid, an IllegalArgumentException
     * is thrown.
     *
     * Pixels are considered connected vertically and horizontally, NOT diagonally.
     * The top-left cell of the array (row:0, column:0) is considered to be
     * coordinate
     * (x:0, y:0). Y increases downward and X increases to the right. For example,
     * (row:4, column:7) corresponds to (x:7, y:4).
     *
     * The method returns a list of sorted groups. The group's size is the number
     * of pixels in the group. The centroid of the group
     * is computed as the average of each of the pixel locations across each
     * dimension.
     * For example, the x coordinate of the centroid is the sum of all the x
     * coordinates of the pixels in the group divided by the number of pixels in
     * that group.
     * Similarly, the y coordinate of the centroid is the sum of all the y
     * coordinates of the pixels in the group divided by the number of pixels in
     * that group.
     * The division should be done as INTEGER DIVISION.
     *
     * The groups are sorted in DESCENDING order according to Group's compareTo
     * method.
     * 
     * @param image a rectangular 2D array containing only 1s and 0s
     * @return the found groups of connected pixels in descending order
     */

    @Override
    public List<Group> findConnectedGroups(int[][] image) {
        if (image == null) {
            throw new NullPointerException("Image array is null");
        }
        int width = image[0].length;
        for (int[] row : image) {
            if (row == null) {
                throw new NullPointerException("Row in image is null");
            }
            if (row.length != width) {
                throw new IllegalArgumentException("Image is not rectangular");
            }
        }

        int[][] move = {
                { 0, 1 },
                { 0, -1 },
                { 1, 0 },
                { -1, 0 }
        };

        List<Group> tracker = new ArrayList<>();

        for (int col = 0; col < image.length; col++) {
            for (int row = 0; row < image[0].length; row++) {
                if (image[col][row] == 1) {
                    Coordinate curr = new Coordinate(col, row);
                    Group location = new Group(dfs(image, curr, move), curr);
                    tracker.add(location);
                }
            }
        }

        tracker.sort(Collections.reverseOrder());
        return tracker;
    }

    private static int dfs(int[][] grid, Coordinate curr, int[][] move) {
        if (curr.x() < 0 || curr.x() >= grid.length || curr.y() < 0 || curr.y() >= grid[0].length
                || grid[curr.x()][curr.y()] == 0) {
            return 0;
        }

        grid[curr.x()][curr.y()] = 0;
        int localCount = 1;

        for (int[] dir : move) {
            localCount += dfs(grid, new Coordinate(curr.x() + dir[0], curr.y() + dir[1]), move);
        }

        return localCount;
    }
}
