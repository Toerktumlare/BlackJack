package se.andolf.player;

import java.util.List;

public interface Brain {
	
	public int getChoice(int currentValue, List<Hand> hands);

}