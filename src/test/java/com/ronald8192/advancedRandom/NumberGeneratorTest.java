package com.ronald8192.advancedRandom;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ronald8192 on 30/07/2017.
 */
public class NumberGeneratorTest {

    @Test
    public void testNextInt() throws Exception {
        NumberGenerator numberGenerator = new NumberGenerator();
        Range r1 = new Range(0,9,1);

        assertTrue(r1.inRange(numberGenerator.nextInt(r1)));
        assertTrue(r1.inRange(numberGenerator.nextInt(r1)));
        assertTrue(r1.inRange(numberGenerator.nextInt(r1)));
        assertTrue(r1.inRange(numberGenerator.nextInt(r1)));
        assertTrue(r1.inRange(numberGenerator.nextInt(r1)));
        assertTrue(r1.inRange(numberGenerator.nextInt(r1)));
        assertTrue(r1.inRange(numberGenerator.nextInt(r1)));
        assertTrue(r1.inRange(numberGenerator.nextInt(r1)));
        assertTrue(r1.inRange(numberGenerator.nextInt(r1)));
        assertTrue(r1.inRange(numberGenerator.nextInt(r1)));
    }
}