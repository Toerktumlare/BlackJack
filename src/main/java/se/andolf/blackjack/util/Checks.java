package se.andolf.blackjack.util;

import java.util.List;

import se.andolf.blackjack.api.Card;
import se.andolf.blackjack.api.Hand;

public final class Checks {

	public static boolean isBust(int currentValue) {
		if (currentValue <= 21) {
			return false;
		}
		return true;
	}

	public static boolean isBlackJack(Hand hand) {

		List<Card> cards = hand.getCards();

		int card1 = cards.get(0).getValue();
		int card2 = cards.get(1).getValue();

		if (card1 == 1 && card2 == 10 || card1 == 10 && card2 == 1) {
			return true;
		}

		if (hand.getCurrentHandTotalValue() == 21) {
			return true;
		}
		return false;
	}

	public static boolean hasWon(int playerValue, int dealerValue) {

		// brain higher than dealer and deler is below 21 - PLAYER WINS
		if (playerValue > dealerValue && dealerValue <= 21) {
			return true;
		}

		// brain is bust, dealer is bust - PLAYER LOOSES
		else if (playerValue == 0 && dealerValue == 0) {
			return false;
		}

		// brain has same as dealer - DRAW
		else if (playerValue == dealerValue) {
			System.out.println("Its a draw!");
		}

		// if brain is under 21 and dealer is equal or higher than 22
		else if (playerValue < 21 && dealerValue >= 22) {
			return true;
		}

		// if all fails, brain looses
		return false;
	}
}
