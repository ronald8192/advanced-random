package com.ronald8192.advancedRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;

/**
 * Random number generator, using the Java SecureRandom API.
 * Generate a number by given range.
 * @see Range
 * 22/07/2017
 */
public class NumberGenerator {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    SecureRandom secureRandom = new SecureRandom();

    /**
     * Generate integer within given range (inclusive)
     * @param range given range
     * @return the random number
     */
    public int nextInt(Range range) {
        return secureRandom.nextInt(range.getUpperRange() - range.getLowerRange() + 1) + range.getLowerRange();
    }
}
