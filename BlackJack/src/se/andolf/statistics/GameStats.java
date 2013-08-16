package se.andolf.statistics;

public class GameStats {
	
	private int players = 0;
	private int rounds = 0;
	private int totalHands = 0;
	
	public void addPlayer(){
		players++;
	}
	
	public int getPlayers(){
		return players;
	}
	
	public void addRound(){
		rounds++;
	}
	
	public int getRounds(){
		return rounds;
	}

	public void addHand() {
		totalHands++;
	}
	
	public int getTotalHands() {
		return totalHands;
	}
}
