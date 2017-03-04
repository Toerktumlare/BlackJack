package se.andolf.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
	
	private List<Card> Cards = new ArrayList<Card>();
	private String name = "Dealer";
	
	public void reciveCard(Card card) {
		Cards.add(card);	
	}
	
	public int getChoice(){
		if (getCurrentValue() <= 16){
			return 0;
		}
		
		else {
			return 1;
		}
	}
	
	public int getNoOfCards() {
		return Cards.size();
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
