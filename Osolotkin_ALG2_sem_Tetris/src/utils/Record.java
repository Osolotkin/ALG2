package utils;

/**
 * Class to store and manage player record. Default format is name and score each of six chars with space between.
 * @author Maxim Osolotkin
 */
public class Record implements CompareInterface {

    private String name;        //name of the record carrier
    private int score;          //record value
    private int nameMaxLength;  //maximal length of the name, default 6
    private int scoreMaxLength; //maximal length of the record value, default 6

    /**
     * Creates Record.
     * @param name name of the record
     * @param score value o the record
     */
    public Record(String name, int score) {
        nameMaxLength = 6;
        scoreMaxLength = 6;
        setName(name);
        setScore(score);
    }

    /**
     * Gets record in String, transform into needed format and set according variables. Basically gets name and value in
     * one String and transpose it into two different variables.
     * @param st record in String, that would be transform into needed format.
     */
    public void decodeStringAndSet(String st) {

        int max = (scoreMaxLength + nameMaxLength + 1 > st.length())? st.length() : scoreMaxLength + nameMaxLength + 1;
        StringBuilder sb = new StringBuilder();
        int tmp = 0;
        for (int i = max - 1; i >= 0; i--) {
            if (st.charAt(i) >= 48 && st.charAt(i) <= 48 + 10) {
                sb.append(st.charAt(i));
            } else {
                tmp = i;
                break;
            }
        }
        sb.reverse();
        if (sb.toString() != "") setScore(Integer.parseInt(sb.toString()));
        else setScore(0);

        sb = new StringBuilder();
        for (int i = 0; i <= tmp; i++) {
            sb.append(st.charAt(i));
        }
        setName(sb.toString());

    }

    /**
     * Gets name in String, transform into needed format and set according variable.
     * @param name name in String, that would be transform into needed format.
     */
    public void setName(String name) {
        String st = "";
        if (name.length() < nameMaxLength) {
            for (int i = name.length(); i < nameMaxLength; i++) {
                st += " ";
            }
            this.name = name + st;
        } else if (name.length() > nameMaxLength) {
            this.name = name.substring(0,6);
        } else {
            this.name = name;
        }
    }

    /**
     * Gets record value in int, transform into needed format and set according variable.
     * @param score record value in int, that would be transform into needed format.
     */
    public void setScore(int score) {
        if (score / (int)Math.pow(10,scoreMaxLength) == 0) {
            this.score = score;
        } else {
            this.score = 999999;
        }
    }

    /**
     * Connects name and record value in one String separated by space and return it.
     * @return name and record value in one String separated by space.
     */
    public String getString() {
        return name + " " + String.format("%06d", score);
    }

    /**
     * Detect if given object has higher score than this object, if so returns false, otherwise true.
     * @param o object that implements CompareInterface.
     * @return true if given object has lesser score than this object, otherwise false.
     */
    public boolean isHigher(CompareInterface o) {
        if (((Record)o).getScore() < this.score ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Detect if given object is lexicographical higher than this object, if so returns false, otherwise true.
     * @param o object that implements CompareInterface.
     * @return true if given object is lexicographical lesser than this object, otherwise false.
     */
    public boolean isLexicographicFirst(CompareInterface o) {
        String tmp = ((Record)o).getName();

        for (int i = 0; i < tmp.length(); i++) {
            if (tmp.charAt(0) <= name.charAt(0)) {
                return false;
            }
        }
        return true;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

}
