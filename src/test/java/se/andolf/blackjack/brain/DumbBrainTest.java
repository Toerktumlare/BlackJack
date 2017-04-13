package se.andolf.blackjack.brain;

import org.junit.Before;
import org.junit.Test;
import se.andolf.blackjack.api.*;

import static junit.framework.TestCase.assertEquals;
import static se.andolf.blackjack.api.Choice.HIT;
import static se.andolf.blackjack.api.Choice.STAND;

/**
 * @author Thomas on 2017-03-18.
 */
public class DumbBrainTest {

    private Brain brain;

    @Before
    public void init() {
        this.brain = new DumbBrain();
    }

    @Test
    public void hitIfLowerThanSixteen() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.TWO, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.FIVE, Suit.HEARTS));
        assertEquals(HIT, brain.getChoice(hand));
    }

    @Test
    public void hitIfOnSixteen() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.NINE, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        assertEquals(HIT, brain.getChoice(hand));
    }

    @Test
    public void standIfHigherThanSixteen() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.JACK, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.QUEEN, Suit.HEARTS));
        assertEquals(STAND, brain.getChoice(hand));
    }
}
