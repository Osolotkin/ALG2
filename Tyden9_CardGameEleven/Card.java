public class Card {

    private int value;
    private String sign;
    private String suit;
    private String color;

    private final String Spades = "\u2660";
    private final String Diamonds = "\u2666";
    private final String Clubs = "♣";
    private final String Hearts = "♥";

    private final String red = "\u001B[31m";
    private final String black = "\u001B[30m";

    private final String square = "" + (char) 3;
    private final String empty = " ";

    Card(int value, int suit) {
        setValue(value);
        setSuit(suit);
    }

    public void setValue(int value) {
        this.value = value;
        if (value == 0) {
            this.sign = square;
        }
        if (value == 1) {
            this.sign = "A";
        }
        for (int i = 2; i <= 10; i++) {
            if (value == i) {
                this.sign =  Integer.toString(i);
            }
        }
        if (value == 11) {
            this.sign = "J";
        }
        if (value == 12) {
            this.sign = "Q";
        }
        if (value == 13) {
            this.sign = "K";
        }
    }

    public void setSuit(int suit) {
        if (suit == 1) {
            this.suit = Spades;
            this.color = black;
        }
        else if (suit == 2) {
            this.suit = Diamonds;
            this.color = red;
        }
        else if (suit == 3) {
            this.suit = Clubs;
            this.color = black;
        }
        else if (suit == 4) {
            this.suit = Hearts;
            this.color = red;
        } else if (suit == 0) {
            this.suit = empty;
            this.color = black;
        }
    }

    public String getSuit() {
        return suit;
    }

    public String getColor() {
        return color;
    }

    public String getSign() {
        return sign;
    }

    public int getValue() {
        return value;
    }
	
}
