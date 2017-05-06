package se.andolf.blackjack.handler;

import se.andolf.blackjack.api.Card;
import se.andolf.blackjack.api.Deck;
import se.andolf.blackjack.api.Player;
import se.andolf.blackjack.util.DeckUtil;

/**
 * @author Thomas on 2017-03-04.
 */
public class DeckHandler {

    private static final int DEFAULT_NUMBER_OF_DECKS = 6;
    private Deck deck;

    public DeckHandler() {
        deck = new Deck(DEFAULT_NUMBER_OF_DECKS);
        DeckUtil.shuffle(deck.getCards());
    }

    public DeckHandler(Deck deck) {
        this.deck = deck;
    }

    public Card getCard() {
        return deck.getCard();
    }

    public int size() {
        return deck.getCards().size();
    }

    public Deck getDeck() {
        return deck;
    }
}
