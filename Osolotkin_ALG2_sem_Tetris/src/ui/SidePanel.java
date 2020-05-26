package ui;

import utils.Util;
import utils.Achievement;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class that is suppose to render side panel, time to time calculate something, but mainly could not be used as
 * something standalone, cause there is not much to set
 * @author Maxim Osolotkin
 */
public class SidePanel extends JPanel {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //-------------------------------------------------Global variables-----------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //=========================== variables that cares about resolution and connected stuff ==========================//
    private int width;          //width of a game field in pixels
    private int height;         //width of a game field in pixels
    private int xSc;            //rate of scale for horizontal direction
    private int ySc;            //rate of scale for vertical direction
    private int dfBlSz;         //default size of a block in pixels (default ratio is 12 blocks x 16 blocks)

    //========================================= variables that stores info ===========================================//
    private int score;              //current players score
    private int level;              //current level
    private int timerSpeed;         //speed of timer is somehow depended on it
    private int s = 0;              //seconds
    private int min = 0;            //minutes

    //====================================== variables that stores useful paths ======================================//
    private String levelPath;           //path that specify folder where are stored block depending on level
    private String color;               //path that specify figure.
    private String clPath;              //path to the folder where are stored colored blocks

    //==================================== Some labels that display useful info ======================================//
    private JLabel displTime = new JLabel();    //label that cares about time
    private JLabel Time = new JLabel();         //label that cares about time logo
    private JLabel countScore = new JLabel();   //label that cares about score
    private JLabel Score = new JLabel();        //label that cares about score logo
    private JLabel nxtFigure = new JLabel();    //label that cares about next figure
    private JLabel displLevel = new JLabel();   //label that cares about level

    //============================================== Something else ==================================================//
    private boolean pause;                      //if pause is true everything tries to freeze otherwise no
    private Font font;                          //variable that stores font
    private final String fontPath = "src/Fonts/kongtext.ttf";  //path to the font
    private Achievement[] ach;                  //stores achievements needed stuff, more info in Achievement class
    private int [] nextFigure = new int [8];    //stores layout of figure

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------------------There are some getters--------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int [] getAchInt() {
        int [] tmp = new int [ach.length];
        for (int i = 0; i < ach.length; i++) {
            tmp[i] = ach[i].getStateInt();
        }
        return tmp;
    }

    public boolean [] getAchBoolean() {
        boolean [] tmp = new boolean [ach.length];
        for (int i = 0; i < ach.length; i++) {
            tmp[i] = ach[i].getStateBoolean();
        }
        return tmp;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------------------There are some setters--------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setTimerSpeed(int timerSpeed) {
        this.timerSpeed = timerSpeed;
    }

    public void setState(ArrayList ar){
        nextFigure = (int []) ar.get(0);
        color = (String) ar.get(1);
        score = (int) ar.get(2);
        level = (int) ar.get(3);
        pause = (boolean) ar.get(4);
    }

    public void setAchInt(int [] st) {
        for(int i = 0; i < ach.length; i++) {
            this.ach[i].setStateInt(st[i]);
        }
    }

    public void setAchBoolean(boolean [] st) {
        for(int i = 0; i < ach.length; i++) {
            this.ach[i].setStateBoolean(st[i]);
        }
    }

    public void setAnimSpeedOfAch(int val) {
        for(int i = 0; i < ach.length; i++) {
            this.ach[i].setAnimSpeed(val);
        }
    }

    public void setClPath(String clPath) {
        this.clPath = clPath;
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

        ySc = height/320;
        if (clPath.equals("Coloured/")) levelPath = "Level" + level + "/";
        else levelPath = "";

        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);

        img = Util.imgLoad("src/Sprites/Wall.png");
        for (int i = 0; i < 16*dfBlSz*xSc; i += dfBlSz*ySc) {
            g.drawImage(img, 5*dfBlSz*xSc, i, dfBlSz*xSc, dfBlSz*ySc, this);
        }

        img = Util.imgLoad("src/Sprites/SidePanel/SidePanelAroundEdges.png");
        g.drawImage(img, 5*xSc,125*ySc, img.getWidth(null)*xSc, img.getHeight(null)*ySc, this);

        img = Util.imgLoad("src/Sprites/" + clPath + levelPath + color + "Block.png");
        for (int i = 0; i < 4; i++) {
            if (nextFigure[i] == 1)
            g.drawImage(img, dfBlSz*xSc*i + 10*xSc,130*ySc, dfBlSz*xSc, dfBlSz*ySc, this);
        }

        for (int i = 4; i < 8; i++) {
            if (nextFigure[i] == 1)
            g.drawImage(img, dfBlSz*xSc*(i-4) + 10*xSc,150*ySc, dfBlSz*xSc, dfBlSz*ySc, this);
        }


        for (int i = 0; i < ach.length; i++) {
            if (ach[i].getStateInt() == 1) {
                ach[i].increaseY();
                int Y = ach[i].getY()/ach[i].getAnimSpeed();
                if (Y <= 20) {

                    img = Util.imgLoad("src/Sprites/SidePanel/AchievementTable.png");
                    g.drawImage(img, 0, height - Y*2*ySc, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                    img = Util.imgLoad("src/Sprites/Menu/Achievements/A" + ach[i].getId() + ".png");
                    g.drawImage(img, 5*xSc, height - (-5 + Y*2)*ySc, xSc*img.getWidth(null), ySc*img.getHeight(null), this);

                } else if (Y <= 25) {

                    img = Util.imgLoad("src/Sprites/SidePanel/AchievementTable.png");
                    g.drawImage(img, 0, height - img.getHeight(null)*ySc, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                    img = Util.imgLoad("src/Sprites/Menu/Achievements/A" + ach[i].getId() + ".png");
                    g.drawImage(img, 5*xSc, height - (-5 + 40)*ySc, xSc*img.getWidth(null), ySc*img.getHeight(null), this);

                } else {

                    ach[i].setStateInt(2);

                }
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //---------------------------------------------Some other helping methods-----------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * function that takes level and adding "Level " in front of it and sets label that cares about displaying level to
     * resulting value.
     * @param  level  integer, sets directly labels variable.
     */
    private void level(int level) {
        String lvl;
        if (level < 10) {
            lvl = "0" + level;
        } else {
            lvl = "" + level;
        }
        displLevel.setText("Level " + lvl);
    }

    /**
     * Just set ups labels, for more clarity placed to the separated method rather than directly in the constructor.
     */
    private void setUp () {

        displTime.setForeground(Color.white);
        displTime.setBounds(15*xSc,250*ySc,70*xSc,30*ySc);
        displTime.setText("0" + min + " : " + "0" + s);
        displTime.setFont(font);
        this.add(displTime);

        Time.setForeground(Color.white);
        Time.setBounds(30*xSc, 220*ySc, 40*xSc,30*ySc);
        Time.setText("Time");
        Time.setFont(font);
        this.add(Time);

        Score.setForeground(Color.white);
        Score.setBounds(25*xSc, 20*ySc, 50*xSc,30*ySc);
        Score.setText("Score");
        Score.setFont(font);
        this.add(Score);

        countScore.setForeground(Color.white);
        countScore.setBounds(20*xSc, 50*ySc, 60*xSc,30*ySc);
        countScore.setText("000000");
        countScore.setFont(font);
        this.add(countScore);

        nxtFigure.setForeground(Color.white);
        nxtFigure.setBounds(30*xSc, 100*ySc, 40*xSc,30*ySc);
        nxtFigure.setText("Next");
        nxtFigure.setFont(font);
        this.add(nxtFigure);

        displLevel.setForeground(Color.white);
        displLevel.setBounds(10*xSc, 170*ySc, 80*xSc,30*ySc);
        displLevel.setText("Level 00");
        displLevel.setFont(font);
        this.add(displLevel);

    }

    /**
     * Updates some variables that need to be updated while program running.
     */
    public void update() {

        if (!pause) {

            countScore.setText(String.format("%06d", score));
            level(level);
            s++;
            if (s / timerSpeed == 60) {
                min++;
                s = 0;
            }

            if ((s / timerSpeed < 10) & (min < 10)) {
                displTime.setText("0" + min + " : " + "0" + s / timerSpeed);
            } else if ((s / timerSpeed > 9) & (min < 10)) {
                displTime.setText("0" + min + " : " + s / timerSpeed);
            } else if ((s / timerSpeed < 10) & (min > 9)) {
                displTime.setText(min + " : " + "0" + s / timerSpeed);
            } else {
                displTime.setText(min + " : " + s / timerSpeed);
            }

            repaint();

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //---------------------------------------------------Constructor--------------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates ui.SidePanel
     * @param  width  integer, sets width.
     * @param  height integer, sets height.
     */
    SidePanel(int width, int height) {

        this.width = width;
        this.height = height;
        xSc = width / 120;
        ySc = height / 320;
        dfBlSz = 20;

        ach = new Achievement[] {
                new Achievement(1),
                new Achievement(2),
                new Achievement(3),
                new Achievement(4),
                new Achievement(5),
                new Achievement(6),
                new Achievement(7),
                new Achievement(8),
                new Achievement(9),
                new Achievement(10),
                new Achievement(11),
                new Achievement(12),
                new Achievement(13),
                new Achievement(14),
                new Achievement(15),
                new Achievement(16),
        };

        font = Util.createFont(fontPath, ySc*10);

        this.setBackground(Color.black);
        this.setLayout(null);

        clPath = "Coloured/";
        level = 0;
        color = "Red";
        setUp();

    }

}
