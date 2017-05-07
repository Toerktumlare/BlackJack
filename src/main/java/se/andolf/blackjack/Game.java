package se.andolf.blackjack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.brain.DumbBrain;
import se.andolf.blackjack.handler.DeckHandler;
import se.andolf.blackjack.util.Checks;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static se.andolf.blackjack.api.Choice.HIT;
import static se.andolf.blackjack.api.Choice.STAND;
import static se.andolf.blackjack.api.GameState.FIRST_DEAL;
import static se.andolf.blackjack.api.GameState.GAME;
import static se.andolf.blackjack.api.Rules.Outcome.*;

public class Game {

    private static final Logger logger = LogManager.getLogger(Game.class);

	private List<Player> players;
	private Player dealer;
    private GameStatistic statistics;
    private DeckHandler deckHandler;
    private Rules rules;

    public Game(List<Player> players, DeckHandler deckHandler, Rules rules) {
		dealer = new Player("Dealer", new DumbBrain(), true);
		this.deckHandler = deckHandler;
        this.players = players;
        this.rules = rules;
        statistics = new GameStatistic();
	}

	public void winCheck(List<Player> players) {
        players.stream().forEach(player -> {
            if(player.getHands().isEmpty()){
                player.getStatistics().addLoss();
            } else if(dealer.getHands().isEmpty()){
                player.getStatistics().addWin();
            } else {
                resolveOutcome(player);
            }
        });
	}

    private void resolveOutcome(Player player) {
        final Rules.Outcome outcome = rules.hasWon(player.getHand().getValue(), dealer.getHand().getValue());
        if (outcome == WIN) {
            player.getStatistics().addWin();

        } else if (outcome == LOSS) {
            player.getStatistics().addLoss();

        } else if (outcome == DRAW) {
            player.getStatistics().addDraw();
        }
    }

    private void bustCheck(Player player) {
		if (Checks.isBust(player.getHand().getValue())) {
			player.removeCurrentHand();
			player.getStatistics().addBust();
		}
	}

    private void bustCheck(List<Player> players) {
        players.stream().forEach(this::bustCheck);
    }

	private void clearCards() {
        players.stream().forEach(player -> player.getHands().clear());
        dealer.getHands().clear();
	}

    private void deal(Deal who) {
        switch (who) {
            case DEALER:
                dealer.addCard(deckHandler.getCard());
                break;
            case PLAYERS:
                players.stream().forEach(p -> p.addCard(deckHandler.getCard()));
                break;
            default:
                players.stream().forEach(p -> p.addCard(deckHandler.getCard()));
                dealer.addCard(deckHandler.getCard());
        }
    }

    public Player getPlayer(String id){
        return players.stream().filter(p -> p.getId().equals(id)).distinct().findFirst().orElse(null);
    }

    Player getDealer() {
        return dealer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    void play(Player player) {
        boolean isPlaying = true;
        while (isPlaying) {
            final Choice choice = player.getChoice();
            if (choice == HIT) {
                player.addCard(deckHandler.getCard());
            }
            else if (choice == STAND) {
                isPlaying = false;
            }
        }
    }

    private void play() {
        players.stream().forEach(this::play);
    }

    private void hasBlackJack(List<Player> players) {
        players.stream().filter(player -> !player.isDealer()).forEach(player -> {
            final List<Hand> hands = player.getHands();
            IntStream.range(0, hands.size()).forEach(i -> {
                if(Checks.isBlackJack(hands.get(i))){
                    player.clearHand(i);
                }
            });
        });
    }

    public void run(){
        run(GAME);
    }

    public void run(Deal deal){
        deal(deal);
    }

    public void run(GameState state) {
        switch (state) {
            case GAME:
                run(FIRST_DEAL);
                run(GameState.Checks.BLACKJACK);
                run(GameState.PLAYERS);
                run(GameState.DEALER);
                run(GameState.Checks.BUST);
                run(GameState.Checks.WIN);
                run(GameState.CLEAR);
                statistics.addRound();
                break;
            case FIRST_DEAL:
                deal(Deal.ALL);
                deal(Deal.PLAYERS);
                break;
            case PLAYERS:
                play();
                break;
            case DEALER:
                play(dealer);
                break;
            case CLEAR:
                clearCards();
                break;
        }
    }

    public void run(GameState.Checks checks) {
        switch (checks) {
            case ALL:
                break;
            case BLACKJACK:
                hasBlackJack(players);
                break;
            case TWENTYONE:
                break;
            case WIN:
                winCheck(players);
                break;
            case BUST:
                bustCheck(players);
                bustCheck(dealer);
                break;
        }
    }


    public Statistic getStatistics() {
        return statistics;
    }

    public static class GameBuilder {

        private DeckHandler deckHandler;
        private List<Player> players;
        private Rules rules;

        public GameBuilder(DeckHandler deckHandler, Player... players) {
            this.deckHandler = deckHandler;
            this.players = asList(players);
        }

        public GameBuilder(Player... players) {
            this.deckHandler = new DeckHandler();
            this.players = asList(players);
        }

        public Game run() {
            final Game game = getGame();
            game.run();
            return game;
        }

        public Game run(Deal deal) {
            final Game game = getGame();
            game.run(deal);
            return game;
        }

        private Game getGame(){
            if(rules == null)
                this.rules = new StandardRules();
            return new Game(players, deckHandler, rules);
        }

        public Game run(GameState state) {
            final Game game = getGame();
            game.run(state);
            return game;
        }
    }

}