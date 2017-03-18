package se.andolf.blackjack.brain;

import se.andolf.blackjack.api.Choice;
import se.andolf.blackjack.api.Hand;

import static se.andolf.blackjack.api.Choice.*;

public class SmartBrain implements Brain {

	public Choice getChoice(Hand hand) {

		int currentValue = hand.getValue();

		if (currentValue == 20 && hand.getCards().get(0).getValue() == 10 && hand.getCards().get(1).getValue() == 10){
			return SPLIT;
		}
		// if value is lower then 16 brain wants a card
		else if (currentValue < 16) {
			return HIT;
		}

		else {
			return STAND;
		}

	}
}
