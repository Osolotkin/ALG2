package utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Just some static methods, that could be useful in more than one class.
 * @author Maxim Osolotkin
 */

public class Util {

    private Util() {}

    /**
     * Returns an Image object according to the given path. Does not care about existence of the image.
     * @param flName a relative path to the image file.
     * @return the image according to the given path.
     */
    public static Image imgLoad(String flName) {
        ImageIcon iia = new ImageIcon(flName);
        return iia.getImage();
    }

    /**
     * Returns a Font object according to the given path and size, if there is no font at given path, sets it to the
     * DialogInput font, style 1 and size 10.
     * @param path a relative path to the font file(.ttf).
     * @return created Font object.
     */
    public static Font createFont(String path, int size) {
        Font font = new Font(null);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont((float)size);
        } catch (IOException |FontFormatException e) {
            System.out.println("Wrong path or incompatibile size value!");
            System.out.println("Setting font to the DialogInput, style: 1, size: 10");
            font = new Font("DialogInput",1,10);
        }
        return font;
    }

    /**
     * Using to sort array of records from the highest to the lowest ones.
     * @param array array of same objects that implements CompareInterface.
     */
    public static void sortByScore(CompareInterface[] array) {
        for(int i = 0; i < array.length; i++) {
            for(int j = 1; j < array.length; j++) {
                if(!array[j-1].isHigher(array[j])){
                    CompareInterface tmp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = tmp;
                }
            }
        }
    }

    /**
     * Using to sort array of records by names the highest to the lowest ones.
     * @param array array of Records.
     */
    public static void sortByName(Record [] array) {
        Arrays.sort(array, new RecordsByNameComparator());
    }

}
