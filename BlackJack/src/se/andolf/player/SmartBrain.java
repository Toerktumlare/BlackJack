package se.andolf.player;


public class SmartBrain implements Brain {

	// private final int DECK_SIZE = 20;

	@Override
	public int getChoice(int currentValue, Hand hand) {
		// TODO Smart Brain that takes more things into concideration
		

		
		// if value is lower then 16 brain wants a card
		if (currentValue <= 16) {
			return 0;
		}

		// if value is higher than 16 it wants to stand
		if (currentValue > 16) {
			return 1;
		}

		// if all checks fail stand
		else {
			return 1;
		}

	}

}
