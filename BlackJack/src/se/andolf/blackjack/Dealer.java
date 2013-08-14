package se.andolf.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
	
	//static methods and static field
	
	//methods to get bets, deal hands, players and dealers turns, comparing the hands
	
	private List<Card> Cards = new ArrayList<Card>();
	private String name = "Dealer";
	
	public void reciveCard(Card card) {
		Cards.add(card);	
	}
	
	public int makeChoice(){
		if (getCurrentValue() <= 16){
			//new card
			return 0;
		}
		
		else {
			//stay
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
