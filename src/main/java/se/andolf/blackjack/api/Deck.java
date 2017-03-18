package se.andolf.blackjack.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.util.DeckUtil;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private static final Logger logger = LogManager.getLogger(Deck.class);

	private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        fill();
        DeckUtil.shuffle(cards);
    }

    private void fill() {
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				Card card = new Card(rank, suit);
				cards.add(card);
			}
		}
	}
	
	public Card getCard(){
		if (cards.isEmpty()){
			this.fill();
            DeckUtil.shuffle(cards);
            logger.info("---- DECK IS EMPTY REFILLING AND SHUFFLEING----");
		}
			Card c = cards.get(0);
            logger.info("Card pulled: " + c.toString());
			cards.remove(0);
			return c;
	}

    public List<Card> getCards() {
        return cards;
    }
}
