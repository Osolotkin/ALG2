public class Game {

    private Deck deck;
    private Table table;
    private Display display;
    private Input input;
    private int cardsOnTable;
    private int score;

    public void play() {

        score = 0;
        cardsOnTable = 9;
        boolean gameOver = false;
        int [] cardsToRemove;
        Card [] cards = new Card [3];
        display = new Display();
        input = new Input();
        deck = new Deck();

        deck.shuffle();
        Card [] tmp = new Card [cardsOnTable];
        for (int i = 0; i < cardsOnTable; i++) {
            tmp[i] = deck.dealTheTopCard();
        }
        table = new Table(tmp);

        display.printCardsLeft(deck.getCountOfCards());
        while(!deck.isEmpty() && !(gameOver = gameOverCheck(table.getCards()))) {

            display.printCards(table.getCards());

            cardsToRemove = input.getTwoInputs();
            cards[0] = table.getCard(cardsToRemove[0]);
            cards[1] = table.getCard(cardsToRemove[1]);
            if (cards[0].getValue() + cards[1].getValue() == 11) {
                table.removeCards(cardsToRemove);
                table.addCards(new Card [] {deck.dealTheTopCard(), deck.dealTheTopCard()}, cardsToRemove);
            } else if (cards[0].getValue() > 10 && cards[1].getValue() > 10){
                cardsToRemove = new int [] {cardsToRemove[0], cardsToRemove[1], input.getInput()};
                cards[2] = table.getCard(cardsToRemove[2]);
                if (cards[0].getValue() + cards[1].getValue() + cards[2].getValue() == 13 + 12 + 11) {
                    table.removeCards(cardsToRemove);
                    table.addCards(new Card [] {deck.dealTheTopCard(), deck.dealTheTopCard(), deck.dealTheTopCard()}, cardsToRemove);
                }
            } else {
                display.printWarningWrongChoice();
                continue;
            }

            for (int i = 0; i < cardsToRemove.length; i++) {
                score += cards[i].getValue();
            }
            display.printScore(score);
            display.printCardsLeft(deck.getCountOfCards());


        }

        if (gameOver) {
            display.printCards(table.getCards());
            display.printGameOver();
            display.printScore(score);
        } else {
            display.printWin();
        }

    }

    private boolean gameOverCheck(Card [] cards) {

        for (int i = 0; i < cards.length; i++) {
            for (int j = i; j < cards.length; j++) {
                if (cards[i].getValue() + cards[j].getValue() == 11) return false;
            }
        }
        return true;

    }

}
