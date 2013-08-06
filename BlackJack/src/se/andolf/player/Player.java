package se.andolf.player;

import java.util.ArrayList;
import java.util.List;

import se.andolf.blackjack.Card;
import se.andolf.blackjack.Game;

public class Player {
	
	private List<List<Card>> Hands = null;
	private List<Card> Cards = null;
	private String name;
	private Game game;
	private int currentValue = 0;
	private int aces = 0;
	boolean hardValue = true;
	Brain brain = null;

	public Player(Game game, String name, boolean inteligence) {
		this.game = game;
		this.name = name;
		Hands = new ArrayList<List<Card>>();
		Cards = new ArrayList<Card>();
		Hands.add(Cards);
		
		if(inteligence){
			brain = new SmartBrain();
		} else {
			brain = new DumbBrain();
		}
		
		
	}
	
	private void createDumbBrain(){
		
	}
	
	private void createSmartBrain(){
		
	}
	
	//player recives a card
	public void reciveCard(Card card) {
		
		//if card is an ace register it
		if (card.getValue() == 11) {
			aces++;
		}
		
		//set currentValue and add card to cardList
		currentValue += card.getValue();
		for(List<Card> c : Hands){
			c.add(card);			
		}
	}

	public int getChoice() {
		
		//get the choice from the brain
		int currentChoice = brain.getChoice(currentValue);
		if(currentChoice == 0){
			System.out.println("Player " + name + " says 'HIT ME!'");
		}
		if(currentChoice == 1){
			System.out.println("Player " + name + " says 'I'LL STAND!'");
		}
		
		return currentChoice;
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
		for(List<Card> Cards : Hands){
			Cards.clear();			
		}
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

	public List<List<Card>> getCards() {
		return Hands;
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