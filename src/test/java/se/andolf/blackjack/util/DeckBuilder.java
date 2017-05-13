package se.andolf.blackjack.util;

import se.andolf.blackjack.api.Card;
import se.andolf.blackjack.api.Deck;
import se.andolf.blackjack.api.Rank;
import se.andolf.blackjack.api.Suit;
import se.andolf.blackjack.handler.DeckHandler;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Thomas on 2017-05-06.
 */
public class DeckBuilder {

    public static DeckHandler get(Decks deck) {
        switch (deck) {
            case SINGLE_CARD:
                return getDeck(new Card(Rank.SEVEN, Suit.DIAMONDS));
            case TWO_CARDS:
                return getDeck(new Card(Rank.SEVEN, Suit.DIAMONDS), new Card(Rank.NINE, Suit.HEARTS));
            case THREE_CARDS:
                return getDeck(
                        new Card(Rank.SEVEN, Suit.DIAMONDS),
                        new Card(Rank.NINE, Suit.HEARTS),
                        new Card(Rank.TWO, Suit.DIAMONDS)
                );
            case FIVE_CARDS:
                return getDeck(
                        new Card(Rank.SEVEN, Suit.DIAMONDS),
                        new Card(Rank.NINE, Suit.SPADES),
                        new Card(Rank.SEVEN, Suit.HEARTS),
                        new Card(Rank.SEVEN, Suit.DIAMONDS),
                        new Card(Rank.THREE, Suit.CLUBS)
                );
            default:
                return new DeckHandler();
        }
    }

    public static DeckHandler get(Decks.Scenarios scenarios){
        switch (scenarios) {
            case FOUR_PLAYERS_ALL_STAND:
                return getDeck(
                        new Card(Rank.SEVEN, Suit.DIAMONDS),
                        new Card(Rank.NINE, Suit.SPADES),
                        new Card(Rank.KING, Suit.HEARTS),
                        new Card(Rank.KING, Suit.DIAMONDS),
                        new Card(Rank.KING, Suit.CLUBS),
                        new Card(Rank.SEVEN, Suit.DIAMONDS),
                        new Card(Rank.FIVE, Suit.DIAMONDS),
                        new Card(Rank.NINE, Suit.SPADES),
                        new Card(Rank.KING, Suit.HEARTS),
                        new Card(Rank.KING, Suit.DIAMONDS),
                        new Card(Rank.SEVEN, Suit.DIAMONDS),
                        new Card(Rank.FOUR, Suit.DIAMONDS)
                );
            case TWO_PLAYERS_ONE_BLACKJACK:
                return getDeck(
                        new Card(Rank.KING, Suit.DIAMONDS),
                        new Card(Rank.NINE, Suit.SPADES),
                        new Card(Rank.SEVEN, Suit.HEARTS),
                        new Card(Rank.ACE, Suit.DIAMONDS),
                        new Card(Rank.KING, Suit.CLUBS)
                );
            case ONE_PLAYER_BOTH_DEALER_AND_PLAYER_BUST:
                return getDeck(
                        new Card(Rank.SEVEN, Suit.DIAMONDS),
                        new Card(Rank.NINE, Suit.SPADES),
                        new Card(Rank.SEVEN, Suit.SPADES),
                        new Card(Rank.KING, Suit.DIAMONDS),
                        new Card(Rank.THREE, Suit.SPADES),
                        new Card(Rank.KING, Suit.SPADES)
                );
            case PLAYER_WINS:
                return getDeck(
                        new Card(Rank.SEVEN, Suit.DIAMONDS),
                        new Card(Rank.NINE, Suit.SPADES),
                        new Card(Rank.SEVEN, Suit.SPADES),
                        new Card(Rank.SEVEN, Suit.DIAMONDS),
                        new Card(Rank.EIGHT, Suit.SPADES)
                );
            case PLAYER_LOSS:
                return getDeck(
                        new Card(Rank.SEVEN, Suit.DIAMONDS),
                        new Card(Rank.NINE, Suit.SPADES),
                        new Card(Rank.SEVEN, Suit.SPADES),
                        new Card(Rank.THREE, Suit.DIAMONDS),
                        new Card(Rank.QUEEN, Suit.SPADES)
                );
            case PLAYER_DRAW:
                return getDeck(
                        new Card(Rank.KING, Suit.DIAMONDS),
                        new Card(Rank.JACK, Suit.SPADES),
                        new Card(Rank.QUEEN, Suit.SPADES),
                        new Card(Rank.JACK, Suit.DIAMONDS)
                );
            case TWO_PLAYERS_UNTIL_BOTH_STAND:
                return getDeck(
                        new Card(Rank.SEVEN, Suit.DIAMONDS),
                        new Card(Rank.NINE, Suit.SPADES),
                        new Card(Rank.QUEEN, Suit.SPADES),
                        new Card(Rank.JACK, Suit.DIAMONDS),
                        new Card(Rank.KING, Suit.DIAMONDS)
                );
            case PLAYER_BLACKJACK_AFTER_DEAL:
                return getDeck(
                        new Card(Rank.ACE, Suit.DIAMONDS),
                        new Card(Rank.SEVEN, Suit.SPADES),
                        new Card(Rank.KING, Suit.SPADES)
                );
            case PLAYER_BLACKJACK_DEALER_TEN:
                return getDeck(
                        new Card(Rank.ACE, Suit.DIAMONDS),
                        new Card(Rank.QUEEN, Suit.SPADES),
                        new Card(Rank.KING, Suit.SPADES),
                        new Card(Rank.THREE, Suit.HEARTS),
                        new Card(Rank.FIVE, Suit.DIAMONDS)
                );
            default:
                return new DeckHandler();
        }
    }

    private static DeckHandler getDeck(Card... cards) {
        return new DeckHandler(new Deck(new ArrayList<>(Arrays.asList(cards))));
    }
}
