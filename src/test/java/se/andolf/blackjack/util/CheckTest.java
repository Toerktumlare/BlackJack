package se.andolf.blackjack.util;

import org.junit.Test;
import se.andolf.blackjack.api.Card;
import se.andolf.blackjack.api.Hand;
import se.andolf.blackjack.api.Rank;
import se.andolf.blackjack.api.Suit;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Thomas on 2017-03-26.
 */
public class CheckTest {

    @Test
    public void shouldReturnTrueIfValueIsOver21() {
        boolean isBust = Checks.isBust(23);
        assertTrue(isBust);
    }

    @Test
    public void shouldReturnFalseIfValueIsUnder21() {
        boolean isBust = Checks.isBust(17);
        assertFalse(isBust);
    }

    @Test
    public void ifPlayerHasHigherThanDealerHeWins() {
        int player = 17;
        int dealer = 16;
        final boolean result = Checks.hasWon(player, dealer);
        assertTrue(result);
    }

    @Test
    public void ifPlayerHasLowerThanDealerButDealerHasBlackjackHeLooses() {
        int player = 14;
        int dealer = 21;
        final boolean result = Checks.hasWon(player, dealer);
        assertFalse(result);
    }

    @Test
    public void ifPlayerHasSameAsDealerHeLooses() {
        int player = 19;
        int dealer = 19;
        final boolean result = Checks.hasWon(player, dealer);
        assertFalse(result);
    }

    @Test
    public void ifDealerHasOver21AndPlayerIsUnderHeWins() {
        int player = 19;
        int dealer = 24;
        final boolean result = Checks.hasWon(player, dealer);
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfNoBlackjack() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.FIVE, Suit.SPADES));
        hand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        final boolean hasBlackJack = Checks.isBlackJack(hand);
        assertFalse(hasBlackJack);
    }

    @Test
    public void shouldReturnFalseIfNoCards() {
        final Hand hand = new Hand();
        final boolean hasBlackJack = Checks.isBlackJack(hand);
        assertFalse(hasBlackJack);
    }

    @Test
    public void shouldReturnFalseIfOneCard() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        final boolean hasBlackJack = Checks.isBlackJack(hand);
        assertFalse(hasBlackJack);
    }

    @Test
    public void shouldReturnFalseIfBust() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        hand.addCard(new Card(Rank.KING, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.NINE, Suit.HEARTS));
        hand.addCard(new Card(Rank.TWO, Suit.SPADES));
        final boolean hasBlackJack = Checks.isBlackJack(hand);
        assertFalse(hasBlackJack);
    }

    @Test
    public void shouldReturnFalseIf21ButNoAce() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        hand.addCard(new Card(Rank.KING, Suit.DIAMONDS));
        hand.addCard(new Card(Rank.FOUR, Suit.HEARTS));
        final boolean hasBlackJack = Checks.isBlackJack(hand);
        assertFalse(hasBlackJack);
    }

    @Test
    public void shouldReturnTrueIf21AndHasAceAce() {
        final Hand hand = new Hand();
        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.KING, Suit.DIAMONDS));
        final boolean hasBlackJack = Checks.isBlackJack(hand);
        assertTrue(hasBlackJack);
    }
}
