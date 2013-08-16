package se.andolf.statistics;

public class GameStats {
	
	int players = 0;
	int rounds = 0;
	int totalHands = 0;
	
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

	public int getTotalHands() {
		return totalHands;
	}

	public void addHand() {
		totalHands++;
	}
	
}
