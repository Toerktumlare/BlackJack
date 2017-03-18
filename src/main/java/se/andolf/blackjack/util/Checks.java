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

		if (hand.getValue() == 21) {
			return true;
		}
		return false;
	}

	public static boolean hasWon(int playerValue, int dealerValue) {

		if (playerValue > dealerValue && dealerValue <= 21) {
			return true;
		}

		else if (playerValue == 0 && dealerValue == 0) {
			return false;
		}

		else if (playerValue == dealerValue) {
			System.out.println("Its a draw!");
		}

		else if (playerValue < 21 && dealerValue >= 22) {
			return true;
		}

		return false;
	}
}
