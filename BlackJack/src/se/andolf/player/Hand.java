package se.andolf.player;

import java.util.ArrayList;
import java.util.List;

import se.andolf.blackjack.Card;

public class Hand {
	
	int aces;
	List<Card> cards = new ArrayList<Card>();
	
	public void addCard(Card card){
		
		if(card.getValue() == 11 && card.getValue() == 1){
			addAce();
		}
		
		cards.add(card);
		System.out.println("Card: " + card.toString() + "added to hand.");
	}
	
	public int getCurrentHandTotalValue(){
		
		int currentValue = 0;
		
		for(Card card : cards){
			currentValue += card.getValue();
		}
		return currentValue;
	}

	private void addAce() {
		aces++;		
	}
	
	public List<Card> getCards(){
		return cards; 
	}
}
