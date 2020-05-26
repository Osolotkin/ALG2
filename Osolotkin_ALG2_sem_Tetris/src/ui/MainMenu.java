package ui;

import javax.swing.*;
import java.awt.*;

import utils.Record;
import utils.Sound;
import utils.Util;
import java.io.*;

import static utils.Util.sortByName;
import static utils.Util.sortByScore;

/**
 * Class represent main menu. Generally provides just rendering and a bit logic.
 * @author Maxim Osolotkin
 */
public class MainMenu extends JPanel {

    //=========================== variables that cares about resolution and connected stuff ==========================//
    private int width;            //width of a main menu in pixels
    private int height;           //height of main menu in pixels
    private int xSc;              //rate of scale for horizontal direction
    private int ySc;              //rate of scale for vertical direction

    //============================ variables that stores conditions of player actions ================================//
    private boolean down;         //true - selecting new item in menu, using bottom direction, false - nothing
    private boolean up;           //true - selecting new item in menu, using up direction, false - nothing
    private boolean left;         //true - selecting new item in menu, using left direction, false - nothing
    private boolean right;        //true - selecting new item in menu, using right direction, false - nothing
    private boolean enter;        //true - selecting item in menu, false - nothing
    private boolean esc;          //true - moving back to the previous menu, false - nothing

    //========================== variables that have something to do with music and sounds ===========================//
    private boolean sound;         //true - sound will be played, false - no sound there
    private boolean music;         //true - music will be played, false - no music there
    private File musicFile;        //stores music file that would be played if music is true
    private File soundFile;        //stores sound file that would be played if sound is true
    private Sound snd;             //sound class that can do some stuff with wav files, this one cares about sound
    private Sound msc;             //sound class, this one cares about music

    //===================================== variables that are important for rendering ===============================//
    private boolean [] ach;        //stores needed info about achievements to display them
    private boolean colored;       //true - block in game would be colored, false - block would be just black&white
    private Image img;             //variable, that stores image, useful for rendering

    //=============================== variables that are using for moving through menu  ==============================//
    private int scr = 1;           //determine which screen in menu user currently is, 1 - main, 2 - minor
    private int mainSelected;      //determine which item on main screen is currently selected
    private int secondSelected;    //determine which item on minor screen is currently selected
    private boolean NwGm;          //true - new game is activated, false - nothing
    private boolean resChange;     //true - resolution was changed, false nothing happen
    private boolean byScore;       //true - records are score by score, false - by name

    //================================================= some labels ==================================================//
    private JLabel RecScore;       //label that is used to display score logo
    private JLabel RecName;        //label that is used to display name logo
    private JLabel [] recLabel;    //labels used to display records

    //====================================== variables that stores useful paths ======================================//
    private final String mainTheme = "src/Music/TetrisMainTheme.wav";
    private final String yesSettPath = "src/Sprites/Menu/Settings/Yes.png";
    private final String noSettPath = "src/Sprites/Menu/Settings/No.png";
    private final String colSettPath = "src/Sprites/Menu/Settings/Colored.png";
    private final String onSettPath = "src/Sprites/Menu/Settings/On.png";
    private final String offSettPath = "src/Sprites/Menu/Settings/Off.png";
    private final String musicSettPath = "src/Sprites/Menu/Settings/Music.png";
    private final String soundSettPath = "src/Sprites/Menu/Settings/Sound.png";
    private final String soundPath = "src/Sounds/";
    private final String AchievementsPath = "src/Sprites/Menu/Achievements.png";
    private final String RecordPath = "src/Sprites/Menu/Records.png";
    private final String SettingsPath = "src/Sprites/Menu/Settings.png";
    private final String QuitePath = "src/Sprites/Menu/Quite.png";
    private final String BackPath = "src/Sprites/Menu/Menu.png";
    private final String newGamePath = "src/Sprites/Menu/NewGame.png";
    private final String resolutionPath = "src/Sprites/Menu/Settings/Resolution.png";
    private String resNumPath = "src/Sprites/Menu/Settings/" + width + "x320.png";
    private final String fontPath = "src/Fonts/kongtext.ttf";
    private final String rgAr = "src/Sprites/Menu/Settings/rightArrow.png";
    private final String lfAr = "src/Sprites/Menu/Settings/leftArrow.png";
    private final String DontKnowAchievePath = "src/Sprites/Menu/Achievements/DontKnow.png";
    private String [] achPath;

    //============================================= something else ===================================================//
    Font font;              //used to change font, affecting only records tab in menu
    private Record [] rec;  //used to store records and corresponding names, also useful for setting them
    private String name;    //player name, that would be displayed next to record, if any of existed was beaten,
                            //momentarily there is no function in program to set it.

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------------------There are some getters--------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean getNwGm() {
        return NwGm;
    }

    public boolean getResChange() {
        return resChange;
    }

    public boolean getMusic() {
        return music;
    }

    public boolean getColored() {
        return colored;
    }

    public boolean getSound() {
        return sound;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean[] getAch() {
        return ach.clone();
    }

    public Record [] getRec() {
        return rec;
    }

    public String [] getRecString() {
        String [] tmp = new String [rec.length];
        for (int i = 0; i < rec.length; i++) {
            tmp[i] = rec[i].getString();
        }
        return tmp;
    }

    public String getPlayerName() {
        return name;
    }

    public boolean getByScore() {
        return byScore;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------------------There are some setters--------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setNwGm(boolean x) {
        this.NwGm = x;
    }

    public void setSound(Boolean x) {
        this.sound = x;
    }

    public void setMusic(Boolean x) {
        this.music = x;
    }

    public void setColored(Boolean x) {
        this.colored = x;
    }

    public void setResChange(boolean x) {
        this.resChange = x;
    }

    public void setAch(boolean [] ar) {
        ach = ar.clone();
        achPath = new String [ach.length];
        for (int i = 1; i <= ach.length; i++) {
            achPath[i-1] = "src/Sprites/Menu/Achievements/A" + i + ".png";
        }
    }

    public void setRec(String [] ar) {
        String [] tmp = ar.clone();
        for (int i = 0; i < ar.length; i++) {
            rec[i].decodeStringAndSet(tmp[i]);
        }
    }

    public void setInput(boolean [] ar) {
        up = ar[0];
        down = ar[1];
        right = ar[2];
        left = ar[3];
        enter = ar[4];
        esc = ar[5];
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //---------------------------------------Some code that cares about render----------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Main function that cares about render using paintComponent and Graphic.
     * @param  g  instance of Graphics that do some black magic.
     */
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        img = Util.imgLoad(BackPath);
        g.drawImage(img,0,0, width, height, this);

        int x = width/6;       //starting
        int y = height/3;
        int step = height/14;  //how far objects are
        xSc = width/240;
        ySc = height/320;
        int ministep = 5*ySc;

        if (scr == 1) {

            if (!enter) {

                img = Util.imgLoad(newGamePath);
                g.drawImage(img, x, y, xSc*img.getWidth(null), ySc*img.getHeight(null), this);

                img =  Util.imgLoad(QuitePath);
                g.drawImage(img, x, y + step*4, xSc*img.getWidth(null), ySc*img.getHeight(null), this);

                img =  Util.imgLoad(SettingsPath);
                g.drawImage(img, x, y + step, xSc*img.getWidth(null), ySc*img.getHeight(null), this);

                img =  Util.imgLoad(RecordPath);
                g.drawImage(img, x, y + step*2, xSc*img.getWidth(null), ySc*img.getHeight(null), this);

                img =  Util.imgLoad(AchievementsPath);
                g.drawImage(img, x, y + step*3, xSc*img.getWidth(null), ySc*img.getHeight(null), this);

                if (mainSelected == 0) {
                    g.drawRect(x - 15*xSc, y + ministep + step*mainSelected, 10*xSc, 10*ySc);
                }

                if (mainSelected == 1) {
                    g.drawRect(x - 15*xSc, y + ministep + step*mainSelected, 10*xSc, 10*ySc);
                }

                if (mainSelected == 2) {
                    g.drawRect(x - 15*xSc, y + ministep + step*mainSelected, 10*xSc, 10*ySc);
                }

                if (mainSelected == 3) {
                    g.drawRect(x - 15*xSc, y + ministep + step*mainSelected, 10*xSc, 10*ySc);
                }

                if (mainSelected == 4) {
                    g.drawRect(x - 15*xSc, y + ministep + step*mainSelected, 10*xSc, 10*ySc);
                }
            }

        }
        else {
            if (mainSelected == 1) {

                int maxWidth = 0;

                img = Util.imgLoad(soundSettPath);
                g.drawImage(img, x, y, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                maxWidth = maxWidth < xSc*img.getWidth(null)? xSc*img.getWidth(null) : maxWidth;

                img = Util.imgLoad(musicSettPath);
                g.drawImage(img, x, y + step, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                maxWidth = maxWidth < xSc*img.getWidth(null)? xSc*img.getWidth(null) : maxWidth;

                img = Util.imgLoad(colSettPath);
                g.drawImage(img, x, y + step*2, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                maxWidth = maxWidth < xSc*img.getWidth(null)? xSc*img.getWidth(null) : maxWidth;

                img = Util.imgLoad(resolutionPath);
                g.drawImage(img, x, y + step*3, xSc*img.getWidth(null), ySc*img.getHeight(null), this);

                if (secondSelected <= 3) {

                    int xss = x + maxWidth + 20*xSc;

                    g.drawRect(x - 15*xSc, y + ministep + step*secondSelected, 10*xSc, 10*ySc);
                    if (sound) {
                        img = Util.imgLoad(onSettPath);
                        g.drawImage(img, xss, y, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                    }
                    if (!sound) {
                        img = Util.imgLoad(offSettPath);
                        g.drawImage(img, xss, y, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                    }

                    if (music) {
                        img = Util.imgLoad(onSettPath);
                        g.drawImage(img, xss, y + step, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                    }
                    if (!music) {
                        img = Util.imgLoad(offSettPath);
                        g.drawImage(img, xss, y + step, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                    }

                    if (colored) {
                        img = Util.imgLoad(yesSettPath);
                        g.drawImage(img, xss, y + step*2, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                    }
                    if (!colored) {
                        img = Util.imgLoad(noSettPath);
                        g.drawImage(img, xss, y + step*2, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                    }

                    if (secondSelected == 3) {

                        g.drawRect(x - 15*xSc, y + ministep + step*secondSelected, 10*xSc, 10*ySc);
                        img = Util.imgLoad(resNumPath);
                        int xAligCenter = (width - xSc*img.getWidth(null))/2;
                        int wd = xSc*img.getWidth(null);
                        g.drawImage(img, xAligCenter, y + step*4, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                        img = Util.imgLoad(lfAr);
                        g.drawImage(img, xAligCenter - xSc*img.getWidth(null) - 5*xSc, y + step*4, xSc*img.getWidth(null), ySc*img.getHeight(null), this);
                        img = Util.imgLoad(rgAr);
                        g.drawImage(img, xAligCenter + wd + 5*xSc, y + step*4, xSc*img.getWidth(null), ySc*img.getHeight(null), this);


                    }

                }

            }
            if (mainSelected == 2) {


            }
            if (mainSelected == 3) {

                int xOffset = 13;

                for (int j = 0; j < 4; j++) {

                    for (int i = 0; i < 4; i++) {
                        if (ach[i + 4*j]) img = Util.imgLoad(achPath[i + 4*j]);
                        else img = Util.imgLoad(DontKnowAchievePath);
                        g.drawImage(img, x + (xOffset + img.getWidth(null))*xSc*j, y + step * i * 2, xSc * img.getWidth(null), ySc * img.getHeight(null), this);
                    }

                }

            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //---------------------------------------------Some other helping methods-----------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Function that by given name load and play corresponding wav file in directory given by soundPath.
     * @param  name  name of the wav file that will be played, without extension.
     */
    private void soundToPlay(String name) {
        if (sound) {
            soundFile = new File(soundPath + name + ".wav");
            snd.play(soundFile);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------------------------Kind of loop------------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Main loop, that updates main menu stats recording to the inputs or state of other variables.
     */
    public void update() {

        if (music && !msc.isPlaying() && !NwGm) {
            msc.play(musicFile);
        }

        if (down == true) {

            soundToPlay("E");

            if (scr == 1) {
                mainSelected = (mainSelected + 1) % 5;
                repaint();
            }
            else {
                secondSelected = (secondSelected + 1) % 4;
                repaint();
            }
        }
        if (up == true) {

            soundToPlay("E");

            if (scr == 1) {
                if ((mainSelected - 1) < 0) {
                    mainSelected = 5;
                }
                mainSelected = (mainSelected - 1) % 5;
                repaint();
            }
            else {
                if ((secondSelected - 1) < 0) {
                    secondSelected = 4;
                }
                secondSelected = (secondSelected - 1) % 4;
                repaint();
            }
        }
        if (enter == true) {

            soundToPlay("EF#");

            if (scr == 1) {
                if (mainSelected == 0) {
                    setFocusable(false);
                    msc.stop();

                    NwGm = true;
                }
                if (mainSelected == 4) {
                    System.exit(0);
                }
                if (mainSelected == 2) {
                    RecScore.setBounds(135*xSc,75*ySc,66*xSc,30*ySc);
                    RecScore.setFont(Util.createFont(fontPath, ySc*10));
                    RecName.setBounds(65*xSc,75*ySc,66*xSc,30*ySc);
                    RecName.setFont(Util.createFont(fontPath, ySc*10));
                    for (int i = 0; i < recLabel.length; i++) {
                        recLabel[i].setFont(Util.createFont(fontPath, ySc*10));
                        recLabel[i].setBounds(45*xSc,(100 + i*30)*ySc,150*xSc,30*ySc);
                        recLabel[i].setText(i + 1 + "." + rec[i].getString());
                        recLabel[i].setVisible(true);
                    }
                    RecScore.setVisible(true);
                    RecName.setVisible(true);
                }
                scr = 2;
                repaint();
            }
            else {
                if (mainSelected == 2) {
                    byScore = !byScore;
                    if (byScore) sortByScore(rec);
                    else sortByName(rec);
                    for (int i = 0; i < recLabel.length; i++) {
                        recLabel[i].setText(i + 1 + "." + rec[i].getString());
                    }
                }
                if (secondSelected == 0) {
                    if (!sound) { sound = true; }
                    else { sound = false; }
                }
                if (secondSelected == 1) {
                    if (!music) { music = true; }
                    else { music = false; }
                }
                if (secondSelected == 2) {
                    if (!colored) { colored = true; }
                    else { colored = false; }
                }
                repaint();
            }

        }

        if (secondSelected == 3) {
            if (left) xSc--;
            if (right) xSc++;
            if (xSc > 3) xSc = 1;
            if (xSc <= 0) xSc = 3;
            ySc = xSc;
            width = 240*xSc;
            height = 320*ySc;
            resChange = true;
            resNumPath = "src/Sprites/Menu/Settings/" + width + "x" + height + ".png";
            repaint();
        }

        if (esc == true) {

            soundToPlay("F#E");

            scr = 1;
            RecName.setVisible(false);
            RecScore.setVisible(false);
            for (int i = 0; i < recLabel.length; i++) {
                recLabel[i].setVisible(false);
            }

            repaint();
        }

        if ((music) && (!NwGm)) {
            msc.setVolume(1);
        } else {
            msc.setVolume(0);
        }

        repaint();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //---------------------------------------------------Constructor--------------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates MainMenu.
     * @param  wd  width in pixels of main menu.
     * @param  hg  height in pixels of main menu.
     */
    MainMenu (int wd, int hg) {

        this.width = wd;
        this.height = hg;
        this.xSc = width/240;
        this.ySc = height/320;
        resChange = false;
        NwGm = false;
        musicFile = new File(mainTheme);
        msc = new Sound();
        snd = new Sound();

        rec = new Record [5];
        for (int i = 0; i < 5; i++) {
            rec[i] = new Record("------", 000000);
        }
        font = Util.createFont(fontPath, ySc*10);
        name = "player";

        RecScore = new JLabel();
        this.setLayout(null);
        RecScore.setForeground(java.awt.Color.white);
        RecScore.setBounds(135*xSc,75*ySc,66*xSc,30*ySc);
        RecScore.setText("Score");
        RecScore.setFont(font);
        add(RecScore);
        RecScore.setVisible(false);

        RecName = new JLabel();
        RecName.setForeground(java.awt.Color.white);
        RecName.setBounds(65*xSc,75*ySc,66*xSc,30*ySc);
        RecName.setText("Name");
        RecName.setFont(font);
        add(RecName);
        RecName.setVisible(false);

        //width of one letter of size 10 is approximately 10px
        recLabel = new JLabel [5];
        for (int i = 0; i < recLabel.length; i++) {
            recLabel[i] = new JLabel();
            recLabel[i].setForeground(java.awt.Color.white);
            recLabel[i].setBounds(45*xSc,(100 + i*30)*ySc,150*xSc,30*ySc);
            recLabel[i].setText(i + 1 + "." + rec[i].getString());
            recLabel[i].setFont(font);
            add(recLabel[i]);
            recLabel[i].setVisible(false);
        }

    }

}
