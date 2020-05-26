package app;

import utils.Sound;
import utils.gameInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Snake implements gameInterface {

    private boolean gameOver;
    private int gameSpeed;
    private int [][] map;
    private Timer Tmr;
    private Timer Animation;

    private boolean down;          //true - moving figure down, false - nothing
    private boolean up;            //at the moment unused
    private boolean right;         //true - move figure to the right, false - nothing
    private boolean left;          //true - move figure to the left, false - nothing
    private boolean enter;
    private int [][] snake;
    private int [] mvVector;
    private int lastIndex;
    private boolean foodIsOn;
    private boolean pause;
    private int pauseAnim;
    private boolean sound;
    private int yAnimOver;
    private int animationSpeed;
    private boolean back;
    private String lastDir;
    Sound snd;
    File soundFile;

    public boolean getBack() {
        return back;
    }

    public void setInput(boolean [] ar) {
        up = ar[0];
        down = ar[1];
        right = ar[2];
        left = ar[3];
        enter = ar[4];
        if (ar[5]) pause = !pause;
    }

    public ArrayList getStateForRender() {
        ArrayList tmp = new ArrayList<>();
        tmp.add(map.clone());
        tmp.add(null);
        tmp.add(false);
        tmp.add(0);
        tmp.add(0);
        tmp.add(gameOver);
        tmp.add(pauseAnim);
        tmp.add("Red");
        tmp.add(0);
        tmp.add(0);
        tmp.add(0);
        tmp.add(yAnimOver);
        tmp.add(new boolean [15]);
        return tmp;
    }

    private void add(int x, int y) {
        lastIndex++;
        snake[lastIndex][0] = x;
        snake[lastIndex][1] = y;
    }

    private void generateFood() {
        Random rnd = new Random();
        int n = rnd.nextInt(150 - lastIndex - 1) + 1;

        int c = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 1; j < 11; j++) {
                if (map[i][j] == 0) {
                  c++;
                  if (c == n) map[i][j] = 88;
                }
            }
        }
    }

    private void move() {
        int lstX = 0;
        int lstY = 0;
        for (int i = lastIndex; i >= 0; i--) {
            int [] mvVector = new int [2];
            if (!(i == lastIndex)) {
                mvVector[0] = lstX - snake[i][0];
                mvVector[1] = lstY - snake[i][1];
            } else {
                mvVector = this.mvVector.clone();
            }
            int x = snake[i][0];
            int y = snake[i][1];
            lstX = x;
            lstY = y;
            map[y][x] = 0;
            if ((x + mvVector[0] > 0 && x + mvVector[0] < 11) && (y + mvVector[1] >= 0 && y + mvVector[1] < 16)) {
                x += mvVector[0];
                y += mvVector[1];
            } else if (x + mvVector[0] <= 0) {
                x = 10;
            } else if (x + mvVector[0] >= 11) {
                x = 1;
            } else if (y + mvVector[1] >= 16) {
                y = 0;
            } else if (y + mvVector[1] < 0) {
                y = 15;
            }
            snake[i][0] = x;
            snake[i][1] = y;
            map[y][x] = 1;
        }
        return;
    }

    private void checkInput() {
        if (up && (!lastDir.equals("Down") || lastIndex == 0)) {
            mvVector[0] = 0;
            mvVector[1] = -1;
            lastDir = "Up";
        } else if (down && (!lastDir.equals("Up") || lastIndex == 0)) {
            mvVector[0] = 0;
            mvVector[1] = 1;
            lastDir = "Down";
        } else if (left && (!lastDir.equals("Right") || lastIndex == 0)) {
            mvVector[0] = -1;
            mvVector[1] = 0;
            lastDir = "Left";
        } else if (right && (!lastDir.equals("Left") || lastIndex == 0)) {
            mvVector[0] = 1;
            mvVector[1] = 0;
            lastDir = "Right";
        }
    }

    public void update() {

        checkInput();
        if (gameOver && enter) {
            back = true;
        }

    }

    public void loop() {
        if (!gameOver) {
            if (!pause) {
                if (!foodIsOn) {
                    generateFood();
                    foodIsOn = true;
                }
                int nxtY = snake[lastIndex][1] + mvVector[1];
                int nxtX = snake[lastIndex][0] + mvVector[0];
                if (nxtX <= 0) {
                    nxtX = 10;
                } else if (nxtX >= 11) {
                    nxtX = 1;
                } else if (nxtY >= 16) {
                    nxtY = 0;
                } else if (nxtY < 0) {
                    nxtY = 15;
                }
                if (map[nxtY][nxtX] == 88) {
                    add(nxtX, nxtY);
                    foodIsOn = false;
                }
                if (!(map[nxtY][nxtX] == 1)) {
                    move();
                } else {
                    gameOver = true;
                    Tmr.stop();
                    Animation.start();
                }
            } else {
                if (!Animation.isRunning()) {
                    Animation.start();
                }
            }
        }
    }

    private void animation() {

        if (pause) {
            pauseAnim++;
        } else if (gameOver) {
            if ((sound) && (yAnimOver < 15 ) && (!snd.isPlaying())) {
                soundFile = new File("src/Sounds/ChromDown.wav");
                snd.play(soundFile);
            }
            yAnimOver++;
        } else {
            Animation.stop();
        }

    }

    public Snake() {

        snd = new Sound();

        snake = new int [10*16][2];

        map = new int [16][12];
        mvVector = new int [2];

        for (int i = 0; i < 16; i++)
            map[i][0] = 2;
        for (int i = 0; i < 16; i++)
            map[i][11] = 2;
        gameOver = false;
        pause = false;

        int x = 1;
        int y = 1;

        snake[0][0] = x;
        snake[0][1] = y;
        lastIndex = 0;

        mvVector[0] = 1;
        mvVector[1] = 0;

        map[y][x] = 1;

        lastDir = "";

        gameSpeed = 300;
        Tmr = new javax.swing.Timer(gameSpeed, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loop();
            }
        });

        animationSpeed = 100;
        Animation = new javax.swing.Timer(animationSpeed, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                animation();
            }
        });

        Tmr.start();

    }

}
