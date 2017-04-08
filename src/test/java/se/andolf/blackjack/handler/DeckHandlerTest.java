package se.andolf.blackjack.handler;

import org.junit.Test;
import se.andolf.blackjack.api.Card;
import se.andolf.blackjack.api.Deck;
import se.andolf.blackjack.api.Rank;
import se.andolf.blackjack.api.Suit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Thomas on 2017-03-04.
 */
public class DeckHandlerTest {

    @Test
    public void shouldGenerateDefaultValueOfDecks(){
        final int expected = 312;
        final DeckHandler deckHandler = new DeckHandler();
        final int cardsLeft = deckHandler.size();

        assertEquals(expected, cardsLeft);
    }

    @Test
    public void shouldReturnTwoPredetermindCards(){
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.NINE, Suit.HEARTS));
        final Deck deck = new Deck(cards);
        final DeckHandler deckHandler = new DeckHandler(deck);
        assertEquals(7, deckHandler.getCard().getValue());
        assertEquals(9, deckHandler.getCard().getValue());
    }
}
