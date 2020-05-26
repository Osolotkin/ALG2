package utils;

/**
 * Abstract class that is mainly abstract just to be abstract. Cares about cheat-codes.
 * @author Maxim Osolotkin
 */
public abstract class CheatCode {

    public char key;        //last pressed key on keyboard in char
    public char code [];    //code of cheat-code
    public int index;       //current index of code[], that was momentarily reached by current streak of strokes
    public int maxIndex;    //maximal index

    /**
     * Creates CheatCode.
     * @param code code in String, that would be code of cheat-code.
     */
    protected CheatCode(String code) {
        this.code = new char [code.length()];
        setCode(code);
        index = 0;
        maxIndex = code.length() - 1;
    }

    /**
     * Abstract method, that let each cheat-code to have individual method it can use, but not really useful created
     * just in educational purposes.
     */
    public abstract void run();

    /**
     * Controls if sequence of last pressed keys was code, otherwise resets index of current streak to zero.
     * @return true if sequence of last pressed keys was code, otherwise false.
     */
    public boolean control() {
        if (key == code[index]) {
            if (index == maxIndex) {
                index = 0;
                run();
                return true;
            }
            index++;
            return false;
        } else {
            index = 0;
            return false;
        }
    }

    public void setKey(char key) {
        this.key = Character.toUpperCase(key);
    }

    public void setCode(String code) {
        for (int i = 0; i < code.length(); i++) {
            this.code[i] = Character.toUpperCase(code.charAt(i));
        }
    }

}
