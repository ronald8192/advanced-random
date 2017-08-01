package com.ronald8192.advancedRandom.demo;

import com.ronald8192.advancedRandom.NumberGenerator;
import com.ronald8192.advancedRandom.Range;
import com.ronald8192.advancedRandom.SequenceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Main class
 */
public class SequenceDemo {

    private static Logger log = LoggerFactory.getLogger(SequenceDemo.class);

    final private static char ESC = (char)27;

    static Range r1 = new Range(1, 5, 0);     // Total   5 possible number: { 1,2,3,4,5 }
    static Range r2 = new Range(11, 20, 0);   // Total  10 possible number: { 11,12,13,14,15,16,17,18,19,20 }
    static Range r3 = new Range(101, 200, 0); // Total 100 possible number: { 101,102,103 ... 198,199,200 }

    public static void main(String[] args) throws Exception {

        fixedLengthRandomByPossibleNumber();
        fixedLengthRandomByRangeThenNumber();
        variableLengthRandomByRangeThenNumber();

    }

    /**
     * Fix length
     * Random by possible number
     *   => all possible number have same occur probability
     *   => 5/115 chance to get number from `r1`, 10/115 chance to get number from `r2`, 100/115 chance to get number from `r3`
     * @throws Exception
     */
    static void fixedLengthRandomByPossibleNumber() throws Exception {
        System.out.println("Fixed length. Random by possible number:");

        SequenceGenerator seqGen = new SequenceGenerator();
        seqGen.setMode(SequenceGenerator.Mode.RANDOM_BY_POSSIBLE_NUMBER)
                .addRanges(r1, r2, r3);

        List<Integer> generated = seqGen.generateList(20, true);
        System.out.print("Generated: ");
        prettyPrint(generated);

        //easier to see the range distribution
        Collections.sort(generated);
        System.out.print("   Sorted: ");
        prettyPrint(generated);

        System.out.println("====================\n");
    }

    /**
     * Fix length
     * Random by range, then possible number
     *   => each range have same occur probability
     *   => 1/3 chance to get number from `r1`, 1/3 chance to get number from `r2`, 1/3 chance to get number from `r3`
     * @throws Exception
     */
    static void fixedLengthRandomByRangeThenNumber() throws Exception {
        System.out.println("Fixed length. Random by range, then number:");

        SequenceGenerator seqGen = new SequenceGenerator();
        seqGen.setMode(SequenceGenerator.Mode.RANDOM_BY_RANGE_THEN_NUMBER)
                .addRanges(r1, r2, r3);

        List<Integer> generated = seqGen.generateList(20, true);
        System.out.print("Generated: ");
        prettyPrint(generated);
        Collections.sort(generated);
        System.out.print("   Sorted: ");
        prettyPrint(generated);


        System.out.println("====================\n");
    }

    /**
     * Variable length
     * Random by range, then possible number
     *   => each range have same occur probability
     *   => 1/3 chance to get number from `r1`, 1/3 chance to get number from `r2`, 1/3 chance to get number from `r3`
     * @throws Exception
     */
    static void variableLengthRandomByRangeThenNumber() throws Exception {

        // 3+2+1 = minimum 6 number in sequence
        r1 = new Range(1, 5, 3);     // Total   5 possible number: { 1,2,3,4,5 }
        r2 = new Range(11, 20, 2);   // Total  10 possible number: { 11,12,13,14,15,16,17,18,19,20 }
        r3 = new Range(101, 200, 1); // Total 100 possible number: { 101,102,103 ... 198,199,200 }

        SequenceGenerator seqGen = new SequenceGenerator();
        seqGen.setMode(SequenceGenerator.Mode.RANDOM_BY_RANGE_THEN_NUMBER)
                .addRanges(r1, r2, r3);

        System.out.println("Variable length [6,6]. Random by range, then number:");
        List<Integer> generated = seqGen.generateList(6, false);
        System.out.print("Generated: ");
        prettyPrint(generated);
        Collections.sort(generated);
        System.out.print("   Sorted: ");
        prettyPrint(generated);

        System.out.println();

        System.out.println("Variable length [6,12]. Random by range, then number. Generate 3 times:");

        for (int i = 0; i < 3; i++) {
            System.out.println(i+1 + ":");
            generated = seqGen.generateList(12, false);
            System.out.print("Generated: ");
            prettyPrint(generated);
            Collections.sort(generated);
            System.out.print("   Sorted: ");
            prettyPrint(generated);
        }

        System.out.println("====================\n");
    }



    //helper
    private static void prettyPrint(List<Integer> generated) {
        if (generated.size() == 0) {
            System.out.print("[Empty List]");
        } else if (generated.size() == 1) {
            System.out.print("[ " + paintNumber(generated.get(0)) + " ]");
        } else {
            System.out.print("[ " + paintNumber(generated.get(0)));
            generated.subList(1, generated.size()).forEach(number -> {
                System.out.print(" , " + paintNumber(number));
            });
            System.out.print(" ]");
        }
        System.out.println();
    }

    private static String paintNumber(int num){
        if(r1.inRange(num)){
            return colorText(ColorCode.red, num);
        } else if(r2.inRange(num)) {
            return colorText(ColorCode.green, num);
        } else if(r3.inRange(num)) {
            return colorText(ColorCode.blue, num);
        } else {
            return num + "";
        }
    }

    /**
     * Get colored text.
     * @param colorCode ANSI color code, should use ColorCode class
     * @param str string
     * @return string with color
     */
    public static String colorText(int colorCode, String str){
        if(System.getProperty("os.name").toLowerCase().contains("win")){
            return str; //implement later
        }else{
            return ESC + "[" + colorCode + "m" + str + ESC + "[0m";
        }
    }
    //overload method
    public static String colorText(int colorCode, int num){
        return colorText(colorCode, num + "");
    }

    /**
     * ANSI color code
     */
    public interface ColorCode {
        /**Black*/
        int black   = 30;
        /**
         * Red
         */
        int red     = 31;
        /**
         * Grees
         */
        int green   = 32;
        /**
         * Yellow
         */
        int yellow  = 33;
        /**
         * Blue
         */
        int blue    = 34;
        /**
         * Magenta
         */
        int magenta = 35;
        /**
         * Cyan
         */
        int cyan    = 36;
        /**
         * White
         */
        int white   = 37;
    }

}
