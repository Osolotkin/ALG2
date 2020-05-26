package ui;

import javax.swing.*;

/**
 * Just main class, creates frame.
 * @author Maxim Osolotkin
 */
public class Main {

    public static void main(String[] args) {

        MainWindow window = new MainWindow();
        window.setTitle("Tetris The app.Game");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);

    }

}
