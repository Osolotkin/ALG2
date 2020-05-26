package utils;

import java.io.File;

/**
 * Cheat-code that generally does nothing.
 * @author Maxim Osolotkin
 */
public class Slow extends CheatCode {

    public Slow() {
        super("SLOW");
    }

    public void run() {
        File f = new File("src/Music/payne.wav");
        Sound msc = new Sound();
        msc.play(f);
    }

}
