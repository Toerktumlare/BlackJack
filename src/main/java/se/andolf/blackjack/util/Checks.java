package se.andolf.blackjack.util;

import se.andolf.blackjack.api.Card;
import se.andolf.blackjack.api.Hand;

import java.util.List;

public final class Checks {

	public static boolean isBust(int currentValue) {
        return currentValue > 21;
    }

	public static boolean isBlackJack(Hand hand) {

		final List<Card> cards = hand.getCards();

		if(cards.isEmpty())
			return false;
		int card1 = cards.get(0).getValue();
		int card2 = cards.get(1).getValue();

		if (card1 == 1 && card2 == 10 || card1 == 10 && card2 == 1) {
			return true;
		}

        return hand.getValue() == 21;
    }

	public static boolean hasWon(int playerValue, int dealerValue) {

        return playerValue > dealerValue && dealerValue <= 21 ||
                dealerValue > 21 && playerValue <= 21;
    }
}
