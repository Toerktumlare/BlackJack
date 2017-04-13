package se.andolf.blackjack.brain;

import se.andolf.blackjack.api.Choice;
import se.andolf.blackjack.api.Hand;

import static se.andolf.blackjack.api.Choice.HIT;
import static se.andolf.blackjack.api.Choice.STAND;

public class DumbBrain implements Brain {

	@Override
	public Choice getChoice(Hand hand) {
		
		int currentValue = hand.getValue();

		if (currentValue <= 16) {
			return HIT;
		}
        return STAND;
	}
}
