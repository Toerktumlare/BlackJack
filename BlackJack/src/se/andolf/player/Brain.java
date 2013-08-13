package se.andolf.player;

import java.util.List;
import se.andolf.blackjack.Card;

public interface Brain {
	
	public int getChoice(int currentValue, Hand hand);

}