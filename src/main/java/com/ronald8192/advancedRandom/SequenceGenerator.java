package com.ronald8192.advancedRandom;

import java.util.*;

/**
 * Generate sequence of numbers by given Ranges
 * @see Range
 * 22/07/2017.
 */
public class SequenceGenerator extends NumberGenerator {
    private Set<Range> ranges = new HashSet<>();
    private Mode mode = Mode.RANDOM_BY_RANGE_THEN_NUMBER;
    private Optional<Integer[]> allPossibleNumberCache;

    public SequenceGenerator(Set<Range> ranges) {
        this.ranges = ranges;
    }

    public SequenceGenerator() {
    }

    public SequenceGenerator setMode(Mode mode) {
        this.mode = mode;
        return this;
    }

    public SequenceGenerator addRange(Range range) {
        this.ranges.add(range);
        allPossibleNumberCache = Optional.empty();
        return this;
    }

    public SequenceGenerator addAllRanges(Set<Range> ranges) {
        this.ranges.addAll(ranges);
        allPossibleNumberCache = Optional.empty();
        return this;
    }

    public SequenceGenerator addRanges(Range... ranges) {
        for (Range range : ranges) {
            this.ranges.add(range);
        }
        allPossibleNumberCache = Optional.empty();
        return this;
    }

    public SequenceGenerator removeRange(Range range) {
        this.ranges.remove(range);
        allPossibleNumberCache = Optional.empty();
        return this;
    }

    public SequenceGenerator clearRanges(){
        this.ranges.removeAll(ranges);
        return this;
    }

    public boolean hasRange(Range range) {
        return this.ranges.contains(range);
    }

    public int minLength() {
        return this.ranges.stream().mapToInt(Range::getMinOccurrence).sum();
    }

    /**
     * Generate list of random number by given ranges.
     *
     * @param preferredLength If `fixedLength` is true, it indicates the preferred length, the length you want the generated sequence to be.
     *                        If `preferredLength` is smaller than minimum length, a sequence of minimum length wil be generated. (Minimum length is the sum of minimum occurrence is all ranges)
     *
     *                        If `fixedLength` is false, it indicates the maximum length (length limit of the generated sequence).
     * @param fixLength If true, generated sequence length will be the grater one between { minimum length, preferredLength }.
     *                  If false, generated sequence length will vary between minimum length and preferredLength.
     * @return Generated random number list
     * @throws Exception When no range is added to the sequence generator.
     */
    public List<Integer> generateList(int preferredLength, boolean fixLength) throws Exception {
        if(ranges.size() == 0) throw new Exception("No available number range.");

        int minLength = minLength();
        preferredLength = preferredLength < minLength ? minLength : preferredLength;

        List<Integer> sequence = new ArrayList<>();

        //generateList all must exist range
        this.ranges.forEach(range -> {
            for (int i = 0; i < range.getMinOccurrence(); i++) {
                sequence.add(nextInt(range));
            }
        });

        int generated = sequence.size();

        int availableSpace = preferredLength - generated;
        if(!fixLength){
            //random length
            availableSpace = nextInt(new Range(0, availableSpace, 0));
        }

        if (mode.equals(Mode.RANDOM_BY_RANGE_THEN_NUMBER)) {
            //randomly pick ranges to fill up the space

            //available space
            for (int i = 0; i < availableSpace; i++) {
                //pick a range
                int selectedIndex = nextInt(new Range(0, this.ranges.size() - 1, 0));
                int j = 0;
                for (Range range : ranges) {
                    if (j == selectedIndex) {
                        //get a random number from that range
                        sequence.add(nextInt(range));
                        break;
                    } else {
                        j++;
                    }
                }
            }
        } else {

            if (!allPossibleNumberCache.isPresent()) {
                buildAllPossibleNumberCache();
            }

            //randomly pick ranges to fill up the space
            for (int i = 0; i < availableSpace; i++) {
                int selectedIndex = nextInt(new Range(0, allPossibleNumberCache.get().length - 1, 0));
                sequence.add(allPossibleNumberCache.get()[selectedIndex]);
            }
        }

        Collections.shuffle(sequence, secureRandom);

        return sequence;
    }

    private void buildAllPossibleNumberCache() {
        int size = ranges.stream().mapToInt(range -> range.getUpperRange() - range.getLowerRange() + 1).sum();
        allPossibleNumberCache = Optional.of(new Integer[size]);
        final int[] nextSpace = {0};
        ranges.forEach(range -> {
            System.arraycopy(
                    range.toPossibleNumbersArray(),
                    0,
                    allPossibleNumberCache.get(),
                    nextSpace[0],
                    range.toPossibleNumbersArray().length
            );
            nextSpace[0] += range.toPossibleNumbersArray().length;
        });
    }

    public enum Mode {
        RANDOM_BY_RANGE_THEN_NUMBER,
        RANDOM_BY_POSSIBLE_NUMBER,
    }

}
