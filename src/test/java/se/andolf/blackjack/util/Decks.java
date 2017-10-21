package se.andolf.blackjack.util;

/**
 * @author Thomas on 2017-05-06.
 */
public enum Decks {
    SINGLE_CARD,
    TWO_CARDS,
    THREE_CARDS,
    FIVE_CARDS;

    public enum Scenarios {
        DEALER_BLACKJACK_AFTER_INIT_DEAL,
        FOUR_PLAYERS_ALL_STAND,
        TWO_PLAYERS_ONE_BLACKJACK,
        ONE_PLAYER_BOTH_DEALER_AND_PLAYER_BUST,
        PLAYER_WINS,
        PLAYER_LOSS,
        PLAYER_DRAW,
        TWO_PLAYERS_UNTIL_BOTH_STAND,
        PLAYER_BLACKJACK_DEALER_TEN,
        PLAYER_BLACKJACK_AFTER_DEAL,
        PLAYER_BUSTS
    }
}
