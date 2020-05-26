package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import utils.Util;

/**
 * Class that is suppose to render game field, generally have just one method - repaint(), that does all stuff according
 * to the variables. There is not to much to set, so it could be hardly used separated
 * @author Maxim Osolotkin
 */
public class GameField extends JPanel {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //-------------------------------------------------Global variables-----------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //============================variables that cares about resolution and connected stuff===========================//
    private int width;          //width of a game field in pixels
    private int height;         //width of a game field in pixels
    private int xSc;            //rate of scale for horizontal direction
    private int ySc;            //rate of scale for vertical direction
    private int dfBlSz;         //default size of a block in pixels (default ratio is 12 blocks x 16 blocks)
    private int mapWd;
    private int mapHg;

    //=================================== variables that are for animations purposes ==================================//
    private int pauseAnim;              //count cycles in Animation, while game is on pause, to animate something
    private int yAnimOver;              //count cycles in Animation, when is game over, to animate something
    private boolean rowElemAnimation;   //if true, row elimination animation will be played, depending on values bellow
    private int rowY;                   //stores info which row is the most upper to eliminate
    private int yAnim;                  //stores info how many rows need to be eliminated
    private int x1Anim;                 //stores info which block of row from the left part is eliminating
    private int x2Anim;                 //stores info which block of row from the right part is eliminating
    private int [][] mapReserv;         //copy of a field for animation purposes
    private boolean [] rowsElim;   //each index indicates according row, true means that row has to be eliminated

    //====================================== variables that stores useful paths ======================================//
    private String clPath;         //path to the folder where are stored colored blocks
    private String levelPath;      //path that specify folder where are stored block depending on level
    private String GameOverPath;   //path to the game over image, that is displayed in the end of the game
    private String WallPath;       //path to the wall block
    private String Back;           //path to the background image
    private String figureColor;    //path that specify figure.

    //============================ variables that stores some important game conditions ===============================//
    private int level;             //form zero to ten
    private boolean gameOver;      //determine if game is over or not, true for yes, false for no
    private int [][] map;          //stores info about field, 1 - active figure, 0 - wall, >1 - already stacked blocks

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------------------There are some setters--------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setClPath(String x) {
        this.clPath = x;
    }

    public void setState(ArrayList ar){
        if (!gameOver) {
            map = (int[][]) ar.get(0);
            mapReserv = (int[][]) ar.get(1);
        }
        rowElemAnimation = (boolean) ar.get(2);
        x1Anim = (int) ar.get(3);
        x2Anim = (int) ar.get(4);
        gameOver = (boolean) ar.get(5);
        pauseAnim = (int) ar.get(6);
        figureColor = (String) ar.get(7);
        level = (int) ar.get(8);
        rowY = (int) ar.get(9);
        yAnim = (int) ar.get(10);
        yAnimOver = (int) ar.get(11);
        rowsElim = ((boolean []) ar.get(12)).clone();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //---------------------------------------Some code that cares about render----------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Main function that cares about render using paintComponent and Graphic.
     * @param  g  instance of Graphics that do some black magic.
     */
    protected void paintComponent(Graphics g) {

        Image img;

        xSc = width/240;
        ySc = height/320;
        if (clPath.equals("Coloured/")) levelPath = "Level" + level + "/";
        else levelPath = "";

        super.paintComponent(g);

        img = Util.imgLoad(WallPath);
        for (int i = 0; i < mapHg*dfBlSz*ySc; i += dfBlSz*ySc) {
            g.drawImage(img, 0, i, dfBlSz*xSc, dfBlSz*ySc, this);
        }
        for (int i = 0; i < mapHg*dfBlSz*ySc; i += dfBlSz*ySc) {
            g.drawImage(img, (mapWd-1)*dfBlSz*xSc, i, dfBlSz*xSc, dfBlSz*ySc, this);
        }
        img = Util.imgLoad(Back);
        g.drawImage(img,dfBlSz*xSc,0, xSc*img.getWidth(null), ySc*img.getHeight(null), this);

        if (!rowElemAnimation) {

            for (int i = 0; i < mapHg*dfBlSz*ySc; i += dfBlSz*ySc) {
                for (int j = 1*dfBlSz*xSc; j < (mapWd-1)*dfBlSz*xSc; j += dfBlSz*xSc) {
                    if (map[i/(dfBlSz*ySc)][j/(dfBlSz*xSc)] == 1) {
                        img = Util.imgLoad("src/Sprites/" + clPath + levelPath + figureColor + "Block.png");
                        g.drawImage(img, j, i, dfBlSz*xSc, dfBlSz*ySc,this);
                    }

                    String [] cl = {"Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Rose"};

                    for (int k = 0; k < cl.length; k++) {
                        if (map[i/(dfBlSz*ySc)][j/(dfBlSz*xSc)] == 11 + k) {
                            img = Util.imgLoad("src/Sprites/" + clPath + levelPath + cl[k] + "Block.png");
                            g.drawImage(img, j, i, dfBlSz*xSc, dfBlSz*ySc,this);
                        }
                    }

                    if (map[i/(dfBlSz*ySc)][j/(dfBlSz*xSc)] == 88) {
                        img = Util.imgLoad("src/Sprites/White.png");
                        g.drawImage(img, j, i, dfBlSz*xSc, dfBlSz*ySc,this);
                    }

                }
            }

        }else {

            for (int i = 0; i < mapHg*dfBlSz*ySc; i += dfBlSz*ySc){
                for (int j = 1; j < (mapWd-1)*dfBlSz*xSc; j += dfBlSz*xSc) {
                    if (mapReserv[i/(dfBlSz*ySc)][j/(dfBlSz*xSc)] == 1) {
                        img = Util.imgLoad("src/Sprites/" + clPath + levelPath + figureColor + "Block.png");
                        g.drawImage(img, j, i, dfBlSz*xSc, dfBlSz*ySc,this);
                    }

                    String [] cl = {"Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Rose"};

                    for (int k = 0; k < cl.length; k++) {
                        if (mapReserv[i/(dfBlSz*ySc)][j/(dfBlSz*xSc)] == 11 + k) {
                            img = Util.imgLoad("src/Sprites/" + clPath + levelPath + cl[k] + "Block.png");
                            g.drawImage(img, j, i, dfBlSz*xSc, dfBlSz*ySc,this);
                        }
                    }

                }
            }

             img = Util.imgLoad("src/Sprites/BackBlock.png");
             for (int i = 0; i < rowsElim.length; i++) {
                 for (int j = x1Anim*dfBlSz*xSc; j < x2Anim*dfBlSz*xSc; j += dfBlSz*xSc) {
                     if (rowsElim[i]) {
                         g.drawImage(img, j, i * dfBlSz * ySc, dfBlSz * xSc, dfBlSz * ySc, this);
                     }
                 }
             }

             img = Util.imgLoad("src/Sprites/White.png");
             for (int i = 0; i < rowsElim.length; i++) {
                 if (rowsElim[i]) {
                     g.drawImage(img, dfBlSz * x1Anim * xSc, i * dfBlSz * ySc, dfBlSz * xSc, dfBlSz * ySc, this);
                     g.drawImage(img, dfBlSz * x2Anim * xSc, i * dfBlSz * ySc, dfBlSz * xSc, dfBlSz * ySc, this);
                 }
             }

         }

        if (gameOver) {

            if (yAnimOver > 15) {

                img = Util.imgLoad(GameOverPath);
                g.drawImage(img, xSc*dfBlSz*2, ySc*dfBlSz*2, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                if (yAnimOver%16 > 7) {
                    img = Util.imgLoad("src/Sprites/afterGame.png");
                    g.drawImage(img, xSc*50, ySc*dfBlSz*7, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                }

            }else {

                img = Util.imgLoad("src/Sprites/White.png");
                for (int i = dfBlSz*xSc; i < 11*dfBlSz*xSc; i += dfBlSz*xSc) {
                    g.drawImage(img, i, yAnimOver*dfBlSz*ySc, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                    map[yAnimOver][i/(dfBlSz*xSc)] = 0;
                }

            }

        }

        if (pauseAnim%16 > 7) {
            img = Util.imgLoad("src/Sprites/Pause.png");
            g.drawImage(img, dfBlSz*4*xSc + 5*xSc, dfBlSz*7*ySc, xSc*img.getWidth(null),ySc*img.getHeight(null), this);
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //---------------------------------------------------Constructor--------------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates ui.GameField
     * @param  width  integer, sets width.
     * @param  height integer, sets height.
     */
    public GameField(int width, int height) {

        this.width = width;
        this.height = height;
        mapWd = 12;
        mapHg = 16;
        mapReserv = new int [mapHg][mapWd];
        map = new int [mapHg][mapWd];
        dfBlSz = 20;

        clPath = "Coloured/";
        GameOverPath = "src/Sprites/GameOverV2.png";
        WallPath = "src/Sprites/Wall.png";
        Back = "src/Sprites/Back.png";
        levelPath = "Level0/";

    }

}
