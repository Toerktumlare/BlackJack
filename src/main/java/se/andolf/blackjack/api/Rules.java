package se.andolf.blackjack.api;

/**
 * @author Thomas on 2017-04-13.
 */
public interface Rules {

    enum Outcome {
        WIN,
        LOSS,
        DRAW
    }

    Outcome hasWon(int player, int dealer);
}
