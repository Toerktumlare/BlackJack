package se.andolf.blackjack.util;

import se.andolf.blackjack.api.Hand;

public final class Checks {

    private Checks() {
    }

    public static boolean isBust(int currentValue) {
        return currentValue > 21;
    }

	public static boolean isBlackJack(Hand hand) {
        return !hand.getCards().isEmpty() && hand.getAces() == 1 && hand.getValue() == 21;

    }

	public static boolean hasWon(int playerValue, int dealerValue) {
        return playerValue > dealerValue && dealerValue <= 21 ||
                dealerValue > 21 && playerValue <= 21;
    }
}
