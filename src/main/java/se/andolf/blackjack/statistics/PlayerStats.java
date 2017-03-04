package se.andolf.blackjack.statistics;

public class PlayerStats {

	private String name;
	private int blackJacks = 0;
	private int busts = 0;
	private int wins = 0;
	private int losses = 0;
	
	public PlayerStats(String name){
		this.name = name;
	}

	public int getBlackJacks() {
		return blackJacks;
	}

	public void addBlackJack() {
		blackJacks++;
	}

	public int getBusts() {
		return busts;
	}

	public void addBust() {
		busts++;
	}

	public int getWins() {
		return wins;
	}

	public void addWin() {
		wins++;
	}

	public int getLosses() {
		return losses;
	}

	public void addLoss() {
		losses++;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
