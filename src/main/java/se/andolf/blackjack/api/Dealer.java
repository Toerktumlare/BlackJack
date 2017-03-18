package se.andolf.blackjack.api;

import java.util.ArrayList;
import java.util.List;

import static se.andolf.blackjack.api.Choice.HIT;
import static se.andolf.blackjack.api.Choice.STAND;

public class Dealer {
	
	private List<Card> Cards = new ArrayList<>();
	private String name = "Dealer";
	
	public void reciveCard(Card card) {
		Cards.add(card);	
	}
	
	public Choice getChoice(){
		if (getCurrentValue() <= 16){
			return STAND;
		}
		
		else {
			return HIT;
		}
	}
	
	public int getCurrentValue() {
		int cardValue = 0;
		for(Card c : Cards){
			cardValue += c.getValue();
		}
		return cardValue;
	}
	
	public String getName(){
		return name;
	}

	public List<Card> getCards() {
		return Cards;
	}
	
}
