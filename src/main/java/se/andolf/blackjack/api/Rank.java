package se.andolf.blackjack.api;

/**
 * @author Thomas on 2017-03-04.
 */
public enum Rank {
    ACE(11, "Ace"), TWO(2, "Two"), THREE(3, "Three"), FOUR(4, "Four"), FIVE(5, "Five"),
    SIX(6, "Six"), SEVEN(7, "Seven"), EIGHT(8, "Eight"), NINE(9, "Nine"), TEN(10, "Ten"),
    JACK(10, "Jack"), QUEEN(10, "Queen"), KING(10, "King");

    private final int rank;
    private final String name;

    Rank(int rank, String name){
        this.rank = rank;
        this.name = name;
    }

    public int getValue(){
        return rank;
    }

    @Override
    public String toString() {
        return name;
    }
}
