package com.example.javaautocad;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleTest {

    @Test
    public void testBasic() {
        assertTrue(true);
    }

    @Test
    public void testStringOperations() {
        String test = "AutoCAD";
        assertEquals("AutoCAD", test);
        assertNotNull(test);
        assertTrue(test.length() > 0);
    }

    @Test
    public void testMath() {
        int result = 2 + 2;
        assertEquals(4, result);
    }
}
