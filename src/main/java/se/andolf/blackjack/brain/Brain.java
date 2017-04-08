package se.andolf.blackjack.brain;

import se.andolf.blackjack.api.Choice;
import se.andolf.blackjack.api.Hand;

public interface Brain {
	
	Choice getChoice(Hand currentHand);

}