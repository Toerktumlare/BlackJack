package se.andolf.blackjack.handler;

import se.andolf.blackjack.api.Card;
import se.andolf.blackjack.api.Deck;
import se.andolf.blackjack.util.DeckUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Thomas on 2017-03-04.
 */
public class DeckHandler {

    private static int DEFAULT_NUMBER_OF_DECKS = 6;
    private Deck deck;


    public DeckHandler() {
        deck = new Deck(DEFAULT_NUMBER_OF_DECKS);
    }

    public Card getCard() {
        return deck.getCard();
    }

    public int size() {
        return deck.getCards().size();
    }
}
