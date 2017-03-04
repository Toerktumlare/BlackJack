package se.andolf.blackjack.api;

/**
 * @author Thomas on 2017-03-04.
 */
public enum Suit {
    CLUBS(1, "Clubs"),
    DIAMONDS(2, "Diamonds"),
    HEARTS(3, "Hearts"),
    SPADES(4, "Spades");

    private final int suit;
    private final String name;

    Suit(int suit, String name){
        this.suit = suit;
        this.name = name;
    }

    public int getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return name;
    }

}
