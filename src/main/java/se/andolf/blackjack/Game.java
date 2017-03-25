package se.andolf.blackjack;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.andolf.blackjack.api.*;
import se.andolf.blackjack.brain.SmartBrain;
import se.andolf.blackjack.util.Checks;
import se.andolf.blackjack.statistics.StatisticsHandler;

import static se.andolf.blackjack.api.Choice.HIT;
import static se.andolf.blackjack.api.Choice.SPLIT;
import static se.andolf.blackjack.api.Choice.STAND;

public class Game {

    private static final Logger logger = LogManager.getLogger(Game.class);


	private static final int ROUNDS = 10000;

	private List<Player> playerList = new ArrayList<>();
	private Dealer dealer;
	private Deck deck;
	private StatisticsHandler statisticsHandler;

	public Game() {
		dealer = new Dealer();
		deck = new Deck();
		statisticsHandler = new StatisticsHandler();
	}

	public void initPlayers() {

			final Player player = new Player("Thomas", new SmartBrain());
			statisticsHandler.createPlayerStats(player.getName());
			playerList.add(player);
	}

	private void firstDeal() {
		dealAllPlayersOneCardEach();
		dealDealerOneCard();
		dealAllPlayersOneCardEach();
	}

	private void dealAllPlayersOneCardEach() {
		
		for (Player player : playerList) {
			for (int i = 0; i < 1; i++) {
					player.setCurrentHand(i);
					player.addCard(deck.getCard());
			}
		}
	}

	private void dealDealerOneCard() {
		dealer.reciveCard(deck.getCard());
		logger.info("Dealer has: " + dealer.getCurrentValue());
	}

	public void start() {
		int played = 0;
		while (played < ROUNDS) {
			
			logger.info("");
			logger.info("---- INITIALIZING HANDS ----");
			initHands();
			logger.info("");
			logger.info("---- INITIALIZING FIRST DEAL ----");
			firstDeal();

			logger.info("");
			logger.info("---- CHECKING BLACKJACKS ----");
			checkBlackJacks();
			logger.info("---- BLACKJACK CHECK HAS ENDED ----");

			logger.info("");
			logger.info("---- INITIAL DEAL ENDED, STARTING PLAYER ROUNDS ----");
			startPlayerRounds();
			logger.info("---- PLAYER ROUNDS ENDED ----");

			logger.info("");
			logger.info("---- CHECKING BLACKJACKS ----");
			checkBlackJacks();
			logger.info("---- BLACKJACK CHECK HAS ENDED ----");

			logger.info("");
			logger.info("---- INITIAL PLAYER ROUNDS ENDED, STARTING DEALERS ROUND ----");
			startDealerRound();
			logger.info("");
			logger.info("---- COMPARING HANDS ----");
			compareHands();
			logger.info("");
			logger.info("---- GAME OVER RESETTING GAME ----");
			removeAllCardsOnTable();
			logger.info("---- GAME RESET ----");
			statisticsHandler.addRound();
			played++;
		}
		logger.info("");
		logger.info("---- PRINTING GAME STATISTICS ----");
		statisticsHandler.printGameStatistics();
		logger.info("");
		logger.info("---- PRINTING PLAYER STATISTICS ----");
		statisticsHandler.printPlayerStatistics();
	}

	private void initHands() {
		for (Player player : playerList) {
			for(int i = 0; i < 1; i++){
				player.addHand();
				statisticsHandler.addHand();
			}
		}		
	}

	private void checkBlackJacks() {

		for (Player player : playerList) {
			for (int i = 0; i < player.getHands().size(); i++) {

				player.setCurrentHand(i);

				if (Checks.isBlackJack(player.getHands().get(i))) {

					logger.info("Player " + player.getName() + " HAS BLACKJACK WIIIHUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU!!!!!!!!");
					logger.info("---- CLEARING PLAYER " + player.getName() + "'s CARDS ----");
					player.clearCurrentHand();

					statisticsHandler.addBlackJack(player.getName());
					statisticsHandler.addWin(player.getName());
				}
			}
		}
	}

	private void compareHands() {

		for (Player player : playerList) {
			for (int i = 0; i < player.getHands().size(); i++) {

				player.setCurrentHand(i);

				if (Checks.hasWon(player.getCurrentHand().getValue(), dealer.getCurrentValue())) {

					logger.info("Player " + player.getName() + " WINS!");
					statisticsHandler.addWin(player.getName());

				} else {

					logger.info("Player " + player.getName() + " LOOSES!");
					statisticsHandler.addLoss(player.getName());

				}
			}
		}

	}

	private void startPlayerRounds() {
		for (Player player : playerList) {
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
				player.addCard(deck.getCard());
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
		Card secondCard = player.getCurrentHand().getCards().get(1);
		
		player.addHand(secondCard);
		
		player.removeCardFromCurrentHand(1);
		
		player.addCard(deck.getCard());
		player.setCurrentHand(player.getCurrentHandIndex()+1);
		player.addCard(deck.getCard());
		player.setCurrentHand(player.getCurrentHandIndex()-1);
				
		statisticsHandler.addDouble();
	}

	private boolean bustCheck(Player player) {
		if (Checks.isBust(player.getCurrentHand().getValue())) {

			logger.info("Dealer says brain " + player.getName() + " is bust!");
			logger.info("---- CLEARING PLAYER " + player.getName() + "'s CARDS ----");
			player.removeCurrentHand();
			statisticsHandler.addBusted(player.getName());
			statisticsHandler.addLoss(player.getName());

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
				dealDealerOneCard();
			}

			else if (dealersChoice == STAND) {
				logger.info(dealer.getName() + " says I'LL STAND!");
				playing = false;
			}

			if (Checks.isBust(dealer.getCurrentValue())) {
				logger.info(dealer.getName() + " is bust!");
				playing = false;
			}
		}
	}

	private void removeAllCardsOnTable() {
		for (Player player : playerList) {
			player.getHands().clear();
		}
		dealer.getCards().clear();
	}
}