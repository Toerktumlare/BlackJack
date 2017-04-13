package se.andolf.blackjack.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static se.andolf.blackjack.api.Rules.Outcome.DRAW;
import static se.andolf.blackjack.api.Rules.Outcome.LOSS;
import static se.andolf.blackjack.api.Rules.Outcome.WIN;

/**
 * @author Thomas on 2017-04-13.
 */
public class StandardRulesTest {

    @Test
    public void ifBothPlayerAndDealerHas17DealerWins(){
        final Rules rules = new StandardRules();
        assertEquals(LOSS, rules.hasWon(17, 17));
    }

    @Test
    public void ifBothPlayerAndDealerHas18DealerWins(){
        final Rules rules = new StandardRules();
        assertEquals(LOSS, rules.hasWon(18, 18));
    }

    @Test
    public void ifBothPlayerAndDealerHas19DealerWins(){
        final Rules rules = new StandardRules();
        assertEquals(LOSS, rules.hasWon(19, 19));
    }

    @Test
    public void ifBothPlayerAndDealerHas20Draw(){
        final Rules rules = new StandardRules();
        assertEquals(DRAW, rules.hasWon(20, 20));
    }

    @Test
    public void ifBothPlayerAndDealerHas21Draw(){
        final Rules rules = new StandardRules();
        assertEquals(DRAW, rules.hasWon(21, 21));
    }

    @Test
    public void ifPlayerHasHigherThanDealerPlayerWins(){
        final Rules rules = new StandardRules();
        assertEquals(WIN, rules.hasWon(20, 17));
    }

    @Test
    public void ifDealerHasHigherThanPlayerLooses(){
        final Rules rules = new StandardRules();
        assertEquals(LOSS, rules.hasWon(17, 19));
    }
}
