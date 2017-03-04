package se.andolf.blackjack.statistics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class StatisticsHandler {

	private static final Logger logger = LogManager.getLogger(StatisticsHandler.class);

	private List<PlayerStats> statisticsList = new ArrayList<>();
	private GameStats gameStats = new GameStats();
	
	public void createPlayerStats(String name){
		PlayerStats playerStats = new PlayerStats(name);
		statisticsList.add(playerStats);
		gameStats.addPlayer();
        logger.info("---- NEW PLAYER STATISTICS OBJECT CREATED AND ADDED TO LIST ----");
	}

	public void addBlackJack(String name) {
		for (PlayerStats p : statisticsList) {
			if(p.getName().equals(name)){
				p.addBlackJack();
			}
		}
	}

	public void addWin(String name) {
		for (PlayerStats p : statisticsList) {
			if(p.getName().equals(name)){
				p.addWin();
			}
		}
	}

	public void addLoss(String name) {
		for (PlayerStats p : statisticsList) {
			if(p.getName().equals(name)){
				p.addLoss();
			}
		}
	}

	public void addBusted(String name) {
		for (PlayerStats p : statisticsList) {
			if(p.getName().equals(name)){
				p.addBust();
			}
		}
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
		for (PlayerStats p : statisticsList) {
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