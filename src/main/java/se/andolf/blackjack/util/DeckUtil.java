package se.andolf.blackjack.util;

import se.andolf.blackjack.api.Card;

import java.util.List;
import java.util.Random;

/**
 * @author Thomas on 2017-03-04.
 */
public final class DeckUtil {

    private DeckUtil() {
    }

    public static List<Card> shuffle(List<Card> deck) {

        final Random random = new Random();

        int n = deck.size();
        for (int i = 0; i < n; i++) {
            int r = i + random.nextInt(n - i);
            Card swap = deck.get(r);
            deck.set(r, deck.get(i));
            deck.set(i, swap);
        }
        return deck;
    }
}
