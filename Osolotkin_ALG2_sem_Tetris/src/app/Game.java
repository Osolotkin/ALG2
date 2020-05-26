package app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import utils.Sound;
import utils.gameInterface;

/**
 * Just main tetris game, generally does what you expect.
 * @author Maxim Osolotkin
 */
public class Game implements gameInterface {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //-------------------------------------------------Global variables-----------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //================================== variables that are for animations purposes ==================================//
    private int animationSpeed;         //determine interval for one frame to render, in milliseconds;
    private Timer Animation;            //timer, that creates loop for animation depending on animationSpeed
    private int pauseAnim;              //count cycles in Animation, while game is on pause, to animate something
    private int yAnimOver;              //count cycles in Animation, when is game over, to animate something
    private boolean rowElemAnimation;   //if true, row elimination animation will be played, depending on values bellow
    private int rowY;                   //stores info which row is the most upper to eliminate
    private int yAnim;                  //stores info how many rows need to be eliminated
    private int x1Anim;                 //stores info which block of row from the left part is eliminating
    private int x2Anim;                 //stores info which block of row from the right part is eliminating
    private int [][] mapReserv;         //copy of a field for animation purposes

    //========================== variables that have something to do with music and sounds ===========================//
    private boolean sound;         //true - sound will be played, false - no sound there
    private boolean music;         //true - music will be played, false - no music there
    private File musicFile;        //stores music file that would be played if music is true
    private File soundFile;        //stores sound file that would be played if sound is true
    private Sound snd;             //sound class that can do some stuff with wav files, this one cares about sound
    private Sound msc;             //sound class, this one cares about music
    private String musicPath;      //path to the folder, where are stored music files
    private int numberOfSongs;     //count of songs that are going to be played
    private int currentSong;       //number of song, that's gonna play after current one

    //========================== variables that stores conditions of players achievements =============================//
    private int [] ach;

    //============================ variables that stores some important game conditions ===============================//
    private int eliminatedRows;    //count of rows that was eliminated
    private boolean [] rowsElim;   //each index indicates according row, true means that row has to be eliminated
    private int level;             //form zero to ten, each 10 rows are new level
    private boolean back;          //determine if return to the main menu has to happen, true for yes, false for no
    private boolean gameOver;      //determine if game is over or not, true for yes, false for no
    private int score;             //calculated by following formula score = score + (level+1)*(eliminated rows)^2
    private int gameSpeed;         //determine interval for figure to fall down by one block, in milliseconds;
    private int speedOfChange;     //changed each level gameSpeed following gameSpeed = gameSpeed-level*speedOfChange
    private boolean pause;         //true - pause is on, just animation of pause is playing, false - nothing happening
    private boolean figureIsOn;    //tells if there is an active figure
    private Figure curFigure;      //current figure, active one, controlled by player
    private Figure nxtFigure;      //figure that will be active next, showing in side panel on right side
    private Timer Tmr;             //timer for main loop
    private int [][] map;          //stores info about field, 1 - active figure, 0 - wall, >1 - already stacked blocks

    //============================ variables that stores conditions of player actions ================================//
    private boolean rotation;      //true - rotate figure, false - nothing
    private boolean down;          //true - moving figure down, false - nothing
    private boolean up;            //at the moment unused
    private boolean right;         //true - move figure to the right, false - nothing
    private boolean left;          //true - move figure to the left, false - nothing
    private boolean enter;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------------------There are some getters--------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int [] getAchStateInt() {
        return ach.clone();
    }

    public int getScore() {
        return score;
    }

    public boolean getBack() {
        return back;
    }

    public ArrayList getStateForRender() {
        ArrayList tmp = new ArrayList<>();
        int [][] mapCl = new int [map.length][map[0].length];
        int [][] mapResCl = new int [map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            mapResCl[i] = mapReserv[i].clone();
            mapCl[i] = map[i].clone();
        }
        tmp.add(mapCl);
        tmp.add(mapResCl);
        tmp.add(rowElemAnimation);
        tmp.add(x1Anim);
        tmp.add(x2Anim);
        tmp.add(gameOver);
        tmp.add(pauseAnim);
        tmp.add(curFigure.getColor());
        tmp.add(level);
        tmp.add(rowY);
        tmp.add(yAnim);
        tmp.add(yAnimOver);
        tmp.add(rowsElim);
        return tmp;
    }

    public ArrayList getStateToDisplay() {
        ArrayList tmp = new ArrayList<>();
        tmp.add(nxtFigure.getArr().clone());
        tmp.add(nxtFigure.getColor());
        tmp.add(score);
        tmp.add(level);
        tmp.add(pause);
        return tmp;
    }

    public int getAchIntIndex(int index) {
        return ach[index];
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------------------There are some setters--------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setMusic(boolean music) {
        this.music = music;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public void setInput(boolean [] ar) {
        rotation = ar[0];
        down = ar[1];
        right = ar[2];
        left = ar[3];
        enter = ar[4];
        if (ar[5]) pause = !pause;
    }

    public void setAchInt(int [] ar) {
        ach = ar.clone();
    }

    public void setAchIntIndex(int index, int val) {
        ach[index] = val;
    }

    public void increaseLevel() {
        setLevelAndGameSpeed(level+1);
    }

    public void decreaseGameSpeed(int val) {
        gameSpeed *= val;
        Tmr.setDelay(gameSpeed);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //--------------------------functions that have something to do with checking directions--------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Returns true, if space to move figure one step down is free, otherwise returns false.
     * @return true, if space to move figure one step down is free, otherwise returns false.
     */
    private boolean checkDown() {

        if (curFigure.getY() == 14 - curFigure.getHeightBottom()) {
            return false;
        }

        for (int i = curFigure.getY() + 1 + curFigure.getHeightBottom(); i > curFigure.getY() - 1 - curFigure.getHeightTop(); i--) {
            for (int j = curFigure.getX(); j < curFigure.getX() + curFigure.getlengthRight() + 2; j++) {
                if ((map[i + 1][j] > 1) && (map[i][j] == 1)) {
                    return false;
                }
            }
        }

        return true;

    }

    /**
     * Returns true, if space to move figure one step to the right is free, otherwise returns false.
     * @return true, if space to move figure one step to the right is free, otherwise returns false.
     */
    private boolean checkRight() {

        for (int i = curFigure.getY() + 1 + curFigure.getHeightBottom(); i > curFigure.getY() - 1 - curFigure.getHeightTop(); i--) {
            for (int j = curFigure.getX(); j < curFigure.getX() + curFigure.getlengthRight() + 2; j++) {
                if ((map[i][j+1] > 1) & (map[i][j] == 1)) {
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * Returns true, if space to move figure one step to the left is free, otherwise returns false.
     * @return true, if space to move figure one step to the left is free, otherwise returns false.
     */
    private boolean checkLeft() {

        for (int i = curFigure.getY() + 1 + curFigure.getHeightBottom(); i > curFigure.getY() - 1 - curFigure.getHeightTop(); i--) {
            for (int j = curFigure.getX() + 2- curFigure.getlengthLeft(); j < curFigure.getX() + curFigure.getlengthRight() + 2; j++) {
                if ((map[i][j-1] > 1) & (map[i][j] == 1)) {
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * Returns true, if space to rotate figure, depends on current phase of rotation, is free, otherwise returns false.
     * @return true, if space to rotate figure, depends on current phase of rotation, is free, otherwise returns false.
     */
    private boolean checkRotation(char type, int phase, int x, int y) {

        if (type == 'I') {

            if (phase%2 == 1) {
                for (int i = y; i < y + 4; i++)
                    if (i > 15 || map[i][x + 1] > 1) return false;
            } else {
                for (int i = x; i < x + 4; i++)
                    if (map[y+1][i] > 1) return false;
            }

        } else if (type == 'J') {

            if (phase%4 == 1) {
                for (int i = y; i < y + 3; i++)
                    if (i > 15 || map[i][x+1] > 1) return false;
                if (map[y][x+2] > 1) return false;
            } else if (phase%4 == 2) {
                for (int i = x; i < x + 3; i++)
                    if (map[y+1][i] > 1) return false;
                if (map[y+2][x+2] > 1) return false;
            } else if (phase%4 == 3) {
                for (int i = y; i < y + 3; i++)
                    if (map[i][x+1] > 1) return false;
                if (map[y+2][x] > 1) return false;
            } else if (phase%4 == 0) {
                for (int i = x; i < x + 3; i++)
                    if (map[y+1][i] > 1) return false;
                if (map[y][x] > 1) return false;
            }

        } else if (type == 'L') {

            if (phase%4 == 1) {
                for (int i = y; i < y + 3; i++)
                    if (i > 15 || map[i][x+1] > 1) return false;
                if (map[y+2][x+2] > 1) return false;
            } else if (phase%4 == 2) {
                for (int i = x; i < x + 3; i++)
                    if (map[y+1][i] > 1) return false;
                if (map[y+2][x] > 1) return false;
            } else if (phase%4 == 3) {
                for (int i = y; i < y + 3; i++)
                    if (map[i][x+1] > 1) return false;
                if (map[y][x] > 1) return false;
            } else if (phase%4 == 0) {
                for (int i = x; i < x + 3; i++)
                    if (map[y+1][i] > 1) return false;
                if (map[y][x+2] > 1) return false;
            }

        } else if (type == 'S') {

            if (phase%4 == 1) {
                if (y + 2 > 15 || y + 1 > 15 || map[y][x] > 1 || map[y+1][x] > 1 || map[y+1][x+1] > 1 || map[y+2][x+1] > 1) return false;
            } else {
                if (y + 1 > 15 || map[y+1][x] > 1 || map[y+1][x+1] > 1 || map[y][x+1] > 1 || map[y][x+2] > 1) return false;
            }

        } else if (type == 'Z') {

            if (phase%4 == 1) {
                if (y - 1 < 0 || map[y-1][x+1] > 1 || map[y][x] > 1 || map[y][x+1] > 1 || map[y+1][x] > 1) return false;
            } else {
                if (map[y][x] > 1 || map[y][x+1] > 1 || map[y+1][x+1] > 1 || map[y+1][x+2] > 1) return false;
            }

        } else if (type == 'T') {

            if (phase%4 == 1) {
                if (y + 2 > 15 || map[y+2][x+1] > 1) return false;
            } else if (phase%4 == 2) {
                if (map[y+1][x] > 1) return false;
            } else if (phase%4 == 3) {
                if (map[y][x+1] > 1) return false;
            } else if (phase%4 == 0) {
                if (map[y+1][x+2] > 1) return false;
            }

        }

        return true;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //-------------------------------Something ugly that eliminate rows if it had to happen---------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Checks for full rows and records it into global variable rowsElim. Also calculates score from count of eliminated
     * rows. And calling setLevelAndGameSpeed method to set level.
     * @return true if there is any row to eliminate, otherwise false.
     */
    private boolean checkRow() {
        int s = 0;
        boolean rowEliminated = true;
        boolean ans = false;
        for (int i = 0; i < rowsElim.length; i++) rowsElim[i] = false;

        for (int i = curFigure.getY() - curFigure.getHeightTop(); i < curFigure.getY() + 2 + curFigure.getHeightBottom(); i++) {
            for (int j = 1; j < 11; j++) {
                if (map[i][j] < 1) {
                    rowEliminated = false;
                    break;
                }
            }
            if (rowEliminated) {
                ans = true;
                rowsElim[i] = true;
                s++;
            }
            rowEliminated = true;
        }
        eliminatedRows += s;
        score = score + (level+1)*s*s;
        if (eliminatedRows/10 > level) {
            setLevelAndGameSpeed(level+1);
        }
        for (int i = 0; i < ach.length; i++) {
            if ((s == i+1) && (ach[i] != 2)) ach[i] = 1;
        }

        return ans;

    }

    /**
     * Sets level and according to it game speed, level could acquire value from 0 to 10. if there is any other value on
     * the input sets level automatically to the most closest value from interval. Minimal value of game speed is 16.
     * @param level integer value to set level.
     */
    private void setLevelAndGameSpeed(int level) {
        this.level = Math.abs(level % 11);
        if (this.level < 8) {
            gameSpeed = 1000 * (48 - speedOfChange * this.level) / 60;
        } else {
            gameSpeed = 1000 * (48 - speedOfChange * 7 - speedOfChange/2) / 60;
            if (gameSpeed < 16) gameSpeed = 16;
        }
        Tmr.setDelay(gameSpeed);
        if (this.level == 1 && (ach[4] != 2)) ach[4] = 1;
        else if (this.level == 4 && (ach[4] != 2)) ach[5] = 1;
        else if (this.level == 7 && (ach[4] != 2)) ach[6] = 1;
        else if (this.level == 10 && (ach[4] != 2)) ach[7] = 1;
    }

    /**
     * Eliminates rows according to the given array, that represent each row by boolean value on corresponding index.
     * Also creates actual copy of map.
     * @param rows array of boolean values that represent state of each row, true has to be for eliminating..
     */
    private void rowEliminating(boolean [] rows) {

        if (sound) {
            soundFile = new File("src/Sounds/EF#GF#.wav");
            snd.play(soundFile);
        }

        x1Anim = 5;
        x2Anim = 6;
        for (int i = 0; i < map.length; i++) {
            mapReserv[i] = map[i].clone();
        }

        boolean [] rowsCl = rows.clone();

        int offset = 0;
        for (int i = 15; i > -1; i--) {
            for (int j = 1; j < 11; j++) {
                if (rows[i]) {
                    for (int k = i + offset; k > 0; k--) {
                        map[k][j] = map[k - 1][j];
                        map[k - 1][j] = 0;
                    }
                }
            }
            if (rows[i]) offset++;
        }

        rowElemAnimation = true;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //-------------------------------------functions that cares about movement----------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Move figure in three directions - right, left, down, depends on condition of appropriate variables.
     * Changes directly map and setting x, y cords of current figure to new ones.
     * Before each move check if it is ok to move, if not, does nothing.
     */
    private void move() {

        if (right) {
            if ((checkRight()) && (curFigure.getX() + curFigure.getlengthRight() + 1 < 10)) {
                for (int i = curFigure.getY() - curFigure.getHeightTop(); i < curFigure.getY() + 2 + curFigure.getHeightBottom(); i++) {
                    for (int j = curFigure.getX() + curFigure.getlengthRight() + 1; j > curFigure.getX() - 1; j--) {
                        if (map[i][j] == 1) {
                            map[i][j] = 0;
                            map[i][j + 1] = 1;
                        }
                    }
                }
                int x = curFigure.getX() + 1;
                curFigure.setX(x);
            }
        }

        if (left) {
            if ((checkLeft()) && (curFigure.getX() - curFigure.getlengthLeft() + 2 > 1)) {
                for (int i = curFigure.getY() - curFigure.getHeightTop(); i < curFigure.getY() + 2 + curFigure.getHeightBottom(); i++) {
                    for (int j = curFigure.getX() - curFigure.getlengthLeft() + 2; j < curFigure.getX() + curFigure.getlengthRight() + 2; j++) {
                        if (map[i][j] == 1) {
                            map[i][j] = 0;
                            map[i][j - 1] = 1;
                        }
                    }
                }
                int x = curFigure.getX() - 1;
                curFigure.setX(x);
            }
        }

        if (down) {
            if ((curFigure.getY() + 1 < 16) & (checkDown())) {
                for (int i = curFigure.getY() + 1 + curFigure.getHeightBottom(); i > curFigure.getY() - 1 - curFigure.getHeightTop(); i--) {
                    for (int j = curFigure.getX(); j < curFigure.getX() + curFigure.getlengthRight() + 2; j++) {
                        if (map[i][j] == 1) {
                            map[i][j] = 0;
                            map[i + 1][j] = 1;
                        }
                    }
                }
                int y = curFigure.getY() + 1;
                curFigure.setY(y);
            }
        }

    }

    /**
     * Rotate figure if there is a space to do it, depends on phase of rotation, just rewrites not needed ones into
     * zeros and write remaining ones.
     */
    private void rotateMe() {

        if (rotation) {

            int x = curFigure.getX();
            int y = curFigure.getY();

            int count = curFigure.getRotCount();
            if (!checkRotation(curFigure.getType(), count + 1, x, y)) return;
            curFigure.setRotCount(curFigure.getRotCount() + 1);
            count = curFigure.getRotCount();

            if (curFigure.getType() == 'I') {

                if (count%2 == 1) {
                    for (int i = x; i < x + 4; i++)
                        map[y+1][i] = 0;

                    for (int i = y; i < y + 4; i++)
                        map[i][x+1] = 1;

                    curFigure.setLengthLeft(1);
                    curFigure.setLengthRight(0);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(2);
                }
                else {
                    for (int i = y; i < y + 4; i++)
                        map[i][x+1] = 0;

                    for (int i = x; i < x + 4; i++)
                        map[y+1][i] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(2);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(0);
                }

            } else if (curFigure.getType() == 'J') {

                if (count%4 == 1) {
                    for (int i = x; i < x + 3; i++)
                        map[y+1][i] = 0;
                    map[y][x] = 0;


                    for (int i = y; i < y + 3; i++)
                        map[i][x+1] = 1;
                    map[y][x+2] = 1;

                    curFigure.setLengthLeft(1);
                    curFigure.setLengthRight(1);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(1);
                }

                if (count%4 == 2) {
                    for (int i = y; i < y + 3; i++)
                        map[i][x+1] = 0;
                    map[y][x+2] = 0;

                    for (int i = x; i < x + 3; i++)
                        map[y+1][i] = 1;
                    map[y+2][x+2] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(1);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(1);
                }

                if (count%4 == 3) {
                    for (int i = x; i < x + 3; i++)
                        map[y+1][i] = 0;
                    map[y+2][x+2] = 0;

                    for (int i = y; i < y + 3; i++)
                        map[i][x+1] = 1;
                    map[y+2][x] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(0);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(1);
                }

                if (count%4 == 0) {
                    for (int i = y; i < y + 3; i++)
                        map[i][x+1] = 0;
                    map[y+2][x] = 0;

                    for (int i = x; i < x + 3; i++)
                        map[y+1][i] = 1;
                    map[y][x] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(1);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(0);
                }

            } else if (curFigure.getType() == 'L') {

                if (count%4 == 1) {
                    for (int i = x; i < x + 3; i++)
                        map[y+1][i] = 0;
                    map[y][x+2] = 0;


                    for (int i = y; i < y + 3; i++)
                        map[i][x+1] = 1;
                    map[y+2][x+2] = 1;

                    curFigure.setLengthLeft(1);
                    curFigure.setLengthRight(1);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(1);
                }

                if (count%4 == 2) {
                    for (int i = y; i < y + 3; i++)
                        map[i][x+1] = 0;
                    map[y+2][x+2] = 0;

                    for (int i = x; i < x + 3; i++)
                        map[y+1][i] = 1;
                    map[y+2][x] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(1);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(1);
                }

                if (count%4 == 3) {
                    for (int i = x; i < x + 3; i++)
                        map[y+1][i] = 0;
                    map[y+2][x] = 0;

                    for (int i = y; i < y + 3; i++)
                        map[i][x+1] = 1;
                    map[y][x] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(0);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(1);
                }

                if (count%4 == 0) {
                    for (int i = y; i < y + 3; i++)
                        map[i][x+1] = 0;
                    map[y][x] = 0;

                    for (int i = x; i < x + 3; i++)
                        map[y+1][i] = 1;
                    map[y][x+2] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(1);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(0);
                }

            } else if (curFigure.getType() == 'S') {

                if (count%2 == 1) {
                    map[y+1][x] = 0; map[y+1][x+1] = 0;
                    map[y][x+1] = 0; map[y][x+2] = 0;

                    map[y][x] = 1; map[y+1][x] = 1;
                    map[y+1][x+1] = 1; map[y+2][x+1] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(0);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(1);
                }
                else {
                    map[y][x] = 0; map[y+1][x] = 0;
                    map[y+1][x+1] = 0; map[y+2][x+1] = 0;

                    map[y+1][x] = 1; map[y+1][x+1] = 1;
                    map[y][x+1] = 1; map[y][x+2] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(1);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(0);
                }

            } else if (curFigure.getType() == 'Z') {

                if (count%2 == 1) {
                    map[y][x] = 0; map[y][x+1] = 0;
                    map[y+1][x+1] = 0; map[y+1][x+2] = 0;

                    map[y-1][x+1] = 1; map[y][x] = 1;
                    map[y][x+1] = 1; map[y+1][x] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(0);
                    curFigure.setHeightTop(1);
                    curFigure.setHeightBottom(0);
                }
                else {
                    map[y-1][x+1] = 0; map[y][x] = 0;
                    map[y][x+1] = 0; map[y+1][x] = 0;

                    map[y][x] = 1; map[y][x+1] = 1;
                    map[y+1][x+1] = 1; map[y+1][x+2] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(1);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(0);
                }

            } else if (curFigure.getType() == 'T') {

                if (count%4 == 1) {
                    map[y+1][x] = 0;
                    map[y+2][x+1] = 1;

                    curFigure.setLengthLeft(1);
                    curFigure.setLengthRight(1);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(1);
                }

                if (count%4 == 2) {
                    map[y][x+1] = 0;
                    map[y+1][x] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(1);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(1);
                }

                if (count%4 == 3) {
                    map[y+1][x+2] = 0;
                    map[y][x+1] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(0);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(1);
                }

                if (count%4 == 0) {
                    map[y+2][x+1] = 0;
                    map[y+1][x+2] = 1;

                    curFigure.setLengthLeft(2);
                    curFigure.setLengthRight(1);
                    curFigure.setHeightTop(0);
                    curFigure.setHeightBottom(0);
                }

            }

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //---------------------------------------For a few more functions look down---------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Return true, if figure, thats gonna be spawn would cover any already existing block(id higher than 1)
     * otherwise returns false.
     * @return true if new figure would cause game over, otherwise false.
     */
    private boolean GameOverCheck() {

        for (int i = 4; i < 8; i++) {
            if ((map[0][i] > 1) && (curFigure.getArr()[i - 4] == 1)) {
                return true;
            }
        }
        for (int i = 4; i < 8; i++) {
            if ((map[1][i] > 1) && (curFigure.getArr()[i] == 1)) {
                return true;
            }
        }

        return false;

    }

    /**
     * Stops current active figure, rewrites ones in map into figures id number.
     */
    private void stopFigure() {
        for(int i = curFigure.getY() + 1 + curFigure.getHeightBottom(); i > curFigure.getY() - 1 - curFigure.getHeightTop(); i--) {
            for(int j = curFigure.getX(); j < curFigure.getX() + curFigure.getlengthRight() + 2; j++) {
                if(map[i][j] == 1) {
                    map[i][j] = curFigure.getCode();
                }
            }
        }
    }

    /**
     * Spawn new figure on the top of the game field(4-7columns and 1-2rows) can be used if there is no active figure.
     */
    private void setFigure() {

        if (!figureIsOn) {
            for (int i = 0; i < 4; i++) { map[0][i + 4] = curFigure.getArr()[i]; }
            for (int i = 4; i < 8; i++) { map[1][i] = curFigure.getArr()[i]; }
            figureIsOn = true;
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //-----------------------------Loops, that are located in methods for some clarity--------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Loop, that is used in timer Tmr, does some important stuff while there is no game over or game is not on pause.
     */
    private void mainLoop() {

        if (checkDown()) {

            for (int i = curFigure.getY() + 1 + curFigure.getHeightBottom(); i > curFigure.getY() - 1 - curFigure.getHeightTop(); i--) {
                for (int j = curFigure.getX(); j < curFigure.getX() + curFigure.getlengthRight() + 2; j++) {
                    if (map[i][j] == 1) {
                        map[i][j] = 0;
                        map[i + 1][j] = 1;
                    }
                }
            }
            int y = curFigure.getY() + 1;
            curFigure.setY(y);

        } else {
            stopFigure();
            if (checkRow()) {
                rowEliminating(rowsElim);
            }
            if (rowElemAnimation) {
                Tmr.stop();
                Animation.start();
            }
            figureIsOn = false;
            curFigure = nxtFigure;
            nxtFigure = new Figure();
            nxtFigure.generateFigure();
            if (!GameOverCheck()) setFigure();
            else {
                gameOver = true;
                Animation.start();
            }
        }

    }

    /**
     * Loop, that cares about animation, used in Animation timer. Does some strange stuff that turns into animation,
     * if any of variable, that taking care of animation, is true.
     */
    private void animation() {

        if (pause) {
            pauseAnim++;
        } else if (rowElemAnimation) {
            x1Anim--;
            x2Anim++;
            if (x1Anim == 0) {
                rowElemAnimation = false;
            }
        } else if (gameOver) {
            if ((sound) && (yAnimOver < 15 ) && (!snd.isPlaying())) {
                soundFile = new File("src/Sounds/ChromDown.wav");
                snd.play(soundFile);
            }
            yAnimOver++;
        } else {
            Tmr.start();
            Animation.stop();
        }

    }

    /**
     * Loop, that is in timer Update, just updates some important stuff, or checking something over an over again,
     * like if there is any input form user.
     */
    public void update() {

        if (music && !gameOver && !msc.isPlaying()) {
            musicFile = new File(musicPath + currentSong + ".wav");
            msc.play(musicFile);
            currentSong = currentSong % numberOfSongs + 1;
        }

        if (!pause && !rowElemAnimation) {
            move();
            rotateMe();
            pauseAnim = 0;
        } else {
            if (!Animation.isRunning()) {
                Animation.start();
            }
        }

        if (gameOver && enter) {
            back = true;
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //---------------------------------------------------Constructor--------------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *Creates ui.GameField
     */
    public Game(boolean music, boolean sound) {

         this.music = music;
         this.sound = sound;
         this.

        curFigure = new Figure();
        nxtFigure = new Figure();
        curFigure.generateFigure();
        nxtFigure.generateFigure();

        mapReserv = new int [16][12];
        map = new int [16][12];
        ach = new int [8];

        pauseAnim = 0;
        yAnimOver = 0;

        msc = new Sound();
        snd = new Sound();
        currentSong = 1;
        numberOfSongs = 4;
        musicPath = "src/Music/Tetris";

        speedOfChange = 5;
        level = 0;
        score = 0;
        gameOver = false;
        pause = false;

        gameSpeed = 800;
        Tmr = new Timer(gameSpeed, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    if (!pause) {
                        mainLoop();
                    }
                }
                else {
                    Tmr.stop();
                    if (music) {
                        msc.stop();
                    }
                }
            }
        });

        animationSpeed = 100;
        Animation = new Timer(animationSpeed, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                animation();
            }
        });

        //filling borders of map
        for (int i = 0; i < 16; i++)
            map[i][0] = 2;
        for (int i = 0; i < 16; i++)
            map[i][11] = 2;
        rowsElim = new boolean [map.length];
        gameOver = false;
        setFigure();

        Tmr.start();

    }

}
