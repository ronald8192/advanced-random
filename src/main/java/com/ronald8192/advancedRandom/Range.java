package com.ronald8192.advancedRandom;

import java.util.Optional;

/**
 * Number range
 * 22/07/2017.
 */
public class Range {

    private int minOccurrence = 0;
    private int lowerRange = 0;
    private int upperRange = 0;
    private Optional<Integer[]> possible = Optional.empty();

    /**
     *
     * @param lowerRange Lower range, inclusive
     * @param upperRange Upper range, inclusive
     * @param minOccurrence Minimum occurrence in generated sequence
     */
    public Range(int lowerRange, int upperRange, int minOccurrence) {
        this.minOccurrence = minOccurrence >= 0 ? minOccurrence : 0;

        if (upperRange >= lowerRange) {
            this.lowerRange = lowerRange;
            this.upperRange = upperRange;
        } else {
            this.lowerRange = upperRange;
            this.upperRange = lowerRange;
        }
    }

    public int getMinOccurrence() {
        return minOccurrence;
    }

    public int getLowerRange() {
        return lowerRange;
    }

    public int getUpperRange() {
        return upperRange;
    }

    public boolean inRange(int number){
        return number >= lowerRange && number <= upperRange;
    }

    public Integer[] toPossibleNumbersArray() {
        if (!possible.isPresent()) {
            Integer[] integers = new Integer[upperRange-lowerRange+1];
            for (int i = 0; i < integers.length; i++) {
                integers[i] = lowerRange+i;
            }
            possible = Optional.of(integers);
        }
        return possible.get();
    }
}
