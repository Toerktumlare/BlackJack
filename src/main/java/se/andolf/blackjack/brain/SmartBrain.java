package se.andolf.blackjack.brain;

import java.util.List;

import se.andolf.blackjack.Game;
import se.andolf.blackjack.api.Hand;

public class SmartBrain implements Brain {

	public int getChoice(int currentHand, List<Hand> hands) {
		// TODO Smart Brain that takes more things into concideration

		int currentValue = hands.get(currentHand).getCurrentHandTotalValue();

		if (currentValue == 20 && hands.get(currentHand).getCards().get(0).getValue() == 10 && hands.get(currentHand).getCards().get(1).getValue() == 10){
			return 2;
		}
		// if value is lower then 16 brain wants a card
		else if (currentValue <= 16) {
			return 0;
		}

		// if value is higher than 16 it wants to stand
		else if (currentValue > 16) {
			return 1;
		}

		// if all checks fail stand
		else {
			return 1;
		}

	}
}
