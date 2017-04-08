package se.andolf.blackjack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.api.exception.GameException;
import se.andolf.blackjack.brain.DumbBrain;
import se.andolf.blackjack.handler.DeckHandler;
import se.andolf.blackjack.util.Checks;

import java.util.List;

import static se.andolf.blackjack.api.Choice.*;
import static se.andolf.blackjack.api.Deal.*;

public class Game {

    private static final Logger logger = LogManager.getLogger(Game.class);

    private int rounds;

	private List<Player> players;
	private Player dealer;
    private GameStatistic gameStatistic;
    private DeckHandler deckHandler;

	public Game(int rounds, List<Player> players, DeckHandler deckHandler) {
        this.rounds = rounds;
		dealer = new Player("Dealer", new DumbBrain(), true);
		this.deckHandler = deckHandler;
        this.players = players;
        gameStatistic = new GameStatistic();
	}

    protected void init() {
		deal(ALL);
		deal(PLAYERS);
	}

	public void start() throws GameException {
        if(players.size() == 1)
            throw new GameException("Add at least one player before you start a game.");

		int played = 0;
		while (played < rounds) {
			logger.info("");
			logger.info("---- INITIALIZING FIRST DEAL ----");
			init();

			logger.info("");
			logger.info("---- CHECKING BLACKJACKS ----");
			blackJackCheck();
			logger.info("---- BLACKJACK CHECK HAS ENDED ----");

			logger.info("");
			logger.info("---- INITIAL DEAL ENDED, STARTING PLAYER sounds ----");
			startPlayerRounds();
			logger.info("---- PLAYER sounds ENDED ----");

			logger.info("");
			logger.info("---- CHECKING BLACKJACKS ----");
			blackJackCheck();
			logger.info("---- BLACKJACK CHECK HAS ENDED ----");

			logger.info("");
			logger.info("---- INITIAL PLAYER sounds ENDED, STARTING DEALERS ROUND ----");
			startDealerRound();
			logger.info("");
			logger.info("---- COMPARING HANDS ----");
			winCheck();
			logger.info("");
			logger.info("---- GAME OVER RESETTING GAME ----");
			clearCards();
			logger.info("---- GAME RESET ----");
			gameStatistic.addRound();
			played++;
		}
		logger.info("");
		logger.info("---- PRINTING GAME STATISTICS ----");
		gameStatistic.toString();
		logger.info("");
		logger.info("---- PRINTING PLAYER STATISTICS ----");
        players.stream().forEach(player -> logger.info(player.toString()));
    }

	private void blackJackCheck() {

		for (Player player : players) {
			for (int i = 0; i < player.getHands().size(); i++) {

				player.setCurrentHand(i);

				if (Checks.isBlackJack(player.getHand())) {

					logger.info("Player " + player.getName() + " HAS BLACKJACK WIIIHUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU!!!!!!!!");
					logger.info("---- CLEARING PLAYER " + player.getName() + "'s CARDS ----");
					player.clearHand();

                    player.getStatistics().addBlackJack();
                    player.getStatistics().addWin();
				}
			}
		}
	}

	private void winCheck() {

		for (Player player : players) {
			for (int i = 0; i < player.getHands().size(); i++) {

				player.setCurrentHand(i);

				if (Checks.hasWon(player.getHand().getValue(), dealer.getHand().getValue())) {

					logger.info("Player " + player.getName() + " WINS!");

                    player.getStatistics().addWin();

				} else {

					logger.info("Player " + player.getName() + " LOOSES!");

                    player.getStatistics().addLoss();

				}
			}
		}
	}

	private void startPlayerRounds() {
		for (Player player : players) {
			for (int i = 0; i < player.getHands().size(); i++) {
				if (!player.getHands().isEmpty()) {
					player.setCurrentHand(i);
					startPlayingSelectedHand(player);
				}
			}
		}
	}

	private void startPlayingSelectedHand(Player player) {

		boolean playing = true;
		while (playing) {

			final Choice playerChoice = player.getChoice();

			if (playerChoice == HIT) {
				player.addCard(deckHandler.getCard());
				playing = bustCheck(player);
			}
			else if (playerChoice == STAND) {
				playing = false;
			}
			else if (playerChoice == SPLIT) {
				doubleCards(player);
			}
		}
	}

	private void doubleCards(Player player) {
		final Card secondCard = player.getHand().getCards().get(1);
		
		player.addHand(secondCard);
		
		player.removeCardFromCurrentHand(1);
		
		player.addCard(deckHandler.getCard());
		player.setCurrentHand(player.getCurrentHandIndex()+1);
		player.addCard(deckHandler.getCard());
		player.setCurrentHand(player.getCurrentHandIndex()-1);

		gameStatistic.addDouble();
	}

	private boolean bustCheck(Player player) {
		if (Checks.isBust(player.getHand().getValue())) {

			logger.info("Dealer says brain " + player.getName() + " is bust!");
			logger.info("---- CLEARING PLAYER " + player.getName() + "'s CARDS ----");
			player.removeCurrentHand();

			player.getStatistics().addBust();
			player.getStatistics().addLoss();

			return false;
		}
		return true;
	}

	private void startDealerRound() {
		boolean playing = true;
		while (playing) {

			final Choice dealersChoice = dealer.getChoice();

			if (dealersChoice == HIT) {
				logger.info(dealer.getName() + " pulls a card");
				deal(DEALER);
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

	private void clearCards() {
        players.stream().forEach(player -> player.getHands().clear());
	}

    void deal(Deal deal) {
        deal(deal, 1);
    }

    private void deal(Deal who, int cards) {
        switch (who) {
            case DEALER:
                players.stream().filter(Player::isDealer).findFirst().ifPresent(p -> p.addCard(deckHandler.getCard()));
                break;
            case PLAYERS:
                players.stream().filter(p -> !p.isDealer()).forEach(p -> p.addCard(deckHandler.getCard()));
                break;
            default:
                players.stream().forEach(p -> p.addCard(deckHandler.getCard()));
        }
    }

    public Player getPlayer(String id){
        return players.stream().filter(p -> p.getId().equals(id)).distinct().findFirst().orElse(null);
    }

    Player getDealer() {
        return players.stream().filter(Player::isDealer).distinct().findFirst().orElse(null);
    }

    public List<Player> getPlayers() {
        return players;
    }
}