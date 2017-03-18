package se.andolf.blackjack.api;

public class Card {
	
	private Rank rank;
	private Suit suit;
	
	public Card(Rank rank, Suit suit){
		this.rank = rank;
		this.suit = suit;
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public int getValue(){
		return rank.getValue();
	}
		
	public String toString(){
		return rank + " of " + suit;
	}
}