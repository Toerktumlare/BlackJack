package se.andolf.blackjack.brain;

import se.andolf.blackjack.api.Hand;

import java.util.List;

public interface Brain {
	
	int getChoice(int currentValue, List<Hand> hands);

}