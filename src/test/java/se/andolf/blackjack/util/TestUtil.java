package se.andolf.blackjack.util;

import se.andolf.blackjack.Game;
import se.andolf.blackjack.api.Card;
import se.andolf.blackjack.api.Deck;
import se.andolf.blackjack.api.GameBuilder;
import se.andolf.blackjack.api.Player;
import se.andolf.blackjack.handler.DeckHandler;

import java.util.List;

/**
 * @author Thomas on 2017-04-13.
 */
public class TestUtil {

    public static Game getGame(List<Card> cards, Player... players){
        final Deck deck = new Deck(cards);
        final Player player = new Player("Thomas");
        return new GameBuilder()
                .setDeckHandler(new DeckHandler(deck))
                .addPlayers(players)
                .build();
    }
}
