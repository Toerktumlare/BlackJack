package se.andolf.player;

import java.util.ArrayList;
import java.util.List;

import se.andolf.blackjack.Card;
import se.andolf.blackjack.Game;

public class SmartPlayer implements Player{
	private List<Card> Cards = null;
	private String name;
	private Game game;
//	private final int DECK_SIZE = 20;
	private int currentValue = 0;
	private int aces = 0;
	boolean hardValue = true;

	public SmartPlayer(Game game, String name) {
		this.game = game;
		this.name = name;
	}
	
	//player recives a card
	public void reciveCard(Card card) {
		
		//if no cardlist init list
		if (Cards == null) {
			Cards = new ArrayList<Card>();
		}
		
		//if card is an ace register it
		if (card.getValue() == 11) {
			aces++;
		}
		
		//set currentValue and add card to cardList
		currentValue += card.getValue();
		Cards.add(card);
	}

	public int makeChoice() {
		// TODO fix intelligence better

//		int dealerValue = game.getDealer().getCurrentValue();
//		int cardsLeft = game.getDeck().getDeckSize();
//		int suitedCards = game.getSuitedCardsOnTable();

		// return 1 if no card, 0 if card

		// if player has 16 and dealer has ace - CARD
		// if(getCurrentValue() == 16 || dealerValue == 1){
		// return 0;
		// }
		//
		// if player has lower than 16 - CARD
		if (currentValue < 16) {
			return 0;
		}
		//
		// //if player has 16 or lower, dealer has ace and decksize is lower
		// than factor - CARD
		// if(getCurrentValue() <= 16 || dealerValue == 1 || cardsLeft <
		// DECK_SIZE_FACTOR){
		// return 0;
		// }
		//
		// if player has 16 or higher - STAND
		if (currentValue >= 16) {
			return 1;
		}

		else {
			return 1;
		}
	}
	
	//CurrentValueObject
	public class currentValueObject {

		private String blackJackValue;
		private int currentValue;

		public currentValueObject(int currentValue) {
			this.currentValue = currentValue;
			this.blackJackValue = (Integer.toString(currentValue-10) + " / " + Integer.toString(currentValue));
		}

		public String getBlackJackValue() {
			return blackJackValue;
		}
		
		public int getCurrentValue() {
			return currentValue;
		}
	}
	
	//return currentValueObject
	public currentValueObject getCurrentValue() {
		currentValueObject b = new currentValueObject(currentValue);
		return b;
	}
	
	//remove all cards
	public void clearCards() {
		Cards.clear();
		currentValue = 0;
		aces = 0;
		System.out.println("Players cards removed");
	}
	
	//get how many suited cards player has atm.
	public int getSuitedCards() {
		int suitedCards = 0;
		for (Card c : Cards) {
			if (c.getValue() == 10) {
				suitedCards++;
			}
		}
		return suitedCards;
	}

	public List<Card> getCards() {
		return Cards;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAces() {
		return aces;
	}
	
	public int getNoOfCards() {
		return Cards.size();
	}
}