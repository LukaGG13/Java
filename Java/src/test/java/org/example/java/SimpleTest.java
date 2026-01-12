package org.example.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleTest {

    @Test
    void simpleTruth() {
        assertTrue(true); // should always pass
    }

    @Test
    void simpleEquality() {
        assertEquals(2, 1 + 1);
    }
}
