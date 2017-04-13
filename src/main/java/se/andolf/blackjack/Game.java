package se.andolf.blackjack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.brain.DumbBrain;
import se.andolf.blackjack.handler.DeckHandler;
import se.andolf.blackjack.util.Checks;

import java.util.List;
import java.util.stream.IntStream;

import static se.andolf.blackjack.api.Choice.HIT;
import static se.andolf.blackjack.api.Choice.STAND;
import static se.andolf.blackjack.api.GameState.FIRST_DEAL;
import static se.andolf.blackjack.api.GameState.GAME;

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
            switch (rules.hasWon(player.getHand().getValue(), dealer.getHand().getValue())) {
                case WIN:
                    player.getStatistics().addWin();
                    break;
                case LOSS:
                    player.getStatistics().addLoss();
                    break;
                case DRAW:
                    player.getStatistics().addDraw();
                    break;
            }
        });
	}

	private void bustCheck(Player player) {
		if (Checks.isBust(player.getHand().getValue())) {

			logger.info("Dealer says brain " + player.getName() + " is bust!");
			logger.info("---- CLEARING PLAYER " + player.getName() + "'s CARDS ----");
			player.removeCurrentHand();

			player.getStatistics().addBust();
			player.getStatistics().addLoss();
		}
	}

    private void bustCheck(List<Player> players) {
        players.stream().forEach(this::bustCheck);
    }

	public void startDealerRound() {
		boolean playing = true;
		while (playing) {

			final Choice dealersChoice = dealer.getChoice();

			if (dealersChoice == HIT) {
				logger.info(dealer.getName() + " pulls a card");
				deal(Deal.DEALER);
			}

			else if (dealersChoice == STAND) {
				logger.info(dealer.getName() + " says I'LL STAND!");
				playing = false;
			}

			if (Checks.isBust(dealer.getHand().getValue())) {
				logger.info(dealer.getName() + " is bust!");
				playing = false;
			}
		}
	}

	public void clearCards() {
        players.stream().forEach(player -> player.getHands().clear());
        dealer.getHands().clear();
	}

    void deal(Deal deal) {
        deal(deal, 1);
    }

    private void deal(Deal who, int cards) {
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

    public void play(List<Player> players) {
        players.stream().forEach(this::play);
    }

    public void hasBlackJack(List<Player> players) {
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
                play(players);
                break;
            case DEALER:
                startDealerRound();
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
}