package se.andolf.blackjack.brain;

import se.andolf.blackjack.api.Choice;
import se.andolf.blackjack.api.Hand;

import java.util.List;

public interface Brain {
	
	Choice getChoice(Hand currentHand);

}