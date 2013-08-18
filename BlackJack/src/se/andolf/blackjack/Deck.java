package se.andolf.blackjack;

import java.util.List;
import java.util.ArrayList;

import se.andolf.blackjack.Card.Rank;
import se.andolf.blackjack.Card.Suit;

public class Deck {
	
	// TODO create so that the deck is 6 decks
	
	private List<Card> deck = new ArrayList<Card>();

	public void fill() {
		for (Rank rank : Card.Rank.values()) {
			for (Suit suit : Card.Suit.values()) {
				Card card = new Card(rank, suit, rank.getValue());
				deck.add(card);
			}
		}
	}
	
	public List<Card> getDeck(){
		return deck;
	}
	
	public void shuffle(){
		int N = deck.size();
		for (int i = 0; i < N; i++) {
            // int from remainder of deck
            int r = i + (int) (Math.random() * (N - i));
            Card swap = deck.get(r);
            deck.set(r, deck.get(i));
            deck.set(i, swap);
        }
	}
	
	public void print(){
		for (Card c : deck){
			System.out.println(c.toString());
		}
	}
	
	public Card getCard(){
		if (deck.size() == 0){
			this.fill();
			this.shuffle();
			System.out.println("---- DECK IS EMPTY REFILLING AND SHUFFLEING----");
		}
			Card c = deck.get(0);
			System.out.println("Card pulled: " + c.toString());
			deck.remove(0);
			return c;
	}
	
	public int getDeckSize(){				
		return deck.size();	
	}
}
