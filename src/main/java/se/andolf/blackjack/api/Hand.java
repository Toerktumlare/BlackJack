package se.andolf.blackjack.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.api.Card;

public class Hand {

    private static final Logger logger = LogManager.getLogger(Hand.class);

    private int aces = 0;
	private List<Card> cards = new ArrayList<Card>();
	
	public void addCard(Card card){
		
		if(card.getValue() == 11){
			aces++;
		}
		
		cards.add(card);
		logger.info("Card: " + card.toString() + " added to hand.");
	}
	
	public int getCurrentHandTotalValue(){
		
		int currentValue = 0;
		
		for(Card card : cards){
			currentValue += card.getValue();
		}
		return currentValue;
	}
	
	public int getAces(){
		return aces;
	}
	
	public List<Card> getCards(){
		return cards; 
	}
	
	public int getNoOfCards(){
		return cards.size();
	}
	public void removeCard(int index) {
		cards.remove(index);
        logger.info("---- CARD REMOVED AT " + index + " ----");
	}
}
