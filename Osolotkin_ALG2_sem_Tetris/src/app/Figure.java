package app;

import java.util.Random;

/**
 * figure is square made object, that is filled in the block of 2 rows and 4 columns by default
 * center of a figure is between 2th and 3th columns, so the maximum right/left length is 2
 * top and bottom height of a figure means how much figure exceeds top or bottom default barrier(2 rows), maximum
 * value is 2
 * @author Maxim Osolotkin
 */
public class Figure {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //-------------------------------------------------Global variables-----------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private int code;             //specific id for each figure, 11 - I, 12 - O, 13 - T, 14 - L, 15 - Z, 16 - S, 17 - J
    private char type;            //type of a figure in char, that stores exactly the same char figure supposed to be
    private int lengthRight;      //length of a figures right side
    private int lengthLeft;       //length of a figures left side
    private int heightTop;        //height of a figures top part
    private int heightBottom;     //height of a figures bottom part
    private int rotCount;         //count of a rotations of a figure, to know in which phase figure is
    private String color;         //color of a figure in string for easier usage of sprites for figure
    private int x;                //x cord of a figures first row and first column
    private int y;                //y cord of a figures first row and first column
    private int [] arr;           //array that stores figure, 0-3 indexes for upper row, 4-7 for bottom,
                                  // 1 - square is part of a figure, 0 - is not part of a figure

    /**
     *Creates empty figure with all values setted to zero.
     */
    public Figure() {
        x = 4;
        y = 0;
        heightBottom = 0;
        heightTop = 0;
        rotCount = 0;
        arr = new int [8];
    }

    private Figure(
            int code, char type, int lengthRight, int lengthLeft, int heightTop, int heightBottom, int rotCount,
            String color, int x, int y, int [] arr
    ) {

        this.code = code;
        this.type = type;
        this.lengthLeft = lengthLeft;
        this.lengthRight = lengthRight;
        this.heightTop = heightTop;
        this.heightBottom = heightBottom;
        this.rotCount = rotCount;
        this.color = color;
        this.x = x;
        this.y = y;
        this.arr = arr.clone();

    }

    public Figure clone() {
        return new Figure(code, type, lengthRight, lengthLeft, heightTop, heightBottom, rotCount, color, x, y, arr);
    }

    /**
     *Pick one figure(I,O,T,L,Z,S,J) and replace current figure with it.
     */
    public void generateFigure() {

        Random rnd = new Random();
        int n = rnd.nextInt(7);

        if (n == 0){
            arr[0] = 0; arr[1] = 0; arr[2] = 0; arr[3] = 0;
            arr[4] = 1; arr[5] = 1; arr[6] = 1; arr[7] = 1;
            type = 'I';
            color = "Red";
            lengthRight = 2;
            lengthLeft = 2;
            code = 11;
        }
        if (n == 1){
            arr[0] = 0; arr[1] = 1; arr[2] = 1; arr[3] = 0;
            arr[4] = 0; arr[5] = 1; arr[6] = 1; arr[7] = 0;
            type = 'O';
            color = "Blue";
            lengthRight = 1;
            lengthLeft = 1;
            code = 12;
        }
        if (n == 2){
            arr[0] = 0; arr[1] = 1; arr[2] = 0; arr[3] = 0;
            arr[4] = 1; arr[5] = 1; arr[6] = 1; arr[7] = 0;
            type = 'T';
            color = "Green";
            lengthRight = 1;
            lengthLeft = 2;
            code = 13;
        }
        if (n == 3){
            arr[0] = 0; arr[1] = 0; arr[2] = 1; arr[3] = 0;
            arr[4] = 1; arr[5] = 1; arr[6] = 1; arr[7] = 0;
            type = 'L';
            color = "Yellow";
            lengthRight = 1;
            lengthLeft = 2;
            code = 14;
        }
        if (n == 4){
            arr[0] = 1; arr[1] = 1; arr[2] = 0; arr[3] = 0;
            arr[4] = 0; arr[5] = 1; arr[6] = 1; arr[7] = 0;
            type = 'Z';
            color = "Purple";
            lengthRight = 1;
            lengthLeft = 2;
            code = 15;
        }
        if (n == 5){
            arr[0] = 0; arr[1] = 1; arr[2] = 1; arr[3] = 0;
            arr[4] = 1; arr[5] = 1; arr[6] = 0; arr[7] = 0;
            type = 'S';
            color = "Orange";
            lengthRight = 1;
            lengthLeft = 2;
            code = 16;
        }
        if (n == 6){
            arr[0] = 1; arr[1] = 0; arr[2] = 0; arr[3] = 0;
            arr[4] = 1; arr[5] = 1; arr[6] = 1; arr[7] = 0;
            type = 'J';
            color = "Rose";
            lengthRight = 1;
            lengthLeft = 2;
            code = 17;
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------------------There are some setters--------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRotCount(int rotCount) {
        this.rotCount = rotCount;
    }

    public void setHeightTop(int heightTop) {
        this.heightTop = heightTop;
    }

    public void setHeightBottom(int heightBottom) {
        this.heightBottom = heightBottom;
    }

    public void setLengthLeft(int lengthLeft) {
        this.lengthLeft = lengthLeft;
    }

    public void setLengthRight(int lengthRight) {
        this.lengthRight = lengthRight;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------------------There are some getters--------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getColor() {
        return color;
    }

    public int getRotCount() {
        return rotCount;
    }

    public int getCode() {
        return code;
    }

    public int getlengthLeft() {
        return lengthLeft;
    }

    public int getlengthRight() {
        return lengthRight;
    }

    public int getHeightTop() {
        return heightTop;
    }

    public int getHeightBottom() {
        return heightBottom;
    }

    public int[] getArr() {
        return arr;
    }

    public char getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
