package se.andolf.blackjack.api;

/**
 * @author Thomas on 2017-04-13.
 */
public enum GameState {
    GAME,
    FIRST_DEAL,
    PLAYERS,
    DEALER,
    CLEAR;

    public enum Checks {
        ALL,
        BLACKJACK,
        TWENTYONE,
        WIN,
        BUST
    }
}
