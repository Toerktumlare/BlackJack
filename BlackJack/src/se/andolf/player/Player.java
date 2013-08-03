package se.andolf.player;

import se.andolf.blackjack.Card;
import se.andolf.player.SmartPlayer.currentValueObject;

public interface Player {
	
	public void reciveCard(Card card);
	
	public int getChoice();
	
	public currentValueObject getCurrentValue();
	
	public void clearCards();
	
	public int getSuitedCards();

}
