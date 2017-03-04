package se.andolf.blackjack.api;

public class Card {
	
	private Rank rank;
	private Suit suit;
	private int value;
	
	public Card(Rank rank, Suit suit, int value){
		this.rank = rank;
		this.suit = suit;
		this.value = value;
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public int getValue(){
		return value;
	}
		
	public String toString(){
		return rank + " of " + suit;
	}
}