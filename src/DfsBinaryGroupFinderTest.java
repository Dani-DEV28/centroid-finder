import static org.junit.Assert.*;

import java.util.ArrayList;
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
    void testFindConnectedGroupsNullList() {
        int[][] image = {
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 }
        };

        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        assertThrows(NullPointerException.class, () -> groupFinder.findConnectedGroups(image));
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
}
