package utils;

import java.io.File;

/**
 * Just cheat-code for fun, quit the game.
 * @author Maxim Osolotkin
 */
public class Win extends CheatCode {

    public Win() {
        super("WIN");
    }

    public void run() {
        File f = new File("src/Sounds/TalaTalaTalaTum.wav");
        Sound msc = new Sound();
        msc.play(f);

        while (true) {
            if (!msc.isPlaying()) {
                System.exit(0);
            }
        }
    }

}
