package se.andolf.blackjack.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.brain.Brain;
import se.andolf.blackjack.brain.DumbBrain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {

	private static final Logger logger = LogManager.getLogger(Player.class);
    private String id;
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

    public Player(String name, Brain brain, boolean isDealer) {
        this.name = name;
        this.hands = new ArrayList<>();
        this.brain = brain;
        this.isDealer = isDealer;
        playerStatistic = new PlayerStatistic();
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void addCard(Card card) {
        if(hands.isEmpty())
            hands.add(new Hand());
		hands.get(currentHand).addCard(card);
	}

	public Choice getChoice() {
		return brain.getChoice(hands.get(currentHand));
	}

    public void clearHand(int index) {
        hands.remove(index);
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

	public void removeCurrentHand(){
		hands.remove(currentHand);
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