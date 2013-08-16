package se.andolf.statistics;

import java.util.ArrayList;
import java.util.List;

public class StatisticsHandler {

	List<PlayerStats> statisticsList = new ArrayList<PlayerStats>();
	GameStats gameStats = new GameStats();
	
	public void createPlayerStats(String name){
		PlayerStats playerStats = new PlayerStats(name);
		statisticsList.add(playerStats);
		gameStats.addPlayer();
		System.out.println("---- NEW PLAYER STATISTICS OBJECT CREATED AND ADDED TO LIST ----");
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
	
	public void printGameStatistics(){
		System.out.println("Number of rounds played: " + gameStats.getRounds());
		System.out.println("Number of players: " + gameStats.getPlayers());
		System.out.println("Total number of hands played: " + gameStats.getTotalHands());
	}

	public void printPlayerStatistics() {
		for (PlayerStats p : statisticsList) {
			System.out.println("---- PLAYER: " + p.getName() + "----");
			System.out.println("Number of Hands won: " + p.getWins());
			System.out.println("Number of Hands lost: " + p.getLosses());
			System.out.println("Number of games busted: " + p.getBusts());
			System.out.println("Number of BlackJacks: " + p.getBlackJacks());
			System.out.println();
		}
	}

}
