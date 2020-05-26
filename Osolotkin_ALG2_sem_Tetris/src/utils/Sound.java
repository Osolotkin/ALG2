package utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Just simple class to control sounds and music, just wav files support.
 * @author Maxim Osolotkin
 */
public class Sound {

    private AudioInputStream stream = null;
    private Clip clip = null;
    private FloatControl gainControl = null;
    private boolean playing = false;

    /**
     * Plays wav file.
     * @param file sound file with wav extension.
     */
    public void play(File file) {

        try {

            playing = true;
            stream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Sound.Listener());
            clip.setFramePosition(0);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.start();

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            System.out.println("Just wav files are supported!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    /**
     * If playback is active, sets volume according to the given value. If given value is greater 1, than sets it to the
     * 1, if less than 0, sets to 0.
     * @param volume volume value in float.
     */
    public void setVolume(float volume) {

        if (playing) {
            if (volume > 1) volume = 1;
            else if (volume < 0) volume = 0;
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }

    }

    /**
     * If there is playback, than stop it.
     */
    public void stop() {

        if (playing) {
            clip.stop();
            playing = false;
        }

    }

    public boolean isPlaying() {
        return playing;
    }

    private class Listener implements LineListener {
        public void update(LineEvent e) {
            if (e.getType() == LineEvent.Type.STOP) {
                playing = false;
                synchronized(clip) {
                    clip.notify();
                }
            }
        }
    }

}
