public class Display {

    private final String resetColor = "\u001B[0m";
    private final String red = "\u001B[31m";
    private final String green = "\u001B[32m";

    public void printCards(Card [] cards) {

        System.out.print("|");
        for (int i = 0; i < cards.length - 1; i++){
            System.out.print((i + 1) + "| " + cards[i].getSign() + cards[i].getColor() + cards[i].getSuit() + resetColor + " |");

        }
        System.out.print(cards.length + "| " + cards[cards.length - 1].getSign() + cards[cards.length - 1].getColor() + cards[cards.length - 1].getSuit() + resetColor);
        System.out.println();
    }

    public void printWarningWrongChoice() {
        System.out.println(red + "Wrong choice! Try again" + resetColor);
    }

    public void printScore(int score) {
        System.out.println(green + "Your score is : " + resetColor + score);
    }

    public void printCardsLeft(int count) {
        System.out.println(green + "Cards in the deck : " + resetColor + count);
    }

    public void printGameOver() {
        System.out.println(red + "Game Over!");
        System.out.println("No more combinations!" + resetColor);
    }

    public void printWin() {
        System.out.println(green + "Congratulations!");
        System.out.println("Win is Yours!" + resetColor);
    }

}
