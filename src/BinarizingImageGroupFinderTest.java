import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BinarizingImageGroupFinderTest {

    @Test
    public void testBinarizingImageGroupFinderTestNullImageBinarizer() {
        ImageBinarizer binarizer = null;
        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();

        assertThrows(NullPointerException.class, () -> {
            new BinarizingImageGroupFinder(binarizer, groupFinder);
        });
    }

    @Test
    public void testBinarizingImageGroupFinderTestNullGroupFinder() {
        ImageBinarizer binarizer = new DistanceImageBinarizer(null, 0, 0);
        BinaryGroupFinder groupFinder = null;

        assertThrows(NullPointerException.class, () -> {
            new BinarizingImageGroupFinder(binarizer, groupFinder);
        });
    }
}
