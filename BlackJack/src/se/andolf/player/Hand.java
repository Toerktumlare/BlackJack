package se.andolf.player;

import java.util.ArrayList;
import java.util.List;

import se.andolf.blackjack.Card;

public class Hand {
	
	int aces = 0;
	List<Card> cards = new ArrayList<Card>();
	
	public void addCard(Card card){
		
		if(card.getValue() == 11){
			aces++;
		}
		
		cards.add(card);
		System.out.println("Card: " + card.toString() + " added to hand.");
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
	
	public int getAces(){
		return aces;
	}
	
	public List<Card> getCards(){
		return cards; 
	}
	
	public int getNoOfCards(){
		int noOfCards = cards.size();
		return noOfCards;
	}
}
