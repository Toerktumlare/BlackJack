package se.andolf.player;

public class DumbBrain implements Brain{

	@Override
	public int getChoice(int currentValue, Hand hand) {
		// Brain that plays like the dealer
		
		//if value is lower then 16 brain wants a card
		if (currentValue <= 16) {
			return 0;
		}
		
		//if value is higher than 16 it wants to stand
		if (currentValue > 16) {
			return 1;
		}
		
		//if all checks fail stand
		else {
			return 1;
		}
		
	}
}
