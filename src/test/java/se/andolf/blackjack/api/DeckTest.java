package se.andolf.blackjack.api;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

    @Test
    public void shouldReturnTwoDecidedCards() {
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.NINE, Suit.SPADES));
        cards.add(new Card(Rank.THREE, Suit.HEARTS));
        final Deck deck = new Deck(cards);
        assertEquals(9, deck.getCard().getValue());
        assertEquals(3, deck.getCard().getValue());
    }

    @Test
    public void shouldBuildANewDeckIfEmpty() {
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.NINE, Suit.SPADES));
        cards.add(new Card(Rank.THREE, Suit.HEARTS));
        final Deck deck = new Deck(cards);
        IntStream.range(0, 3).forEach(i -> deck.getCard());
        assertEquals(51, deck.size());
    }
}
