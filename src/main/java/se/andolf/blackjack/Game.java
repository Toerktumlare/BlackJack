package se.andolf.blackjack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.brain.DumbBrain;
import se.andolf.blackjack.handler.DeckHandler;
import se.andolf.blackjack.util.Checks;

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

    private static BlackJackSetup blackJackSetup;
    private final int rounds;
    private final List<Player> players;
	private final Player dealer;
    private final Statistics statistics;
    private final DeckHandler deckHandler;
    private final Rules rules;

    public Game(List<Player> players, DeckHandler deckHandler, Rules rules, int rounds) {
		dealer = new Player("Dealer", new DumbBrain(), true);
		this.deckHandler = deckHandler;
        this.players = players;
        this.rules = rules;
        statistics = new Statistics(new Results());
        this.rounds = rounds;
	}

	public void winCheck(List<Player> players) {
        players.stream().forEach(p -> {
            p.getHands().stream().forEach(h -> {
                final Rules.Outcome outcome = rules.hasWon(h.getValue(), dealer.getHand().getValue());
                if (outcome == WIN) {
                    p.getStatistics().addWin();
                    statistics.getGame().addWin();
                    dealer.getStatistics().addLoss();

                } else if (outcome == LOSS) {
                    p.getStatistics().addLoss();
                    statistics.getGame().addLoss();
                    dealer.getStatistics().addWin();

                } else if (outcome == DRAW) {
                    p.getStatistics().addDraw();
                    statistics.getGame().addDraw();
                    dealer.getStatistics().addDraw();
                }
            });
        });
	}

    private void bustCheck(Player player) {
		if (!player.getHands().isEmpty() && Checks.isBust(player.getHand().getValue())) {
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
            case PLAYERS:
                players.stream().forEach(p -> p.addCard(deckHandler.getCard()));
                break;
            default:
                players.stream().forEach(p -> p.addCard(deckHandler.getCard()));
                dealer.addCard(deckHandler.getCard());
        }
    }

    void playHand(Player player) {
        if(player.getHands().isEmpty())
            return;
        boolean isPlaying = true;
        while (isPlaying) {
            final Choice choice = player.getChoice();
            if (choice == HIT) {
                player.addCard(deckHandler.getCard());
            }
            else if (choice == STAND) {
                isPlaying = false;
                if(!player.isDealer())
                    statistics.getGame().addHand();
            }
        }
    }

    private void playHands() {
        players.stream().forEach(this::playHand);
    }

    private void playDealer() {
            final List<Player> playersLeft = players.stream().filter(p -> !p.getHands().isEmpty()).collect(Collectors.toList());
            if(!playersLeft.isEmpty())
                playHand(dealer);
    }

    private void blackJackCheck(List<Player> players) {
        players.stream().filter(player -> !player.isDealer()).forEach(player -> {
            final List<Hand> hands = player.getHands();
            IntStream.range(0, hands.size()).forEach(i -> {
                if(Checks.isBlackJack(hands.get(i))){
                    if(dealer.getHand().getValue() != 10) {
                        player.clearHand(i);
//                        player.getStatistics().addBlackJack();
//                        player.getStatistics().addWin();
                        statistics.getGame().addBlackJack();
                        statistics.getGame().addWin();
                    } else {
                        player.getStatistics().addBlackJack();
//                        statistics.addBlackJack();
                    }
                }
            });
        });
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
//                statistics.addRound();
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


    public Statistics getStatistics() {
        return statistics;
    }

    public static BlackJackSetup play() {
        blackJackSetup = new BlackJackSetup();
        return blackJackSetup;
    }

    public static class BlackJackSetup {

        private DeckHandler deckHandler;
        private List<Player> players;
        private Rules rules;
        private int rounds = 1;
        private Game game;

        public BlackJackSetup(DeckHandler deckHandler, Player... players) {
            this.deckHandler = deckHandler;
            this.players = asList(players);
            this.rules = new StandardRules();
        }

        public BlackJackSetup(Player... players) {
            this(new DeckHandler(), players);
        }

        public BlackJackSetup() {
            this(new DeckHandler(), new Player("1"));
        }

        public BlackJackSetup deck(DeckHandler deckHandler) {
            this.deckHandler =  deckHandler;
            return this;
        }

        public BlackJackSetup rounds(int rounds) {
            this.rounds = rounds;
            return this;
        }

        public Statistics then() {
            return new Game(players, deckHandler, rules, rounds).start();
        }

        public Statistics stats() {
            return game.getStatistics();
        }
    }

    private Statistics start() {


        IntStream.range(0, rounds).forEach(value -> {
            clearCards();
            deal(Deal.ALL);
            deal(Deal.ALL);
            blackJackCheck(players);
            playHands();
            playDealer();
//            bustCheck(players);
//            bustCheck(dealer);
            winCheck(players);
            statistics.getGame().addRound();
        });

        return statistics;
    }

}