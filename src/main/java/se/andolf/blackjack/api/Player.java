package se.andolf.blackjack.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.Game;
import se.andolf.blackjack.brain.Brain;
import se.andolf.blackjack.brain.DumbBrain;
import se.andolf.blackjack.brain.SmartBrain;

import java.util.ArrayList;
import java.util.List;

import static se.andolf.blackjack.api.Choice.HIT;
import static se.andolf.blackjack.api.Choice.SPLIT;
import static se.andolf.blackjack.api.Choice.STAND;

public class Player {

	private static final Logger logger = LogManager.getLogger(Player.class);

	private List<Hand> hands;
	private String name;
	private int currentHand = 0;
	private Brain brain;

	public Player(String name, Brain brain) {
        this.name = name;
		this.hands = new ArrayList<>();
		this.brain = brain;
	}
	
	public void reciveCard(Card card) {
		
		if(hands.isEmpty()){
			hands.add(new Hand());
		}
		
		hands.get(currentHand).addCard(card);
	}

	public Choice getChoice() {
		return brain.getChoice(hands.get(currentHand));
	}
	
//	public class CurrentValueObject {
//
//		private Hand hand;
//		private String softValue;
//		private int hardValue;
//
//        CurrentValueObject(Hand hand) {
//			this.hand = hand;
//			this.hardValue = hand.getValue();
//			this.softValue = Integer.toString(hardValue-10) + " / " + Integer.toString(hardValue);
//		}
//
//		public String getCurrentValue() {
//
//			if(hand.getAces() > 0 && hardValue >= 11 && hardValue <= 21){
//				return softValue;
//			}
//			return Integer.toString(hardValue);
//		}
//	}

//	public CurrentValueObject getHandValueObject() {
//		return new CurrentValueObject(hands.get(currentHand));
//	}
	
	public void clearCurrentHand() {		
		hands.remove(currentHand);
		logger.info("Players cards removed");
	}

	public List<Hand> getAllHands() {
		return hands;
	}

	public String getName() {
		return name;
	}

	public Hand getCurrentHand() {
		return hands.get(currentHand);
	}
	public int getCurrentHandIndex(){
		return currentHand;
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
	
	public void initSecondHandWithCard(Card card){
		hands.add(new Hand());
		hands.get(currentHand+1).addCard(card);
        logger.info("---- NEW HAND CREATED WITH CARD " + card.toString() + " ----");
	}
	
	public void removeCardFromCurrentHand(int index) {
		hands.get(currentHand).removeCard(index);
	}
}