package se.andolf.blackjack.api;

import se.andolf.blackjack.Game;
import se.andolf.blackjack.handler.DeckHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas on 2017-04-08.
 */
public class GameBuilder {

    private DeckHandler deckHandler = new DeckHandler();
    private List<Player> players = new ArrayList<>();

    public GameBuilder setDeckHandler(DeckHandler deckHandler) {
        this.deckHandler = deckHandler;
        return this;
    }

    public GameBuilder addPlayer(Player player) {
        players.add(player);
        return this;
    }

    public Game build() {
        return new Game(players, deckHandler);
    }
}
