import java.util.Random;

public class Deck {

    private Card [] order;
    private int topCardIndex;
    private boolean isEmpty;
    private final int cardsInDeck = 52;

    Deck() {
        order = new Card[cardsInDeck];
        createFreshDeck();
        topCardIndex = 0;
    }

    public void createFreshDeck() {
        int tmp = 13;
		for (int i = 1; i <= 4; i++) {
			int suit = i;
            for (int j = 1; j <= tmp; j++) {
                order[j + tmp*(i-1) - 1] = new Card(j, suit);
            }
		}
	}

    public void shuffle() {
        Random rnd = new Random();
        for (int i = 0; i < 52; i++) {
            Card tmp = order[i];
            int n = rnd.nextInt(52 - i) + i;
            order[i] = order[n];
            order[n] = tmp;

        }
    }

    public Card dealTheTopCard() {
        topCardIndex ++;
        Card tmp = order[topCardIndex - 1];
        order[topCardIndex - 1] = new Card(0,0);
        return tmp;
    }

    public boolean isEmpty() {
        return !(topCardIndex < cardsInDeck);
    }

    public int getCountOfCards() {
        return cardsInDeck - topCardIndex;
    }

}
