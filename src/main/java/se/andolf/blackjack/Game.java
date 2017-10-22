package se.andolf.blackjack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.NameUtil;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.brain.DumbBrain;
import se.andolf.blackjack.handler.DeckHandler;
import se.andolf.blackjack.util.Checks;
import se.andolf.blackjack.util.NamesUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static se.andolf.blackjack.api.Choice.HIT;
import static se.andolf.blackjack.api.Choice.STAND;
import static se.andolf.blackjack.api.GameState.FIRST_DEAL;
import static se.andolf.blackjack.api.Rules.Outcome.*;

public class Game {

    private static final Logger logger = LogManager.getLogger(Game.class);

    private static GameBuilder gameBuilder;
    private final int rounds;
    private final List<Player> players;
	private final Player dealer;
    private final Outcome outcome;
    private final DeckHandler deckHandler;
    private final Rules rules;

    public Game(List<Player> players, DeckHandler deckHandler, Rules rules, int rounds) {
		dealer = new Player("Dealer", new DumbBrain(), true);
		this.deckHandler = deckHandler;
        this.players = players;
        this.rules = rules;
        outcome = new Outcome();
        this.rounds = rounds;
	}

	public void winCheck(List<Player> players) {
        players.forEach(p -> {
            p.getHands().forEach(h -> {
                final Rules.Outcome outcome = rules.hasWon(h.getValue(), dealer.getHand().getValue());
                if (outcome == WIN) {
                    p.getStatistics().addWin();
                    this.outcome.addWin();
                    dealer.getStatistics().addLoss();

                } else if (outcome == LOSS) {
                    p.getStatistics().addLoss();
                    this.outcome.addLoss();
                    dealer.getStatistics().addWin();

                } else if (outcome == DRAW) {
                    p.getStatistics().addDraw();
                    this.outcome.addDraw();
                    dealer.getStatistics().addDraw();
                }
            });
        });
	}

    private void bustCheck(Player player) {
		if (!player.getHands().isEmpty() && Checks.isBust(player.getHand().getValue())) {
			player.clearHand();
			player.getStatistics().addBust();
			outcome.addBust();
			outcome.addLoss();
		}
	}

    private void bustCheck(List<Player> players) {
        players.forEach(this::bustCheck);
    }

	private void clearCards() {
        players.forEach(player -> player.getHands().clear());
        dealer.getHands().clear();
	}

    private void deal(Deal who) {
        switch (who) {
            case PLAYERS:
                players.forEach(p -> p.addCard(deckHandler.getCard()));
                break;
            default:
                players.forEach(p -> p.addCard(deckHandler.getCard()));
                dealer.addCard(deckHandler.getCard());
        }
    }

    private void playHand(Player player) {
        boolean isPlaying = true;
        while (isPlaying) {
            final Choice choice = player.getChoice();
            if (choice == HIT) {
                player.addCard(deckHandler.getCard());
            }
            else if (choice == STAND) {
                isPlaying = false;
                if(!player.isDealer())
                    outcome.addHand();
            }
        }
    }

    private void playHands() {
        players.forEach(this::playHand);
    }

    private void playDealer() {
            final List<Player> playersLeft = players.stream().filter(p -> !p.getHands().isEmpty()).collect(Collectors.toList());
            if(!playersLeft.isEmpty())
                playHand(dealer);
    }

    private void blackJackCheck(List<Player> players) {
        players.forEach(player -> {
            final List<Hand> hands = player.getHands();
            IntStream.range(0, hands.size()).forEach(i -> {
                if(Checks.isBlackJack(hands.get(i))){
                    if(dealer.getHand().getValue() != 10) {
                        player.clearHand(i);
//                        player.getOutcome().addBlackJack();
//                        player.getOutcome().addWin();
                        outcome.addBlackJack();
                        outcome.addWin();
                    } else {
                        player.getStatistics().addBlackJack();
//                        outcome.addBlackJack();
                    }
                }
            });
        });

        if(Checks.isBlackJack(dealer.getHand())){
            outcome.getDealer().addBlackJack();
            outcome.addBlackJack();
        }

    }

    public void run(Deal deal){
        deal(deal);
    }

    public void run(GameState state) {
        switch (state) {
            case GAME:
                run(GameState.CLEAR);
                run(FIRST_DEAL);
                run(GameState.Checks.BLACKJACK);
                run(GameState.PLAYERS);
                run(GameState.DEALER);
                run(GameState.Checks.BUST);
                run(GameState.Checks.WIN);
//                outcome.addRound();
                break;
            case FIRST_DEAL:
                deal(Deal.ALL);
                deal(Deal.PLAYERS);
                break;
            case PLAYERS:
                playHands();
                break;
            case DEALER:
                final List<Player> playersLeft = players.stream().filter(p -> !p.getHands().isEmpty()).collect(Collectors.toList());
                if(!playersLeft.isEmpty())
                    playHand(dealer);
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
                blackJackCheck(players);
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

    public static GameBuilder settings() {
        gameBuilder = new GameBuilder();
        return gameBuilder;
    }

    public static GameBuilder settings(Settings settings) {
        gameBuilder = new GameBuilder(settings);
        return gameBuilder;
    }

    public static Outcome play() {
        gameBuilder = new GameBuilder(new Settings.Builder().build());
        return gameBuilder.play();
    }

    public static class GameBuilder {

        private Settings.Builder builder;
        private  Settings settings;

        public GameBuilder() {
            builder = new Settings.Builder();
        }

        public GameBuilder(Settings settings) {
            this.settings = settings;
        }

        public GameBuilder deck(DeckHandler deckHandler) {
            this.builder.setDeckHandler(deckHandler);
            return this;
        }

        public GameBuilder deck(Deck deck) {
            this.builder.setDeckHandler(new DeckHandler(deck));
            return this;
        }

        public GameBuilder rounds(int rounds) {
            this.builder.setRounds(rounds);
            return this;
        }

        public GameBuilder players(int value) {
            final List<Player> players = IntStream.range(0, value).mapToObj(i -> new Player(NamesUtil.getName())).collect(Collectors.toList());
            this.builder.setPlayers(players);
            return this;
        }

        public Outcome play() {
            if(settings == null)
                settings = this.builder.build();
            return new Game(this.settings.getPlayers(), this.settings.getDeckHandler(), this.settings.getRules(), this.settings.getRounds()).start();
        }
    }

    private Outcome start() {
        IntStream.range(0, rounds).forEach(value -> {
            clearCards();
            deal(Deal.ALL);
            deal(Deal.ALL);
            if(!Checks.isBlackJack(dealer.getHand())){
                playHands();
                bustCheck(players);
                playDealer();
//            bustCheck(dealer);
            }
            blackJackCheck(players);
            winCheck(players);
            outcome.addRound();
        });

        return outcome;
    }

}