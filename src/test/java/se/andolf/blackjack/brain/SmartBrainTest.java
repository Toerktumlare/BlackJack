package se.andolf.blackjack.brain;

import org.junit.Before;
import org.junit.Test;
import se.andolf.blackjack.api.Card;
import se.andolf.blackjack.api.Hand;
import se.andolf.blackjack.api.Rank;
import se.andolf.blackjack.api.Suit;

import static junit.framework.TestCase.assertEquals;
import static se.andolf.blackjack.api.Choice.HIT;
import static se.andolf.blackjack.api.Choice.SPLIT;
import static se.andolf.blackjack.api.Choice.STAND;

/**
 * @author Thomas on 2017-03-18.
 */
public class SmartBrainTest {

    private Brain brain;

    @Before
    public void init() {
        this.brain = new SmartBrain();
    }

    @Test
    public void hitIfLowerThanSixteen() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.TWO, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.FIVE, Suit.HEARTS));
        assertEquals(HIT, brain.getChoice(hand));
    }

    @Test
    public void standIfHigherThanSixteen() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.JACK, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        assertEquals(STAND, brain.getChoice(hand));
    }

    @Test
    public void splitIfSumOfCardsIsTwentyAndTwoFirstAreOfSameValue() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.JACK, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.QUEEN, Suit.HEARTS));
        assertEquals(SPLIT, brain.getChoice(hand));
    }

    @Test
    public void standIfUnknownValues() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.TWO, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.QUEEN, Suit.HEARTS));
        hand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        hand.addCard(new Card(Rank.QUEEN, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.JACK, Suit.HEARTS));
        hand.addCard(new Card(Rank.THREE, Suit.SPADES));
        assertEquals(STAND, brain.getChoice(hand));
    }

}