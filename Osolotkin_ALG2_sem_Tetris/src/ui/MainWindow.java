package ui;

import app.Game;
import app.Snake;
import utils.*;
import input.Controls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import static utils.Util.sortByScore;

/**
 * Class that manage frame, provides listening to user input and cares about main logic.
 * @author Maxim Osolotkin
 */
public class MainWindow extends JFrame {

    //=========================== variables that cares about resolution and connected stuff ==========================//
    private int width;      //width of window in pixels
    private int height;     //height of window in pixels

    //================================================ controls ======================================================//
    private Controls keyInp;       //instance of Control, that cares about input
    private boolean down;          //true - down arrow is pressed
    private boolean up;            //true - up arrow is pressed
    private boolean right;         //true - right arrow is pressed
    private boolean left;          //true - left arrow is pressed
    private boolean p;             //true - p key is pressed;
    private boolean enter;         //true - enter key is pressed;
    private boolean esc;           //true - esc key is pressed;

    //================================================== cheats ======================================================//
    private IDDQD iddqd;    //level up, unlock achievement ach13
    private IDKFA idkfa;    //start snake game in tetris field, unlock achievement ach14
    private Slow slow;      //decrease game speed twice, unlock achievement ach15
    private Win win;        //let you win the game, unlock achievement ach16

    //================================================== paths =======================================================//
    private String dataPath = "src/Files/data.txt";
    private String recordsPath = "src/Files/records.txt";

    //================================================== games =======================================================//
    private Game game;      //main game - tetris
    private Snake snake;    //bonus game - snake, can be accessed only with cheat in main game

    //=============================================== components =====================================================//
    private MainMenu mainMenu;      //main menu, allows to change some settings and launch new game
    private GameField gameField;    //game field, only field for falling figures with borders, without info
    private SidePanel sidePanel;    //side panel, shows info about current game(level, score, next figure etc.)
    Container field;                //using to compose gameField and sidePanel in one peace.

    //=============================================== other stuff ====================================================//
    private Timer update;       //timer for main loop
    private int updateSpeed;    //update speed of timer update
    private Data data;          //instance of data, stores data, can read from file and save all current data
    private boolean gameIsOn;   //true - any game is on, false - no
    private boolean snakeIsOn;  //true - game snake is on, false - no
    private String plName;      //name of a player

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //-----------------------------------------There are some checking methods----------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void checkInput() {

        if ( keyInp.isKeyPressedCooldown(KeyEvent.VK_RIGHT) ) right = true;
        else right = false;
        if ( keyInp.isKeyPressedCooldown(KeyEvent.VK_LEFT) ) left = true;
        else left = false;
        if ( keyInp.isKeyPressedCooldown(KeyEvent.VK_DOWN) ) down = true;
        else down = false;
        if ( keyInp.isKeyPressedCooldown(KeyEvent.VK_UP) ) up = true;
        else up = false;
        if ( keyInp.isKeyPressedCooldown(KeyEvent.VK_P) ) p = true;
        else p = false;
        if ( keyInp.isKeyPressedCooldown(KeyEvent.VK_ENTER) ) enter = true;
        else enter = false;
        if ( keyInp.isKeyPressedCooldown(KeyEvent.VK_ESCAPE) ) esc = true;
        else esc = false;
        if ( keyInp.isKeyPressedCooldown(KeyEvent.VK_BACK_SPACE) ) esc = true;
        else esc = false;

    }

    private void checkCheats() {

        char key = keyInp.getKey();
        if (key != '\u0000') {
            iddqd.setKey(key);
            if (iddqd.control() && gameIsOn) {
                game.increaseLevel();
                if (game.getAchIntIndex(12) != 2) game.setAchIntIndex(12, 1);
            }
            idkfa.setKey(key);
            if (idkfa.control() && gameIsOn) {
                snake = new Snake();
                snakeIsOn = true;
                if (game.getAchIntIndex(13) != 2) game.setAchIntIndex(13, 1);
            }
            slow.setKey(key);
            if (slow.control() && gameIsOn) {
                game.decreaseGameSpeed(2);
                if (game.getAchIntIndex(14) != 2) game.setAchIntIndex(14, 1);
            }
            win.setKey(key);
            if (win.control() && gameIsOn) {
                if (game.getAchIntIndex(15) != 2) game.setAchIntIndex(15, 1);
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //---------------------------------------There are some record cares methods--------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void addRecord(String name, int score) {

        Record rc = new Record(name, score);
        Record [] tmp = Arrays.copyOf(mainMenu.getRec(), mainMenu.getRec().length + 1);
        tmp[tmp.length - 1] = rc;

        sortByScore(tmp);
        Record [] tmp2 = Arrays.copyOf(tmp, tmp.length - 1);

        String [] ans = new String [tmp2.length];
        for (int i = 0; i < tmp2.length; i++) {
            ans[i] = tmp2[i].getString();
        }
        data.setRec(ans);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------------------------Kind of loop------------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void loop() {

        checkInput();
        checkCheats();
        if (gameIsOn) {
            if (snakeIsOn) {

                snake.setInput(new boolean [] {up, down, right, left, enter, p}.clone());
                snake.update();
                gameField.setState(snake.getStateForRender());
                gameField.repaint();
                if (snake.getBack()) {
                    snake = null;
                    remove(sidePanel);
                    remove(gameField);
                    snakeIsOn = false;
                    gameIsOn = false;
                    createMainMenu();
                }
                return;

            }
            game.setInput(new boolean [] {up, down, right, left, enter, p}.clone());
            game.update();
            gameField.setState(game.getStateForRender());
            sidePanel.setState(game.getStateToDisplay());
            sidePanel.setAchInt(game.getAchStateInt());
            game.setAchInt(sidePanel.getAchInt());
            gameField.repaint();
            sidePanel.update();
            sidePanel.repaint();
            if (game.getBack()) {
                addRecord(plName, game.getScore());
                game = null;
                remove(sidePanel);
                remove(gameField);
                gameIsOn = false;
                createMainMenu();
            }
        } else {
            if (mainMenu.getNwGm()) {
                data.setSnd(mainMenu.getSound());
                data.setMsc(mainMenu.getMusic());
                data.setCld(mainMenu.getColored());
                data.setAch(mainMenu.getAch());
                data.setRec(mainMenu.getRecString());
                data.setName(plName);
                game = new Game(mainMenu.getMusic(), mainMenu.getSound());
                mainMenu.setNwGm(false);
                gameIsOn = true;
                remove(mainMenu);
                createGameField();
            } else{
                if (mainMenu.getResChange()) {
                    mainMenu.setResChange(false);
                    width = mainMenu.getWidth();
                    height = mainMenu.getHeight();
                    mainMenu.setPreferredSize(new Dimension(width, height));
                    getContentPane().setSize(new Dimension(width, height));
                    pack();
                }
                mainMenu.setInput(new boolean [] {up, down, right, left, enter, esc}.clone());
                mainMenu.update();
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------Methods that helping in creating components-----------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void createMainMenu() {

        mainMenu = new MainMenu(width,height);

        mainMenu.setSound(data.getSnd());
        mainMenu.setMusic(data.getMsc());
        mainMenu.setColored(data.getCld());
        mainMenu.setAch(data.getAch());
        mainMenu.setRec(data.getRec());

        mainMenu.setPreferredSize(new Dimension(mainMenu.getWidth(),mainMenu.getHeight()));
        this.add(mainMenu);
        pack();

    }

    private void createGameField() {

        field = getContentPane();

        gameField = new GameField(width, height);
        sidePanel = new SidePanel(6 * 20 * width / 240, height);

        if (mainMenu.getColored()) {
            gameField.setClPath("Coloured/");
            sidePanel.setClPath("Coloured/");
        }
        else {
            gameField.setClPath("Blocks/");
            sidePanel.setClPath("Blocks/");
        }

        gameField.setPreferredSize(new Dimension(width, height));
        field.add(gameField);

        sidePanel.setPreferredSize(new Dimension((6 * 20 * width) / 240, height));
        sidePanel.setTimerSpeed(1000/updateSpeed);
        sidePanel.setAnimSpeedOfAch(100/updateSpeed);
        sidePanel.setAchBoolean(mainMenu.getAch());
        game.setAchInt(sidePanel.getAchInt());
        field.add(sidePanel);

        field.setLayout(new BoxLayout(field, BoxLayout.X_AXIS));
        field.setVisible(true);
        this.pack();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //------------------------------------------------Creates MainWindow----------------------------------------------//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    MainWindow() {

        data = new Data(16, 5);
        data.readAndSet("data", dataPath);
        data.readAndSet("records", recordsPath);

        width = data.getWd();
        height = data.getHg();

        keyInp = new Controls();
        addKeyListener(keyInp);
        this.setFocusable(true);

        //createGameField();
        createMainMenu();
        gameIsOn = false;
        snakeIsOn = false;

        updateSpeed = 5;
        update = new Timer(updateSpeed, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loop();
            }
        });

        iddqd = new IDDQD();
        idkfa = new IDKFA();
        slow = new Slow();
        win = new Win();

        plName = "player";

        this.setVisible(true);

        update.start();

        //saving important values into txt file
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                data.setMsc(mainMenu.getMusic());
                data.setSnd(mainMenu.getSound());
                data.setCld(mainMenu.getColored());
                data.setWd(mainMenu.getWidth());
                data.setHg(mainMenu.getHeight());
                if (gameIsOn) {
                    data.setAch(sidePanel.getAchBoolean());
                } else {
                    data.setAch(mainMenu.getAch());
                }
                data.writeFile("data");
                data.setRec(mainMenu.getRecString());
                data.writeFile("records");
            }
        });

    }


}