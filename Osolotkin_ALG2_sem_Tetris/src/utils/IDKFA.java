package utils;

import java.io.File;

/**
 * Cheat-code that generally does nothing.
 * @author Maxim Osolotkin
 */
public class IDKFA extends CheatCode {

    public IDKFA() {
        super("IDKFA");
    }

    public void run() {
        File f = new File("src/Music/E1M1.wav");
        Sound msc = new Sound();
        msc.play(f);
    }

}
