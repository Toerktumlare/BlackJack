package se.andolf.player;

import java.util.List;

import se.andolf.blackjack.Game;

public class SmartBrain implements Brain {

	private final int DECK_SIZE = 20;
	private Game game;

	public SmartBrain(Game game) {
		this.game = game;
	}

	public int getChoice(int currentHand, List<Hand> hands) {
		// TODO Smart Brain that takes more things into concideration

		int currentValue = hands.get(currentHand).getCurrentHandTotalValue();

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
