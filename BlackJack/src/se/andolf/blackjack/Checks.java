package se.andolf.blackjack;

import java.util.ArrayList;
import java.util.List;

import se.andolf.player.Hand;

public final class Checks {

	static boolean bustCheck(int currentValue) {
		if (currentValue <= 21) {
			return false;
		}
		return true;
	}

	static boolean blackJackCheck(Hand hand) {

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

	static boolean winCheck(int playerValue, int dealerValue) {

		// player higher than dealer and deler is below 21 - PLAYER WINS
		if (playerValue > dealerValue && dealerValue <= 21) {
			return true;
		}

		// player is bust, dealer is bust - PLAYER LOOSES
		else if (playerValue == 0 && dealerValue == 0) {
			return false;
		}

		// player has same as dealer - DRAW
		else if (playerValue == dealerValue) {
			System.out.println("Its a draw!");
		}

		// if player is under 21 and dealer is equal or higher than 22
		else if (playerValue < 21 && dealerValue >= 22) {
			return true;
		}

		// if all fails, player looses
		return false;
	}
}
