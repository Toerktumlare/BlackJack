package se.andolf.blackjack.api;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Thomas on 2017-04-02.
 */
public class DeckTest {

    @Test
    public void shouldCreateADeckWith52Cards() {
        final Deck deck = new Deck();
        assertEquals(52, deck.getCards().size());
    }

    @Test
    public void shouldCreateADeckWith312Cards() {
        final Deck deck = new Deck(6);
        assertEquals(312, deck.getCards().size());
    }

    @Test
    public void shouldReturnARandomCard() {
        final Deck deck = new Deck();
        assertTrue(deck.getCard() != null);
    }
}
