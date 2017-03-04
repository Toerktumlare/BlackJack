package se.andolf.blackjack;

//- methods to access Card data as integer and String

public class Card {
	
	//define all rank cards as enums
	public enum Rank { 
		ACE(11, "Ace"), TWO(2, "Two"), THREE(3, "Three"), FOUR(4, "Four"), FIVE(5, "Five"), 
		SIX(6, "Six"), SEVEN(7, "Seven"), EIGHT(8, "Eight"), NINE(9, "Nine"), TEN(10, "Ten"), 
		JACK(10, "Jack"), QUEEN(10, "Queen"), KING(10, "King");
		
		private final int rank;
		private final String name;
		
		private Rank(int rank, String name){
			this.rank = rank;
			this.name = name;
		}
		
		//get value of rank
		public int getValue(){
			return rank;
		}
		
		@Override
		public String toString() {			
			return name;
		}
	}
	
	//define all suits as enums
	public enum Suit { CLUBS(1, "Clubs"), DIAMONDS(2, "Diamonds"), HEARTS(3, "Hearts"), SPADES(4, "Spades");
	
		@SuppressWarnings("unused")
		private final int suit;
		private final String name;
		
		private Suit(int suit, String name){
			this.suit = suit;
			this.name = name;
		}
		
		@Override
		public String toString() {			
			return name;
		}
		
	}
	
	private Rank name;
	private Suit suit;
	private int value;
	
	public Card(Rank name, Suit suit, int value){
		this.name = name;
		this.suit = suit;
		this.value = value;
	}
	
	public Card(Rank name, Suit suit, int value, int value2){
		this.name = name;
		this.suit = suit;
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
		
	public String toString(){
		return name + " of " + suit;		
	}
}