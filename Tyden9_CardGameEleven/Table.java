public class Table {

    private Card cards[];
    private int numberOfCards;

    Table(Card [] cards) {
        this.cards = cards.clone();
        this.numberOfCards = cards.length;
    }

    public void addCard(Card card, int position) {
        if (position < numberOfCards && cards[position].getValue() == 0) {
            cards[position] = card;
        }
    }

    public void addCards(Card [] cards, int [] positions) {
        for (int i = 0; i < positions.length; i++) {
            addCard(cards[i], positions[i]);
        }
    }

    public void removeCards(int [] positions) {
        for (int i = 0; i < positions.length; i++) {
            if (cards[positions[i]].getValue() == 0) return;
        }
        for (int i = 0; i < positions.length; i++) {
            cards[positions[i]] = new Card(0,0);
        }
    }

    public Card [] getCards() {
        return cards.clone();
    }

    public Card getCard(int position) {
        return cards[position];
    }

}
