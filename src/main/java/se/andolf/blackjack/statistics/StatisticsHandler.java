package se.andolf.blackjack.statistics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.api.Player;

import java.util.ArrayList;
import java.util.List;

public class StatisticsHandler {

	private static final Logger logger = LogManager.getLogger(StatisticsHandler.class);

	private List<PlayerStats> playerStatses;
	private GameStats gameStats;

	public StatisticsHandler(){
        playerStatses = new ArrayList<>();
        gameStats = new GameStats();
    }

	public void createPlayerStats(String name){
		final PlayerStats playerStats = new PlayerStats(name);
		playerStatses.add(playerStats);
		gameStats.addPlayer();
        logger.info("---- NEW PLAYER STATISTICS OBJECT CREATED AND ADDED TO LIST ----");
	}

	public void addBlackJack(String name) {
		playerStatses.stream().filter(p -> p.getName().equals(name)).forEach(PlayerStats::addBlackJack);
	}

	public void addWin(String name) {
		playerStatses.stream().filter(p -> p.getName().equals(name)).forEach(PlayerStats::addWin);
	}

	public void addLoss(String name) {
		playerStatses.stream().filter(p -> p.getName().equals(name)).forEach(PlayerStats::addLoss);
	}

	public void addBusted(String name) {
		playerStatses.stream().filter(p -> p.getName().equals(name)).forEach(PlayerStats::addBust);
	}
	
	public void addRound(){
		gameStats.addRound();
	}
	
	public void addHand(){
		gameStats.addHand();
	}
	
	public void addDouble() {
		gameStats.addDouble();
	}
	
	public void printGameStatistics(){
        logger.info("Number of rounds played: " + gameStats.getRounds());
        logger.info("Number of players: " + gameStats.getPlayers());
        logger.info("Total number of hands played: " + gameStats.getTotalHands());
        logger.info("Total number of hands doubled: " + gameStats.getTotalDoubles());
	}

	public void printPlayerStatistics() {
		for (PlayerStats p : playerStatses) {
            logger.info("---- PLAYER: " + p.getName() + "----");
            logger.info("Number of Hands won: " + p.getWins());
            logger.info("Number of Hands lost: " + p.getLosses());
            logger.info("");
            logger.info("Number of games busted: " + p.getBusts());
            logger.info("Number of BlackJacks: " + p.getBlackJacks());
            logger.info("");
		}
	}
}