package com.ronald8192.advancedRandom.demo;

import com.ronald8192.advancedRandom.Range;
import com.ronald8192.advancedRandom.SequenceGenerator;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ronald8192 on 02/08/2017.
 */
public class ImageDemo {
    public static void main(String[] args) throws Exception {
        generateImages();
    }

    public static void generateImages() throws Exception {
        int width = 240;
        int height = 240;

        List<List<Integer>> normalColor = new ArrayList<>();
        List<List<Integer>> enhancedColor = new ArrayList<>();
        Range colorBit = new Range(0, 255, width);

        SequenceGenerator seqGen = new SequenceGenerator();

        Set<Range> enhanceColor = new HashSet<>();
        enhanceColor.add(new Range(0, 127, 0));
        enhanceColor.add(new Range(128, 191, 0));
        enhanceColor.add(new Range(192, 223, 0));
        enhanceColor.add(new Range(224, 255, 0));
        enhanceColor.add(new Range(224, 255, 0));
        enhanceColor.add(new Range(224, 255, 0));

        seqGen.setMode(SequenceGenerator.Mode.RANDOM_BY_RANGE_THEN_NUMBER)
                .clearRanges()
                .addRange(colorBit);
        for (int i = 0; i < height; i++) {
            normalColor.add(i, seqGen.generateList(width, true));
        }

        seqGen.setMode(SequenceGenerator.Mode.RANDOM_BY_RANGE_THEN_NUMBER)
                .clearRanges()
                .addAllRanges(enhanceColor);
        for (int i = 0; i < height; i++) {
            enhancedColor.add(i, seqGen.generateList(width, true));
        }


        int[] flattenedDataGrey = new int[width * height * 3];
        int[] flattenedDataRed = new int[width * height * 3];
        int[] flattenedDataGreen = new int[width * height * 3];
        int[] flattenedDataBlue = new int[width * height * 3];
        int[] flattenedDataCircle = new int[width * height * 3];
        BufferedImage imgGrey = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage imgRed = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage imgGreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage imgBlue = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage imgCircle = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int ind = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int greyShade = normalColor.get(i).get(j);
                flattenedDataGrey[ind + j * 3] = greyShade;
                flattenedDataGrey[ind + j * 3 + 1] = greyShade;
                flattenedDataGrey[ind + j * 3 + 2] = greyShade;

                flattenedDataRed[ind + j * 3] = enhancedColor.get(i).get(j);
                flattenedDataRed[ind + j * 3 + 1] = normalColor.get(i).get(j);
                flattenedDataRed[ind + j * 3 + 2] = normalColor.get(i).get(j);

                flattenedDataGreen[ind + j * 3] = normalColor.get(i).get(j);
                flattenedDataGreen[ind + j * 3 + 1] = enhancedColor.get(i).get(j);
                flattenedDataGreen[ind + j * 3 + 2] = normalColor.get(i).get(j);

                flattenedDataBlue[ind + j * 3] = normalColor.get(i).get(j);
                flattenedDataBlue[ind + j * 3 + 1] = normalColor.get(i).get(j);
                flattenedDataBlue[ind + j * 3 + 2] = enhancedColor.get(i).get(j);


                int offset = width/4;
                int r, g, b;
                if (Double.compare((i - offset)   * (i - offset)   + (j - offset)   * (j - offset),   width/10 * width/10) < 0.1 ||
                    Double.compare((i - offset*3) * (i - offset*3) + (j - offset)   * (j - offset),   width/10 * width/10) < 0.1 ||
                    Double.compare((i - offset)   * (i - offset)   + (j - offset*3) * (j - offset*3), width/10 * width/10) < 0.1 ||
                    Double.compare((i - offset*3) * (i - offset*3) + (j - offset*3) * (j - offset*3), width/10 * width/10) < 0.1 ||
                    Double.compare((i - offset*2) * (i - offset*2) + (j - offset*2) * (j - offset*2), width/5  * width/5 ) < 0.1
                        ) {
                    r = normalColor.get(i).get(j);
                    g = enhancedColor.get(i).get(j);
                    b = normalColor.get(i).get(j);
                } else {
                    r = normalColor.get(i).get(j);
                    g = normalColor.get(i).get(j);
                    b = enhancedColor.get(i).get(j);
                }
                if (Math.abs(i - j) < 8 || Math.abs(i + j - width) < 8) {
                    r = enhancedColor.get(i).get(j);
                    g = normalColor.get(i).get(j);
                    b = normalColor.get(i).get(j);
                }
                normalColor.get(i).get(j);
                flattenedDataCircle[ind + j * 3] = r;
                flattenedDataCircle[ind + j * 3 + 1] = g;
                flattenedDataCircle[ind + j * 3 + 2] = b;
            }
            ind += height * 3;
        }


        imgGrey.getRaster().setPixels(0, 0, width, height, flattenedDataGrey);
        imgRed.getRaster().setPixels(0, 0, width, height, flattenedDataRed);
        imgGreen.getRaster().setPixels(0, 0, width, height, flattenedDataGreen);
        imgBlue.getRaster().setPixels(0, 0, width, height, flattenedDataBlue);
        imgCircle.getRaster().setPixels(0, 0, width, height, flattenedDataCircle);

        JLabel jLabelGrey = new JLabel(new ImageIcon(imgGrey));
        JLabel jLabelRed = new JLabel(new ImageIcon(imgRed));
        JLabel jLabelGreen = new JLabel(new ImageIcon(imgGreen));
        JLabel jLabelBlue = new JLabel(new ImageIcon(imgBlue));
        JLabel jLabelCircle = new JLabel(new ImageIcon(imgCircle));

        JPanel jPanel = new JPanel();
        jPanel.add(jLabelGrey);
        jPanel.add(jLabelRed);
        jPanel.add(jLabelGreen);
        jPanel.add(jLabelBlue);
        jPanel.add(jLabelCircle);
        JFrame r = new JFrame();
        r.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        r.add(jPanel);
        r.pack();
        r.setVisible(true);
        r.setLocationRelativeTo(null);
        r.show();
    }
}
