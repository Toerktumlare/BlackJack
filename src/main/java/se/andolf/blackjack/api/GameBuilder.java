package se.andolf.blackjack.api;

import se.andolf.blackjack.Game;
import se.andolf.blackjack.handler.DeckHandler;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Thomas on 2017-04-08.
 */
public class GameBuilder {

    private DeckHandler deckHandler = new DeckHandler();
    private List<Player> players = new ArrayList<>();
    private Rules rules;

    public GameBuilder setDeckHandler(DeckHandler deckHandler) {
        this.deckHandler = deckHandler;
        return this;
    }

    public GameBuilder addPlayer(Player player) {
        players.add(player);
        return this;
    }

    public GameBuilder setRules(Rules rules){
        this.rules = rules;
        return this;
    }

    public GameBuilder addPlayers(Player... players) {
        this.players = asList(players);
        return this;
    }

    public Game build() {
        if(rules == null)
            this.rules = new StandardRules();
        return new Game(players, deckHandler, rules);
    }
}
