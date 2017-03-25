package se.andolf.blackjack.api;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Thomas on 2017-03-18.
 */
public class HandTest {

    @Test
    public void shouldReturnValueZeroIfNoCards() {
        final Hand hand = new Hand();
        assertEquals(0, hand.getValue());
    }

    @Test
    public void shouldReturnTheCorrectValueOfCards() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.EIGHT, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.TWO, Suit.HEARTS));
        hand.addCard(new Card(Rank.QUEEN, Suit.CLUBS));

        assertEquals(20, hand.getValue());
    }

    @Test
    public void shouldReturnCorrectNumberOfAces() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.ACE, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.TWO, Suit.HEARTS));
        hand.addCard(new Card(Rank.ACE, Suit.CLUBS));

        assertEquals(2, hand.getAces());
    }

    @Test
    public void shouldReturnAnEmptyListIfNoCardsHaveBeenAdded() {
        final Hand hand = new Hand();
        assertNotNull(hand.getCards());
        assertTrue(hand.getCards().isEmpty());
    }

    @Test
    public void shouldRemoveCardFromHand(){
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.EIGHT, Suit.DIAMONDS));
        hand.removeCard(0);
        assertEquals(hand.getCards().get(0).getRank().getValue(), Rank.EIGHT.getValue());
        assertEquals(hand.getCards().get(0).getSuit().getValue(), Suit.DIAMONDS.getValue());
    }

}
