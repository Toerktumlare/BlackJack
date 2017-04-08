package se.andolf.blackjack.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.brain.Brain;
import se.andolf.blackjack.brain.DumbBrain;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private static final Logger logger = LogManager.getLogger(Player.class);
    private final boolean isDealer;
    private int currentHand = 0;
    private final String name;
    private final Brain brain;
    private final List<Hand> hands;
    private final PlayerStatistic playerStatistic;

	public Player(String name) {
		this(name, new DumbBrain(), false);
	}

    public Player(String name, boolean isDealer) {
        this(name, new DumbBrain(), isDealer);
    }

	public Player(String name, Brain brain) {
        this(name, brain, false);
	}

    public Player(String name, Brain brain, boolean isDealer) {
        this.name = name;
        this.hands = new ArrayList<>();
        this.brain = brain;
        this.isDealer = isDealer;
        playerStatistic = new PlayerStatistic();
    }

    public void addCard(Card card) {
        if(hands.isEmpty())
            hands.add(new Hand());
		hands.get(currentHand).addCard(card);
	}

	public Choice getChoice() {
		return brain.getChoice(hands.get(currentHand));
	}
	
	public void clearHand() {
		hands.remove(currentHand);
		logger.info("Players cards removed");
	}

	public List<Hand> getHands() {
		return hands;
	}

	public String getName() {
		return name;
	}

	public Hand getHand() {
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

	public void addHand() {
		hands.add(new Hand());		
	}
	
	public void addHand(Card card){
		hands.add(new Hand());
		hands.get(currentHand+1).addCard(card);
        logger.info("---- NEW HAND CREATED WITH CARD " + card.toString() + " ----");
	}
	
	public void removeCardFromCurrentHand(int index) {
		hands.get(currentHand).removeCard(index);
	}

    public boolean isDealer() {
        return isDealer;
    }

    public PlayerStatistic getStatistics() {
        return playerStatistic;
    }

    @Override
    public String toString() {
        return "Player: " + name + "\n" + playerStatistic.toString();
    }
}