package input;

import java.awt.event.*;

/**
 * Provides control above user input via KeyListener.
 * @author Maxim Osolotkin
 */
public class Controls implements KeyListener{

    private boolean[] keys;         //stores boolean values for each key in corresponding index
    private int cooldown;           //cooldown in ms, using to block activation of key several times in one stroke
    private long lastPressedTime;   //time in ms, when was last key pressed
    private char lastPressedKey;    //last pressed key
    private boolean anyKeyPressed;  //true if any key is pressed, otherwise false

    /**
     * Creates Controls
     */
    public Controls() {
        cooldown = 100;
        lastPressedTime = 0;
        keys = new boolean[ 256 ];
    }

    public boolean isKeyPressed( int keyCode ) {
        return keys[ keyCode ];
    }

    /**
     * Returns true if according key to given keyCode is pressed and there haven been pressed any key in cooldown
     * period, otherwise false.
     * @param keyCode integer code of key.
     * @return true if key is pressed after cooldown, otherwise false.
     */
    public boolean isKeyPressedCooldown( int keyCode ) {
        if (isKeyPressed(keyCode) && checkCooldown(cooldown, lastPressedTime)) {
            lastPressedTime = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if elapsed time from last key stroke was greater then cooldown, otherwise false.
     * @param cooldown integer value in ms.
     * @param lastPressedTime integer value in ms.
     * @return true if elapsed time from last key stroke was greater then cooldown, otherwise false
     */
    private boolean checkCooldown(int cooldown, long lastPressedTime) {
        if (java.time.LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli() - lastPressedTime > cooldown) {
            return true;
        } else return false;
    }

    /**
     * Gets info about pressed keys and applies all necessary changes to variables.
     * @param e KeyEvent.
     */
    public synchronized void keyPressed( KeyEvent e ) {
        int keyCode = e.getKeyCode();
        if( keyCode > 0 && keyCode < keys.length ) {
            keys[ keyCode ] = true;
            lastPressedKey = e.getKeyChar();
            anyKeyPressed = true;
        }
    }

    /**
     * Gets info about released keys and applies all necessary changes to variables.
     * @param e KeyEvent.
     */
    public synchronized void keyReleased( KeyEvent e ) {
        int keyCode = e.getKeyCode();
        if( keyCode >= 0 && keyCode < keys.length ) {
            keys[ keyCode ] = false;
            anyKeyPressed = false;
        }
    }

    /**
     * Just because of interface.
     * @param e KeyEvent.
     */
    public synchronized void keyTyped( KeyEvent e ) {
    }

    public void setCooldown(int cooldown) {
        if (cooldown >= 0) {
            this.cooldown = cooldown;
        }
        else {
            System.out.println("Value of cooldown is negative, setting to zero");
            this.cooldown = 0;
        }
    }

    /**
     * Returns char according to the last pressed key, also check for cooldown, if there is no last key returns '\u0000'.
     * @return char according to the last pressed key.
     */
    public char getKey() {
        if (anyKeyPressed && checkCooldown(cooldown, lastPressedTime)) {
            lastPressedTime = System.currentTimeMillis();
            return lastPressedKey;
        }
        return '\u0000';
    }

    public int getCooldown() {
        return cooldown;
    }
}
