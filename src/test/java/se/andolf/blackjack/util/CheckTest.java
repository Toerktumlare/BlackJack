package se.andolf.blackjack.util;

import org.junit.Test;

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
}
