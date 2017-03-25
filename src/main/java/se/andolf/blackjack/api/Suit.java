package se.andolf.blackjack.api;

/**
 * @author Thomas on 2017-03-04.
 */
public enum Suit {
    CLUBS(1, "Clubs"),
    DIAMONDS(2, "Diamonds"),
    HEARTS(3, "Hearts"),
    SPADES(4, "Spades");

    private final int value;
    private final String name;

    Suit(int value, String name){
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }

}
