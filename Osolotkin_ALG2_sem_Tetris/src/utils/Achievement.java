package utils;

/**
 * Class that manage achievement.
 * @author Maxim Osolotkin
 */
public class Achievement {

    private int Y;              //using in animation
    private int id;             //id of animation
    private int stateI;         //integer state, 0 - locked, 1 - animation is active, 2 - unlocked
    private boolean stateB;     //true - unlocked, false - locked
    private int animSpeed;      //speed of achievement receiving animation

    /**
     * Creates Achievement.
     * @param id id of an achievement.
     */
    public Achievement(int id) {
        this.id = id;
        animSpeed = 100;
    }

    /**
     * Setting state of achievement in ints (0 for locked, 1 for active animation and unlocked, 2 for unlocked).
     * According to these values setts boolean state of achievement.
     * @param st integer state to set
     */
    public void setStateInt(int st) {
        this.stateI = st;
        if (st < 1) stateB = false;
        else stateB = true;
    }

    /**
     * Setting state of achievement in boolean. According to it setts int state of achievement.
     * @param st boolean state to set
     */
    public void setStateBoolean(boolean st) {
        this.stateB = st;
        if (st) stateI = 2;
    }

    public void increaseY() {
        this.Y ++;
    }

    public void setY (int y) {
        this.Y = y;
    }

    public void setAnimSpeed(int animSpeed) {
        this.animSpeed = animSpeed;
    }

    public int getY () {
        return Y;
    }

    public boolean getStateBoolean(){
        return stateB;
    }

    public int getAnimSpeed() {
        return animSpeed;
    }

    public int getId() {
        return id;
    }

    public int getStateInt(){
        return stateI;
    }

}
