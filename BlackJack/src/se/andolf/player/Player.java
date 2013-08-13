package se.andolf.player;

import java.util.ArrayList;
import java.util.List;

import se.andolf.blackjack.Card;
import se.andolf.blackjack.Game;

public class Player {
	
	private List<Hand> hands = null;
	private String name;
	private Game game;

	boolean hardValue = true;
	Brain brain = null;

	public Player(Game game, String name, boolean intelligence) {
		this.game = game;
		this.name = name;
		hands = new ArrayList<Hand>();
		hands.add(new Hand());
		
		if(intelligence){
			brain = new SmartBrain();
		} else {
			brain = new DumbBrain();
		}	
	}
	
	//player recives a card
	public void reciveCard(Card card, int currentHand) {

		hands.get(currentHand).addCard(card);
	}

	public int getChoice(int currentHand) {
		
		//get the choice from the brain
		int currentChoice = brain.getChoice(currentHand, hands.get(0));
		
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
	public currentValueObject getHandValueObject(int whatHand) {
		
		currentValueObject valueObject = new currentValueObject(hands.get(whatHand).getCurrentHandTotalValue());
		
		return valueObject;
	}
	
	//remove all cards
	public void clearHand(int whatHand) {
		
		hands.remove(whatHand);
		System.out.println("Players cards removed");
	}

	public List<Hand> getHands() {
		return hands;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}