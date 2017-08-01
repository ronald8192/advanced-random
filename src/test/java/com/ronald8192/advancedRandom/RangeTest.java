package com.ronald8192.advancedRandom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ronald8192 on 30/07/2017.
 */
public class RangeTest {

    Range r1;
    Range r2;
    Range r3;

    @Before
    public void setUp() throws Exception {
        r1 = new Range(5, 0, 0); // min max reverse, should fix automatically
        r2 = new Range(-200, -100, 7);
        r3 = new Range(19, 24, 6);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLowerBound() throws Exception {
        assertEquals(0, r1.getLowerRange());
        assertEquals(-200, r2.getLowerRange());
        assertEquals(19, r3.getLowerRange());
    }

    @Test
    public void testUpperBound() throws Exception {
        assertEquals(5, r1.getUpperRange());
        assertEquals(-100, r2.getUpperRange());
        assertEquals(24, r3.getUpperRange());
    }

    @Test
    public void testMinOccurrence() throws Exception {
        assertEquals(0, r1.getMinOccurrence());
        assertEquals(7, r2.getMinOccurrence());
        assertEquals(6, r3.getMinOccurrence());
    }

    @Test
    public void testInRange() throws Exception {
        assertFalse(r1.inRange(-1));
        assertTrue(r1.inRange(0));
        assertTrue(r1.inRange(1));
        assertTrue(r1.inRange(4));
        assertTrue(r1.inRange(5));
        assertFalse(r1.inRange(6));

        assertFalse(r2.inRange(-201));
        assertTrue(r2.inRange(-200));
        assertTrue(r2.inRange(-199));
        assertTrue(r2.inRange(-101));
        assertTrue(r2.inRange(-100));
        assertFalse(r2.inRange(-99));

        assertFalse(r3.inRange(18));
        assertTrue(r3.inRange(19));
        assertTrue(r3.inRange(20));
        assertTrue(r3.inRange(23));
        assertTrue(r3.inRange(24));
        assertFalse(r3.inRange(25));
    }

    @Test
    public void testToPossibleNumbersArray() throws Exception {
        Integer[] numArray1 = r1.toPossibleNumbersArray();
        assertEquals(numArray1.length, (r1.getUpperRange() - r1.getLowerRange() +1));
        for (int i = 0; i < numArray1.length; i++) {
            assertEquals(r1.getLowerRange() + i, numArray1[i].intValue());
        }

        Integer[] numArray2 = r2.toPossibleNumbersArray();
        assertEquals(numArray2.length, (r2.getUpperRange() - r2.getLowerRange() +1));
        for (int i = 0; i < numArray2.length; i++) {
            assertEquals(r2.getLowerRange() + i, numArray2[i].intValue());
        }

        Integer[] numArray3 = r3.toPossibleNumbersArray();
        assertEquals(numArray3.length, (r3.getUpperRange() - r3.getLowerRange() +1));
        for (int i = 0; i < numArray3.length; i++) {
            assertEquals(r3.getLowerRange() + i, numArray3[i].intValue());
        }
    }
}