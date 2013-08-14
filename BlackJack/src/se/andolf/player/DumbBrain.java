package se.andolf.player;

import java.util.List;

import se.andolf.blackjack.Game;

public class DumbBrain implements Brain{
	
	Game game;
	
	public DumbBrain(Game game){
		this.game = game;
	}

	@Override
	public int getChoice(int currentHand, List<Hand>hands) {
		
		int currentValue = hands.get(currentHand).getCurrentHandTotalValue();
		
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
