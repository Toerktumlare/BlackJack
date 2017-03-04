package se.andolf.blackjack.handler;

import se.andolf.blackjack.api.Card;
import se.andolf.blackjack.api.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas on 2017-03-04.
 */
public class DeckHandler {

    private static int DEFAULT_NUMBER_OF_DECKS = 6;
    private List<Card> cards;


    public DeckHandler() {
        cards = new ArrayList<>();
        generate();
    }

    public int getCardsLeft() {
        return cards.size();
    }

    private void generate(){

        final List<Deck> decks = new ArrayList<>();
        for (int i = 0; i < DEFAULT_NUMBER_OF_DECKS; i++) {
            decks.add(new Deck());
        }

        cards = decks.stream().flatMap(deck -> deck.getCards().stream()).collect(Collectors.toList());
    }
}
