package se.andolf.blackjack.api;

import static se.andolf.blackjack.api.Rules.Outcome.DRAW;
import static se.andolf.blackjack.api.Rules.Outcome.LOSS;
import static se.andolf.blackjack.api.Rules.Outcome.WIN;

/**
 * @author Thomas on 2017-04-13.
 */
public class StandardRules implements Rules {

    @Override
    public Outcome hasWon(int player, int dealer) {

        if(player == 20 && dealer == 20)
            return DRAW;
        if(player == 21 && dealer == 21)
            return DRAW;
        if(player > dealer)
            return WIN;

        return LOSS;
    }
}
