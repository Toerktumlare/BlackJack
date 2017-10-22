package se.andolf.blackjack.api;

import se.andolf.blackjack.handler.DeckHandler;
import se.andolf.blackjack.util.NamesUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Thomas on 2017-10-22.
 */
public class Settings {

    private final DeckHandler deckHandler;
    private final List<Player> players;
    private final Rules rules;
    private int rounds = 1;

    private Settings(DeckHandler deckHandler, List<Player> players, Rules rules, int rounds) {
        this.deckHandler = deckHandler;
        this.players = players;
        this.rules = rules;
        this.rounds = rounds;
    }

    public DeckHandler getDeckHandler() {
        return deckHandler;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Rules getRules() {
        return rules;
    }

    public int getRounds() {
        return rounds;
    }

    public static class Builder {

        private DeckHandler deckHandler;
        private List<Player> players;
        private Rules rules;
        private int rounds;

        public Builder() {
            deckHandler = new DeckHandler();
            players = Collections.singletonList(new Player(NamesUtil.getName()));
            rules = new StandardRules();
            rounds = 1;
        }

        public Builder setDeckHandler(DeckHandler deckHandler) {
            this.deckHandler = deckHandler;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setRules(Rules rules) {
            this.rules = rules;
            return this;
        }

        public Builder setRounds(int rounds) {
            this.rounds = rounds;
            return this;
        }

        public Settings build() {
            return new Settings(deckHandler, players, rules, rounds);
        }
    }
}
