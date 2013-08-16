package se.andolf.player;

import java.util.ArrayList;
import java.util.List;

import se.andolf.blackjack.Card;
import se.andolf.blackjack.Game;

public class Player {
	
	private List<Hand> hands = null;
	private String name;
	private Game game;
	private int currentHand, playerPositions;
	private boolean hardValue = true;
	private Brain brain;

	public Player(Game game, boolean smart) {
		this.game = game;
		this.name = name;
		hands = new ArrayList<Hand>();
				
		if(smart){
			brain = new SmartBrain(game);
		} else {
			brain = new DumbBrain(game);
		}	
	}
	
	public void reciveCard(Card card) {
		
		if(hands.isEmpty()){
			hands.add(new Hand());
		}
		
		hands.get(currentHand).addCard(card);
	}

	public int getPlayerChoice() {
		
		//get the choice from the brain
		int brainsChoice = brain.getChoice(currentHand, hands);
		
		if(brainsChoice == 0){
			System.out.println("Player " + name + " says 'HIT ME!'");
		}
		
		if(brainsChoice == 1){
			System.out.println("Player " + name + " says 'I'LL STAND!'");
		}
		
		return brainsChoice;
	}
	
	public class currentValueObject {
		
		private Hand hand;
		private String softValue;
		private int hardValue;

		public currentValueObject(Hand hand) {
			this.hand = hand;
			this.hardValue = hand.getCurrentHandTotalValue();
			this.softValue = (Integer.toString(hardValue-10) + " / " + Integer.toString(hardValue));
		}
		
		public String getCurrentValue() {
			
			if(hand.getAces() > 0 && hardValue >= 11 && hardValue <= 21){
				return softValue;
			}
			return Integer.toString(hardValue);
		}
	}

	public currentValueObject getHandValueObject() {
		currentValueObject valueObject = new currentValueObject(hands.get(currentHand));		
		return valueObject;
	}
	
	public void clearCurrentHand() {		
		hands.remove(currentHand);
		System.out.println("Players cards removed");
	}

	public List<Hand> getAllHands() {
		return hands;
	}
	
	public int getCurrentHandNoOfCards(){		
		return hands.get(currentHand).getNoOfCards();		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Hand getCurrentHand() {
		return hands.get(currentHand);
	}

	public void setCurrentHand(int currentHand) {
		this.currentHand = currentHand;
	}
	public void removeCurrentHand(){
		hands.remove(currentHand);
	}

	public void initHand() {
		hands.add(new Hand());		
	}

	public int getPlayerPositions() {
		return playerPositions;
	}

	public void setPlayerPositions(int playerPositions) {
		this.playerPositions = playerPositions;
	}
}