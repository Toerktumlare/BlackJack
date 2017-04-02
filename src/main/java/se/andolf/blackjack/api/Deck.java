package se.andolf.blackjack.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.util.DeckUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Deck {

    private static final Logger logger = LogManager.getLogger(Deck.class);
    private static final int DEFAULT_NUMBER_OF_DECKS = 1;

    private List<Card> cards;

    public Deck() {
        this(DEFAULT_NUMBER_OF_DECKS);
    }

    public Deck(int numberOfDecks) {
        cards = new ArrayList<>();
        IntStream.range(0, numberOfDecks).forEach((i) -> fill());
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
            logger.debug("Card pulled: " + c.toString());
			cards.remove(0);
			return c;
	}

    public List<Card> getCards() {
        return cards;
    }
}
