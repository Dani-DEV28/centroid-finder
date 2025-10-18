import static org.junit.Assert.*;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DfsBinaryGroupFinderTest {
    @Test
    void testFindConnectedGroupsOneGroupSizeOne() {
        int[][] image = {
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 }
        };

        Group testGroup1 = new Group(1, new Coordinate(3, 2));
        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        List<Group> groups = groupFinder.findConnectedGroups(image);

        assertTrue(testGroup1.equals(groups.get(0)));
    }

    @Test
    void testNullImageThrowsNullPointerException() {
        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        assertThrows(NullPointerException.class, () -> {
            groupFinder.findConnectedGroups(null);
        });
    }

    @Test
    void testNullRowThrowsNullPointerException() {
        int[][] image = {
                { 1, 0, 1 },
                null,
                { 0, 1, 0 }
        };

        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        assertThrows(NullPointerException.class, () -> {
            groupFinder.findConnectedGroups(image);
        });
    }

    @Test
    void testNonRectangularArrayThrowsIllegalArgumentException() {
        int[][] image = {
                { 1, 0, 1 },
                { 0, 1 }
        }; // shorter row

        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        assertThrows(IllegalArgumentException.class, () -> {
            groupFinder.findConnectedGroups(image);
        });
    }

    @Test
    void testFindConnectedGroupsIllegalImage1() {
        int[][] image = {
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, -1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 }
        };

        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        assertThrows(IllegalArgumentException.class, () -> groupFinder.findConnectedGroups(image));
    }

    @Test
    void testFindConnectedGroupsIllegalImage2() {
        int[][] image = {
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 3, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 }
        };

        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> groupFinder.findConnectedGroups(image));
        System.out.println(exception);
    }

    @Test
    void testFindConnectedGroupsOneGroupSizeFour() {
        int[][] image = {
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 1, 0, 0 },
                { 0, 0, 0, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 }
        };

        Group testGroup1 = new Group(4, new Coordinate(3, 2));
        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        List<Group> groups = groupFinder.findConnectedGroups(image);

        assertTrue(testGroup1.equals(groups.get(0)));
    }

    @Test
    void testFindConnectedGroupsTwo() {
        int[][] image = {
                { 1, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 1, 0, 0 },
                { 0, 0, 0, 1, 1, 0, 0 },
                { 0, 0, 0, 0, 1, 0, 0 }
        };

        Group testGroup1 = new Group(5, new Coordinate(3, 2));
        Group testGroup2 = new Group(3, new Coordinate(0, 0));
        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        List<Group> groups = groupFinder.findConnectedGroups(image);

        assertTrue(testGroup1.equals(groups.get(0)));
        assertTrue(testGroup2.equals(groups.get(1)));
    }

    @Test
    void testFindConnectedGroupsFourSameSize() {
        int[][] image = {
                { 1, 1, 0, 0, 0, 1, 1 },
                { 1, 1, 0, 0, 0, 1, 1 },
                { 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 0, 1, 1 },
                { 1, 1, 0, 0, 0, 1, 1 }
        };

        Group testGroup1 = new Group(4, new Coordinate(0, 0));
        Group testGroup2 = new Group(4, new Coordinate(5, 0));
        Group testGroup3 = new Group(4, new Coordinate(0, 3));
        Group testGroup4 = new Group(4, new Coordinate(5, 3));
        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        List<Group> groups = groupFinder.findConnectedGroups(image);

        assertTrue(testGroup4.equals(groups.get(0)));
        assertTrue(testGroup2.equals(groups.get(1)));
        assertTrue(testGroup3.equals(groups.get(2)));
        assertTrue(testGroup1.equals(groups.get(3)));
    }
}
